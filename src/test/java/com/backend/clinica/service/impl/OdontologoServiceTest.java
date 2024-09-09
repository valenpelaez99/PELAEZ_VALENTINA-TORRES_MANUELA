package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.repository.OdontologoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class OdontologoServiceTest {

    private static OdontologoRepository odontologoRepositoryMock;
    private static OdontologoService odontologoService;
    private static ModelMapper modelMapper;
    private static Odontologo odontologo;
    private static OdontologoEntradaDto odontologoEntradaDto;

    @BeforeAll
    static void setUp() {
        odontologoRepositoryMock = mock(OdontologoRepository.class);
        modelMapper = new ModelMapper();
        odontologoService = new OdontologoService(odontologoRepositoryMock, modelMapper);

        odontologo = new Odontologo(1L, "M123", "Juan", "Perez");
        odontologoEntradaDto = new OdontologoEntradaDto("M123", "Juan", "Perez");

    }

    @Test
    void deberiaRegistrarUnOdontologoConNombreJuan_yRetornarSalidaDtoConSuId() {
        when(odontologoRepositoryMock.save(any(Odontologo.class))).thenReturn(odontologo);

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Juan", odontologoSalidaDto.getNombre());
        verify(odontologoRepositoryMock, times(1)).save(any(Odontologo.class));
    }

    @Test
    void deberiaBuscarUnOdontologoPorId_yRetornarSuSalidaDto() {
        when(odontologoRepositoryMock.findById(1L)).thenReturn(Optional.of(odontologo));

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(1L);

        assertNotNull(odontologoSalidaDto);
        assertEquals(1L, odontologoSalidaDto.getId());
        assertEquals("Juan", odontologoSalidaDto.getNombre());
        verify(odontologoRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void deberiaDevolverUnListadoNoVacioDeOdontologos() {
        when(odontologoRepositoryMock.findAll()).thenReturn(List.of(odontologo));

        List<OdontologoSalidaDto> listadoDeOdontologos = odontologoService.listarOdontologos();

        assertFalse(listadoDeOdontologos.isEmpty());
        assertEquals(1, listadoDeOdontologos.size());
        verify(odontologoRepositoryMock, times(1)).findAll();
    }



    @Test
    void deberiaRetornarUnOdontologoAlBuscarPorId() {

        when(odontologoRepositoryMock.findById(1L)).thenReturn(Optional.of(odontologo));


        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(1L);


        assertNotNull(odontologoSalidaDto);
        assertEquals(1L, odontologoSalidaDto.getId());
        assertEquals("Juan", odontologoSalidaDto.getNombre());


        verify(odontologoRepositoryMock, times(1)).findById(1L);
    }
}