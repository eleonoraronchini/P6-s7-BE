package com.example.demo.controller;

import com.example.demo.exceptions.EmailDuplicateException;
import com.example.demo.exceptions.UsernameDuplicateException;
import com.example.demo.mapper.UtenteMapper;
import com.example.demo.model.Utente;
import com.example.demo.payload.UtenteDTO;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.RegistrazioneRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.repository.UtenteRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
@EnableMethodSecurity
@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteService utenteService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UtenteMapper utenteMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registrazioneUtente(@RequestBody @Validated RegistrazioneRequest nuovoUtente, BindingResult validazione) {
        try {
            if (validazione.hasErrors()) {
                String errors = "Problemi nella validazione dati: \n";
                for (ObjectError errore : validazione.getAllErrors()) {
                    errors += errore.getDefaultMessage() + "\n";
                }
                return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
            }
            String messaggio = utenteService.insertUtente(nuovoUtente);
            return new ResponseEntity<>(messaggio, HttpStatus.OK);
        } catch (UsernameDuplicateException e) {
            return new ResponseEntity<>("Username già utilizzato", HttpStatus.CONFLICT);
        } catch (EmailDuplicateException e) {
            return new ResponseEntity<>("email non disponibile, già presente", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest login, BindingResult validazione) {
        // Verifica la validazione dei dati
        if (validazione.hasErrors()) {
            String errors = "Problemi nella validazione dei dati: \n";
            for (ObjectError errore : validazione.getAllErrors()) {
                errors += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            // Recupera l'utente dal servizio
            Optional<Utente> utente = utenteService.getUserByUsername(login.getUsername());

            if (utente.isPresent() && passwordEncoder.matches(login.getPassword(), utente.get().getPassword())) {
                // Autenticazione dell'utente
                UsernamePasswordAuthenticationToken tokenNoAuth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
                Authentication authentication = authenticationManager.authenticate(tokenNoAuth);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Creazione del token JWT
                String token = jwtUtils.creaJwtToken(authentication);

                // Ottieni i dettagli dell'utente
                UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();

                // Crea il ruolo (se esiste) separato da virgole
                String ruoloWeb = details.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", "));

                // Risposta JWT
                JwtResponse responseJwt = new JwtResponse(
                        details.getUsername(),
                        details.getId(),
                        details.getEmail(),
                        ruoloWeb,
                        token
                );
                // Restituisci la risposta con i dettagli dell'utente
                return new ResponseEntity<>(responseJwt, HttpStatus.OK);
            }

            // Se le credenziali non sono corrette
            return new ResponseEntity<>("Credenziali non valide", HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();  // Logga l'errore completo per il debug
            return new ResponseEntity<>("Credenziali non valide", HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/updateRole/{id}")
    public ResponseEntity<?> updateRoleById (@PathVariable Long id, @RequestBody UtenteDTO utenteDTO){
        Utente utente = utenteMapper.toEntity2(utenteDTO);
        utente.setId(id);
        if(utente == null){
            return new ResponseEntity<>("Nessun utente trovato nel database",HttpStatus.NOT_FOUND);
        } else{
            UtenteDTO utDTO = utenteService.modificaRuoloByAdmin(utente);
            return new ResponseEntity<>("Ruolo dell'utente aggiornato con successo", HttpStatus.OK);
        }

    }
    @PatchMapping("/updateUtente/{id}")
    public ResponseEntity<?> updateUtente (@PathVariable Long id, @RequestBody UtenteDTO utenteDTO){
        Utente utente = utenteMapper.toEntity2(utenteDTO);
        utente.setId(id);
        if (utente == null){
            return  new ResponseEntity<>("Nessun utente trovato nel database",HttpStatus.NOT_FOUND);
        } utenteService.updateUtente(utente);
        return new ResponseEntity<>("Utente aggiornato nel database", HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public UtenteDTO getUtente(@PathVariable Long id) {
        return utenteService.getUtenteById(id);
    }

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<UtenteDTO> getAllUtenti() {
        return utenteService.getAllUtenti();
    }


}


