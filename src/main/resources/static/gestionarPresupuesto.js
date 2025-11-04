document.addEventListener("DOMContentLoaded", () => {
    const barrios = [
    ];

    const barrioSelect = document.getElementById("barrio");
    const presupuestoTotalInput = document.getElementById("presupuesto-total");
    const puntosList = document.getElementById("puntos-interes-list");
    const presupuestoAsignadoSpan = document.getElementById("presupuesto-asignado");
    const presupuestoRestanteSpan = document.getElementById("presupuesto-restante");

    barrios.forEach((b, i) => {
        const option = document.createElement("option");
        option.value = i;
        option.textContent = b.nombre;
        barrioSelect.appendChild(option);
    });

    function actualizarPuntos() {
        const b = barrios[barrioSelect.value];
        presupuestoTotalInput.value = `$${b.presupuesto}`;
        puntosList.innerHTML = "";
        b.puntos.forEach(p => {
            const div = document.createElement("div");
            div.style.display = "flex";
            div.style.justifyContent = "space-between";
            div.style.marginBottom = "8px";
            const label = document.createElement("label");
            label.textContent = p;
            const input = document.createElement("input");
            input.type = "number";
            input.min = 0;
            input.value = 0;
            input.style.width = "100px";
            input.addEventListener("input", calcularPresupuesto);
            div.appendChild(label);
            div.appendChild(input);
            puntosList.appendChild(div);
        });
        calcularPresupuesto();
    }

    function calcularPresupuesto() {
        const b = barrios[barrioSelect.value];
        let totalAsignado = 0;
        puntosList.querySelectorAll("input").forEach(input => {
            totalAsignado += Number(input.value) || 0;
        });
        presupuestoAsignadoSpan.textContent = `$${totalAsignado}`;
        presupuestoRestanteSpan.textContent = `$${b.presupuesto - totalAsignado}`;
    }

    barrioSelect.addEventListener("change", actualizarPuntos);
    actualizarPuntos();

    document.getElementById("gestionar-form").addEventListener("submit", e => {
        e.preventDefault();
        alert("Distribución guardada");
    });
});
