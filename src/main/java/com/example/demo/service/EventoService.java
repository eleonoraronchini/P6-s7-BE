package com.example.demo.service;

import com.example.demo.mapper.EventoMapper;
import com.example.demo.model.Evento;
import com.example.demo.payload.EventoDTO;
import com.example.demo.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.mapper.EventoMapper.toDTO;
import static com.example.demo.mapper.EventoMapper.toEntity;

@Service
public class EventoService {

    @Autowired
    EventoMapper eventoMapper;
    @Autowired
    EventoRepository eventoRepository;

    public EventoDTO createEvento(EventoDTO eventoDTO) {
        Evento e = toEntity(eventoDTO);
        eventoRepository.save(e);
        return eventoDTO;
    }

    public EventoDTO getEventoById(Long id) {
        Optional<Evento> e = eventoRepository.findById(id);
        if (e.isPresent()) {
            return toDTO(e.get());
        } else {
            throw new RuntimeException("l'evento con chiave: " + id + " non è presente");
        }
    }
    public List<EventoDTO> getAllEventi() {
        List<Evento> lista = eventoRepository.findAll();
        List<EventoDTO> listaDTO = new ArrayList<>();
        for (Evento evento : lista) {
            listaDTO.add(toDTO(evento));
        }
        return listaDTO;
    }
}
