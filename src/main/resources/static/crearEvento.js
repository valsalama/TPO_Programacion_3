document.addEventListener("DOMContentLoaded", async () => {
    const barrioSelect = document.getElementById("barrio");
    const plazaSelect = document.getElementById("plaza");
    const form = document.getElementById("crear-evento-form");
    const mensajeDiv = document.getElementById("mensaje");

    // 🔹 Cargar barrios desde Neo4j
    try {
        const barrios = await fetch("http://localhost:8080/barrios/listar").then(r => r.json());
        if (Array.isArray(barrios)) {
            barrios.forEach(b => {
                const opt = document.createElement("option");
                opt.value = b.nombre;
                opt.textContent = b.nombre;
                barrioSelect.appendChild(opt);
            });
        }
    } catch (err) {
        console.error("Error al cargar barrios:", err);
    }

    // 🔹 Cargar plazas cuando cambia el barrio
    barrioSelect.addEventListener("change", async () => {
        plazaSelect.innerHTML = '<option value="">Seleccione una plaza</option>';
        if (!barrioSelect.value) return;

        try {
            const plazas = await fetch(`http://localhost:8080/plazas/porBarrio?barrio=${encodeURIComponent(barrioSelect.value)}`)
                .then(r => r.json());
            if (Array.isArray(plazas)) {
                plazas.forEach(p => {
                    const opt = document.createElement("option");
                    opt.value = p.nombre;
                    opt.textContent = p.nombre;
                    plazaSelect.appendChild(opt);
                });
            }
        } catch (err) {
            console.error("Error al cargar plazas:", err);
        }
    });

    // 🔹 Enviar formulario para crear evento
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const nombre = document.getElementById("nombre").value;
        const barrio = barrioSelect.value;
        const plaza = plazaSelect.value;
        const fecha = document.getElementById("fecha").value;

        if (!nombre || !barrio || !plaza || !fecha) {
            mensajeDiv.textContent = "Por favor complete todos los campos.";
            mensajeDiv.style.color = "red";
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/eventos/crear", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: new URLSearchParams({ nombre, barrio, plaza, fecha })
            });

            const mensaje = await response.text();
            mensajeDiv.textContent = mensaje;
            mensajeDiv.style.color = "green";
            form.reset();
            plazaSelect.innerHTML = '<option value="">Seleccione una plaza</option>';

        } catch (err) {
            console.error("Error al crear evento:", err);
            mensajeDiv.textContent = "Error al crear el evento. Intente nuevamente.";
            mensajeDiv.style.color = "red";
        }
    });
});

