package com.dh.IntegradorBackend.service;

import com.dh.IntegradorBackend.entities.Paciente;
import com.dh.IntegradorBackend.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public List<Paciente> listarPacientes(){
        return repository.findAll();
    }

    public Optional<Paciente> buscarPaciente(Long id){
        return repository.findById(id);
    }

    public Paciente guardarPaciente(Paciente paciente){
        return repository.save(paciente);
    }

    public Paciente actualizarPaciente(Paciente paciente){
        if (buscarPaciente(paciente.getId()).isPresent()){
            return (Paciente) repository.save(paciente);
        }
        return null;
    }

    public void eliminarPaciente(Long id){
        repository.deleteById(id);
    }

    public Optional<Paciente> buscarPorEmail(String email){
        return repository.buscarPacientePorEmail(email);
    }
}
