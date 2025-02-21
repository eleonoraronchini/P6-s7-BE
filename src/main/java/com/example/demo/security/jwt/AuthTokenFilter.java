package com.example.demo.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class AuthTokenFilter {
    @Autowired
    JwtUtils utils;
    @Autowired
    UserDetailsService userDetailsService;

    private String anallizaJwt(HttpServletRequest request){
        String headAuthentication = request.getHeader("Authorization");
        if(StringUtils.hasText(headAuthentication)&&(headAuthentication.startsWith("Bearer "))){
            return headAuthentication.substring(7);
        } return null;
    }
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
      String jwt = anallizaJwt(request);
      if(jwt != null && utils.validazioneJwtToken(jwt)){
          String username = utils.recuperoUsernameDaJwts(jwt);
          UserDetails dettagliUtente = userDetailsService.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken autenticazione = new UsernamePasswordAuthenticationToken(
                  dettagliUtente,
                  null,
                  dettagliUtente.getAuthorities());
          autenticazione.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(autenticazione);

        }
    }
}
