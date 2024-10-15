document.addEventListener("DOMContentLoaded", function () {
    const input_user = document.getElementById('user')
    const input_password = document.getElementById('password')
    const input_nombre = document.getElementById('Nombre')
    const input_apellido = document.getElementById('Apellido')
    const input_nacimiento = document.getElementById('Nacimiento')
    const input_direccion = document.getElementById('Direccion')
    const input_provincia = document.getElementById('Provincia')
    const input_email = document.getElementById('Mail')
    const input_telegram = document.getElementById('Telegram')
    const input_wpp = document.getElementById('WhatsApp')

    const campos_obligatorios = Array.from(document.querySelectorAll('.obligatorio'));

    const btn_register = document.getElementById('btn-submit-humana')

    function tieneMedioDeContacto() {
        return input_email.value || input_telegram.value || input_wpp.value
    }

    function camposObligatoriosLlenos(){
        return campos_obligatorios.every(c => c.value)
    }

    function direccionValida(){
        if (input_provincia.value){
            return input_direccion.value
        } else if (input_direccion.value){
            return input_provincia.value
        }
        return true;
    }

    btn_register.addEventListener('click', () => {
        if (camposObligatoriosLlenos() && tieneMedioDeContacto() && direccionValida()) {
            const data = {
                user: input_user.value,
                password: input_password.value,
                nombre: input_nombre.value,
                apellido: input_apellido.value,
                nacimiento: input_nacimiento.value,
                direccion: input_direccion.value,
                provincia: input_provincia.value,
                email: input_email.value,
                telegram: input_telegram.value,
                wpp: input_wpp.value
            }

            fetch('/registro/humano',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)

            })
            console.log(data)
        } else {
            alert('Faltan campos por completar')
        }
    })
})