package com.backend.clinica.dto.entrada;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OdontologoEntradaDto {

    @NotBlank(message = "Debe especificarse el numero de matricula")
    @Size(min = 1, max = 10, message = "La matricula debe tener entre 1 y 10 caracteres")
    private String matricula;

    @NotBlank(message = "Debe especificarse el nombre")
    private String nombre;

    @NotBlank(message = "Debe especificarse el apellido")
    private String apellido;

    public OdontologoEntradaDto() {

    }

    public OdontologoEntradaDto(String matricula, String nombre, String apellido) {

        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
