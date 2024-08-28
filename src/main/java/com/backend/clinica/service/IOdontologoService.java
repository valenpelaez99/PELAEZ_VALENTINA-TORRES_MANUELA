package com.backend.clinica.service;

import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo);
    OdontologoSalidaDto buscarOdontologoPorId(Long id);
    List<OdontologoSalidaDto> listarOdontologos();
    OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologo, Long id);
    void eliminarOdontologo(Long id);
}
