package com.prueba.model;

public class Arista {
    private final String destino;
    private final double peso;

    public Arista(String destino, double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
}

