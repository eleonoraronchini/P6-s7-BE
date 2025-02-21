package com.example.demo.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDTO {
    @NotBlank(message = "il campo titolo non può essere vuoto")
    private String titolo;
    private String descrizione;
    @NotNull(message = "il campo data non può essere vuoto")
    private LocalDate data;
    @NotBlank(message = "il campo luogo non può essere vuoto")
    private String luogo;
    @NotNull (message = "il campo posti dipsonibili non puo essere vuoto")
    private int postiDisponibili;
}
