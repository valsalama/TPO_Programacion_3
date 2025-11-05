package com.prueba.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.prueba.model.Evento;
import com.prueba.service.EventoService;
import com.prueba.model.Neo4jConnector;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "*") // permite peticiones desde el front (Live Server, etc.)
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private Neo4jConnector neo4jConnector; 

    @PostMapping("/crear")
    public String crearEvento(@RequestParam String nombre,
                              @RequestParam String barrio,
                              @RequestParam String plaza,
                              @RequestParam String fecha) {

        LocalDate dia = LocalDate.parse(fecha);
        Evento nuevo = new Evento(nombre, barrio, plaza, dia);
        eventoService.agregarEvento(nuevo); // usa backtracking internamente
        return "Evento creado y asignado correctamente.";
    }

    
    @GetMapping
    public List<Evento> listarEventos() {
        return eventoService.getEventos();
    }

    @GetMapping("/barrios")
    public List<Map<String, Object>> listarBarrios() {
        String query = "MATCH (b:Barrio) RETURN b.nombre AS nombre ORDER BY b.nombre";
        return neo4jConnector.runQuery(query);
    }

    // Listar plazas según el barrio seleccionado
    @GetMapping("/plazas")
    public List<Map<String, Object>> plazasPorBarrio(@RequestParam String barrio) {
        String query = "MATCH (p:Plaza {barrio: $barrio}) RETURN p.nombre AS nombre ORDER BY p.nombre";
        return neo4jConnector.runQuery(query, Map.of("barrio", barrio));
    }

}


