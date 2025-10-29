package com.prueba;

import com.prueba.model.Arista;
import com.prueba.model.Dijkstra;
import com.prueba.model.Neo4jConnector;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Neo4jConnector neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345678")) {

            Map<String, List<Arista>> grafo = neo4j.cargarGrafo();

            String inicio = "Belgrano";
            String fin = "San Telmo";

            Map<String, Object> resultado = Dijkstra.calcular(grafo, inicio, fin);

            System.out.println("Camino más corto: " + resultado.get("camino"));
            System.out.println("Costo total: " + resultado.get("costo"));
        }
    }
}


