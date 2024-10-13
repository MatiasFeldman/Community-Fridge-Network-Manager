// Inicializar el mapa
// Define un nuevo icono para tu ubicación actual

document.addEventListener("DOMContentLoaded", function () {
    const currentLocationIcon = L.icon({
        iconUrl: '../imagenes/marker_red.png',
        iconSize: [25, 30], // Tamaño del icono
        iconAnchor: [12, 41], // Punto en el que el icono se ancla
        popupAnchor: [1, -34], // Punto en el que el popup se ancla al icono
    });

    const latitud = parseFloat(document.getElementById('map').dataset.latitud);
    const longitud = parseFloat(document.getElementById('map').dataset.longitud);
    const lugares = JSON.parse(document.getElementById('map').dataset.lugares);
    console.log('Lugares:', lugares);
    const map = L.map('map').setView([latitud, longitud], 12);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    L.marker([latitud, longitud], { icon: currentLocationIcon }).addTo(map)
        .bindPopup('Tu ubicación actual');

    lugares.forEach(lugar => {
        const latitudLugar = lugar.coordenadas.latitud;
        const longitudLugar = lugar.coordenadas.longitud;

        if (latitudLugar !== undefined && longitudLugar !== undefined) {
            L.marker([latitudLugar, longitudLugar]).addTo(map)
                .bindPopup(`<strong>${lugar.nombre}</strong><br>${lugar.direccion}`);
        } else {
            console.error('Latitud o longitud indefinida para:', lugar);
        }
    });

    const lugarItems = document.querySelectorAll('.lugar-item');
    lugarItems.forEach((lugarItem, index) => {
        lugarItem.addEventListener('click', () => {
            const lugar = lugares[index];
            const latitudLugar = lugar.coordenadas.latitud;
            const longitudLugar = lugar.coordenadas.longitud;

            if (latitudLugar !== undefined && longitudLugar !== undefined) {
                map.panTo([latitudLugar, longitudLugar]);
            } else {
                console.error('Latitud o longitud indefinida para:', lugar);
            }
        });
    });
});