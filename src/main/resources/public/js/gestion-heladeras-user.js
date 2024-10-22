document.addEventListener("DOMContentLoaded", function () {

    // Inicializar el mapa
    const map = L.map('map').setView([-34.61, -58.44], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {maxZoom: 19}).addTo(map);

    // Agregar marcadores para cada heladera desde el DOM (ya renderizado con Handlebars)
    document.querySelectorAll('.heladera-card').forEach(heladeraCard => {
        const latitud = heladeraCard.getAttribute('data-latitud');
        const longitud = heladeraCard.getAttribute('data-longitud');
        const nombre = heladeraCard.getAttribute('data-nombre');
        const direccion = heladeraCard.getAttribute('data-direccion');

        // Agregar marcador en el mapa
        L.marker([latitud, longitud]).addTo(map).bindPopup(`<b>${nombre}</b><br>${direccion}`);

        // Al hacer clic en la tarjeta, centrar el mapa en la heladera
        heladeraCard.addEventListener('click', function (e) {
            if (e.target.tagName === 'A') {
                return;
            }
            e.preventDefault();
            centrarMapaEnHeladera(latitud, longitud);
        });

        // Evitar la propagación del clic para el botón de desuscripción
        const botonDesuscripcion = heladeraCard.querySelector('.boton-desuscribirse');
        if (botonDesuscripcion) {
            botonDesuscripcion.addEventListener('click', function (e) {
                e.stopPropagation(); // Detener la propagación para evitar que el clic en la tarjeta se active
                // Aquí puedes manejar el evento de desuscripción (por ejemplo, enviar una solicitud POST o algo similar)
                console.log('Botón de desuscripción clicado');
            });
        }
    });

    // Función para centrar el mapa en una heladera específica
    function centrarMapaEnHeladera(lat, lng) {
        map.setView([lat, lng], 14);
    }

});

