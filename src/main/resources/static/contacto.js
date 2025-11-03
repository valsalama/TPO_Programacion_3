document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");

    form.addEventListener("submit", e => {
        e.preventDefault();
        const nombre = document.getElementById("nombre").value;
        const email = document.getElementById("email").value;
        const mensaje = document.getElementById("mensaje").value;
        alert(`Gracias por tu mensaje, ${nombre}!\nEmail: ${email}\nMensaje: ${mensaje}`);
        form.reset();
    });
});
