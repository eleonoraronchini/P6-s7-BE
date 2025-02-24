package com.example.demo.controller;

import com.example.demo.model.ERuolo;
import com.example.demo.model.Ruolo;
import com.example.demo.service.RuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ruoli")
public class RuoloController {

    @Autowired
    private RuoloService ruoloService;

    @PostMapping("/crea")
    @ResponseStatus(HttpStatus.CREATED)
    public Ruolo creaRuolo(@RequestParam ERuolo type) {
        return ruoloService.creaRuolo(type);
    }
}
