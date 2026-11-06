package com.dawes.bibliotecavideojuegos.dto;

import java.time.LocalDate;

public class SocioDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;
    private Boolean activo;

    // Campos calculados para el listado (RF-03)
    private Integer edad;
    private Integer prestamosAbiertos;

    public SocioDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPrestamosAbiertos() {
        return prestamosAbiertos;
    }

    public void setPrestamosAbiertos(Integer prestamosAbiertos) {
        this.prestamosAbiertos = prestamosAbiertos;
    }
}