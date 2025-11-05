document.addEventListener("DOMContentLoaded", async () => {
    const paradaSelect = document.getElementById("paradaSelect");
    const btnSeleccionar = document.getElementById("btnSeleccionar");
    const btnCalcularRuta = document.getElementById("btnCalcularRuta");
    const puntosContainer = document.getElementById("puntos-container");

    // ==========================
    // Cargar paradas desde Neo4j
    // ==========================
    async function cargarParadas() {
        try {
            const res = await fetch("http://localhost:8080/paradas/listar");
            if (!res.ok) throw new Error("Error al cargar paradas");
            const paradas = await res.json();

            paradas.forEach(p => {
                const opt = document.createElement("option");
                opt.value = p.nombre;
                opt.textContent = p.nombre;
                paradaSelect.appendChild(opt);
            });
        } catch (err) {
            console.error("Error cargando paradas:", err);
            alert("No se pudieron cargar las paradas.");
        }
    }

    // ==========================
    // Mostrar las paradas seleccionadas
    // ==========================
    btnSeleccionar.addEventListener("click", () => {
        const seleccionadas = Array.from(paradaSelect.selectedOptions).map(opt => opt.value);

        if (seleccionadas.length === 0) {
            alert("Seleccioná al menos una parada.");
            return;
        }

        puntosContainer.innerHTML = `
            <h3>Paradas seleccionadas:</h3>
            <ul>
                ${seleccionadas.map(p => `<li>${p}</li>`).join("")}
            </ul>
        `;
    });

    // ==========================
    // Ejemplo: Calcular ruta entre las paradas seleccionadas
    // (si más adelante querés integrarlo con Dijkstra)
    // ==========================
    btnCalcularRuta.addEventListener("click", async () => {
        const seleccionadas = Array.from(paradaSelect.selectedOptions).map(opt => opt.value);

        if (seleccionadas.length < 2) {
            alert("Seleccioná al menos dos paradas para calcular la ruta.");
            return;
        }

        try {
            const res = await fetch("http://localhost:8080/api/ruta", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    origen: seleccionadas[0],
                    destino: seleccionadas[seleccionadas.length - 1]
                })
            });

            const data = await res.json();
            if (data.camino) {
                puntosContainer.innerHTML = `
                    <h3>Ruta calculada:</h3>
                    <p><strong>Camino:</strong> ${data.camino.join(" → ")}</p>
                    <p><strong>Costo:</strong> ${data.costo}</p>
                `;
            } else {
                puntosContainer.innerHTML = "<p>No se pudo calcular la ruta.</p>";
            }
        } catch (err) {
            console.error("Error calculando ruta:", err);
            alert("Ocurrió un error al calcular la ruta.");
        }
    });

    // Inicializar
    cargarParadas();
});
