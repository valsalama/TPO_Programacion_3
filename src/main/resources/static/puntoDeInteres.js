document.addEventListener("DOMContentLoaded", () => {
    const puntos = [
    ];

    const contenedor = document.getElementById("puntos-container");

    puntos.forEach(p => {
        const div = document.createElement("div");
        div.innerHTML = `<h3>${p.nombre}</h3><p><strong>Tipo:</strong> ${p.tipo}</p><p><strong>Barrio:</strong> ${p.barrio}</p>`;
        div.style.border = "1px solid #ccc";
        div.style.borderRadius = "8px";
        div.style.padding = "15px";
        div.style.margin = "15px auto";
        div.style.maxWidth = "500px";
        div.style.background = "#ffffff";
        contenedor.appendChild(div);
    });
});
