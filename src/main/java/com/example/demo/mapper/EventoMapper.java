package com.example.demo.mapper;

import com.example.demo.model.Evento;
import com.example.demo.payload.EventoDTO;
import org.springframework.stereotype.Component;

@Component
public class EventoMapper {
    public static EventoDTO toDTO(Evento evento) {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setTitolo(evento.getTitolo());
        eventoDTO.setDescrizione(evento.getDescrizione());
        eventoDTO.setData(evento.getData());
        eventoDTO.setLuogo(evento.getLuogo());
        eventoDTO.setPostiDisponibili(evento.getPostiDisponibili());
        return  eventoDTO;
    }

    public static Evento toEntity(EventoDTO eventoDTO) {
       Evento evento = new Evento();
       evento.setTitolo(eventoDTO.getTitolo());
       evento.setDescrizione(eventoDTO.getDescrizione());
       evento.setData(eventoDTO.getData());
       evento.setLuogo(eventoDTO.getLuogo());
       evento.setPostiDisponibili(eventoDTO.getPostiDisponibili());
       return evento;
    }

}
