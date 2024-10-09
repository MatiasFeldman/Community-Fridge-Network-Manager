document.addEventListener("DOMContentLoaded", function () {
    const boton_cerrar = document.getElementById("cerrar");
    const boton_guardar = document.getElementById("guardar");
    const input_direccion = document.getElementById("Direccion");
    const input_provincia = document.getElementById("Provincia");
    const input_mail = document.getElementById("Mail");
    const input_telegram = document.getElementById("Telegram");
    const input_whatsapp = document.getElementById("WhatsApp");
    const id = boton_guardar.getAttribute('data-id')

    const valores_iniciales = {
        direccion: input_direccion.value ? input_direccion.value : '',
        provincia: input_provincia.value ? input_provincia.value : '',
        mail: input_mail.value ? input_mail.value : '',
        telegram: input_telegram.value ? input_telegram.value : '',
        whatsapp: input_whatsapp.value ? input_whatsapp.value : ''
    }

    boton_cerrar.addEventListener('click', () => {
        fetch('/logout', {
            method: 'POST',
        })
        window.location.href = "/";
    })

    boton_guardar.addEventListener('click', () =>{
        const valores_actuales = {
            id,
            direccion: input_direccion.value,
            provincia: input_provincia.value,
            mail: input_mail.value,
            telegram: input_telegram.value,
            whatsapp: input_whatsapp.value
        }

        if (Object.keys(valores_iniciales).some(key => valores_iniciales[key] !== valores_actuales[key])) {
            fetch('/perfil', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(valores_actuales)
            }).then(response => {
                if (response.status === 200) {
                } else {
                    alert('Error al actualizar el perfil');
                    window.location.href = "/perfil";
                }
            })
        } else {
            alert('No se han realizado cambios');
        }
    })


});
