package com.example.demo.mapper;

import com.example.demo.model.ERuolo;
import com.example.demo.model.Utente;
import com.example.demo.payload.UtenteDTO;
import com.example.demo.payload.request.RegistrazioneRequest;
import com.example.demo.repository.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtenteMapper {
    @Autowired
    RuoloRepository ruoloRepository;
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

    public Utente toEntity (RegistrazioneRequest utenteRequestDTO) {
        Utente user = new Utente();
        user.setUsername(utenteRequestDTO.getUsername());
        user.setNome(utenteRequestDTO.getNome());
        user.setCognome(utenteRequestDTO.getCognome());
        user.setEmail(utenteRequestDTO.getEmail());
        user.setPassword(utenteRequestDTO.getPassword());
        user.setRuolo(ruoloRepository.findByType(ERuolo.valueOf(utenteRequestDTO.getRuolo())));
        return user;
    }
}
