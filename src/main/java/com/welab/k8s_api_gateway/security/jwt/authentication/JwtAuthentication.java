package com.welab.k8s_api_gateway.security.jwt.authentication;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
//결과적으로는 Authentication을 구현한 것(인증된 최상위 정보)
public class JwtAuthentication extends AbstractAuthenticationToken {
//    인증된 credential
    private final String token;
//    인증된 사용자 정보
    private final UserPrincipal principal;

    public JwtAuthentication(UserPrincipal principal, String token,
//                              제네릭에 ? 에 extends를 하면 GrantedAuthority를 상속 받은 객체가 들어온다는 뜻이다.
                              Collection<? extends GrantedAuthority> authorities)
    {
        super(authorities);
        this.principal = principal;
        this.token = token;
        this.setDetails(principal);
        setAuthenticated(true);
    }

//    항상 인증이 됐을 때만 객체를 구현 해주는 것이기 때문
    @Override
    public boolean isAuthenticated(){
        return true;
    }

    @Override
    public String getCredentials(){
        return token;
    }

    @Override
    public UserPrincipal getPrincipal(){
        return principal;
    }
}
