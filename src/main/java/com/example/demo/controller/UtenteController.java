package com.example.demo.controller;

import com.example.demo.exceptions.EmailDuplicateException;
import com.example.demo.exceptions.UsernameDuplicateException;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.RegistrazioneRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
@Component
@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteService utenteService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<String> registrazioneUtente(@RequestBody @Validated RegistrazioneRequest nuovoUtente, BindingResult validazione) {
        try {
            if (validazione.hasErrors()) {
                String errors = "Problemi nella validazione dati: \n";
                for (ObjectError errore : validazione.getAllErrors()) {
                    errors += errore.getDefaultMessage() + "\n";
                }
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest login, BindingResult validazione){
     if (validazione.hasErrors()){
         String errors = "Problemi nella validazione dei dati: \n";
         for(ObjectError errore : validazione.getAllErrors()){
             errors += errore.getDefaultMessage()+ "\n";
         } return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
     } try {
        UsernamePasswordAuthenticationToken tokenNoAuth = new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());
        Authentication authentication = authenticationManager.authenticate(tokenNoAuth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.creaJwtToken(authentication);
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        String ruoloWeb = String.valueOf(details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        JwtResponse responseJwt = new JwtResponse(
                details.getUsername(),
                details.getId(),
                details.getEmail(),
                ruoloWeb,
                token
        );
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e){
         return new ResponseEntity<>("Credenziali non valide", HttpStatus.UNAUTHORIZED);
    }
  }
}
