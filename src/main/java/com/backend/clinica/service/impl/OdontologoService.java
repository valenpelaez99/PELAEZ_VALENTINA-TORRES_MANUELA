package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.service.IOdontologoService;
import com.backend.clinica.repository.IDao;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OdontologoService implements IOdontologoService {

    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final IDao<Odontologo> odontologoIDao;
    private final ModelMapper modelMapper;

    public OdontologoService(IDao<Odontologo> odontologoIDao, ModelMapper modelMapper) {

        this.odontologoIDao = odontologoIDao;
        this.modelMapper = modelMapper;

    }
    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologoEntradaDto) {
        LOGGER.info("OdontologoEntradaDto: {}", JsonPrinter.toString(odontologoEntradaDto));
        Odontologo entidadOdontologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);

        LOGGER.info("EntidadOdontologo: {}", JsonPrinter.toString(entidadOdontologo));
        Odontologo odontologoRegistrado = odontologoIDao.registrar(entidadOdontologo);

        LOGGER.info("OdontologoRegistrado: {}", JsonPrinter.toString(odontologoRegistrado));
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoRegistrado, OdontologoSalidaDto.class);
        LOGGER.info("OdontologoSalidaDto: {}", JsonPrinter.toString(odontologoSalidaDto));

        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {

        return null;
    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologoSalidaDto = odontologoIDao.listarTodos()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los odontologos: {}", JsonPrinter.toString(odontologoSalidaDto));

        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologo, Long id) {
        return null;
    }

    @Override
    public void eliminarOdontologo(Long id) {
        if(buscarOdontologoPorId(id) != null){
            //llamada a la capa repositorio para eliminar
            LOGGER.warn("Se ha eliminado el odontologo con id {}", id);
        } else {
            //excepcion resource not found
        }

    }


}
