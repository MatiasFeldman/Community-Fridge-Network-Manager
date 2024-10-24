// Cuando se apreta el boton de submit en el formulario de donar, se chequea que la direccion tenga algo sino mandar una alerta
document.addEventListener("DOMContentLoaded", function () {
    console.log('DOM cargado');
    document.getElementById('btn-submit').addEventListener('click', function (event) {
        if (document.getElementById('direccion').value === '') {
            alert('Por favor, ingrese una dirección');
            event.preventDefault();
        }
    });
});