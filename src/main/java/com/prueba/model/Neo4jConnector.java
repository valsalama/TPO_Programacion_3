package com.prueba.model;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.AuthTokens;
import java.util.*;
import org.neo4j.driver.Record; // IMPORTANTE: usa este Record explícito de Neo4j

public class Neo4jConnector implements AutoCloseable {
    private final Driver driver;

    public Neo4jConnector(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    // Devuelve todos los barrios (para llenar los <select>)
    public List<String> getBarrios() {
        List<String> barrios = new ArrayList<>();
        try (Session session = driver.session()) {
            Result result = session.run("MATCH (b:Barrio) RETURN b.nombre AS nombre");
            while (result.hasNext()) {
                Record record = result.next(); // Record aquí es org.neo4j.driver.Record
                barrios.add(record.get("nombre").asString());
            }
        }
        return barrios;
    }

    // Cargar grafo completo desde Neo4j (para Dijkstra)
    public Map<String, List<Arista>> cargarGrafo() {
        Map<String, List<Arista>> grafo = new HashMap<>();
        try (Session session = driver.session()) {
            String query = """
                MATCH (a:Barrio)-[r:LIMITA_CON]->(b:Barrio)
                WITH a, b, (coalesce(a.peso, 1.0) + coalesce(b.peso, 1.0))/2 AS peso
                RETURN a.nombre AS origen, b.nombre AS destino, peso
            """;

            Result result = session.run(query);

            while (result.hasNext()) {
                Record record = result.next(); // Record explícito de Neo4j
                String origen = record.get("origen").asString();
                String destino = record.get("destino").asString();
                double peso = record.get("peso").asDouble();

                grafo.computeIfAbsent(origen, k -> new ArrayList<>())
                     .add(new Arista(destino, peso));

                // Aseguramos que todos los nodos estén en el grafo incluso sin aristas salientes
                grafo.putIfAbsent(destino, new ArrayList<>());
            }
        }
        return grafo;
    }
}
