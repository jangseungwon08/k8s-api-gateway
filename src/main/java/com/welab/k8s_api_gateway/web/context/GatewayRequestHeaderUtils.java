package com.welab.k8s_api_gateway.web.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GatewayRequestHeaderUtils {
    public static String getRequestHeaderParamAsString(String key){
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getHeader(key);
    }

    public static String getUserId(){
        String userId = getRequestHeaderParamAsString("X-Auth-UserId");
        if(userId == null){
            return null;
        }
        return userId;
    }

}
