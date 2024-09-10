document.addEventListener("DOMContentLoaded", function() {
    const heladeraList = document.getElementById("heladera-list");
    const modificarHeladeraSection = document.getElementById("modificar-heladera-container");
    const modificarBtn = document.getElementById("modificar-btn");

    // Simulación de datos recibidos del backend
    const heladeras = [
        {
            id: 1,
            nombre: "Heladera 1",
            calle: "Calle 1",
            altura: "123",
            capacidadMaxima: "100",
            cantidadViandas: "50",
            comuna: "Comuna 1"
        },
        {
            id: 2,
            nombre: "Heladera 2",
            calle: "Calle 2",
            altura: "456",
            capacidadMaxima: "200",
            cantidadViandas: "120",
            comuna: "Comuna 2"
        },
        {
            id: 3,
            nombre: "Heladera 3",
            calle: "Calle 3",
            altura: "789",
            capacidadMaxima: "300",
            cantidadViandas: "200",
            comuna: "Comuna 3"
        },
        {
            id: 4,
            nombre: "Heladera 4",
            calle: "Calle 4",
            altura: "789",
            capacidadMaxima: "300",
            cantidadViandas: "200",
            comuna: "Comuna 4"
        }
    ];


    // Mostrar lista de heladeras
    heladeras.forEach(heladera => {
        const heladeraItem = document.createElement("div");
        heladeraItem.className = "heladera-item p-3 mb-4 rounded shadow d-flex flex-column justify-content-between";
        heladeraItem.dataset.id = heladera.id;

        const h3 = document.createElement("h3");
        h3.textContent = heladera.nombre;

        const pCalleAltura = document.createElement("p");
        pCalleAltura.textContent = `Direccion: ${heladera.calle}, ${heladera.altura}`;

        heladeraItem.appendChild(h3);
        heladeraItem.appendChild(pCalleAltura);

        heladeraItem.addEventListener("click", function() {
            seleccionarHeladera(heladera.id);
        });

        heladeraList.appendChild(heladeraItem);
    });

    function seleccionarHeladera(id) {
        // Encontrar la heladera seleccionada
        const heladera = heladeras.find(h => h.id === id);

        // Rellenar los campos del formulario con los datos de la heladera seleccionada
        document.getElementById("nombre").value = heladera.nombre;
        document.getElementById("capacidadMaxima").value = heladera.capacidadMaxima;
        document.getElementById("cantidadViandas").value = heladera.cantidadViandas || 0;
        document.getElementById("calle").value = heladera.calle;
        document.getElementById("altura").value = heladera.altura;
        document.getElementById("comuna").value = heladera.comuna;

        // Mostrar la sección de modificación de la heladera
        modificarHeladeraSection.classList.remove("d-none");
        modificarHeladeraSection.scrollIntoView({ behavior: 'smooth' });  // Para enfocar la sección de modificación

        // Guardar el ID de la heladera seleccionada
        modificarBtn.dataset.id = heladera.id;
    }

    // Enviar datos al backend
    modificarBtn.addEventListener("click", function() {
        const id = modificarBtn.dataset.id;
        const nombre = document.getElementById("nombre").value;
        const capacidadMaxima = document.getElementById("capacidadMaxima").value;
        const cantidadViandas = document.getElementById("cantidadViandas").value;
        const calle = document.getElementById("calle").value;
        const altura = document.getElementById("altura").value;
        const comuna = document.getElementById("comuna").value;

        const datosModificados = {
            id: id,
            nombre: nombre,
            capacidadMaxima: capacidadMaxima,
            cantidadViandas: cantidadViandas,
            calle: calle,
            altura: altura,
            comuna: comuna
        };

        // aca enviariamos los datos modificados al backend 
        console.log("Datos modificados enviados:", datosModificados);
    });
});
