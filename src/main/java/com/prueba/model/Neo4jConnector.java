package com.prueba.model;

import org.neo4j.driver.*;
import java.util.*;

public class Neo4jConnector implements AutoCloseable {
    private final Driver driver;

    public Neo4jConnector(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    // Cargar grafo desde Neo4j
    public Map<String, List<Arista>> cargarGrafo() {
        Map<String, List<Arista>> grafo = new HashMap<>();

        try (Session session = driver.session()) {
            // Calculamos peso como promedio de los pesos de los barrios
            String query = """
                MATCH (a:Barrio)-[r:LIMITA_CON]->(b:Barrio)
                WITH a, b, (coalesce(a.peso, 1.0) + coalesce(b.peso, 1.0))/2 AS peso
                RETURN a.nombre AS origen, b.nombre AS destino, peso
            """;

            Result result = session.run(query);

            while (result.hasNext()) {
                org.neo4j.driver.Record record = result.next();
                String origen = record.get("origen").asString();
                String destino = record.get("destino").asString();
                double peso = record.get("peso").asDouble();

                grafo.computeIfAbsent(origen, k -> new ArrayList<>()).add(new Arista(destino, peso));
            }
        }

        return grafo;
    }
}
