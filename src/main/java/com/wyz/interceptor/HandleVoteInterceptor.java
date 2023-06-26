package com.wyz.interceptor;

import com.wyz.common.UserHolder;
import com.wyz.dto.UserDTO;
import com.wyz.utils.PermissionJudge;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandleVoteInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDTO userDTO = UserHolder.getUser();
        Integer type = userDTO.getType();
        Integer examine = userDTO.getExamine();
        if(PermissionJudge.judgeAuthority(type,examine)<3)
        {
            //此时能进行投票操作
            response.setStatus(401);
            return false;
        }
        //有更高级别的权限，则放行
        return true;
    }
}
