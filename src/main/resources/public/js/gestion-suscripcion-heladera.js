document.addEventListener("DOMContentLoaded", function () {
    // Heladera
    const listaHeladeras = document.getElementById('lista-heladeras');
    // Suscripción
    const suscripcionContainer = document.getElementById('suscripcion-container');
    const formSuscripcion = document.getElementById('form-suscripcion');
    const medioNotificacion = document.getElementById('medioNotificacion');
    const contactoAdicional = document.getElementById('contactoAdicional');
    const contactoAdicionalInput = document.getElementById('contactoAdicionalInput');
    const tipoContacto = document.getElementById('tipoContacto');

    let heladeraSeleccionada = null;
    let nConfiguradoMin = null;
    let nConfiguradoMax = null;


    // Mostrar el formulario de suscripción sin afectar las heladeras
    function mostrarFormularioSuscripcion() {
        suscripcionContainer.style.display = 'block'; // Mostrar el formulario
        const heladeraIdInput = document.getElementById('heladeraIdInput');
        heladeraIdInput.value = heladeraSeleccionada.id; // Guardar el ID de la heladera seleccionada en el input hidden
        window.scrollTo(0, document.body.scrollHeight); // Desplazar al final de la página para ver el formulario
    }

    // Mostrar/ocultar el campo de configuración para N (número de viandas)
    /*
    const notificacionViandasMin = document.getElementById('notificacionViandasMin');
    const notificacionViandasMax = document.getElementById('notificacionViandasMax');

    notificacionViandasMin.addEventListener('change', function () {
        if (this.checked) {
            abrirModalConfigurarN('min');
        } else {
            nConfiguradoMin = null;
        }
    });

    notificacionViandasMax.addEventListener('change', function () {
        if (this.checked) {
            abrirModalConfigurarN('max');
        } else {
            nConfiguradoMax = null;
        }
    });
     */

    // Función para abrir el modal y configurar N
    function abrirModalConfigurarN(tipo) {
        const modal = new bootstrap.Modal(document.getElementById('configurarNModal'), {
            backdrop: false  // Deshabilitar bloqueo de fondo
        });
        document.getElementById('guardarNValue').onclick = function () {
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
            modal.hide(); // Cerrar el modal
        };
        modal.show();
    }

    // Función para encontrar un contacto según el medio seleccionado
    function encontrarContactoPorTipo(tipo) {
        return contactosGuardados.find(contacto => contacto.tipoContacto.nombre === tipo);
    }

    // Cambiar el tipo de contacto según el medio seleccionado
    /*
    medioNotificacion.addEventListener('change', function () {
        const medio = medioNotificacion.value;

        const contactoExistente = encontrarContactoPorTipo(medio);

        if (contactoExistente && contactoExistente.valorContacto) {
            contactoAdicional.style.display = 'none';
        } else {
            tipoContacto.textContent = medio;
            contactoAdicionalInput.placeholder = `Ingrese su ${medio}`;
            contactoAdicional.style.display = 'block';
        }
    });


    // Enviar el formulario de suscripción
    formSuscripcion.addEventListener('submit', function (event) {
        let isValid = true; // Bandera de validación

        // Verificar si se seleccionó un tipo de suscripción
        const tipoSuscripcion = document.querySelectorAll('input[name="tipo_suscripcion"]:checked');
        if (tipoSuscripcion.length === 0) {
            alert('Debe seleccionar al menos un tipo de suscripción.');
            isValid = false;
        }

        // Verificar si el usuario ha ingresado el contacto adicional (si es necesario)
        if (contactoAdicional.style.display === 'block' && !contactoAdicionalInput.value.trim()) {
            alert('Por favor, ingrese el contacto adicional.');
            isValid = false;
        }

        // Verificar si se configuró N cuando es necesario (mínimas o máximas)
        if (nConfiguradoMin === null && document.getElementById('notificacionViandasMin').checked) {
            alert('Debe configurar el valor de N para las viandas disponibles.');
            isValid = false;
        }

        if (nConfiguradoMax === null && document.getElementById('notificacionViandasMax').checked) {
            alert('Debe configurar el valor de N para la heladera llena.');
            isValid = false;
        }

        // Si la validación falla, prevenimos el envío
        if (!isValid) {
            event.preventDefault(); // Detener envío si hay errores de validación
        } else {
            // Asegurarnos de que el heladera_id esté incluido en el formulario
            document.getElementById('heladeraIdInput').value = heladeraSeleccionada;
            const heladeraIdInput = document.getElementById('heladeraIdInput');
            if (!heladeraIdInput.value) {
                alert('El ID de la heladera no está definido.');
                event.preventDefault();
                return;
            }

            // Adjuntar el valor de N configurado al formulario
            if (nConfiguradoMin !== null) {
                const inputMin = document.createElement('input');
                inputMin.type = 'hidden';
                inputMin.name = 'cantidad';
                inputMin.value = nConfiguradoMin;
                formSuscripcion.appendChild(inputMin);
            }

            if (nConfiguradoMax !== null) {
                const inputMax = document.createElement('input');
                inputMax.type = 'hidden';
                inputMax.name = 'cantidad';
                inputMax.value = nConfiguradoMax;
                formSuscripcion.appendChild(inputMax);
            }

            // Aquí se permite el envío normal del formulario
        }
    });


     */

    // Aplicar filtro de heladeras
    const formFilter = document.getElementById('filter-form');
    formFilter.addEventListener('submit', function (event) {
        event.preventDefault();
        // Lógica de filtrado
    });

    // Mostrar/ocultar filtros adicionales
    const filtroInactivosDiv = document.getElementById('filtros-inactivos');
    const filtroAlertasDiv = document.getElementById('filtros-alerta');

    const filtroInactivo = document.getElementById('filtro-inactivo');
    const filtroAlerta = document.getElementById('filtro-alerta');

    filtroInactivo.addEventListener('change', function () {
        filtroInactivosDiv.style.display = this.checked ? 'block' : 'none';
    });

    filtroAlerta.addEventListener('change', function () {
        filtroAlertasDiv.style.display = this.checked ? 'block' : 'none';
    });
});
