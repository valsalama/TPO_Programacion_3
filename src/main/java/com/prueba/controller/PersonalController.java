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
@CrossOrigin(origins = "*")  // ✅ para Live Server
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



        // 3️⃣ Ordenar solo si corresponde
        if ("legajo".equals(sort)) {
            Personal.mergeSort(lista);
        } else if ("edad".equals(sort)) {
            Personal.quickSort(lista, 0, lista.size() - 1);
        }
        // ✅ Si es nombre o null → NO ordenamos

        
        return ResponseEntity.ok(lista);
    }
}
