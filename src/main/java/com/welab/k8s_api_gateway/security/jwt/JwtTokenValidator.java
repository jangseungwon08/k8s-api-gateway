package com.welab.k8s_api_gateway.security.jwt;

import com.welab.k8s_api_gateway.security.jwt.authentication.JwtAuthentication;
import com.welab.k8s_api_gateway.security.jwt.authentication.UserPrincipal;
import com.welab.k8s_api_gateway.security.jwt.props.JwtConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
//클라이언트에서 받은 토큰을
public class JwtTokenValidator {
    private final JwtConfigProperties jwtConfigProperties;
    private volatile SecretKey secretKey;

//    키를 가져오는 로직
    private SecretKey getSecretKey(){
        if(secretKey == null){
            synchronized (this){
                if(secretKey == null){
                    secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfigProperties.getSecretKey()));
                }
            }
        }
        return secretKey;
    }
//    토큰 검증하는 메서드 -> validate가 되면 authentication을 리턴하도록
    public JwtAuthentication validateToken(String token){
        String userId = null;
        final Claims claims = this.verifyAndGetClaims(token);
//        null로 리턴하는 이유는 흐름을 스프링 시큐리티한테 맡기려고 하는 것이다.
        if (claims == null){
            return null;
        }
//
        Date expirationDate = claims.getExpiration();
        if(expirationDate == null || expirationDate.before(new Date())){
            return null;
        }
        userId = claims.get("userId",String.class);
        String tokenType = claims.get("tokenType", String.class);
        if(!"access".equals(tokenType)){
            return null;
        }
        UserPrincipal principal = new UserPrincipal(userId);
//        역할도 지정을 해서 validate를 해준다. -> 그 사람이 어떤 권한을 가지고 있는지 체크를 해주는 것
//        인증이 성공하면 role을 user로 주면서 인가를 만들어준다
        return new JwtAuthentication(principal,token, getGranthedAuthorities("user"));
    }
//    전자서명이 바르게 되어있는지 확인
    private Claims verifyAndGetClaims(String token){
        Claims claims;
        try{
            claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            claims = null;
        }
        return  claims;
    }
    private List<GrantedAuthority> getGranthedAuthorities(String role){
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(role != null){
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return grantedAuthorities;
    }
    public String getToken(HttpServletRequest request){
        String authHeader = getAuthHeaderFromHeader(request);
        if(authHeader != null && authHeader.startsWith("Bearer")){
            return authHeader.substring(7);
        }
        return null;
    }
    private String getAuthHeaderFromHeader(HttpServletRequest request){
        return request.getHeader(jwtConfigProperties.getHeader());
    }
}