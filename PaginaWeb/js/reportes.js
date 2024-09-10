document.addEventListener("DOMContentLoaded", function() {
    const heladeraList = document.getElementById("heladera-list");
    const colaboradorList = document.getElementById("colaborador-list");

    // Datos simulados del backend
    const datosReporte = {
        heladeras: [
            {
                nombre: "Heladera 1",
                cantidadFallas: 3,
                viandasRetiradas: 120
            },
            {
                nombre: "Heladera 2",
                cantidadFallas: 1,
                viandasRetiradas: 80
            },
            {
                nombre: "Heladera 3",
                cantidadFallas: 0,
                viandasRetiradas: 50
            }
        ],
        colaboradores: [
            {
                nombre: "Colaborador 1",
                viandasDonadas: 200
            },
            {
                nombre: "Colaborador 2",
                viandasDonadas: 150
            },
            {
                nombre: "Colaborador 3",
                viandasDonadas: 300
            }
        ]
    };

    // Renderizar la lista de heladeras
    function renderizarHeladeras(heladeras) {
        heladeraList.innerHTML = ''; 

        heladeras.forEach(heladera => {
            const item = document.createElement("div");
            item.className = "item";

            const h3 = document.createElement("h3");
            h3.textContent = heladera.nombre;

            const fallas = document.createElement("p");
            fallas.textContent = `Cantidad de fallas: ${heladera.cantidadFallas}`;

            const viandas = document.createElement("p");
            viandas.textContent = `Viandas Retiradas: ${heladera.viandasRetiradas}`;

            item.appendChild(h3);
            item.appendChild(fallas);
            item.appendChild(viandas);

            heladeraList.appendChild(item);
        });
    }

    // Renderizar la lista de colaboradores
    function renderizarColaboradores(colaboradores) {
        colaboradorList.innerHTML = ''; 

        colaboradores.forEach(colaborador => {
            const item = document.createElement("div");
            item.className = "item";

            const h3 = document.createElement("h3");
            h3.textContent = colaborador.nombre;

            const viandas = document.createElement("p");
            viandas.textContent = `Cantidad de viandas Donadas: ${colaborador.viandasDonadas}`;

            item.appendChild(h3);
            item.appendChild(viandas);

            colaboradorList.appendChild(item);
        });
    }

    renderizarHeladeras(datosReporte.heladeras);
    renderizarColaboradores(datosReporte.colaboradores);
});
