package com.backend.clinica.repository;

import com.backend.clinica.entity.Domicilio;
import com.backend.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomicilioRepository extends JpaRepository<Domicilio,Long> {
}
