package com.prueba.controller;

import com.prueba.model.Arista;
import com.prueba.model.Dijkstra;
import com.prueba.model.Neo4jConnector;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class DijkstraController {

    private final Neo4jConnector neo4j;

    public DijkstraController() {
        neo4j = new Neo4jConnector();
    }

    @GetMapping("/barrios")
    public List<String> getBarrios() {
        return neo4j.getBarrios();
    }

    @PostMapping("/ruta")
    public Map<String,Object> buscarRuta(@RequestBody Map<String,String> body){
        String origen = body.get("origen");
        String destino = body.get("destino");
        Map<String, List<Arista>> grafo = neo4j.cargarGrafo();
        return Dijkstra.calcular(grafo, origen, destino);
    }
}




