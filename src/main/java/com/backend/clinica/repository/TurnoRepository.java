package com.backend.clinica.repository;

import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoRepository extends JpaRepository<Turno,Long> {
}
