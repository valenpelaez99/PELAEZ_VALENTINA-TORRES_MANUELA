package com.backend.clinica.dto.salida;

public class OdontologoNombreCompletoDto {

    private String nombre;
    private String apellido;

    public OdontologoNombreCompletoDto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public OdontologoNombreCompletoDto() {
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
