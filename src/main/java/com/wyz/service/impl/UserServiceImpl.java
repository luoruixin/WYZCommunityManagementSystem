package com.wyz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyz.common.R;
import com.wyz.dto.*;
import com.wyz.entity.User;
import com.wyz.mapper.UserMapper;
import com.wyz.service.UserService;
import com.wyz.utils.RegexUtils;
import com.wyz.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.wyz.utils.RedisConstants.LOGIN_CODE_KEY;
import static com.wyz.utils.RedisConstants.LOGIN_USER_TTL;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //发送验证码
    @Override
    public R<String> sendCode(String phone, HttpSession session) {
        if(StrUtil.isEmpty(phone)){
            return R.error("手机号不能为空");
        }
        //1.校验手机号
        boolean codeInvalid = RegexUtils.isCodeInvalid(phone);
//        if(codeInvalid){
//            //2.如果不符合，返回错误信息
//            return Result.fail("手机号格式错误");
//        }
        // 3. 符合，生成验证码
        String code = RandomUtil.randomNumbers(6);
//        // 4.保存验证码到session
//        session.setAttribute("code",code);

        //4.保存验证码到redis
        stringRedisTemplate.opsForValue().set("code:"+phone,code,2, TimeUnit.MINUTES);//有效期是两分钟
        //5.发送验证码(假的)
        log.debug("发送短信验证码成功，验证码：{}",code);
        //返回ok
        return R.success("发送成功，请填写您的验证码");
    }

    @Override
    public R<String> loginByPhone(LoginFormDTO loginForm, HttpSession session) {
        if(StrUtil.isEmpty(loginForm.getPhone())||StrUtil.isEmpty(loginForm.getCode())){
            return R.error("手机号或者验证码为空");
        }
        //1.校验手机号和验证码
        String phone = loginForm.getPhone();
        String code = loginForm.getCode();

        if(!verificatePhoneAndCode(phone,code)){ //这里采用反向校验
            //3.不一致，报错
            return R.error("验证码错误或手机号格式错误");
        }
        //4.一致,根据手机号查询用户
        User user = query().eq("phone", phone).one();

        //5.判断用户是否存在
        if(user==null){
            // 6.不存在
            return R.error("该用户不存在，请注册");
        }

        //TODO:7.存在，用户信息到redis
        UserDTO userDTO=new UserDTO();
        //7.1随机生成token，作为登录令牌(token不能使用手机号，不安全)
        String token = UUID.randomUUID().toString(true);
        //7.2将User对象转为HashMap存储
        BeanUtils.copyProperties(user,userDTO);
//        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO,new HashMap<>(),
//                CopyOptions.create()
//                        .setIgnoreNullValue(true)
//                        .setFieldValueEditor((fieldName,fieldValue)->fieldValue.toString()));

        Map<String, String> userMap=new HashMap<>();
        userToMap(userDTO, userMap);

        userMap.put("phone",userDTO.getPhone());

        // 7.3.存储
        stringRedisTemplate.opsForHash().putAll("login:token:"+token,userMap);
        //7.4设置有效期
        stringRedisTemplate.expire("login:token:"+token,LOGIN_USER_TTL,TimeUnit.MINUTES);
        // 8. 返回token

        //这里将token返回给前端后，前端会自动缓存起来
        return R.success(token);
    }

    @Override
    public R<String> loginByNickName(LoginFormDTO loginForm, HttpSession session) {
        if(StrUtil.isEmpty(loginForm.getNickname())||StrUtil.isEmpty(loginForm.getPassword())){
            return R.error("用户名或者密码为空");
        }
        String nickname=loginForm.getNickname();
        String password=loginForm.getPassword();
        //4.一致,根据手机号查询用户
        User user = query().eq("username", nickname).one();

        //5.判断用户是否存在
        if(user==null){
            // 6.不存在
            return R.error("该用户不存在，请注册");
        }
        //判断密码是否正确(加密后再对比)
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return R.error("密码不正确");
        }
        //TODO:7.存在，用户信息到redis
        UserDTO userDTO=new UserDTO();
        //7.1随机生成token，作为登录令牌(token不能使用手机号，不安全)
        String token = UUID.randomUUID().toString(true);
        //7.2将User对象转为HashMap存储
        BeanUtils.copyProperties(user,userDTO);

        Map<String, String> userMap=new HashMap<>();
        userToMap(userDTO, userMap);

        userMap.put("phone",userDTO.getPhone());

        // 7.3.存储
        stringRedisTemplate.opsForHash().putAll("login:token:"+token,userMap);
        //7.4设置有效期
        stringRedisTemplate.expire("login:token:"+token,LOGIN_USER_TTL,TimeUnit.MINUTES);
        // 8. 返回token

        //这里将token返回给前端后，前端会自动缓存起来
        return R.success(token);
    }

    @Override
    public R<String> register(RegisterFormDTO registerForm, HttpSession session) {
        if(StrUtil.isEmpty(registerForm.getPhone())
                ||StrUtil.isEmpty(registerForm.getNickname())
                ||StrUtil.isEmpty(registerForm.getPassword())
                ||StrUtil.isEmpty(registerForm.getCode())){
            return R.error("请将信息补充完整");
        }

        //1.校验手机号和验证码
        String phone = registerForm.getPhone();
        String code = registerForm.getCode();

        if(!verificatePhoneAndCode(phone,code)){ //这里采用反向校验
            //3.不一致，报错
            return R.error("验证码错误或手机号格式错误");
        }
        //4.一致,根据手机号查询用户
        User user = query().eq("phone", phone).one();
        if(query().eq("username",registerForm.getNickname()).one()!=null){
            return R.error("该用户名已经被注册，请更换用户名");
        }

        //用户已经存在
        if(user!=null){
            return R.error("该用户已经存在");
        }else {
            // 6.不存在,注册
            user=new User();
            user.setPhone(registerForm.getPhone());
            user.setNickname(registerForm.getNickname());
            //注意下面要md5加密
            user.setPassword(DigestUtils.md5DigestAsHex(registerForm.getPassword().getBytes()));
            save(user);
            return R.success("注册成功");
        }
    }

    @Override
    public R<String> foundPwd(FoundPasswordFormDTO foundPasswordFormDTO, HttpSession session) {
        if(StrUtil.isEmpty(foundPasswordFormDTO.getPhone())
                ||StrUtil.isEmpty(foundPasswordFormDTO.getCode())
                ||StrUtil.isEmpty(foundPasswordFormDTO.getNewPassword())){
            return R.error("请将信息补充完整");
        }

        //1.校验手机号和验证码
        String phone = foundPasswordFormDTO.getPhone();
        String code = foundPasswordFormDTO.getCode();

        if(!verificatePhoneAndCode(phone,code)){ //这里采用反向校验
            //3.不一致，报错
            return R.error("验证码错误或手机号格式错误");
        }
        //4.一致,根据手机号查询用户
        User user = query().eq("phone", phone).one();

        //用户不存在
        if(user==null){
            return R.error("用户不存在");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(foundPasswordFormDTO.getNewPassword().getBytes()));
        updateById(user);
        return R.success("找回密码成功");
    }

    @Override
    public R<String> updatePwd(UpdatePwdFormDTO updatePwdFormDTO, HttpSession session) {
        if(StrUtil.isEmpty(updatePwdFormDTO.getOldPassword())||StrUtil.isEmpty(updatePwdFormDTO.getNewPassword())){
            return R.error("请将信息补充完整");
        }
        String oldPassword = updatePwdFormDTO.getOldPassword();
        //4.查询用户
        UserDTO userDTO = UserHolder.getUser();
        User user = getById(userDTO.getId());

        //用户不存在
        if(user==null){
            return R.error("用户不存在");
        }
        //判断密码是否正确
        if(!DigestUtils.md5DigestAsHex(oldPassword.getBytes()).equals(user.getPassword())){
            return R.error("原密码输入错误");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(updatePwdFormDTO.getNewPassword().getBytes()));
        updateById(user);
        return R.success("修改密码成功");
    }

    @Override
    public R<String> updatePhone(UpdatePhoneFormDTO updatePhoneFormDTO, HttpSession session) {
        if(StrUtil.isEmpty(updatePhoneFormDTO.getOldPhone())
            ||StrUtil.isEmpty(updatePhoneFormDTO.getNewPhone())
            ||StrUtil.isEmpty(updatePhoneFormDTO.getCode())){
            return R.error("请将信息填完整");
        }
        User user = query().eq("phone", updatePhoneFormDTO.getOldPhone()).one();
        UserDTO user1 = UserHolder.getUser();
        if(user==null|| !Objects.equals(user1.getId(), user.getId())){
            return R.error("您输入的原手机号有误");
        }
        User user2 = query().eq("phone", updatePhoneFormDTO.getNewPhone()).one();
        if(user2!=null){
            return R.error("新手机号已经被绑定");
        }
        user.setPhone(updatePhoneFormDTO.getNewPhone());
        updateById(user);

        return R.success("绑定手机号成功");
    }


    //-----------------------------------工具类-----------------------------------------------

    private boolean verificatePhoneAndCode(String phone,String code){
//        boolean codeInvalid = RegexUtils.isCodeInvalid(phone);
//        if(codeInvalid){
//            //2.如果不符合，返回错误信息
//            return false;
//        }

        //2.校验验证码  从redis中获取
        String cacheCode=stringRedisTemplate.opsForValue().get("code:"+phone);
        //这是前端传过来的code
        if(cacheCode==null ||!cacheCode.equals(code)){ //这里采用反向校验
            //3.不一致，报错
            return false;
        }
        return true;
    }

    //将userDto映射到map的工具类
    private static void userToMap(UserDTO userDTO, Map<String, String> userMap) {
        userMap.put("id", userDTO.getId().toString());
        userMap.put("nickname", userDTO.getNickname());
        userMap.put("name", userDTO.getName());
        if(userDTO.getType()==null){
            userMap.put("type", null);
        }else {
            userMap.put("type", String.valueOf(userDTO.getType()));
        }

        if(userDTO.getExamine()==null){
            userMap.put("examine", null);
        }else {
            userMap.put("examine", String.valueOf(userDTO.getExamine()));
        }

        if(userDTO.getAge()==null){
            userMap.put("age", null);
        }else {
            userMap.put("age", String.valueOf(userDTO.getAge()));
        }

        if(userDTO.getSex()==null){
            userMap.put("sex", null);
        }else {
            userMap.put("sex", String.valueOf(userDTO.getSex()));
        }
    }
}
