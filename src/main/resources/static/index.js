document.addEventListener("DOMContentLoaded", () => {
    const barrios = [
    ];

    const main = document.querySelector("main");

    const contenedor = document.createElement("div");
    contenedor.style.maxWidth = "800px";
    contenedor.style.margin = "20px auto";
    contenedor.style.display = "flex";
    contenedor.style.flexDirection = "column";
    contenedor.style.gap = "15px";

    barrios.forEach(b => {
        const div = document.createElement("div");
        div.style.border = "1px solid #ccc";
        div.style.borderRadius = "8px";
        div.style.padding = "15px";
        div.style.background = "#ffffff";
        div.innerHTML = `<h3>${b.nombre}</h3><p>${b.descripcion}</p>`;
        contenedor.appendChild(div);
    });

    main.appendChild(contenedor);
});
