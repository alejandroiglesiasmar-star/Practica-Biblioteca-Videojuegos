package com.dawes.bibliotecavideojuegos.dto;

import java.time.LocalDate;

public class SocioDTO {
    private Integer idSocio;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fechaAlta;

    public SocioDTO() {}

    public SocioDTO(Integer idSocio, String nombre, String apellidos, String dni, String email, String telefono, LocalDate fechaAlta) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaAlta = fechaAlta;
    }


    public Integer getIdSocio() { return idSocio; }
    public void setIdSocio(Integer idSocio) { this.idSocio = idSocio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
}