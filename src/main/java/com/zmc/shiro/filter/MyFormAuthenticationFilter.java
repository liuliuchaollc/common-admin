package com.zmc.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by zhongmc on 2017/6/22.
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            //如果验证码错误，直接放行，跳转到/  发现没登陆又回到login
        	
        	System.out.println(getUsername(request));
        	System.out.println(getPassword(request));
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
    @Override
    public void afterCompletion(ServletRequest request,
    		ServletResponse response, Exception exception) throws Exception {
    	// TODO Auto-generated method stub
    	super.afterCompletion(request, response, exception);
    }
}
