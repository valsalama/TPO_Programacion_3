document.addEventListener("DOMContentLoaded", () => {
    const barrios = [
    ];

    const puntos = [
    ];

    const contBarrios = document.getElementById("presupuesto-barrios");
    barrios.forEach(b => {
        const div = document.createElement("div");
        div.innerHTML = `<h3>${b.nombre}</h3><p>Presupuesto: $${b.presupuesto.toLocaleString()}</p>`;
        div.style.border = "1px solid #ccc";
        div.style.borderRadius = "8px";
        div.style.padding = "15px";
        div.style.margin = "10px";
        div.style.background = "#ffffff";
        contBarrios.appendChild(div);
    });

    const contPuntos = document.getElementById("distribucion-poi");
    puntos.forEach(p => {
        const div = document.createElement("div");
        div.innerHTML = `<h3>${p.nombre}</h3><p>Barrio: ${p.barrio}</p><p>Presupuesto: $${p.presupuesto.toLocaleString()}</p>`;
        div.style.border = "1px solid #ccc";
        div.style.borderRadius = "8px";
        div.style.padding = "15px";
        div.style.margin = "10px";
        div.style.background = "#ffffff";
        contPuntos.appendChild(div);
    });
});
