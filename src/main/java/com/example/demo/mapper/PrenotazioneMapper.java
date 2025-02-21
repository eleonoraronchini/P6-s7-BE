package com.example.demo.mapper;

import com.example.demo.model.Prenotazione;
import com.example.demo.payload.PrenotazioneDTO;
import org.springframework.stereotype.Component;

@Component
public class PrenotazioneMapper {
    public static PrenotazioneDTO toDTO (Prenotazione prenotazione){
        PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
        prenotazioneDTO.setEvento(prenotazione.getEvento());
        prenotazioneDTO.setUtente(prenotazione.getUtente());
        prenotazioneDTO.setDataRichiesta(prenotazione.getDataRichiesta());
        prenotazioneDTO.setNote(prenotazione.getNote());
        return prenotazioneDTO;
    }

    public static Prenotazione toEntity (PrenotazioneDTO prenotazioneDTO){
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(prenotazioneDTO.getEvento());
        prenotazione.setUtente(prenotazioneDTO.getUtente());
        prenotazione.setDataRichiesta(prenotazioneDTO.getDataRichiesta());
        prenotazione.setNote(prenotazioneDTO.getNote());
        return  prenotazione;
    }
}
