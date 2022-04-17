package com.dh.IntegradorBackend.service;

import com.dh.IntegradorBackend.entities.Odontologo;
import com.dh.IntegradorBackend.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository repository;

    public List<Odontologo> listarOdontologos(){
        return repository.findAll();
    }

    public Optional<Odontologo> buscarOdontologo(Long id){
        return repository.findById(id);
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return (Odontologo) repository.save(odontologo);
    }

    public Odontologo actualizarODontologo(Odontologo odontologo){
        if (buscarOdontologo(odontologo.getId()).isPresent()){
            return (Odontologo) repository.save(odontologo);
        }
        return null;
    }

    public void eliminarOdontologo(Long id){
        repository.deleteById(id);
    }
}
