package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.*;
import com.wyz.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    R<String> sendCode(String phone, HttpSession session);

    R<String> loginByPhone(LoginFormDTO loginForm, HttpSession session);

    R<String> register(RegisterFormDTO registerForm, HttpSession session);

    R<String> loginByNickName(LoginFormDTO loginForm, HttpSession session);

    R<String> foundPwd(FoundPasswordFormDTO foundPasswordFormDTO, HttpSession session);

    R<String> updatePwd(UpdatePwdFormDTO updatePwdFormDTO, HttpSession session);

    R<String> updatePhone(UpdatePhoneFormDTO updatePhoneFormDTO, HttpSession session);

    R<String> realNameIdentify(HttpServletRequest request, RealNameFormDTO realNameFormDTO, HttpSession session);

    R<String> writeOff();

    R<String> judgeResidentEntrance();

    R<String> judgeOtherPermissions();

    R<String> judgeVotePermissions();
}
