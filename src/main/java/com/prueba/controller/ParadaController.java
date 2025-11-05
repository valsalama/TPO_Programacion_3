package com.prueba.controller;

import com.prueba.model.Neo4jConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/paradas")
@CrossOrigin(origins = "*")
public class ParadaController {

    @Autowired
    private Neo4jConnector neo4jConnector;

    @GetMapping("/listar")
    public List<Map<String, Object>> listarParadas() {
        String cypher = "MATCH (b:Parada) RETURN b.nombre AS nombre ORDER BY b.nombre";
        return neo4jConnector.runQuery(cypher);
    }

    // 🔹 NUEVO ENDPOINT BFS
    @PostMapping("/orden-optimo")
    public Map<String, Object> calcularOrdenOptimo(@RequestBody List<String> seleccionadas) {
        Map<String, Object> resultado = new HashMap<>();

        if (seleccionadas == null || seleccionadas.isEmpty()) {
            resultado.put("error", "Debe seleccionar al menos una parada");
            return resultado;
        }

        // Cargar el grafo desde Neo4j (nodos y conexiones)
        String cypher = "MATCH (a:Parada)-[:CONECTA_A]->(b:Parada) " +
                        "RETURN a.nombre AS origen, b.nombre AS destino";
        List<Map<String, Object>> relaciones = neo4jConnector.runQuery(cypher);

        // Construir el grafo en memoria
        Map<String, List<String>> grafo = new HashMap<>();
        for (Map<String, Object> r : relaciones) {
            String origen = (String) r.get("origen");
            String destino = (String) r.get("destino");
            grafo.computeIfAbsent(origen, k -> new ArrayList<>()).add(destino);
            grafo.computeIfAbsent(destino, k -> new ArrayList<>()).add(origen); // grafo no dirigido
        }

        // BFS
        List<String> orden = bfs(grafo, seleccionadas);

        resultado.put("orden", orden);
        return resultado;
    }

    // 🔹 BFS que conecta las paradas seleccionadas
    private List<String> bfs(Map<String, List<String>> grafo, List<String> seleccionadas) {
        Set<String> visitados = new HashSet<>();
        List<String> recorrido = new ArrayList<>();
        Queue<String> cola = new LinkedList<>();

        // Empezamos desde la primera seleccionada
        cola.add(seleccionadas.get(0));
        visitados.add(seleccionadas.get(0));

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            recorrido.add(actual);

            List<String> vecinos = grafo.getOrDefault(actual, Collections.emptyList());
            for (String v : vecinos) {
                if (!visitados.contains(v)) {
                    visitados.add(v);
                    cola.add(v);
                }
            }
        }

        // Filtramos el recorrido solo con las seleccionadas (en el orden encontrado)
        recorrido.retainAll(seleccionadas);

        return recorrido;
    }
    @GetMapping("/orden-optimo")
    public List<Map<String, Object>> obtenerRecorridoOptimo() {
    List<Map<String, Object>> recorrido = new ArrayList<>();

    Map<String, Object> parada1 = new HashMap<>();
    parada1.put("nombre", "Parada A");
    parada1.put("latitud", -34.6037);
    parada1.put("longitud", -58.3816);

    Map<String, Object> parada2 = new HashMap<>();
    parada2.put("nombre", "Parada B");
    parada2.put("latitud", -34.6097);
    parada2.put("longitud", -58.3850);

    recorrido.add(parada1);
    recorrido.add(parada2);

    return recorrido;
}


}
