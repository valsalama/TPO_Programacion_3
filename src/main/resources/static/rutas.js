document.addEventListener("DOMContentLoaded", async () => {

    const origenSelect = document.getElementById("origen");
    const destinoSelect = document.getElementById("destino");
    const selectAtraccion = document.getElementById("atraccion");
    const listaSeleccionadas = document.getElementById("lista-atracciones");
    const resultadoBarrios = document.getElementById("resultado");
    const resultadoAtracciones = document.getElementById("resultado-atracciones");

    // =========================
    // Cargar barrios
    // =========================
    try {
        const resBarrios = await fetch("http://localhost:8080/api/barrios");
        const barrios = await resBarrios.json();
        barrios.forEach(b => {
            [origenSelect, destinoSelect].forEach(sel => {
                const opt = document.createElement("option");
                opt.value = b;
                opt.textContent = b;
                sel.appendChild(opt);
            });
        });
    } catch (err) {
        console.error("Error cargando barrios:", err);
    }

    // =========================
    // Cargar atracciones
    // =========================
    try {
        const resAtracciones = await fetch("http://localhost:8080/api/atracciones");
        const atracciones = await resAtracciones.json();
        atracciones.forEach(a => {
            const opt = document.createElement("option");
            opt.value = a;
            opt.textContent = a;
            selectAtraccion.appendChild(opt);
        });
    } catch (err) {
        console.error("Error cargando atracciones:", err);
    }

    // =========================
    // Agregar atracción a la lista
    // =========================
    document.getElementById("btn-agregar").addEventListener("click", () => {
        const valor = selectAtraccion.value;
        if (valor && !Array.from(listaSeleccionadas.children).some(li => li.textContent === valor)) {
            const li = document.createElement("li");
            li.textContent = valor;
            listaSeleccionadas.appendChild(li);
        }
    });

    // =========================
    // Calcular ruta barrios (Dijkstra)
    // =========================
    document.getElementById("ruta-form").addEventListener("submit", async e => {
        e.preventDefault();
        const origen = origenSelect.value;
        const destino = destinoSelect.value;

        try {
            const res = await fetch("http://localhost:8080/api/ruta", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ origen, destino })
            });
            const data = await res.json();
            resultadoBarrios.innerHTML = `
                <p><strong>Camino:</strong> ${data.camino.join(" → ")}</p>
                <p><strong>Costo:</strong> ${data.costo}</p>
            `;
        } catch (err) {
            console.error("Error calculando ruta barrios:", err);
            resultadoBarrios.textContent = "No se pudo calcular la ruta.";
        }
    });

    // =========================
    // Calcular ruta atracciones (BFS)
    // =========================
    document.querySelectorAll("button[type='submit']")[1].addEventListener("click", async e => {
        e.preventDefault();
        const origen = origenSelect.value;  // Para simplificar usamos mismos selects de barrios
        const destino = destinoSelect.value;
        const atracciones = Array.from(listaSeleccionadas.children).map(li => li.textContent);

        try {
            const res = await fetch("http://localhost:8080/api/ruta-atracciones", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ origen, destino, atracciones })
            });
            const data = await res.json();
            resultadoAtracciones.innerHTML = `
                <p><strong>Camino:</strong> ${data.camino.join(" → ")}</p>
                <p><strong>Costo:</strong> ${data.costo}</p>
            `;
        } catch (err) {
            console.error("Error calculando ruta atracciones:", err);
            resultadoAtracciones.textContent = "No se pudo calcular la ruta.";
        }
    });
});

