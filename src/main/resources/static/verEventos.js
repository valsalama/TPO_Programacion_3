document.addEventListener("DOMContentLoaded", () => {
    const eventosContainer = document.getElementById("eventos-container");

    const eventos = [
        
    ];

    if (eventos.length === 0) {
        eventosContainer.innerHTML = "<p>No hay eventos disponibles actualmente.</p>";
        return;
    }

    eventos.forEach(evento => {
        const div = document.createElement("div");
        div.classList.add("evento-card");
        div.innerHTML = `
            <h2>${evento.nombre}</h2>
            <p><strong>Fecha:</strong> ${evento.fecha}</p>
            <p><strong>Lugar:</strong> ${evento.lugar}</p>
        `;
        eventosContainer.appendChild(div);
    });
});
