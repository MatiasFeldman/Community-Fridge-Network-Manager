document.addEventListener("DOMContentLoaded", function () {
    const modificarHeladeraSection = document.getElementById("modificar-heladera-container");
    const modificarBtn = document.getElementById("modificar-btn");
    const eliminarBtn = document.getElementById("eliminar-btn");
    const activaBtn = document.getElementById("activa-btn");

    const modificarEstadoHeladera = (id, activa) => {
        if (activa === 'Activar') {
            activa = 'true';
        } else {
            activa = 'false';
        }
        const data = {
            id: id,
            activa: activa
        };
        fetch('/heladeras/modificar', {
            method: 'PUT', headers: {
                'Content-Type': 'application/json',
            }, body: JSON.stringify(data),
        }).then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text || 'Error en la solicitud');
                });
            }
            return response.text();
        })
            .then(data => {
                activa === 'true' ? alert('Heladera activada') : alert('Heladera desactivada');
                window.location.reload();
            })
            .catch((error) => {
                // Manejo de errores
                console.error('Error:', error.message);
                alert('No se pudo eliminar la heladera: ' + error.message);
            });

    }

    const eliminarHeladera = (id) => {
        const data = {
            id: id
        };

        fetch('/heladeras/modificar', {
            method: 'DELETE', headers: {
                'Content-Type': 'application/json',
            }, body: JSON.stringify(data),
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text || 'Error en la solicitud');
                    });
                }
                return response.text();
            })
            .then(data => {
                console.log('Success:', data);
                window.location.reload();
            })
            .catch((error) => {
                // Manejo de errores
                console.error('Error:', error.message);
                alert('No se pudo eliminar la heladera: ' + error.message);
            });
    }

    const modificarHeladera = (id, nombre, capacidadMaxima, capacidadActual, direccion) => {
        const data = {
            id: id,
            nombre: nombre,
            capacidadMaxima: capacidadMaxima,
            capacidadActual: capacidadActual,
            direccion: direccion,
        };

        fetch('/heladeras/modificar', {
            method: 'POST', headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text || 'Error en la solicitud');
                    });
                }
                return response.text();
            })
            .then(data => {
                alert("Heladera modificada correctamente")
                window.location.reload();
            })
            .catch((error) => {
                // Manejo de errores
                console.error('Error:', error.message);
                alert('No se pudo modificar la heladera: ' + error.message);
            });
    }

    eliminarBtn.addEventListener("click", function () {
        const id = eliminarBtn.getAttribute('data-id');
        eliminarHeladera(id)
    });

    activaBtn.addEventListener("click", () => {
        const activa = activaBtn.innerText;
        const id = activaBtn.getAttribute('data-id');
        modificarEstadoHeladera(id, activa);

    });
    // Simulación de datos recibidos del backend
    const heladeras = Array.from(document.getElementsByClassName('heladera-item'))


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

        activaBtn.innerText = activa === 'true' ? 'Desactivar' : 'Activar';
        activaBtn.classList = activa === 'true' ? 'boton boton-desactivar' : 'boton';

        // Rellenar los campos del formulario con los datos de la heladera seleccionada
        document.getElementById("nombre").value = nombre_heladera;
        document.getElementById("capacidadMaxima").value = capacidad_maxima;
        document.getElementById("cantidadViandas").value = capacidad_maxima - capacidad_actual;
        document.getElementById("direccion").value = direccion_heladera;

        // Mostrar la sección de modificación de la heladera
        modificarHeladeraSection.classList.remove("d-none");
        modificarHeladeraSection.scrollIntoView({behavior: 'smooth'});  // Para enfocar la sección de modificación

        // Guardar el ID de la heladera seleccionada
        modificarBtn.dataset.id = id;
        eliminarBtn.setAttribute('data-id', id)
        activaBtn.setAttribute('data-id', id)
    }

    // Enviar datos al backend
    modificarBtn.addEventListener("click", function () {
        const id = modificarBtn.dataset.id;
        const nombre = document.getElementById("nombre").value;
        const capacidadMaxima = document.getElementById("capacidadMaxima").value;
        const cantidadViandas = document.getElementById("cantidadViandas").value;
        const direccion = document.getElementById("direccion").value;

        modificarHeladera(id, nombre, capacidadMaxima, capacidadMaxima - cantidadViandas, direccion)
    });
});
