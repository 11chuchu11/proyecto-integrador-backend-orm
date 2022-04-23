package com.dh.IntegradorBackend.service;



import com.dh.IntegradorBackend.entities.Domicilio;
import com.dh.IntegradorBackend.exceptions.BadRequestException;
import com.dh.IntegradorBackend.exceptions.ResourceNotFoundException;
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

    public void eliminarDomicilio(Long id)throws ResourceNotFoundException{
        Optional<Domicilio> domicilio = buscarDomicilio(id);
        if (domicilio.isPresent())
            repository.deleteById(id);
        else
            throw new ResourceNotFoundException("No existe el domicilio con el id" + id + ", no se pudo eliminar.");
    }

    public Domicilio actualizarDomicilio(Domicilio domicilio) throws BadRequestException {
        if (buscarDomicilio(domicilio.getId()).isPresent()){
            return (Domicilio) repository.save(domicilio);
        }
        else {
            throw new BadRequestException("No se encontro el domicilio, no fue actualizado");
        }
    }

    /*public Domicilio actualizarDomicilio(Domicilio domicilio) {
        if (buscarDomicilio(domicilio.getId()).isPresent()){
            return (Domicilio) repository.save(domicilio);
        }
        else {
            return null;
        }
    }*/


}
