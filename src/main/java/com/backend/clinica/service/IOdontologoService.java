package com.backend.clinica.service;

import com.backend.clinica.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo);
    Odontologo buscarOdontologoPorId(Long id);
}
