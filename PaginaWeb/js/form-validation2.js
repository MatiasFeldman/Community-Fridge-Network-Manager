document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("form-juridico");

    form.addEventListener('submit', function(event) {
        event.preventDefault(); 

        if (!validarMediosContacto()) {
            return;
        }

        const formData = new FormData(this);
        
    });
    //quiza tendriamos que agregar una validacion para que si pone calle tiene que poner direccion y viceversa
    // Validación de medios de contacto
    function validarMediosContacto() {
        const email = document.getElementById('email').value.trim();
        const telefono = document.getElementById('telefono').value.trim();
        
        if (!email && !telefono) {
            alert('Por favor, proporciona al menos un medio de contacto (Correo electrónico o Teléfono).');
            return false;
        }
        return true;
    }
});
