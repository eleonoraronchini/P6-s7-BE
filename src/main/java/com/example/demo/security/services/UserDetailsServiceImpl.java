package com.example.demo.security.services;

import com.example.demo.model.Utente;
import com.example.demo.repository.UtenteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UtenteRepository utenteRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> utente = utenteRepository.findByUsername(username);
        Utente user = utente.orElseThrow();
        return UserDetailsImpl.costruisciDettagli(user);
    }
}
