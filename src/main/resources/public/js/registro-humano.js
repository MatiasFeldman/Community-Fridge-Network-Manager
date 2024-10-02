document.addEventListener("DOMContentLoaded", function () {
    const input_user = document.getElementById('user')
    const input_password = document.getElementById('password')
    const input_nombre = document.getElementById('Nombre')
    const input_apellido = document.getElementById('Apellido')
    const input_nacimiento = document.getElementById('Nacimiento')
    const input_direccion = document.getElementById('direccion')
    const input_email = document.getElementById('Mail')
    const input_telegram = document.getElementById('Telegram')
    const input_wpp = document.getElementById('Whatsapp')

    const btn_register = document.getElementById('btn-submit-humana')

    function tieneMedioDeContacto() {
        return input_email.value || input_telegram.value || input_wpp.value
    }

    btn_register.addEventListener('click', () => {
        if (input_user.value && input_password.value && input_nombre.value && input_apellido.value && input_nacimiento.value && input_direccion.value && tieneMedioDeContacto()) {
            const data = {
                user: input_user.value,
                password: input_password.value,
                nombre: input_nombre.value,
                apellido: input_apellido.value,
                nacimiento: input_nacimiento.value,
                direccion: input_direccion.value,
                email: input_email.value,
                telegram: input_telegram.value,
                wpp: input_wpp.value
            }
            console.log(data)
        } else {
            alert('Faltan campos por completar')
        }
    })
})