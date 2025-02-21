package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String username;
    private Long id;
    private String email;
    private String ruolo;
    private String token;
    private String type = "Bearer ";

    public JwtResponse(String username, Long id, String email, String ruolo, String token) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.ruolo = ruolo;
        this.token = token;
    }
}
