package com.example.demo.payload;

import com.example.demo.model.Evento;
import com.example.demo.model.Utente;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PrenotazioneDTO {
    @NotBlank(message = "il campo evento non può essere vuoto")
    private Evento evento;
    @NotNull(message = "il campo data non può essere vuoto")
    private LocalDate dataRichiesta;
    private String note;
    @NotBlank(message = "il campo utente non può essere vuoto")
    private Utente utente;
}
