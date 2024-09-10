document.addEventListener("DOMContentLoaded", function() {
    const eliminarBtn = document.getElementById("eliminar-btn");
    const confirmarBtn = document.getElementById("confirmar-btn");
    const cancelarBtn = document.getElementById("cancelar-btn");
    const inputContainer = document.getElementById("input-container");
    const confirmationContainer = document.getElementById("confirmation-container");
    const errorMessage = document.getElementById("error-message");
    let heladeraSeleccionada = null;

    // Datos simulados que vendrían del backend
    const heladeras = [
        { id: 1, nombre: "Heladera 1" },
        { id: 2, nombre: "Heladera 2" },
        { id: 3, nombre: "Heladera 3" }
    ];

    // Función para buscar la heladera por nombre
    function buscarHeladera(nombre) {
        return heladeras.find(heladera => heladera.nombre.toLowerCase() === nombre.toLowerCase());
    }

    eliminarBtn.addEventListener("click", function() {
        const nombreHeladera = document.getElementById("heladera-nombre").value.trim();

        const heladera = buscarHeladera(nombreHeladera);

        if (heladera) {
            heladeraSeleccionada = heladera;
            inputContainer.style.display = "none";
            confirmationContainer.style.display = "block";
        } else {
            errorMessage.style.display = "block";
        }
    });

    confirmarBtn.addEventListener("click", function() {
        if (heladeraSeleccionada) {
            // aca enviamos la solicitud al backend para eliminar la heladera
            alert(`Eliminando heladera con ID: ${heladeraSeleccionada.id}`);
            // Simulación de la eliminación
            heladeraSeleccionada = null;
            inputContainer.style.display = "block";
            confirmationContainer.style.display = "none";
            document.getElementById("heladera-nombre").value = '';
            errorMessage.style.display = "none";
        }
    });

    // clic para cancelar
    cancelarBtn.addEventListener("click", function() {
        heladeraSeleccionada = null;
        inputContainer.style.display = "block";
        confirmationContainer.style.display = "none";
        document.getElementById("heladera-nombre").value = '';
        errorMessage.style.display = "none";
    });
});
