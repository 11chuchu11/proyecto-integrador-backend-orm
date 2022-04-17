package com.dh.IntegradorBackend.controller;

import com.dh.IntegradorBackend.entities.Turno;
import com.dh.IntegradorBackend.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService service;

    @GetMapping
    public ResponseEntity<List<Turno>> listar(){
        List<Turno> lista = service.listarTurnos();
        ResponseEntity response = ResponseEntity.status(HttpStatus.NO_CONTENT).build() ;
        if ( lista!= null) {
            response = ResponseEntity.ok(lista);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscar(@PathVariable Long id){
        Optional<Turno> turno = service.buscarTurno(id);
        ResponseEntity response = ResponseEntity.notFound().build();
        if (turno.isPresent()){
            response = ResponseEntity.ok(turno);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno){
        return ResponseEntity.ok(service.guardarTurno(turno));
    }

    @PutMapping
    public ResponseEntity<Turno> actualizar(@RequestBody Turno turno){
        return ResponseEntity.ok(service.actualizarTurno(turno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado");
        if (service.buscarTurno(id).isPresent()){
            service.eliminarTurno(id);
            response = ResponseEntity.ok("Turno eliminado");
        }
        return response;
    }
}
