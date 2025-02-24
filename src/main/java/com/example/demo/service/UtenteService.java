package com.example.demo.service;

import com.example.demo.exceptions.EmailDuplicateException;
import com.example.demo.exceptions.UsernameDuplicateException;
import com.example.demo.mapper.UtenteMapper;
import com.example.demo.model.ERuolo;
import com.example.demo.model.Utente;
import com.example.demo.payload.UtenteDTO;
import com.example.demo.payload.request.RegistrazioneRequest;
import com.example.demo.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UtenteService {
    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    UtenteMapper utenteMapper;

    public void checkDuplicateKey(String username, String email) throws UsernameDuplicateException, EmailDuplicateException {
        if (utenteRepository.existsByUsername(username)) {
            throw new UsernameDuplicateException("Username già presente");
        }
        if (utenteRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("Email già presente");
        }
    }


    public String insertUtente(RegistrazioneRequest dto) throws UsernameDuplicateException, EmailDuplicateException {
        checkDuplicateKey(dto.getUsername(), dto.getEmail());
        Utente user = utenteMapper.toEntity(dto);
        Long id = utenteRepository.save(user).getId();
        return "L'utente " + user.getUsername() + " con id " + id + "è stato inserito correttamente";
    }

    public UtenteDTO updateUtente(Utente utente) {
        Optional<Utente> utenteTrovato = utenteRepository.findById(utente.getId());
        Utente user = utenteTrovato.orElseThrow();
        user.setUsername(utente.getUsername());
        user.setNome(utente.getNome());
        user.setCognome(utente.getCognome());
        user.setEmail(utente.getEmail());
        utenteRepository.save(user);
        //non faccio save perchè cè il transactional che lo svolge in automatico
        UtenteDTO userDTO = utenteMapper.toDTO(user);
        return userDTO;
    }

    public String deleteUtente(Utente utente) {
        Optional<Utente> utenteTrovato = utenteRepository.findById(utente.getId());
        Utente user = utenteTrovato.orElseThrow();
        utenteRepository.delete(user);
        return "dipendente" + user.getUsername() + "rimosso con successo";
    }

    public Optional<Utente> getUserByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    public UtenteDTO modificaRuoloByAdmin(Utente utente) {
        Optional<Utente> utenteRicercato = utenteRepository.findById(utente.getId());
        if (utenteRicercato.isPresent()) {
            Utente utenteTrovato = utenteRicercato.get();
            utenteTrovato.setRuolo(utente.getRuolo());
            utenteRepository.save(utenteTrovato);
            UtenteDTO utenteDTO = utenteMapper.toDTO(utenteTrovato);
            return utenteDTO;
        } else {
          throw new RuntimeException("Nessun utente trovato nel database");
        }
    }
}

