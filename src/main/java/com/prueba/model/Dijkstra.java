package com.prueba.model;

import java.util.*;

public class Dijkstra {

    public static Map<String, Object> calcular(Map<String, List<Arista>> grafo, String origen, String destino) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (String nodo : grafo.keySet()) dist.put(nodo, Double.MAX_VALUE);
        dist.put(origen, 0.0);
        pq.add(origen);

        while (!pq.isEmpty()) {
            String actual = pq.poll();
            for (Arista arista : grafo.getOrDefault(actual, Collections.emptyList())) {
                double alt = dist.get(actual) + arista.getPeso();
                if (alt < dist.get(arista.getDestino())) {
                    dist.put(arista.getDestino(), alt);
                    prev.put(arista.getDestino(), actual);
                    pq.add(arista.getDestino());
                }
            }
        }

        List<String> camino = new ArrayList<>();
        String u = destino;
        while (u != null) {
            camino.add(0, u);
            u = prev.get(u);
        }

        double costo = dist.getOrDefault(destino, Double.MAX_VALUE);
        Map<String, Object> resp = new HashMap<>();
        resp.put("camino", camino);
        resp.put("costo", costo);
        return resp;
    }

    // BFS para recorridos simples (opcional)
    public static List<String> bfs(Map<String, List<String>> grafo, String inicio, String fin) {
        Map<String, String> prev = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        q.add(inicio);
        visitados.add(inicio);

        while (!q.isEmpty()) {
            String actual = q.poll();
            if (actual.equals(fin)) break;
            for (String vecino : grafo.getOrDefault(actual, Collections.emptyList())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    prev.put(vecino, actual);
                    q.add(vecino);
                }
            }
        }

        List<String> camino = new ArrayList<>();
        String u = fin;
        while (u != null) {
            camino.add(0, u);
            u = prev.get(u);
        }
        return camino;
    }
}

