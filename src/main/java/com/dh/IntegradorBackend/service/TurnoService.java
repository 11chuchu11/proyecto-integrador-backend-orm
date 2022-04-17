package com.dh.IntegradorBackend.service;

import com.dh.IntegradorBackend.entities.Turno;
import com.dh.IntegradorBackend.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository repository;

    public List<Turno> listarTurnos(){
        return repository.findAll();
    }

    public Optional<Turno> buscarTurno(Long id){
        return repository.findById(id);
    }

    public Turno guardarTurno(Turno turno){
        return (Turno) repository.save(turno);
    }

    public Turno actualizarTurno(Turno turno){
        if (buscarTurno(turno.getId()).isPresent()){
            return (Turno) repository.save(turno);
        }
        return null;
    }

    public void eliminarTurno(Long id){
        repository.deleteById(id);
    }

}
