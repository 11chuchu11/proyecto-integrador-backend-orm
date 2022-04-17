package com.dh.IntegradorBackend.service;



import com.dh.IntegradorBackend.entities.Domicilio;
import com.dh.IntegradorBackend.repository.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService {
    @Autowired
    private DomicilioRepository repository;

    public Optional<Domicilio> buscarDomicilio(Long id){
        return repository.findById(id);
    }

    public List<Domicilio> listarDomicilios(){
        return repository.findAll();
    }

    public Domicilio registrarDomicilio(Domicilio domicilio){
        return (Domicilio) repository.save(domicilio);
    }

    public void eliminarDomicilio(Long id){
        repository.deleteById(id);
    }

    public Domicilio actualizarDomicilio(Domicilio domicilio){
        if (buscarDomicilio(domicilio.getId()).isPresent()){
            return (Domicilio) repository.save(domicilio);
        }
        return null;
    }


}
