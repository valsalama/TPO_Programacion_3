package com.prueba.service;

import com.prueba.model.Evento;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService {

    private List<Evento> eventos = new ArrayList<>();

    public List<Evento> getEventos() {
        return eventos;
    }

    public void agregarEvento(Evento nuevo) {
        eventos.add(nuevo);
        asignarHorarios();
    }

    @PostConstruct
    public void init() {
        //Eventos precargados
        agregarEvento(new Evento(
                "Feria de Artesanos",
                "Palermo",
                "El Rosedal (Plaza Holanda)",
                LocalDate.of(2025, 11, 6)
        ));

        agregarEvento(new Evento(
                "Concierto al Aire Libre",
                "Palermo",
                "El Rosedal (Plaza Holanda)",
                LocalDate.of(2025, 11, 6)
        ));

        agregarEvento(new Evento(
                "Festival de Food Trucks",
                "Recoleta",
                "Plaza República Oriental del Uruguay",
                LocalDate.of(2025, 5, 1)
        ));

        agregarEvento(new Evento(
                "Show de Musica Local",
                "Recoleta",
                "Plaza República Oriental del Uruguay",
                LocalDate.of(2025, 5, 1)
        ));

        agregarEvento(new Evento(
                "Feria de Arte",
                "Mataderos",
                "Bo. Los Perales (ex Manuel Dorrego)",
                LocalDate.of(2025, 5, 1)
        ));
    }

    private void asignarHorarios() {
        asignarHorariosRecursivo(eventos, 0);
    }

    private boolean asignarHorariosRecursivo(List<Evento> eventos, int index) {
        if (index == eventos.size()) return true;

        Evento actual = eventos.get(index);

        for (int hora = 14; hora < 19; hora++) {
            LocalTime posibleHora = LocalTime.of(hora, 0);

            if (esHoraValida(eventos, actual, posibleHora)) {
                actual.setHoraInicio(posibleHora);

                if (asignarHorariosRecursivo(eventos, index + 1))
                    return true;

                actual.setHoraInicio(null); // backtrack
            }
        }

        return false;
    }

    private boolean esHoraValida(List<Evento> eventos, Evento actual, LocalTime hora) {
        for (Evento e : eventos) {
            if (e == actual) continue;
            if (e.getPlaza().equals(actual.getPlaza()) &&
                e.getFecha().equals(actual.getFecha()) &&
                e.getHoraInicio() != null &&
                e.getHoraInicio().equals(hora)) {
                return false;
            }
        }
        return true;
    }
}

