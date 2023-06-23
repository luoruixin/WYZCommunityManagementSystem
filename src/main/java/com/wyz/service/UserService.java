package com.wyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyz.common.R;
import com.wyz.dto.FoundPasswordFormDTO;
import com.wyz.dto.LoginFormDTO;
import com.wyz.dto.RegisterFormDTO;
import com.wyz.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    R<String> sendCode(String phone, HttpSession session);

    R<String> loginByPhone(LoginFormDTO loginForm, HttpSession session);

    R<String> register(RegisterFormDTO registerForm, HttpSession session);

    R<String> loginByNickName(LoginFormDTO loginForm, HttpSession session);

    R<String> foundPwd(FoundPasswordFormDTO foundPasswordFormDTO, HttpSession session);
}
