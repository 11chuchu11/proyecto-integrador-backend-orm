package com.dh.IntegradorBackend.controller;

import com.dh.IntegradorBackend.entities.Paciente;
import com.dh.IntegradorBackend.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> lista = service.listarPacientes();
        ResponseEntity response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        if (lista != null) {
            response = ResponseEntity.ok(lista);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) {
        Optional<Paciente> pacienteBuscado = service.buscarPaciente(id);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/email")
    public ResponseEntity<Paciente> buscar(@RequestParam String email) {
        Optional<Paciente> paciente = service.buscarPorEmail(email);
        ResponseEntity response = ResponseEntity.notFound().build();
        if (paciente.isPresent()) {
            response = ResponseEntity.ok(paciente.get());
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(service.guardarPaciente(paciente));
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(service.actualizarPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado");
        if (service.buscarPaciente(id).isPresent()) {
            service.eliminarPaciente(id);
            response = ResponseEntity.ok("Paciente eliminado");
        }
        return response;
    }
}
