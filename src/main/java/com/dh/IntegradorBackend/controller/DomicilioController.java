package com.dh.IntegradorBackend.controller;

import com.dh.IntegradorBackend.entities.Domicilio;
import com.dh.IntegradorBackend.entities.Paciente;
import com.dh.IntegradorBackend.service.DomicilioService;
import com.dh.IntegradorBackend.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {
    @Autowired
    private DomicilioService service;

    @GetMapping
    public ResponseEntity<List<Domicilio>> listar(){
        List<Domicilio> lista = service.listarDomicilios();
        ResponseEntity response = ResponseEntity.status(HttpStatus.NO_CONTENT).build() ;
        if ( lista!= null) {
            response = ResponseEntity.ok(lista);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> buscarDomicilio(@PathVariable Long id){
        Optional<Domicilio> domicilio=service.buscarDomicilio(id);
        ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if(domicilio.isPresent()){
            response = ResponseEntity.ok(domicilio.get());
        }

            return response;

    }


    @PostMapping
    public ResponseEntity<Domicilio> guardar(@RequestBody Domicilio domicilio) {
        return ResponseEntity.ok(service.registrarDomicilio(domicilio));
    }

    @PutMapping
    public ResponseEntity<Domicilio> actualizar(@RequestBody Domicilio domicilio){
        return ResponseEntity.ok(service.actualizarDomicilio(domicilio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado");
        if (service.buscarDomicilio(id).isPresent()){
            service.eliminarDomicilio(id);
            response = ResponseEntity.ok("Paciente eliminado");
        }
        return response;
    }

}
