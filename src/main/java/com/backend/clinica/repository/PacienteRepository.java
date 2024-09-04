package com.backend.clinica.repository;

import com.backend.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository <Paciente,Long> {
    // Method to find a Paciente by dni
    Optional<Paciente> findByDni(int dni);
}
