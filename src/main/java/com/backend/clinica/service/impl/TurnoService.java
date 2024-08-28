package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.repository.IDao;
import com.backend.clinica.service.ITurnoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private IDao<Turno> turnoIDao;
    private final ModelMapper modelMapper;

    public TurnoService(IDao<Turno> turnoIDao, ModelMapper modelMapper) {
        this.turnoIDao = turnoIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {

        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turno));
        Turno entidadTurno = modelMapper.map(turno, Turno.class);

        LOGGER.info("EntidadTurno: {}", JsonPrinter.toString(entidadTurno));
        Turno turnoRegistrado = turnoIDao.registrar(entidadTurno);

        LOGGER.info("TurnoRegistrado: {}", JsonPrinter.toString(turnoRegistrado));
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);

        LOGGER.info("TurnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));

        return turnoSalidaDto;

    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        return null;
    }

    @Override
    public List<TurnoSalidaDto> listarTurno() {
        List<TurnoSalidaDto> turnoSalidaDtos = turnoIDao.listarTodos()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(turnoSalidaDtos));

        return turnoSalidaDtos;
    }

    @Override
    public void eliminarTurno(Long id) {

        if(buscarTurnoPorId(id) != null){
            //llamada a la capa repositorio para eliminar
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            //excepcion resource not found
        }

    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) {
        return null;
    }

    private void configureMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> {
                    mapper.map(TurnoEntradaDto::getPacienteEntradaDto, Turno::setPaciente);
                    mapper.map(TurnoEntradaDto::getOdontologoEntradaDto, Turno::setOdontologo);
                });

        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> {
                    mapper.map(Turno::getPaciente, TurnoSalidaDto::setPaciente);
                    mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologo);
                });
    }
}
