package com.prueba.controller;

import com.prueba.model.Neo4jConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/barrios")
@CrossOrigin(origins = "*")
public class BarrioController {

    @Autowired
    private Neo4jConnector neo4jConnector;

    @GetMapping("/listar")
    public List<Map<String, Object>> listarBarrios() {
        String cypher = "MATCH (b:Barrio) RETURN b.nombre AS nombre ORDER BY b.nombre";
        return neo4jConnector.runQuery(cypher);
    }
}

