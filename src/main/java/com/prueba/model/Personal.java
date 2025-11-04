package com.prueba.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Personal {
    int legajo;
    String nombreApellido;
    String dni;
    int edad;
    String sexo;
    String nacionalidad;
    String barrio;
    String departamento;

    public Personal(int legajo, String nombreApellido, String dni, int edad, String sexo, String nacionalidad, String barrio, String departamento) {
        this.legajo = legajo;
        this.nombreApellido = nombreApellido;
        this.dni = dni;
        this.edad = edad;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.barrio = barrio;
        this.departamento = departamento;
    }

    public Personal() {
        //TODO Auto-generated constructor stub
    }

    //QuickSort por edad
    public static List<Personal> quickSort(List<Personal> lista, int low, int high) {
        if (low < high) {
            int pi = partition(lista, low, high);
            quickSort(lista, low, pi - 1);
            quickSort(lista, pi + 1, high);
        }
        return lista;
    }

    private static int partition(List<Personal> lista, int low, int high) {
        int pivot = lista.get(high).edad;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (lista.get(j).edad <= pivot) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, high);
        return i + 1;
    }

    //MergeSort por legajo
    public static List<Personal> mergeSort(List<Personal> lista) {
        if (lista.size() <= 1) {
            return lista;
        }

        int mid = lista.size() / 2;
        List<Personal> izquierda = mergeSort(lista.subList(0, mid));
        List<Personal> derecha = mergeSort(lista.subList(mid, lista.size()));

        return merge(izquierda, derecha);
    }

    private static List<Personal> merge(List<Personal> izq, List<Personal> der) {
        List<Personal> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izq.size() && j < der.size()) {
            if (izq.get(i).legajo <= der.get(j).legajo) {
                resultado.add(izq.get(i));
                i++;
            } else {
                resultado.add(der.get(j));
                j++;
            }
        }

        while (i < izq.size()) {
            resultado.add(izq.get(i++));
        }
        while (j < der.size()) {
            resultado.add(der.get(j++));
        }

        return resultado;
    }
    public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("legajo", legajo);
    map.put("nombre", nombreApellido);
    map.put("edad", edad);
    map.put("barrio", barrio);
    map.put("genero", sexo);
    map.put("sector", departamento);
    map.put("dni", dni);
    map.put("pais", nacionalidad);
    return map;
}

    
    public int getLegajo() {
        return legajo;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public String getDni() {
        return dni;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    

}
