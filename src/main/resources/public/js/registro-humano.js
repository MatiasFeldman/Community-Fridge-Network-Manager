document.addEventListener("DOMContentLoaded", function () {
    const input_user = document.getElementById('user');
    const input_password = document.getElementById('password');
    const input_nombre = document.getElementById('Nombre');
    const input_apellido = document.getElementById('Apellido');
    const input_nacimiento = document.getElementById('Nacimiento');
    const input_direccion = document.getElementById('Direccion');
    const input_provincia = document.getElementById('Provincia');
    const input_email = document.getElementById('Mail');
    const input_telegram = document.getElementById('Telegram');
    const input_wpp = document.getElementById('WhatsApp');

    const campos_obligatorios = Array.from(document.querySelectorAll('.obligatorio'));

    const formulario = document.getElementById('form-colaboradorHumano');

    function tieneMedioDeContacto() {
        console.log("Validando medios de contacto...");
        return input_email.value || input_telegram.value || input_wpp.value;
    }

    function camposObligatoriosLlenos() {
        const todosLlenos = campos_obligatorios.every(campo => campo.value.trim() !== "");
        console.log("Campos obligatorios llenos: ", todosLlenos);
        return todosLlenos;
    }

    function direccionValida() {
        console.log("Validando dirección y provincia...");
        if (input_provincia.value.trim()) {
            return input_direccion.value.trim() !== "";
        } else if (input_direccion.value.trim()) {
            return input_provincia.value.trim() !== "";
        }
        return true;  // Si ninguno está lleno, consideramos que es válido
    }

    formulario.addEventListener('submit', function (event) {
        const validacionObligatorios = camposObligatoriosLlenos();
        const validacionMedioContacto = tieneMedioDeContacto();
        const validacionDireccion = direccionValida();

        if (!validacionObligatorios || !validacionMedioContacto || !validacionDireccion) {
            event.preventDefault();  // Detener el envío del formulario
            console.log("Validaciones fallidas, no se envía el formulario.");
            alert('Faltan campos por completar o hay un error en los datos.');
        } else {
            console.log("Formulario válido, se procede a enviar.");
        }
    });
});
