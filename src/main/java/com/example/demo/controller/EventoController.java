package com.example.demo.controller;

import com.example.demo.payload.EventoDTO;
import com.example.demo.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@EnableMethodSecurity
@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<EventoDTO> getAllEventi() {
        return eventoService.getAllEventi();
    }

    @GetMapping("/findById/{id}")
    public EventoDTO getEvento(@PathVariable Long id) {
        return eventoService.getEventoById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createEvento(@RequestBody EventoDTO eventoDTO) {
        EventoDTO e = eventoService.createEvento(eventoDTO);
        return "Evento aggiunto con successo!";
    }
}

