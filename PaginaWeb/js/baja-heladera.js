document.addEventListener("DOMContentLoaded", function() {
    const bajaBtn = document.getElementById("baja-btn");
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

    bajaBtn.addEventListener("click", function() {
        const nombreHeladera = document.getElementById("heladera-nombre").value.trim();

        // Verificar si la heladera existe
        const heladera = buscarHeladera(nombreHeladera);

        if (heladera) {
            heladeraSeleccionada = heladera;
            // Aca mandariamos la solicitud al backend para dar de baja la heladera
            alert(`Dando de baja heladera con ID: ${heladeraSeleccionada.id}`);
            // Simulación de la baja
            heladeraSeleccionada = null;
            document.getElementById("heladera-nombre").value = '';
            errorMessage.style.display = "none";
        } else {
            errorMessage.style.display = "block";
        }
    });
});
