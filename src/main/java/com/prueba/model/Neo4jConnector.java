package com.prueba.model;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Neo4jConnector implements AutoCloseable {

    private final Driver driver;

    public Neo4jConnector() {
        String uri = "bolt://127.0.0.1:7687";  // 
        String user = "neo4j";
        String password = "lolachimichu";              
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    public List<String> getBarrios() {
        List<String> barrios = new ArrayList<>();
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            Result result = session.run("MATCH (b:Barrio) RETURN b.nombre AS nombre ORDER BY b.nombre");
            while (result.hasNext()) {
                Record record = (Record) result.next();
                barrios.add(record.get("nombre").asString());
            }
        }
        return barrios;
    }

    public Map<String, List<Arista>> cargarGrafo() {
        Map<String, List<Arista>> grafo = new HashMap<>();
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            String query = """
                MATCH (a:Barrio)-[r:LIMITA_CON]->(b:Barrio)
                WITH a, b, (coalesce(a.peso, 1.0) + coalesce(b.peso, 1.0))/2 AS peso
                RETURN a.nombre AS origen, b.nombre AS destino, peso
            """;

            Result result = session.run(query);

            while (result.hasNext()) {
                Record record = result.next();
                String origen = record.get("origen").asString();
                String destino = record.get("destino").asString();
                double peso = record.get("peso").asDouble();

                grafo.computeIfAbsent(origen, k -> new ArrayList<>())
                     .add(new Arista(destino, peso));

                grafo.putIfAbsent(destino, new ArrayList<>());
            }
        }
        return grafo;
    }

    public List<Map<String, Object>> runQuery(String cypher) {
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            Result result = session.run(cypher);
            List<Map<String, Object>> records = new ArrayList<>();
            result.stream().forEach(r -> records.add(r.asMap()));
            return records;
        }
    }

    public List<Map<String, Object>> runQuery(String cypher, Map<String, Object> params) {
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            Result result = session.run(cypher, params);
            List<Map<String, Object>> records = new ArrayList<>();
            result.stream().forEach(r -> records.add(r.asMap()));
            return records;
        }
    }
}
