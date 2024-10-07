document.addEventListener("DOMContentLoaded", function() {
    const boton_reportar = document.getElementById("reportar-btn");
    const id_heladera = boton_reportar.getAttribute('data-id');
    const fecha_incidente_input = document.getElementById('fechaIncidente')
    const hora_incidente_input = document.getElementById('horaIncidente')
    const tipo_incidente_inpu = document.getElementById('tipoIncidente')
    const foto_input = document.getElementById('foto')
    const descripcion_input = document.getElementById('descripcion')

    const reportarFalla = () =>{
        const data = {
            id_heladera,
            fecha: fecha_incidente_input.value + 'T' + hora_incidente_input.value,
            tipo: tipo_incidente_inpu.value,
            foto: foto_input.value,
            descripcion: descripcion_input.value
        }

        fetch('/heladeras/reportar-falla-tecnica', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        reiniciarValores()
        console.log(data)
        alert('Falla reportada con éxito!')
    }

    const reiniciarValores = () =>{
        fecha_incidente_input.value = ""
        hora_incidente_input.value = ""
        tipo_incidente_inpu.value = ""
        foto_input.value = ""
        descripcion_input.value = ""
    }

    boton_reportar.addEventListener('click', reportarFalla)
});
