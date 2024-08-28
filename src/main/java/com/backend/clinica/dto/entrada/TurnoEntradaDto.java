package com.backend.clinica.dto.entrada;

import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {

    @NotNull(message="El paciente no puede estar vacío")
    @Valid
    private PacienteEntradaDto pacienteEntradaDto;

    @NotNull(message="El odontologo no puede estar vacío")
    @Valid
    private OdontologoEntradaDto odontologoEntradaDto;

    @FutureOrPresent(message="La fehca y hora no puede ser anterior al momento de creación")
    @NotNull
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaTurno;


    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(PacienteEntradaDto pacienteEntradaDto, OdontologoEntradaDto odontologoEntradaDto, LocalDateTime fechaTurno) {
        this.pacienteEntradaDto = pacienteEntradaDto;
        this.odontologoEntradaDto = odontologoEntradaDto;
        this.fechaTurno = fechaTurno;
    }

    public  PacienteEntradaDto getPacienteEntradaDto() {
        return pacienteEntradaDto;
    }

    public void setPacienteEntradaDto( PacienteEntradaDto pacienteEntradaDto) {
        this.pacienteEntradaDto = pacienteEntradaDto;
    }

    public  OdontologoEntradaDto getOdontologoEntradaDto() {
        return odontologoEntradaDto;
    }

    public void setOdontologoEntradaDto( OdontologoEntradaDto odontologoEntradaDto) {
        this.odontologoEntradaDto = odontologoEntradaDto;
    }

    public LocalDateTime getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno( LocalDateTime fechaTurno) {
        this.fechaTurno = fechaTurno;
    }


}
