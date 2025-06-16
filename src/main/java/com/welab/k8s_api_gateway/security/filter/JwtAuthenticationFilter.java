package com.welab.k8s_api_gateway.security.filter;

import com.welab.k8s_api_gateway.security.jwt.JwtTokenValidator;
import com.welab.k8s_api_gateway.security.jwt.authentication.JwtAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        String jwtToken = jwtTokenValidator.getToken(request);
        if(jwtToken != null){
            JwtAuthentication jwtAuthentication = jwtTokenValidator.validateToken(jwtToken);
//            jwtAuthentication이 null이 아니면 인증이 된 토큰이라는 뜻이다.
            if(jwtAuthentication != null){
//                스프링 시큐리티의 컨텍스트 홀더에 jwtAuthentication을 넣어준다.
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
