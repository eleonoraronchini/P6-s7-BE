package com.example.demo.service;

import com.example.demo.model.ERuolo;
import com.example.demo.model.Ruolo;
import com.example.demo.repository.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {

    @Autowired
    private RuoloRepository ruoloRepository;

    public Ruolo creaRuolo(ERuolo type) {
        // Verifica se il ruolo esiste già
        Ruolo ruoloEsistente = ruoloRepository.findByType(type);
        if (ruoloEsistente != null) {
            throw new RuntimeException("Ruolo già esistente: " + type);
        }

        // Crea un nuovo ruolo
        Ruolo nuovoRuolo = new Ruolo();
        nuovoRuolo.setType(type);
        return ruoloRepository.save(nuovoRuolo);
    }
}
