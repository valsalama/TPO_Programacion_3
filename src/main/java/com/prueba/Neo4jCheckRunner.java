package com.prueba;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4jCheckRunner implements CommandLineRunner {

    private final Driver driver;

    public Neo4jCheckRunner(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Session session = driver.session()) {
            // Consulta simple para ver la base de datos activa
            String dbName = session.run("CALL db.info() YIELD name RETURN name")
                                   .single()
                                   .get("name")
                                   .asString();

            System.out.println("Conectado a la base de datos: " + dbName);
        }
    }
}