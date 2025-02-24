package com.example.demo.payload.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrazioneRequest {
    @NotBlank
    @Size(min =3, max=15)
    private String username;
    @NotBlank
    @Size(min =3,max =20)
    private String password;
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    @NotBlank
    @Email
    private String email;
    private String ruolo;
}
