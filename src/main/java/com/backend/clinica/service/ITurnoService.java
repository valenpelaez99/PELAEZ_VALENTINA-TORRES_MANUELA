package com.backend.clinica.service;


import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {

    TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException;
    TurnoSalidaDto buscarTurnoPorId(Long id);

    List<TurnoSalidaDto> listarTurno();

    void eliminarTurno(Long id) throws ResourceNotFoundException;
    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id);
}
