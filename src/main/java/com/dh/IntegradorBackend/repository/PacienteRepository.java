package com.dh.IntegradorBackend.repository;

import com.dh.IntegradorBackend.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("Select p From Paciente p where p.email=?1")
    Optional<Paciente> buscarPacientePorEmail(String email);
}
