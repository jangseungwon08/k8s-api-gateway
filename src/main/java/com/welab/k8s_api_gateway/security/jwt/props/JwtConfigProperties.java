package com.welab.k8s_api_gateway.security.jwt.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//yml파일이나 properites파일에 있는 속성들을 자바 객체에 직접 연결하는 방법
@ConfigurationProperties(value = "jwt", ignoreUnknownFields = true)
@Getter
@Setter
public class JwtConfigProperties {
    private String header;
    private String secretKey;
}
