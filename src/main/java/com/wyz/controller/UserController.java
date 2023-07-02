package com.wyz.controller;

import com.wyz.common.R;
import com.wyz.dto.*;
import com.wyz.service.UserService;
import com.wyz.common.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     */
    @PostMapping("/code")
    public R<String> sendCode(@RequestParam("phone") String phone, HttpSession session) {

        return userService.sendCode(phone,session);

    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/loginByPhone")
    public R<String> loginByPhone(@RequestBody LoginFormDTO loginForm, HttpSession session){
        try {
            return userService.loginByPhone(loginForm,session);
        }
        catch (Exception e){
            e.printStackTrace();
            return R.error("登录失败");
        }
    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/loginByNickName")
    public R<String> loginByNickName(@RequestBody LoginFormDTO loginForm, HttpSession session){
        try {
            return userService.loginByNickName(loginForm,session);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("登录失败");
        }
    }

    /**
     * 登出功能
     */
    @PostMapping("/loginOut")
    public R<String> loginOut(){
        try {
            UserHolder.removeUser();
            return R.success("登出成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return R.error("登录失败");
        }
    }


    //查询自己的信息
    @GetMapping("/me")
    public R<UserDTO> me(){
        try {
            UserDTO user = UserHolder.getUser();
            user.setPhone("*******"+user.getPhone().substring(user.getPhone().length() - 4));
            user.setIdCard("******************");
            return R.success(user);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("请先登录，请重试");
        }

    }

    //注册功能
    @PostMapping("/register")
    public R<String> register(@RequestBody RegisterFormDTO registerForm, HttpSession session){
        try {
            return userService.register(registerForm,session);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("注册失败");
        }
    }

    //找回密码
    @PutMapping("/foundPwd")
    public R<String> foundPwd(@RequestBody FoundPasswordFormDTO foundPasswordFormDTO, HttpSession session){
        try {
            return userService.foundPwd(foundPasswordFormDTO,session);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("找回密码失败");
        }
    }

    //修改密码
    @PutMapping("/updatePwd")
    public R<String> updatePwd(@RequestBody UpdatePwdFormDTO updatePwdFormDTO, HttpSession session){
        try {
            return userService.updatePwd(updatePwdFormDTO,session);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("修改密码失败");
        }
    }

    //修改绑定的手机号
    @PutMapping("/updatePhone")
    public R<String> updatePhone(@RequestBody UpdatePhoneFormDTO updatePhoneFormDTO, HttpSession session){
        try {
            return userService.updatePhone(updatePhoneFormDTO,session);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("修改绑定手机号失败");
        }
    }

    //实名认证
    @PostMapping("/realNameIdentify")
    public R<String> realNameIdentify(HttpServletRequest request, @RequestBody RealNameFormDTO realNameFormDTO, HttpSession session){
        try {
            return userService.realNameIdentify(request,realNameFormDTO,session);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("实名认证失败");
        }
    }

    //注销功能
    @DeleteMapping("/writeOff")
    public R<String> writeOff(){
        try {
            return userService.writeOff();
        }catch (Exception e){
            e.printStackTrace();
            return R.error("注销失败");
        }
    }

    //判断是否有进入居民入口的权限
    @PostMapping("/judgeResidentEntrance")
    public R<String> judgeResidentEntrance(){
        try {
            return userService.judgeResidentEntrance();
        }catch (Exception e){
            e.printStackTrace();
            return R.error("进入失败");
        }
    }

    //判断是否有除了投票之外的所有权限
    @PostMapping("/judgeOtherPermissions")
    public R<String> judgeOtherPermissions(){

        return userService.judgeOtherPermissions();

    }

    //判断是否有除了投票之外的所有权限
    @PostMapping("/judgeVotePermissions")
    public R<String> judgeVotePermissions(){

        return userService.judgeVotePermissions();

    }
}
