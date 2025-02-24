package com.example.demo.repository;

import com.example.demo.model.ERuolo;
import com.example.demo.model.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Ruolo findByType (ERuolo type);
}
