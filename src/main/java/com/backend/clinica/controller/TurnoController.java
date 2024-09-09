package com.backend.clinica.controller;

import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.service.IPacienteService;
import com.backend.clinica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("turnos")
@CrossOrigin


public class TurnoController {



        private ITurnoService turnoService;

        public TurnoController(ITurnoService turnoService) {
            this.turnoService = turnoService;
        }


        //POST
        @PostMapping("/registrar")
        public ResponseEntity<?> registrarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto){

            try{
                TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);
                return new ResponseEntity<>(turnoSalidaDto, HttpStatus.CREATED);
            }catch (BadRequestException e) {
                // Retornar un mensaje con el error y status 400 BAD_REQUEST si ocurre BadRequestException
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

        }

        //GET
        @GetMapping("/listar")
        public ResponseEntity<List<TurnoSalidaDto>> listarTurnos(){
            return new ResponseEntity<>(turnoService.listarTurno(), HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id){
            return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), HttpStatus.OK);
        }

        //PUT
        @PutMapping("/actualizar/{id}")
        public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoEntradaDto turno, @PathVariable Long id){
            return new ResponseEntity<>(turnoService.actualizarTurno(turno, id), HttpStatus.OK);
        }

        //DELETE
        @DeleteMapping("/eliminar")
        public ResponseEntity<String> eliminarTruno(@RequestParam Long id) throws ResourceNotFoundException {
            turnoService.eliminarTurno(id);
            return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.NO_CONTENT);
        }



}
