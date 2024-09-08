package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.repository.OdontologoRepository;
import com.backend.clinica.repository.PacienteRepository;
import com.backend.clinica.repository.TurnoRepository;
import com.backend.clinica.service.ITurnoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException {

        // Buscar Paciente por DNI a través del servicio
        Paciente paciente = pacienteService.findByDni(turno.getPacienteEntradaDto().getDni())
                .orElseThrow(() -> new BadRequestException("El paciente con DNI " + turno.getPacienteEntradaDto().getDni() + " no existe."));

        // Buscar Odontologo por matrícula a través del servicio
        Odontologo odontologo = odontologoService.findByMatricula(turno.getOdontologoEntradaDto().getMatricula())
                .orElseThrow(() -> new BadRequestException("El odontólogo con matrícula " + turno.getOdontologoEntradaDto().getMatricula() + " no existe."));

        // Crear y persistir Turno
        Turno entidadTurno = modelMapper.map(turno, Turno.class);
        entidadTurno.setPaciente(paciente);
        entidadTurno.setOdontologo(odontologo);

        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turno));
        LOGGER.info("EntidadTurno: {}", JsonPrinter.toString(entidadTurno));
        Turno turnoRegistrado = turnoRepository.save(entidadTurno);

        LOGGER.info("TurnoRegistrado: {}", JsonPrinter.toString(turnoRegistrado));
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);

        LOGGER.info("TurnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));

        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) {
        Turno turnoAActualizar = turnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado con ID: " + id));

        // Buscar Paciente por DNI usando el servicio
        Paciente paciente = pacienteService.findByDni(turnoEntradaDto.getPacienteEntradaDto().getDni())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con DNI: "
                        + turnoEntradaDto.getPacienteEntradaDto().getDni()));

        // Buscar Odontologo por Matrícula usando el servicio
        Odontologo odontologo = odontologoService.findByMatricula(turnoEntradaDto.getOdontologoEntradaDto().getMatricula())
                .orElseThrow(() -> new IllegalArgumentException("Odontólogo no encontrado con Matrícula: "
                        + turnoEntradaDto.getOdontologoEntradaDto().getMatricula()));

        // Actualizar Turno
        Turno turnoRecibido = modelMapper.map(turnoEntradaDto, Turno.class);
        turnoRecibido.setId(turnoAActualizar.getId());
        turnoRecibido.setPaciente(paciente);
        turnoRecibido.setOdontologo(odontologo);

        Turno turnoActualizado = turnoRepository.save(turnoRecibido);
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoActualizado, TurnoSalidaDto.class);

        LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoSalidaDto));

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
    public void eliminarTurno(Long id) throws ResourceNotFoundException {

        if(buscarTurnoPorId(id) != null){
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            throw new ResourceNotFoundException("No existe el turno con id "+id);
        }

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
