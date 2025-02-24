package com.example.demo.mapper;

import com.example.demo.model.ERuolo;
import com.example.demo.model.Ruolo;
import com.example.demo.model.Utente;
import com.example.demo.payload.UtenteDTO;
import com.example.demo.payload.request.RegistrazioneRequest;
import com.example.demo.repository.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UtenteMapper {
    @Autowired
    RuoloRepository ruoloRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UtenteDTO toDTO(Utente utente) {
        UtenteDTO userDTO = new UtenteDTO();
        userDTO.setUsername(utente.getUsername());
        userDTO.setNome(utente.getNome());
        userDTO.setCognome(utente.getCognome());
        userDTO.setPassword(utente.getPassword());
        userDTO.setEmail(utente.getEmail());
        userDTO.setRuolo(String.valueOf(utente.getRuolo().getType()));
        return userDTO;
    }
    public Utente toEntity2 (UtenteDTO utenteDTO) {
        Utente user = new Utente();
        user.setUsername(utenteDTO.getUsername());
        user.setNome(utenteDTO.getNome());
        user.setCognome(utenteDTO.getCognome());
        user.setEmail(utenteDTO.getEmail());
        user.setPassword(utenteDTO.getPassword());
        user.setRuolo(ruoloRepository.findByType(ERuolo.valueOf(utenteDTO.getRuolo())));
       return  user;
    }

        public Utente toEntity (RegistrazioneRequest utenteRequestDTO) {
        Utente user = new Utente();
        user.setUsername(utenteRequestDTO.getUsername());
        user.setNome(utenteRequestDTO.getNome());
        user.setCognome(utenteRequestDTO.getCognome());
        user.setEmail(utenteRequestDTO.getEmail());
        user.setPassword(utenteRequestDTO.getPassword());
        user.setRuolo(ruoloRepository.findByType(ERuolo.valueOf(utenteRequestDTO.getRuolo())));


    // Hash password
        user.setPassword(passwordEncoder.encode(utenteRequestDTO.getPassword()));

    // String to Enum
    String ruoloString = utenteRequestDTO.getRuolo().trim().toUpperCase();

        try {
        ERuolo ruoloEnum = ERuolo.valueOf(ruoloString);
        Ruolo ruolo = ruoloRepository.findByType(ruoloEnum);
        if(ruolo == null ){
            throw new RuntimeException("Ruolo non trovato nel database" + ruoloEnum);
        } else user.setRuolo(ruolo);
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Valore di ruolo non valido: " + ruoloString);
    }

        return user;
}
}
