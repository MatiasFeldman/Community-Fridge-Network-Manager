document.addEventListener("DOMContentLoaded", function () {
    const heladeraList = document.getElementById("heladera-list");
    const modificarHeladeraSection = document.getElementById("modificar-heladera-container");
    const modificarBtn = document.getElementById("modificar-btn");

    // Simulación de datos recibidos del backend
    const heladeras = Array.from(document.getElementsByClassName('heladera-item'))
    console.log(heladeras)


    // Mostrar lista de heladeras
    heladeras.forEach(heladera => {
        const id = heladera.getAttribute('id')

        heladera.addEventListener("click", function () {
            seleccionarHeladera(id);
        });
    });

    function seleccionarHeladera(id) {
        // Encontrar la heladera seleccionada
        const heladera = document.getElementById(id)

        const nombre_heladera = heladera.getAttribute('data-nombre');
        const direccion_heladera = heladera.getAttribute('data-direccion')
        const capacidad_actual = heladera.getAttribute('data-capacidad-actual')
        const capacidad_maxima = heladera.getAttribute('data-capacidad-maxima')
        const activa = heladera.getAttribute('data-activa')


        console.log(heladera)

        // Rellenar los campos del formulario con los datos de la heladera seleccionada
        document.getElementById("nombre").value = nombre_heladera;
        document.getElementById("capacidadMaxima").value = capacidad_maxima;
        document.getElementById("cantidadViandas").value = capacidad_maxima - capacidad_actual;
        document.getElementById("direccion").value = direccion_heladera;
        document.getElementById("activa").value = activa;

        // Mostrar la sección de modificación de la heladera
        modificarHeladeraSection.classList.remove("d-none");
        modificarHeladeraSection.scrollIntoView({behavior: 'smooth'});  // Para enfocar la sección de modificación

        // Guardar el ID de la heladera seleccionada
        modificarBtn.dataset.id = heladera.id;
    }

    // Enviar datos al backend
    modificarBtn.addEventListener("click", function () {
        const id = modificarBtn.dataset.id;
        const nombre = document.getElementById("nombre").value;
        const capacidadMaxima = document.getElementById("capacidadMaxima").value;
        const cantidadViandas = document.getElementById("cantidadViandas").value;
        const direccion = document.getElementById("direccion").value;
        const activa = document.getElementById("activa").value;

        const datosModificados = {
            id: id,
            nombre: nombre,
            capacidadMaxima: capacidadMaxima,
            capacidadActual: capacidadMaxima - cantidadViandas,
            direccion: direccion,
            activa: activa
        };

        // aca enviariamos los datos modificados al backend
        console.log("Datos modificados enviados:", datosModificados);
    });
});
