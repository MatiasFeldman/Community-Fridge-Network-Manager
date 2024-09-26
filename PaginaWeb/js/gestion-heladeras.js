document.addEventListener("DOMContentLoaded", function() {
    //rol
    const userRole = localStorage.getItem("userRole"); 
    //heladera
    const listaHeladeras = document.getElementById('lista-heladeras');
    //suscripcion
    const suscripcionContainer = document.getElementById('suscripcion-container');
    const formSuscripcion = document.getElementById('form-suscripcion');
    const medioNotificacion = document.getElementById('medioNotificacion');
    const contactoAdicional = document.getElementById('contactoAdicional');
    const contactoAdicionalInput = document.getElementById('contactoAdicionalInput');
    const tipoContacto = document.getElementById('tipoContacto');
    //filtro
    const formFilter = document.getElementById('filter-form');
    const filtroActivo = document.getElementById('filtro-activo');
    const filtroInactivo = document.getElementById('filtro-inactivo');
    const filtroFalla = document.getElementById('filtro-falla');
    const filtroAlerta = document.getElementById('filtro-alerta');
    const filtroTemperatura = document.getElementById('filtro-temperatura');
    const filtroFraude = document.getElementById('filtro-fraude');
    const filtroConexion = document.getElementById('filtro-conexion');

    let heladeraSeleccionada = null;
    let nConfiguradoMin = null;
    let nConfiguradoMax = null;

    // Datos simulados del backend para las heladeras
    const heladeras = [
        { id: 1, nombre: 'Heladera UTN Lugano', direccion: 'Mozart 2300', viandas: 50, estado: 'activa', alerta: null, lat: -34.662, lng: -58.469, suscrito: false },
        { id: 2, nombre: 'Heladera Parque Patricios', direccion: 'Pepiri 1234', viandas: 30, estado: 'inactiva', alerta: 'falla', lat: -34.634, lng: -58.392, suscrito: true },
        { id: 3, nombre: 'Heladera Villa Urquiza', direccion: 'Avenida Triunvirato 4000', viandas: 20, estado: 'inactiva', alerta: 'temperatura', lat: -34.573, lng: -58.475, suscrito: false },
        { id: 4, nombre: 'Heladera Flores', direccion: 'Nazca 2000', viandas: 70, estado: 'activa', alerta: 'conexion', lat: -34.625, lng: -58.467, suscrito: true },
        { id: 5, nombre: 'Heladera UTN Lugano', direccion: 'Mozart 2300', viandas: 50, estado: 'activa', alerta: null, lat: -34.662, lng: -58.469, suscrito: false },
        { id: 6, nombre: 'Heladera Parque Patricios', direccion: 'Pepiri 1234', viandas: 30, estado: 'inactiva', alerta: 'falla', lat: -34.634, lng: -58.392, suscrito: true },
        { id: 7, nombre: 'Heladera Villa Urquiza', direccion: 'Avenida Triunvirato 4000', viandas: 20, estado: 'inactiva', alerta: 'temperatura', lat: -34.573, lng: -58.475, suscrito: false },
        { id: 8, nombre: 'Heladera Flores', direccion: 'Nazca 2000', viandas: 70, estado: 'activa', alerta: 'conexion', lat: -34.625, lng: -58.467, suscrito: true }
    ];

    const contactosGuardados = { email: "colaborador@example.com", whatsapp: null, telegram: null };

    // Inicializar el mapa
    const map = L.map('map').setView([-34.61, -58.44], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19 }).addTo(map);

    // Función para renderizar las heladeras
    function renderizarHeladeras(heladerasAListar = heladeras) {
        listaHeladeras.innerHTML = '';  // Limpiar la lista existente
        heladerasAListar.forEach(heladera => {
            const item = document.createElement('div');
            item.className = "p-3 d-flex flex-column justify-content-between rounded heladera-card";
            item.innerHTML = `
                    <div class="d-flex justify-content-between">
                        <h5 class="text-dark fs-5">${heladera.nombre}</h5>
                        <span class="${heladera.estado === 'activa' ? 'estado-activa' : 'estado-inactiva'}">${heladera.estado === 'activa' ? 'Activa' : 'Inactiva'}</span>
                    </div>
                    <div>
                        <p class="text-black fs-5 fw-semibold mb-1">${heladera.direccion}</p>
                        <p class="fs-6 fw-medium text-body-tertiary mb-2">Viandas disponibles: ${heladera.viandas}</p>
                        ${userRole ? `<button class="boton ${heladera.suscrito && "boton-desuscribirse"} suscribirse-btn">${heladera.suscrito ? 'Desuscribirse' : 'Suscribirse'}</button>` : ''}
                    </div>
            `;

            item.addEventListener('click', function(e) {
                e.preventDefault();
                centrarMapaEnHeladera(heladera.lat, heladera.lng);
            });

            //  botón de suscripción/desuscripción solo aparece si el usuario ha iniciado sesión
            if (userRole) {
                const btnSuscribirse = item.querySelector('.suscribirse-btn');
                btnSuscribirse.addEventListener('click', function(e) {
                    e.preventDefault();
                    heladeraSeleccionada = heladera;

                    // Si el usuario ya está suscrito, manejar la desuscripción
                    if (heladera.suscrito) {
                        desuscribirse(heladera);
                    } else {
                        mostrarFormularioSuscripcion(heladera);
                    }
                });
            }

            listaHeladeras.appendChild(item);
            L.marker([heladera.lat, heladera.lng]).addTo(map).bindPopup(`<b>${heladera.nombre}</b><br>${heladera.direccion}`);
        });
    }

    // Función para desuscribirse
    function desuscribirse(heladera) {
        //aca le mandamos la info al backend para que sepa que se desuscribio
        heladera.suscrito = false;
        alert(`Has desuscrito de ${heladera.nombre}`);
        renderizarHeladeras(); // actualizamos lista de heladeras 
    }

    // formulario de suscripción no aparece si no inicio sesión
    if (!userRole) {
        suscripcionContainer.style.display = 'none'; 
    }

    // Centrar el mapa en una heladera específica
    function centrarMapaEnHeladera(lat, lng) {
        map.setView([lat, lng], 14);
    }

    // Mostrar el formulario de suscripción
    function mostrarFormularioSuscripcion(heladera) {
        suscripcionContainer.style.display = 'block';
        formSuscripcion.dataset.heladeraId = heladera.id;
    }

    // Mostrar/ocultar el campo de configuración para N (número de viandas) dinámicamente usando modal
    const notificacionViandasMin = document.getElementById('notificacionViandasMin');
    const notificacionViandasMax = document.getElementById('notificacionViandasMax');

    notificacionViandasMin.addEventListener('change', function() {
        if (this.checked) {
            abrirModalConfigurarN('min');
        } else {
            nConfiguradoMin = null; 
        }
    });

    notificacionViandasMax.addEventListener('change', function() {
        if (this.checked) {
            abrirModalConfigurarN('max');
        } else {
            nConfiguradoMax = null; 
        }
    });

    // Función para abrir el modal y configurar N
    function abrirModalConfigurarN(tipo) {
        const modal = new bootstrap.Modal(document.getElementById('configurarNModal'));
        document.getElementById('guardarNValue').onclick = function() {
            const nValue = document.getElementById('nValueInput').value;
            if (nValue) {
                if (tipo === 'min') {
                    nConfiguradoMin = nValue;
                    alert(`Valor de N para mínimas configurado: ${nConfiguradoMin}`);
                } else if (tipo === 'max') {
                    nConfiguradoMax = nValue;
                    alert(`Valor de N para máximas configurado: ${nConfiguradoMax}`);
                }
            }
            const modalInstance = bootstrap.Modal.getInstance(document.getElementById('configurarNModal'));
            modalInstance.hide(); // Cerrar el modal
        };
        modal.show();
    }

    // Cambiar el tipo de contacto según el medio seleccionado
    medioNotificacion.addEventListener('change', function() {
        const medio = medioNotificacion.value;
        if (contactosGuardados[medio]) {
            contactoAdicional.style.display = 'none';
        } else {
            tipoContacto.textContent = medio;
            contactoAdicionalInput.placeholder = `Ingrese su ${medio}`;
            contactoAdicional.style.display = 'block';
        }
    });

    // Enviar el formulario de suscripción
    formSuscripcion.addEventListener('submit', function(event) {
        event.preventDefault();
        const medio = medioNotificacion.value;
        const contacto = contactoAdicionalInput.value;

        //usamos console.log para simular el envio de datos al backend
        console.log(`Heladera ID: ${heladeraSeleccionada.id}`);
        console.log(`Notificar por: ${medio} (${contacto})`);
        if (nConfiguradoMin) {
            console.log(`Valor de N para mínimas: ${nConfiguradoMin}`);
        }
        if (nConfiguradoMax) {
            console.log(`Valor de N para máximas: ${nConfiguradoMax}`);
        }

        heladeraSeleccionada.suscrito = true;
        renderizarHeladeras();
    });

    // Aplicar filtro de heladeras
    formFilter.addEventListener('submit', function(event) {
        event.preventDefault();

        const heladerasFiltradas = heladeras.filter(heladera => {
            let pasaFiltro = true;

            // Filtrar por estado (activa/inactiva)
            if (filtroActivo.checked && heladera.estado !== 'activa') {
                pasaFiltro = false;
            }
            if (filtroInactivo.checked && heladera.estado !== 'inactiva') {
                pasaFiltro = false;
            }

            if (filtroInactivo.checked) {
                // Filtrar por fallas técnicas
                if (filtroFalla.checked && heladera.alerta !== 'falla') {
                    pasaFiltro = false;
                }

                // Filtrar por alertas específicas
                if (filtroAlerta.checked) {
                    const alertasSeleccionadas = [];
                    if (filtroTemperatura.checked) alertasSeleccionadas.push('temperatura');
                    if (filtroFraude.checked) alertasSeleccionadas.push('fraude');
                    if (filtroConexion.checked) alertasSeleccionadas.push('conexion');

                    // Verificar si la heladera tiene alguna de las alertas seleccionadas
                    if (alertasSeleccionadas.length > 0 && !alertasSeleccionadas.includes(heladera.alerta)) {
                        pasaFiltro = false;
                    }
                }
            }

            return pasaFiltro;
        });

        // Renderizar las heladeras filtradas
        renderizarHeladeras(heladerasFiltradas);
    });

    // Mostrar/ocultar filtros adicionales
    const filtroInactivosDiv = document.getElementById('filtros-inactivos');
    const filtroAlertasDiv = document.getElementById('filtros-alerta');

    filtroInactivo.addEventListener('change', function () {
        filtroInactivosDiv.style.display = this.checked ? 'block' : 'none';
    });

    filtroAlerta.addEventListener('change', function () {
        filtroAlertasDiv.style.display = this.checked ? 'block' : 'none';
    });

    // Inicializar heladeras en el mapa y lista
    renderizarHeladeras();
});
