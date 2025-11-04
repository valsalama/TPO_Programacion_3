document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("crear-evento-form");
    const mensajeDiv = document.getElementById("mensaje");

    const barrioSelect = document.getElementById("barrio");
    barrios.forEach(b => {
        const option = document.createElement("option");
        option.value = b;
        option.textContent = b;
        barrioSelect.appendChild(option);
    });

    form.addEventListener("submit", e => {
        e.preventDefault();
        const nombre = document.getElementById("nombre").value;
        const descripcion = document.getElementById("descripcion").value;
        const fecha = document.getElementById("fecha").value;
        const hora = document.getElementById("hora").value;
        const barrio = document.getElementById("barrio").value;
        mensajeDiv.textContent = `Evento "${nombre}" creado en ${barrio} el ${fecha} a las ${hora}.`;
        form.reset();
    });
});
