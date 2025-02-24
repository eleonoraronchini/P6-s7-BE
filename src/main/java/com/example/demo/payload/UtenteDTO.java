package com.example.demo.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteDTO {
    @NotBlank(message = "il campo username è obbligatorio")
    private String username;
    @NotBlank(message = "il campo nome è obbligatorio")
    private String nome;
    @NotBlank(message = "il campo cognome è obbligatorio")
    private String cognome;
    @NotBlank(message = "il campo password è obbligatorio")
    private String password;
    @NotBlank(message = "il campo email è obbligatorio")
    @Email(message = "indirizzo email non valido")
    private String email;
    @NotBlank(message = "il campo ruolo è obbligatorio")
    private String ruolo;
}
