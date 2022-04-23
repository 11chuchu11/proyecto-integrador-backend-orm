package com.dh.IntegradorBackend.service;

import com.dh.IntegradorBackend.entities.Odontologo;
import com.dh.IntegradorBackend.exceptions.BadRequestException;
import com.dh.IntegradorBackend.exceptions.ResourceNotFoundException;
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

    public Odontologo actualizarODontologo(Odontologo odontologo) throws BadRequestException {
        if (buscarOdontologo(odontologo.getId()).isPresent()){
            return (Odontologo) repository.save(odontologo);
        }
        else {
            throw new BadRequestException("No se encontro el odontologo, no fue actualizado");
        }
    }


    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = buscarOdontologo(id);
        if (odontologo.isPresent())
            repository.deleteById(id);
        else
            throw new ResourceNotFoundException("No existe el paciente con el id " + id + ", no se pudo eliminar.");
    }
}
