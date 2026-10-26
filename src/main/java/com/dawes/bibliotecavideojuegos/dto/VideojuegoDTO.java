package com.dawes.bibliotecavideojuegos.dto;

public class VideojuegoDTO {
    private int id;
    private String titulo;
    private int anioLanzamiento;
    private int pegi;
    private int idDesarrolladora;
    private String desarrolladora;
    private int copiasTotales;
    private int copiasDisponibles;

    public VideojuegoDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public int getPegi() {
        return pegi;
    }

    public void setPegi(int pegi) {
        this.pegi = pegi;
    }

    public int getIdDesarrolladora() {
        return idDesarrolladora;
    }

    public void setIdDesarrolladora(int idDesarrolladora) {
        this.idDesarrolladora = idDesarrolladora;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public int getCopiasTotales() {
        return copiasTotales;
    }

    public void setCopiasTotales(int copiasTotales) {
        this.copiasTotales = copiasTotales;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }
}