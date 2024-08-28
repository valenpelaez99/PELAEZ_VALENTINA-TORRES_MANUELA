package com.backend.clinica.dto.salida;

import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;

import java.time.LocalDateTime;

public class TurnoSalidaDto {

    private Long id;
    private PacienteNombreCompletoDto paciente;
    private OdontologoNombreCompletoDto odontologo;
    private LocalDateTime fechaTurno;

    public TurnoSalidaDto(Long id, PacienteNombreCompletoDto paciente, OdontologoNombreCompletoDto odontologo, LocalDateTime fechaTurno) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaTurno = fechaTurno;
    }

    public TurnoSalidaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PacienteNombreCompletoDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteNombreCompletoDto paciente) {
        this.paciente = paciente;
    }

    public OdontologoNombreCompletoDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoNombreCompletoDto odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDateTime fechaTurno) {
        this.fechaTurno = fechaTurno;
    }
}
