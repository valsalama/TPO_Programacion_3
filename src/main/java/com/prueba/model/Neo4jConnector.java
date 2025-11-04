package com.prueba.model;

import org.neo4j.driver.*;
import java.util.*;
import org.neo4j.driver.Record;

public class Neo4jConnector implements AutoCloseable {
    private final Driver driver;

    public Neo4jConnector(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    // =========================
    // Barrios
    // =========================
    public List<String> getBarrios() {
        List<String> barrios = new ArrayList<>();
        try (Session session = driver.session()) {
            Result result = session.run("MATCH (b:Barrio) RETURN b.nombre AS nombre ORDER BY b.nombre");
            while (result.hasNext()) {
                Record record = result.next();
                barrios.add(record.get("nombre").asString());
            }
        }
        return barrios;
    }

    public Map<String, List<Arista>> cargarGrafoBarrios() {
        Map<String, List<Arista>> grafo = new HashMap<>();
        try (Session session = driver.session()) {
            String query = """
                MATCH (a:Barrio)-[r:LIMITA_CON]->(b:Barrio)
                WITH a, b, (coalesce(a.peso,1.0)+coalesce(b.peso,1.0))/2 AS peso
                RETURN a.nombre AS origen, b.nombre AS destino, peso
            """;

            Result result = session.run(query);
            while (result.hasNext()) {
                Record rec = result.next();
                String origen = rec.get("origen").asString();
                String destino = rec.get("destino").asString();
                double peso = rec.get("peso").asDouble();

                grafo.computeIfAbsent(origen, k -> new ArrayList<>()).add(new Arista(destino, peso));
                grafo.putIfAbsent(destino, new ArrayList<>()); // asegurar nodo sin salidas
            }
        }
        return grafo;
    }

    // =========================
    // Atracciones / Paradas
    // =========================
    public List<String> getAtracciones() {
        List<String> atracciones = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:Parada) RETURN p.atractivos AS atractivos";
            Result result = session.run(query);
            while (result.hasNext()) {
                Record rec = result.next();
                String atractivosStr = rec.get("atractivos").asString("");
                if (!atractivosStr.isEmpty()) {
                    // separar por " - " y agregar
                    for (String a : atractivosStr.split(" - ")) {
                        atracciones.add(a.trim());
                    }
                }
            }
        }
        return atracciones;
    }

    public Map<String, String> mapaAtracciones() {
        Map<String, String> mapa = new HashMap<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:Parada) RETURN p.nombre AS parada, p.atractivos AS atractivos";
            Result result = session.run(query);
            while (result.hasNext()) {
                Record rec = result.next();
                String parada = rec.get("parada").asString();
                String atractivosStr = rec.get("atractivos").asString("");
                if (!atractivosStr.isEmpty()) {
                    for (String a : atractivosStr.split(" - ")) {
                        mapa.put(a.trim(), parada); // atracción -> parada
                    }
                }
            }
        }
        return mapa;
    }

    public Map<String, List<Arista>> cargarGrafoParadas() {
        Map<String, List<Arista>> grafo = new HashMap<>();
        try (Session session = driver.session()) {
            String query = """
                MATCH (p1:Parada)-[r:SIGUE_A]->(p2:Parada)
                WITH p1, p2, coalesce(r.peso,1.0) AS peso
                RETURN p1.nombre AS origen, p2.nombre AS destino, peso
            """;

            Result result = session.run(query);
            while (result.hasNext()) {
                Record rec = result.next();
                String origen = rec.get("origen").asString();
                String destino = rec.get("destino").asString();
                double peso = rec.get("peso").asDouble();

                grafo.computeIfAbsent(origen, k -> new ArrayList<>()).add(new Arista(destino, peso));
                grafo.putIfAbsent(destino, new ArrayList<>());
            }
        }
        return grafo;
    }
}


