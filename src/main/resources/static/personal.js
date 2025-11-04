document.addEventListener("DOMContentLoaded", () => {
    const personal = [
        
    ];

    const main = document.querySelector("main");

    const contenedor = document.createElement("div");
    contenedor.style.maxWidth = "800px";
    contenedor.style.margin = "20px auto";
    contenedor.style.display = "flex";
    contenedor.style.flexDirection = "column";
    contenedor.style.gap = "15px";

    personal.forEach(p => {
        const div = document.createElement("div");
        div.style.border = "1px solid #ccc";
        div.style.borderRadius = "8px";
        div.style.padding = "15px";
        div.style.background = "#ffffff";
        div.innerHTML = `<h3>${p.nombre}</h3><p>${p.cargo}</p><p>Contacto: <a href="mailto:${p.email}">${p.email}</a></p>`;
        contenedor.appendChild(div);
    });

    main.appendChild(contenedor);
});
