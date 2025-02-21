package com.example.demo.repository;

import com.example.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    public <Optional> java.util.Optional<Utente> findByUsername (String username);

    // check duplicate key
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);


}
