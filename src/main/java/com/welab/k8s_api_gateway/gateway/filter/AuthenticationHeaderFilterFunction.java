package com.welab.k8s_api_gateway.gateway.filter;

import com.welab.k8s_api_gateway.security.jwt.authentication.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

public class AuthenticationHeaderFilterFunction {
    public static Function<ServerRequest, ServerRequest> addHeader(){
//        Server Request는 클라이언트에서 온 reqeust이다.
//        그래서 헤더에 사용자 인증 정보를 넣어주는 작업을 할 것이다.
        return request -> {
            ServerRequest.Builder requestBuilder = ServerRequest.from(request);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof UserPrincipal userPrincipal){
                requestBuilder.header("X-Auth-UserId", userPrincipal.getUserId());
//                다른 Claims 들도...
            }
//            Client Id, Device Type등
            String remoteAddr = "70.1.23.15";
            requestBuilder.header("X-Client-Address",remoteAddr);
            String device = "WEB";
            requestBuilder.header("X-Client-Device",device);
            return requestBuilder.build();
        };
    }
}
