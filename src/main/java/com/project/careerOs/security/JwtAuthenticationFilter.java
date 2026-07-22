package com.project.careerOs.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            


                String authHeader = request.getHeader("Authorization");
                System.out.println("Header: "+authHeader);
                if(authHeader==null || !authHeader.startsWith("Bearer")){
                    filterChain.doFilter(request, response);
                    return;
                }
                String token = authHeader.substring(7);
                System.out.println("Token: "+token);
                System.out.println("isValid: "+jwtUtil.validateToken(token));
                if(jwtUtil.validateToken(token)){
                    String userEmail = jwtUtil.extractEmail(token);
                    System.out.println("userEmail: "+userEmail);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEmail, null,List.of());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(SecurityContextHolder.getContext().getAuthentication());
                }
                filterChain.doFilter(request, response);


    }
    
}
