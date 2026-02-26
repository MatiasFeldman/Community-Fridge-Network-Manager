document.addEventListener("DOMContentLoaded", function () {
    let nConfiguradoMin = null;
    let nConfiguradoMax = null;

    const notificacionViandasMin = document.getElementById('notificacionViandasMin');
    const notificacionViandasMax = document.getElementById('notificacionViandasMax');
    const medioNotificacion = document.getElementById('medioNotificacion');
    const contactoAdicional = document.getElementById('contactoAdicional');
    const contactoAdicionalInput = document.getElementById('contactoAdicionalInput');
    const tipoContacto = document.getElementById('tipoContacto');
    const formSuscripcion = document.getElementById('formSuscripcion');
    const heladeraIdInput = document.getElementById('heladeraIdInput');

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

    function abrirModalConfigurarN(tipo) {
        const modal = new bootstrap.Modal(document.getElementById('configurarNModal'), {
            backdrop: false
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
            modal.hide();
        };
        modal.show();
    }

    medioNotificacion.addEventListener('change', function () {
        const medio = medioNotificacion.value.toLowerCase();
        if (encontrarContactoPorTipo(medio) || medio === "") {
            contactoAdicional.style.display = 'none';
        } else {
            tipoContacto.textContent = medio;
            contactoAdicionalInput.placeholder = `Ingrese su ${medio}`;
            contactoAdicional.style.display = 'block';
        }
    });

    function encontrarContactoPorTipo(tipo) {
        return contactosGuardados && contactosGuardados.find(contacto => contacto.tipoContacto.toLowerCase() === tipo.toLowerCase());
    }

    formSuscripcion.addEventListener('submit', function (event) {
        event.preventDefault();
        let isValid = true;

        const tipoSuscripcion = document.querySelectorAll('input[name="tipo_suscripcion"]:checked');
        if (tipoSuscripcion.length === 0) {
            alert('Debe seleccionar al menos un tipo de suscripción.');
            isValid = false;
        }

        if (contactoAdicional.style.display === 'block') {
            const medio = medioNotificacion.value.toLowerCase();
            const contactoValor = contactoAdicionalInput.value.trim();

            if (!contactoValor) {
                alert('Por favor, ingrese el contacto adicional.');
                isValid = false;
            } else if (medio === 'whatsapp' || medio === 'telegram') {
                // Nueva expresión regular para validar el número de teléfono
                const phoneRegex = /^\+?[0-9]{7,15}$/;
                if (!phoneRegex.test(contactoValor)) {
                    alert('Por favor, ingrese un número de teléfono válido (solo dígitos y opcional + al inicio).');
                    isValid = false;
                }
            } else if (medio === 'mail') {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(contactoValor)) {
                    alert('Por favor, ingrese un correo electrónico válido.');
                    isValid = false;
                }
            }
        }

        if (nConfiguradoMin === null && notificacionViandasMin.checked) {
            alert('Debe configurar el valor de N para las viandas disponibles.');
            isValid = false;
        }
        if (nConfiguradoMax === null && notificacionViandasMax.checked) {
            alert('Debe configurar el valor de N para la heladera llena.');
            isValid = false;
        }

        if (!heladeraIdInput.value) {
            alert('El ID de la heladera no está definido.');
            isValid = false;
        }

        if (isValid) {
            if (nConfiguradoMin !== null) {
                const inputMin = document.createElement('input');
                inputMin.type = 'hidden';
                inputMin.name = 'cantidadMin';
                inputMin.value = nConfiguradoMin;
                formSuscripcion.appendChild(inputMin);
            }
            if (nConfiguradoMax !== null) {
                const inputMax = document.createElement('input');
                inputMax.type = 'hidden';
                inputMax.name = 'cantidadMax';
                inputMax.value = nConfiguradoMax;
                formSuscripcion.appendChild(inputMax);
            }

            formSuscripcion.submit();
        }
    });
});
