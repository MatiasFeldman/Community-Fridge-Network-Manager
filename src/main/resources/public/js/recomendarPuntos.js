document.getElementById("recomendacionForm").addEventListener("submit", function(event) {
    event.preventDefault();  // Evitar el envío tradicional del formulario

    // Obtener los valores del formulario
    const latitud = document.querySelector("input[name='latitud']").value;
    const longitud = document.querySelector("input[name='longitud']").value;
    const radio = document.querySelector("input[name='radio']").value;

    // Crear los datos para la solicitud POST
    const formData = new URLSearchParams();
    formData.append("latitud", latitud);
    formData.append("longitud", longitud);
    formData.append("radio", radio);

    // Hacer la solicitud POST
    fetch("/recomendar-puntos", {
        method: "POST",
        body: formData
    })
        .then(response => response.json())  // Convertir la respuesta en JSON
        .then(data => {
            // Limpiar la lista anterior de coordenadas
            const coordenadasList = document.getElementById("coordenadasList");
            coordenadasList.innerHTML = '';

            // Mostrar el contenedor de recomendaciones (que estaba oculto)
            document.getElementById("recomendacionesContainer").style.display = 'block';

            // Agregar las coordenadas recomendadas a la lista
            data.forEach(coordenada => {
                const listItem = document.createElement("li");
                listItem.textContent = `Latitud: ${coordenada.latitud}, Longitud: ${coordenada.longitud}`;
                listItem.classList.add("list-group-item");
                coordenadasList.appendChild(listItem);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Hubo un error al obtener las coordenadas recomendadas.");
        });
});