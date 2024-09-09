package com.backend.clinica.service.impl;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.backend.clinica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Domicilio;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.repository.OdontologoRepository;
import com.backend.clinica.repository.PacienteRepository;
import com.backend.clinica.repository.TurnoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TurnoServiceTest {

    private static TurnoRepository turnoRepositoryMock;
    private static PacienteRepository pacienteRepositoryMock;
    private static OdontologoRepository odontologoRepositoryMock;
    private static PacienteService pacienteService;
    private static OdontologoService odontologoService;
    private static TurnoService turnoService;
    private static ModelMapper modelMapper;

    private static Paciente paciente;
    private static Odontologo odontologo;
    private static Turno turno;
    private static TurnoEntradaDto turnoEntradaDto;

    @BeforeAll
    static void setUp() {
        turnoRepositoryMock = mock(TurnoRepository.class);
        pacienteRepositoryMock = mock(PacienteRepository.class);
        odontologoRepositoryMock = mock(OdontologoRepository.class);
        modelMapper = new ModelMapper();

        pacienteService = new PacienteService(pacienteRepositoryMock, modelMapper);
        odontologoService = new OdontologoService(odontologoRepositoryMock, modelMapper);
        turnoService = new TurnoService(turnoRepositoryMock, pacienteService, odontologoService, modelMapper);

        // Crear un objeto Domicilio
        Domicilio domicilio = new Domicilio(1L, "Calle Falsa", 123, "Ciudad", "Provincia");

        // Crear el objeto Paciente
        paciente = new Paciente(1L, "Juan", "Pérez", 12345678, LocalDate.now(), domicilio);

        // Crear un objeto Odontologo
        odontologo = new Odontologo(1L, "M123", "Ana", "Gómez");

        // Crear el objeto DomicilioEntradaDto
        DomicilioEntradaDto domicilioEntradaDto = new DomicilioEntradaDto("Calle Falsa", 123, "Ciudad", "Provincia");

        // Ajustar el constructor de Turno
        turno = new Turno(1L, paciente, odontologo, LocalDateTime.now());

        // Ajustar el constructor de PacienteEntradaDto
        turnoEntradaDto = new TurnoEntradaDto(
                new PacienteEntradaDto("Juan", "Pérez", 12345678, LocalDate.now(), domicilioEntradaDto),
                new OdontologoEntradaDto("M123", "Ana", "Gómez"),
                LocalDateTime.now()
        );
    }

    @Test
    void deberiaRegistrarUnTurno_yRetornarSalidaDtoConSuId() throws BadRequestException {
        when(pacienteRepositoryMock.findByDni(12345678)).thenReturn(Optional.of(paciente));
        when(odontologoRepositoryMock.findByMatricula("M123")).thenReturn(Optional.of(odontologo));
        when(turnoRepositoryMock.save(any(Turno.class))).thenReturn(turno);

        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto);
        assertNotNull(turnoSalidaDto.getId());
        assertEquals("Juan", turnoSalidaDto.getPaciente().getNombre());
        verify(turnoRepositoryMock, times(1)).save(any(Turno.class));
    }

    @Test
    void deberiaBuscarUnTurnoPorId_yRetornarSuSalidaDto() {
        when(turnoRepositoryMock.findById(1L)).thenReturn(Optional.of(turno));

        TurnoSalidaDto turnoSalidaDto = turnoService.buscarTurnoPorId(1L);

        assertNotNull(turnoSalidaDto);
        assertEquals(1L, turnoSalidaDto.getId());
        assertEquals("Juan", turnoSalidaDto.getPaciente().getNombre());
        verify(turnoRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void deberiaDevolverUnListadoNoVacioDeTurnos() {
        when(turnoRepositoryMock.findAll()).thenReturn(List.of(turno));

        List<TurnoSalidaDto> listadoDeTurnos = turnoService.listarTurno();

        assertFalse(listadoDeTurnos.isEmpty());
        assertEquals(1, listadoDeTurnos.size());
        verify(turnoRepositoryMock, times(1)).findAll();
    }

}