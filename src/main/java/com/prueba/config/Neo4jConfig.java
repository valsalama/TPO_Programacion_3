package com.prueba.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;

@Configuration
public class Neo4jConfig {

    private final Driver driver;
    private final DatabaseSelectionProvider databaseSelectionProvider;

    public Neo4jConfig(Driver driver, DatabaseSelectionProvider databaseSelectionProvider) {
        this.driver = driver;
        this.databaseSelectionProvider = databaseSelectionProvider;
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(driver, databaseSelectionProvider);
    }
}