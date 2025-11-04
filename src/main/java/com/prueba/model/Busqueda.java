package com.prueba.model;

import java.util.*;

public class Busqueda {

    public static List<String> bfs(Map<String, List<String>> grafo, String inicio, String fin) {
        Queue<List<String>> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        cola.add(List.of(inicio));
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            List<String> camino = cola.poll();
            String actual = camino.get(camino.size() - 1);
            if (actual.equals(fin)) return camino;

            for (String vecino : grafo.getOrDefault(actual, new ArrayList<>())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    List<String> nuevo = new ArrayList<>(camino);
                    nuevo.add(vecino);
                    cola.add(nuevo);
                }
            }
        }
        return List.of();
    }

    public static List<String> dfs(Map<String, List<String>> grafo, String inicio, String fin) {
        Stack<List<String>> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();
        pila.push(List.of(inicio));

        while (!pila.isEmpty()) {
            List<String> camino = pila.pop();
            String actual = camino.get(camino.size() - 1);
            if (actual.equals(fin)) return camino;

            if (!visitados.contains(actual)) {
                visitados.add(actual);
                for (String vecino : grafo.getOrDefault(actual, new ArrayList<>())) {
                    List<String> nuevo = new ArrayList<>(camino);
                    nuevo.add(vecino);
                    pila.push(nuevo);
                }
            }
        }
        return List.of();
    }
}
