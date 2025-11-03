document.addEventListener("DOMContentLoaded", () => {
    const barrioSelect = document.getElementById("barrio");
    const form = document.getElementById("presupuesto-form");

    barrios.forEach(b => {
        const option = document.createElement("option");
        option.value = b;
        option.textContent = b;
        barrioSelect.appendChild(option);
    });

    form.addEventListener("submit", e => {
        e.preventDefault();
        const barrio = barrioSelect.value;
        const monto = document.getElementById("monto").value;
        const periodo = document.getElementById("periodo").value;
        const descripcion = document.getElementById("descripcion").value;
        alert(`Presupuesto cargado:\nBarrio: ${barrio}\nMonto: $${monto}\nPeriodo: ${periodo}\nDescripción: ${descripcion}`);
        form.reset();
    });
});
