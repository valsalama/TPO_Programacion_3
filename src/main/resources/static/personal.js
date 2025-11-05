const API_BASE = "http://localhost:8080/api";
const tableBody = document.getElementById("personal-body");
const sortSelect = document.getElementById("sort-select");
const errorDiv = document.getElementById("error");

function buildUrl() {
    const sort = sortSelect.value;
    console.log("Orden seleccionado:", sort);  // 👈 ver qué devuelve
    if (!sort || sort === "legajo") {
        return `${API_BASE}/personal`;
    }
    return `${API_BASE}/personal?sort=${encodeURIComponent(sort)}`;
}

async function fetchAndRender() {
    try {
        const response = await fetch(buildUrl());
        const data = await response.json();
        renderTable(data);
    } catch (err) {
        console.error("Error al cargar personal:", err);
        showError("Ocurrió un error al cargar la lista. Reintentá más tarde.");
    }
}

function showError(msg) {
    errorDiv.textContent = msg;
    errorDiv.style.display = "block";
}

function renderTable(lista) {
    tableBody.innerHTML = "";

    if (!lista || lista.length === 0) {
        showError("No hay registros de personal para mostrar.");
        return;
    }

    errorDiv.style.display = "none";

    lista.forEach(per => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${per.legajo}</td>
            <td>${per.nombreApellido}</td>
            <td>${per.edad}</td>
            <td>${per.barrio}</td>
            <td>${per.sexo}</td>
            <td>${per.departamento}</td>
            <td>${per.dni}</td>
            <td>${per.nacionalidad}</td>
        `;
        tableBody.appendChild(row);
    });
}

document.addEventListener("DOMContentLoaded", fetchAndRender);
sortSelect.addEventListener("change", fetchAndRender);
