package com.example.board2.filter;

import com.example.board2.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            if (token != null && !token.equalsIgnoreCase("null")) {
                //토큰 검증해서 payload의 userEmail 가져옴
                String userEmail = tokenProvider.validate(token);

                // SecurityContext에 추가할 객체
                AbstractAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(userEmail, null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 AbstractAuthenticationToken 객체를 추가해서 해당 thread가 지속적으로 인증정보를 가질 수 있도록 해줌
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); //인터페이스라서 바로 못만드니까 이렇게 빈 컨텍스트 만들고
                securityContext.setAuthentication(authentication);// SecurityContext에 AbstractAuthenticationToken 객체를 추가
                SecurityContextHolder.setContext(securityContext);// AbstractAuthenticationToken 객체가 추가된 컨텍스트를 홀더에 넣어야 지속적으로 정보를 유지하게 됨
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }


    // request header 의 authorization 필드의 bearer token을 가져오는 메서드
    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) //bearerToken 이라는 텍스트가 포함되어 있고(null,빈값,공백체크), "Bearer " 로 시작되면.
            return bearerToken.substring(7);
        return null;
    }
}
