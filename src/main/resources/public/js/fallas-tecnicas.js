document.addEventListener('DOMContentLoaded', function () {
    const select_busqueda = document.getElementById('busqueda');
    const input_busqueda = document.getElementById('input-busqueda');
    const btn_buscar = document.getElementById('boton-buscar');

    select_busqueda.addEventListener('change', () => {
        switch (select_busqueda.value) {
            case 'direccion':
                input_busqueda.placeholder = 'Ingrese la dirección';
                break;
            case 'comuna':
                input_busqueda.placeholder = 'Ingrese el departamento o comuna';
                break;
            case 'provincia':
                input_busqueda.placeholder = 'Ingrese la provincia';
                break;
            case 'todas':
                input_busqueda.placeholder = 'Dele click al boton para buscar';
                input_busqueda.value = ''; // Resetea el campo de entrada
                break;
        }
    });

    const buscarHeladeras = (event) => {
        event.preventDefault(); // Evita que el formulario se envíe de manera estándar
        const valorBusqueda = input_busqueda.value; // Obtener el valor del input sin recortar

        // Verifica que el valor de búsqueda no esté vacío para ciertos casos
        if (select_busqueda.value !== 'todas' && valorBusqueda === '') {
            alert('Por favor ingrese un valor para buscar.'); // Muestra un mensaje si el input está vacío
            return; // Sale de la función
        }

        // Redirige a la URL con los parámetros
        window.location.href = '/heladeras/reportar-falla-tecnica?busqueda=' + select_busqueda.value + '&valor=' + encodeURIComponent(valorBusqueda);
    };

    btn_buscar.addEventListener('click', buscarHeladeras);
});
