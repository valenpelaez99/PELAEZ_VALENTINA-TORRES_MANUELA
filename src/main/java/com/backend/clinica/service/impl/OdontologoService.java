package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.repository.OdontologoRepository;
import com.backend.clinica.service.IOdontologoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class OdontologoService implements IOdontologoService {

    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;

    }
    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologoEntradaDto) {
        LOGGER.info("OdontologoEntradaDto: {}", JsonPrinter.toString(odontologoEntradaDto));
        Odontologo entidadOdontologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);

        LOGGER.info("EntidadOdontologo: {}", JsonPrinter.toString(entidadOdontologo));
        Odontologo odontologoRegistrado = odontologoRepository.save(entidadOdontologo);

        LOGGER.info("OdontologoRegistrado: {}", JsonPrinter.toString(odontologoRegistrado));
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoRegistrado, OdontologoSalidaDto.class);
        LOGGER.info("OdontologoSalidaDto: {}", JsonPrinter.toString(odontologoSalidaDto));

        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        LOGGER.info("Odontologo buscado: {}", JsonPrinter.toString(odontologoBuscado));
        OdontologoSalidaDto odontologoEncontrado = null;
        if(odontologoBuscado != null){
            odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologoEncontrado));
        } else LOGGER.error("No se ha encontrado el Odontologo con id {}", id);

        return odontologoEncontrado;
    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologoSalidaDto = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los odontologos: {}", JsonPrinter.toString(odontologoSalidaDto));

        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) {
        Odontologo odontologoAActualizar = odontologoRepository.findById(id).orElse(null);
        Odontologo odontologoRecibido = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        OdontologoSalidaDto odontologoSalidaDto = null;

        if (odontologoAActualizar != null){

            odontologoRecibido.setId(odontologoAActualizar.getId());

            odontologoAActualizar = odontologoRecibido;

            odontologoRepository.save(odontologoAActualizar);
            odontologoSalidaDto = modelMapper.map(odontologoAActualizar, OdontologoSalidaDto.class);
            LOGGER.warn("Odontologo actualizado: {}", JsonPrinter.toString(odontologoSalidaDto));

        } else LOGGER.error("No fue posible actualizar el odontologo porque no se encuentra en nuestra base de datos");
        //lanzar exception

        return odontologoSalidaDto;
    }

    @Override
    public void eliminarOdontologo(Long id) {
        if(buscarOdontologoPorId(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id {}", id);
        } else {
            throw new EntityNotFoundException("Odontologo no encontrado con id " + id);
        }

    }


}
