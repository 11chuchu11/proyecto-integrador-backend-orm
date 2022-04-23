package com.dh.IntegradorBackend.controller;

import com.dh.IntegradorBackend.entities.Odontologo;
import com.dh.IntegradorBackend.exceptions.BadRequestException;
import com.dh.IntegradorBackend.exceptions.ResourceNotFoundException;
import com.dh.IntegradorBackend.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService service;

    @GetMapping
    public ResponseEntity<List<Odontologo>> listar(){
        List<Odontologo> lista = service.listarOdontologos();
        ResponseEntity response = ResponseEntity.status(HttpStatus.NO_CONTENT).build() ;
        if ( lista!= null) {
            response = ResponseEntity.ok(lista);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscar(@PathVariable Long id){
        Optional<Odontologo> odontologo = service.buscarOdontologo(id);
        ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if (odontologo.isPresent()){
            response = ResponseEntity.ok(odontologo);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(service.guardarOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) throws BadRequestException {
        return ResponseEntity.ok(service.actualizarODontologo(odontologo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        service.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontologo Eliminado");
    }
}
