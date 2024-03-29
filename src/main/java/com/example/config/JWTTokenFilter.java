package com.example.config;

import com.example.dto.JwtDTO;
import com.example.exceptions.UnAuthorizedException;
import com.example.service.CustomUserDetailsService;
import com.example.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {
    @Autowired

    private CustomUserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        AntPathMatcher pathMatcher=new AntPathMatcher();
        return Arrays.stream(SpringSecurityConfig.AUTH_WHITELIST)
                .anyMatch(p->pathMatcher.match(p,request.getServletPath()));
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message","Token not found");
            return;
        }
        String token=authHeader.substring(7);
        JwtDTO jwtDTO;
        try {
            jwtDTO= JWTUtil.decode(token);
        }catch (UnAuthorizedException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("message",e.getMessage());
            return;
        }
        UserDetails userDetails=userDetailsService.loadUserByUsername(jwtDTO.getEmail());
        UsernamePasswordAuthenticationToken authentication=
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
