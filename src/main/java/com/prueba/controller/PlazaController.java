package com.prueba.controller;

import com.prueba.model.Neo4jConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plazas")
@CrossOrigin(origins = "*")
public class PlazaController {

    @Autowired
    private Neo4jConnector neo4jConnector;

    // 🔹 Devuelve las plazas de un barrio específico
    @GetMapping("/porBarrio")
    public List<Map<String, Object>> plazasPorBarrio(@RequestParam String barrio) {
        String cypher = """
            MATCH (p:Plaza {barrio: $barrio})
            RETURN p.nombre AS nombre
            ORDER BY p.nombre
        """;
        Map<String, Object> params = Map.of("barrio", barrio);
        return neo4jConnector.runQuery(cypher, params);
    }
}
