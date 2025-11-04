document.addEventListener("DOMContentLoaded", async () => {
    const eventosContainer = document.getElementById("eventos-container");

    try {
        // 🔹 Pedimos los eventos desde el backend (Spring Boot)
        const response = await fetch("http://localhost:8080/eventos");
        if (!response.ok) throw new Error("Error al obtener los eventos del servidor");

        const eventos = await response.json();

        if (!Array.isArray(eventos) || eventos.length === 0) {
            eventosContainer.innerHTML = "<p>No hay eventos disponibles actualmente.</p>";
            return;
        }

        // 🔹 Mostrar cada evento en pantalla
        eventos.forEach(evento => {
            const div = document.createElement("div");
            div.classList.add("evento-card");

            div.innerHTML = `
                <h2>${evento.nombre}</h2>
                <p><strong>Fecha:</strong> ${evento.fecha || "Sin fecha"}</p>
                <p><strong>Hora:</strong> ${evento.horaInicio || "Sin horario"}</p>
                <p><strong>Barrio:</strong> ${evento.barrio || "Sin barrio"}</p>
                <p><strong>Plaza:</strong> ${evento.plaza || "Sin plaza"}</p>
            `;

            eventosContainer.appendChild(div);
        });

    } catch (error) {
        console.error("Error al cargar los eventos:", error);
        eventosContainer.innerHTML = "<p>Error al cargar los eventos. Intenta nuevamente más tarde.</p>";
    }
});

