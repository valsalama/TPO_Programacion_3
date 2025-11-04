package com.prueba.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {
    private String nombre;
    private String barrio;
    private String plaza;
    private LocalDate fecha;
    private LocalTime horaInicio;

    public Evento(String nombre, String barrio, String plaza, LocalDate fecha) {
        this.nombre = nombre;
        this.barrio = barrio;
        this.plaza = plaza;
        this.fecha = fecha;
    }

    public String getNombre() { return nombre; }
    public String getBarrio() { return barrio; }
    public String getPlaza() { return plaza; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    @Override
    public String toString() {
        return nombre + " - " + plaza + " - " + fecha + " - " + horaInicio;
    }
}
