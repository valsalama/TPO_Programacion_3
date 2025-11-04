package com.prueba.model;

import java.util.*;


public class Dijkstra {

    public static Map<String, Object> calcular(Map<String, List<Arista>> grafo, String inicio, String fin) {
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));

        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Double.MAX_VALUE);
        }
        distancias.put(inicio, 0.0);
        pq.add(inicio);

        while (!pq.isEmpty()) {
            String actual = pq.poll();

            if (!grafo.containsKey(actual)) continue;

            for (Arista arista : grafo.get(actual)) {
                double nuevaDistancia = distancias.get(actual) + arista.peso;
                if (nuevaDistancia < distancias.getOrDefault(arista.destino, Double.MAX_VALUE)) {
                    distancias.put(arista.destino, nuevaDistancia);
                    predecesores.put(arista.destino, actual);
                    pq.add(arista.destino);
                }
            }
        }

        // Reconstruir camino
        List<String> camino = new ArrayList<>();
        String nodo = fin;
        while (nodo != null) {
            camino.add(nodo);
            nodo = predecesores.get(nodo);
        }
        Collections.reverse(camino);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("camino", camino);
        resultado.put("costo", distancias.get(fin));
        return resultado;
    }
}
