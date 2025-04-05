package com.example.plog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        try{
            String token = parseBearerToken(request);
            log.info("token: {}", token);
            //token 검사
            if(token != null && !token.equalsIgnoreCase("null")){
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("userId: {}", userId);

                // 추출한 userId 를 이용해서 인증객체 생성
                /*
                * AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(주체, 자격증명, 권한정보);
                * 권한정보: 해당 userId를 가진 user 의 권한 정보 (admin, user, .. ), 현재는 권한X
                * */
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // SecurityContext: 현재 사용자의 보안 컨텍스트(SecurityContext)를 저장하고 관리하는 전역 저장소
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication); 
                SecurityContextHolder.setContext(securityContext); // 생성한 보안 컨텍스트를 현재 스레드에 등록

                log.info("➡️ [JwtFilter] SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication());    
            }else{
                log.info("token is null");
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                log.info("Current authentication: {}", auth);
            }

        }catch(Exception ex){
            throw new RuntimeException("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }
    
    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
