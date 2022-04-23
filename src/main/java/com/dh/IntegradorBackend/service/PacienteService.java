package com.dh.IntegradorBackend.service;

import com.dh.IntegradorBackend.entities.Paciente;
import com.dh.IntegradorBackend.exceptions.BadRequestException;
import com.dh.IntegradorBackend.exceptions.ResourceNotFoundException;
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
        Optional<Paciente> paciente = repository.findById(id);
        return repository.findById(id);
    }

    public Paciente guardarPaciente(Paciente paciente){
        return repository.save(paciente);
    }

    public Paciente actualizarPaciente (Paciente paciente)throws BadRequestException{

        if (buscarPaciente(paciente.getId()).isPresent()){
            return (Paciente) repository.save(paciente);
        }else{
            throw new BadRequestException("No se encontro el paciente, no fue actualizado ");
        }

    }

    /*public Paciente actualizarPaciente (Paciente paciente){

        if (buscarPaciente(paciente.getId()).isPresent()){
            return (Paciente) repository.save(paciente);
        }else{
            return null;
        }

    }*/

    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        Optional<Paciente> paciente = buscarPaciente(id);
        if(paciente.isPresent()){
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("No existe el paciente con el id" + id + ", no se pudo eliminar.");
        }

    }

    public Optional<Paciente> buscarPorEmail(String email) throws ResourceNotFoundException {
        Optional<Paciente> paciente = repository.buscarPacientePorEmail(email);
        if (paciente.isPresent()){
            return paciente;
        }else {
            throw new ResourceNotFoundException("No se encontro el paciente con email " + email );
        }
    }


}
