package com.wyz.filter;

import com.alibaba.fastjson.JSON;
import com.wyz.common.BaseContext_ThreadLocalHandler;
import com.wyz.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检测用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")  //所有的请求都会被拦截
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)  servletRequest; //先强转一下
        HttpServletResponse response=(HttpServletResponse)  servletResponse; //先强转一下

        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();

        log.info("拦截到请求：{}",requestURI);

        //1.1、设定需要放行的请求(无需登录的请求)
        String[] urls=new String[]{
            "/employee/login",  //登录和退出的请求需要放行
                "/employee/logout",
                "/backend/**",  //静态资源需要放行
                "/front/**",
                "/common/**",
                "/user/sendMsg",//移动端发送短信
                "/user/login", //移动端登录
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs",
        };
        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行
        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);//放行
            return;
        }

        //4-1、判断登录状态，如果已登录，则直接放行(看session里面有没有数据)
        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));

            //设置threadlocal中的线程共享id
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext_ThreadLocalHandler.setCurrentId(empId);

            filterChain.doFilter(request,response);//放行
            return;
        }

        //4-2、判断移动端用户登录状态，如果已登录，则直接放行(看session里面有没有数据)
        if(request.getSession().getAttribute("user")!=null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            //设置threadlocal中的线程共享id
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext_ThreadLocalHandler.setCurrentId(userId);

            filterChain.doFilter(request,response);//放行
            return;
        }

        log.info("用户未登录");
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        //注意要将R对象转为json字符串
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


        //JSON.parseObject 是将Json字符串转化为相应的对象；
        //
        //JSON.toJSONString 是将对象转化为Json字符串
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);  //看路径是否可以匹配上
            if(match){
                return true;
            }
        }
        return false;
    }
}
