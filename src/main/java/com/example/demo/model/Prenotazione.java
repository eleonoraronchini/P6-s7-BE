package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity(name = "prenotazioni")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;
    private LocalDate dataRichiesta;
    private String note;
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;
}