package com.prueba.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.prueba.model.Personal;
import com.prueba.model.Neo4jConnector;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PersonalController {
    
    @Autowired
    private Neo4jConnector neo4j;

    @GetMapping("/personal")
    public ResponseEntity<List<Personal>> getPersonal(
        @RequestParam(name = "sort", required = false) String sort) {

    List<Map<String, Object>> raw = neo4j.runQuery(
            "MATCH (p:Persona) RETURN properties(p) AS p"
    );

    List<Personal> lista = new ArrayList<>();
    List<Personal> listaOrdenada = new ArrayList<>();
    for (Map<String, Object> fila : raw) {
        Object pObj = fila.get("p");
        if (!(pObj instanceof Map)) continue;

        @SuppressWarnings("unchecked")
        Map<String,Object> props = (Map<String, Object>) pObj;

        Personal per = new Personal();
        per.setBarrio((String) props.get("barrio"));
        per.setSexo((String) props.get("genero"));
        per.setLegajo(((Number) props.get("id")).intValue());
        per.setNombreApellido((String) props.get("nombre"));
        per.setDepartamento((String) props.get("sector"));
        per.setEdad(((Number) props.get("edad")).intValue());
        per.setDni(String.valueOf(props.get("dni")));
        per.setNacionalidad((String) props.get("pais"));

        lista.add(per);
    }


        
        if ("legajo".equals(sort)) {
            listaOrdenada = Personal.mergeSort(new ArrayList<>(lista));
        } else if ("edad".equals(sort)) {
            listaOrdenada = Personal.quickSort(new ArrayList<>(lista), 0, lista.size() -1);
        } else if ("divideYVenceras".equals(sort)){
            listaOrdenada = Personal.divideYVenceras(new ArrayList<>(lista));
        }else {
            return ResponseEntity.ok(lista);
        }


        return ResponseEntity.ok(listaOrdenada);
    }
}
