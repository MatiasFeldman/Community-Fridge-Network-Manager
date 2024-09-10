document.addEventListener('DOMContentLoaded', function() {
    const siguienteBtn = document.getElementById('siguiente-btn');
    const tipoDonacionSelect = document.getElementById('tipoDonacion');


    siguienteBtn.addEventListener('click', function() {
        const tipoDonacion = tipoDonacionSelect.value;
       
        if (tipoDonacion) {
            window.location.href = tipoDonacion;
        } else {
            alert('Por favor, seleccione un tipo de donación antes de continuar.');
        }
    });
});
