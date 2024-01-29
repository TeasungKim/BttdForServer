package com.finalproject.bttd.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTGenerator tokenGenerator;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("not normal process 1: ");


        String token = getJWTFromRequest(request);
//        if(token == null){
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.setContentType("application/json"); // 콘텐츠 유형 설정
//            response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정
//            response.getWriter().write("{\"error\":\"토큰이 없슈! \"}");
//            return;
//        }

        log.info("not normal process 2: " + token);

        if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)){

            if(request.getRequestURI().equals("/api/reissue")) {
                // 토큰 재발급
                String api = request.getRequestURI();
                log.info("refresh token does exist : " + token);

                log.info("refresh Token gerating proccess : 1");

//                SecurityContext context = SecurityContextHolder.createEmptyContext();
//                String userName = tokenGenerator.getUserNameFromJWT(token);
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(userName, 1234));
//                context.setAuthentication(authentication);
//                SecurityContextHolder.setContext(context);
//
            try {
                String username = tokenGenerator.getUserNameFromJWT(token);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e){
                e.printStackTrace();
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.getWriter().write("해당 토큰의 이름값이 다릅니다");
                return;
            }



            }else {
                // 정상 진행
                log.info("normal process 1: ");
                String username = tokenGenerator.getUserNameFromJWT(token);
                log.info("normal process name : " + username);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                log.info("normal process 2: " + userDetails);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                log.info("normal process 3: " + authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }else if(StringUtils.hasText(token) && !tokenGenerator.validateToken(token)){
            // 에러처리
            log.info("fileter 3");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json"); // 콘텐츠 유형 설정
            response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정
            response.getWriter().write("{\"error\":\"유효한 토큰이 없습니다\"}");
            return;


        }

        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        log.info("request 1:" + bearerToken);
//        bearerToken = "bearer " + bearerToken;
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("bearer ")) {
            log.info("request 2: true");
            return bearerToken.substring(7, bearerToken.length());
        }
        log.info("request 3: false");
        return null;
    }


}
