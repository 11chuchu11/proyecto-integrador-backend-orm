package com.dh.IntegradorBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("<h1>Ingresaste al sistema</h1>");

    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("<h1> Bienvenido administrador. </h1>");
    }
}
