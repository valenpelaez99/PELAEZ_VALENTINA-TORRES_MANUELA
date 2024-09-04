package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.repository.OdontologoRepository;
import com.backend.clinica.repository.PacienteRepository;
import com.backend.clinica.repository.TurnoRepository;
import com.backend.clinica.service.ITurnoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepositoryo, PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepositoryo;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {

        // Verificar y persistir Paciente
        Paciente paciente = modelMapper.map(turno.getPacienteEntradaDto(), Paciente.class);
        if (paciente.getId() == null || !pacienteRepository.existsById(paciente.getId())) {
            paciente = pacienteRepository.save(paciente);
        }

        // Verificar y persistir Odontologo
        Odontologo odontologo = modelMapper.map(turno.getOdontologoEntradaDto(), Odontologo.class);
        if (odontologo.getId() == null || !odontologoRepository.existsById(odontologo.getId())) {
            odontologo = odontologoRepository.save(odontologo);
        }

        // Crear y persistir Turno
        Turno entidadTurno = modelMapper.map(turno, Turno.class);
        entidadTurno.setPaciente(paciente);
        entidadTurno.setOdontologo(odontologo);

        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turno));
        //Turno entidadTurno = modelMapper.map(turno, Turno.class);

        LOGGER.info("EntidadTurno: {}", JsonPrinter.toString(entidadTurno));
        Turno turnoRegistrado = turnoRepository.save(entidadTurno);

        LOGGER.info("TurnoRegistrado: {}", JsonPrinter.toString(turnoRegistrado));
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);

        LOGGER.info("TurnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));

        return turnoSalidaDto;

    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        LOGGER.info("Turno buscado: {}", JsonPrinter.toString(turnoBuscado));
        TurnoSalidaDto turnoEncontrado = null;
        if(turnoBuscado != null){
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("No se ha encontrado el turno con id {}", id);

        return turnoEncontrado;
    }

    @Override
    public List<TurnoSalidaDto> listarTurno() {
        List<TurnoSalidaDto> turnoSalidaDtos = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(turnoSalidaDtos));

        return turnoSalidaDtos;
    }

    @Override
    public void eliminarTurno(Long id) {

        if(buscarTurnoPorId(id) != null){
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            //excepcion resource not found
        }

    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) {
        Turno turnoAActualizar = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoAActualizar != null) {
            // Verificar y obtener Paciente
            Paciente paciente = modelMapper.map(turnoEntradaDto.getPacienteEntradaDto(), Paciente.class);
            if (paciente.getId() == null || !pacienteRepository.existsById(paciente.getId())) {
                paciente = pacienteRepository.save(paciente);
            }

            // Verificar y obtener Odontologo
            Odontologo odontologo = modelMapper.map(turnoEntradaDto.getOdontologoEntradaDto(), Odontologo.class);
            if (odontologo.getId() == null || !odontologoRepository.existsById(odontologo.getId())) {
                odontologo = odontologoRepository.save(odontologo);
            }

            // Actualizar los campos del turno
            turnoAActualizar.setPaciente(paciente);
            turnoAActualizar.setOdontologo(odontologo);
            turnoAActualizar.setFechaTurno(turnoEntradaDto.getFechaTurno());

            // Guardar el turno actualizado
            Turno turnoActualizado = turnoRepository.save(turnoAActualizar);

            // Mapear a DTO de salida
            turnoSalidaDto = modelMapper.map(turnoActualizado, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
            // lanzar exception
        }

        return turnoSalidaDto;
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
