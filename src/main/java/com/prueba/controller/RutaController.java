package com.prueba.controller;

import com.prueba.model.Arista;
import com.prueba.model.Dijkstra;
import com.prueba.model.Neo4jConnector;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RutaController {

    private final Neo4jConnector neo4j;

    public RutaController() {
        neo4j = new Neo4jConnector("bolt://localhost:7687", "neo4j", "12345678");
    }

    // =========================
    // Barrios
    // =========================
    @GetMapping("/barrios")
    public List<String> getBarrios() {
        return neo4j.getBarrios();
    }

    @PostMapping("/ruta-barrios")
    public Map<String,Object> rutaBarrios(@RequestBody Map<String,String> body) {
        String origen = body.get("origen");
        String destino = body.get("destino");

        Map<String, List<Arista>> grafoBarrios = neo4j.cargarGrafoBarrios(); // grafo de barrios
        Map<String,Object> resultado = Dijkstra.calcular(grafoBarrios, origen, destino);

        return resultado;
    }

    // =========================
    // Atracciones / Paradas de bus
    // =========================
    @GetMapping("/atracciones")
    public List<String> getAtracciones() {
        return neo4j.getAtracciones();
    }

    @PostMapping("/ruta-atracciones")
    public Map<String,Object> rutaAtracciones(@RequestBody Map<String,Object> body){
        String origen = (String) body.get("origen");
        String destino = (String) body.get("destino");
        List<String> atracciones = (List<String>) body.get("atracciones");

        Map<String, List<Arista>> grafoParadas = neo4j.cargarGrafoParadas(); // grafo de paradas
        Map<String, String> paradaPorAtraccion = neo4j.mapaAtracciones(); // atracción -> parada

        List<String> camino = new ArrayList<>();

        String actual = origen;

        for (String a : atracciones) {
            String parada = paradaPorAtraccion.get(a);
            if (parada != null) {
                List<String> tramo = bfs(grafoParadas, actual, parada);
                for (String p : tramo) {
                    if (camino.isEmpty() || !camino.get(camino.size()-1).equals(p)) {
                        camino.add(p);
                    }
                }
                actual = parada;
            }
        }

        // Tramo final hasta destino
        List<String> tramoFinal = bfs(grafoParadas, actual, destino);
        for (String p : tramoFinal) {
            if (camino.isEmpty() || !camino.get(camino.size()-1).equals(p)) {
                camino.add(p);
            }
        }

        Map<String,Object> resp = new HashMap<>();
        resp.put("camino", camino);
        resp.put("costo", camino.size()); // BFS simple, costo = cantidad de paradas
        return resp;
    }

    // =========================
    // BFS simple para paradas
    // =========================
    private List<String> bfs(Map<String, List<Arista>> grafo, String inicio, String fin) {
    Queue<List<String>> queue = new LinkedList<>();
    Set<String> visitado = new HashSet<>();
    queue.add(Arrays.asList(inicio));

    while (!queue.isEmpty()) {
        List<String> path = queue.poll();
        String nodo = path.get(path.size() - 1);

        if (nodo.equals(fin)) return path;
        if (visitado.contains(nodo)) continue;

        visitado.add(nodo);
        for (Arista a : grafo.getOrDefault(nodo, new ArrayList<>())) {
            List<String> nuevaRuta = new ArrayList<>(path);
            nuevaRuta.add(a.getDestino());  // <-- Aquí usamos el getter
            queue.add(nuevaRuta);
        }
    }

    return Collections.emptyList(); // no se encontró camino
}
}

