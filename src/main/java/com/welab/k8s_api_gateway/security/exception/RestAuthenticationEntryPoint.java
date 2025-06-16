package com.welab.k8s_api_gateway.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
//인증이 안됐을 때 처리하는 코드
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException{
        Exception exception = authException;
        Throwable cause = authException.getCause();
        if(cause instanceof ResourceAccessException accessException){
            exception = accessException;
        }
        handlerExceptionResolver.resolveException(request, response,null,exception);
    }
}
