package com.example.demo.security.services;

import com.example.demo.model.Utente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Component
@Data
@NoArgsConstructor
@NotBlank
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
   private Collection<? extends GrantedAuthority> ruoli;

    public UserDetailsImpl(long id, String username, String email, String password, List<GrantedAuthority> ruoliUtente) {
    }

    public static UserDetailsImpl costruisciDettagli (Utente user){
        List<GrantedAuthority> ruoliUtente = Collections.singletonList(new SimpleGrantedAuthority(user.getRuolo().getType().name()));
   return new UserDetailsImpl(
           user.getId(),
           user.getUsername(),
           user.getEmail(),
           user.getPassword(),
           ruoliUtente);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
