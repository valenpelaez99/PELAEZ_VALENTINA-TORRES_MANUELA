package com.backend.clinica.repository;

import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OdontologoRepository extends JpaRepository<Odontologo,Long> {
}
