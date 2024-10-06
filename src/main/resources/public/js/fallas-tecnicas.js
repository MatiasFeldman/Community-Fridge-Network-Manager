document.addEventListener('DOMContentLoaded', function() {
    const select_busqueda = document.getElementById('select-busqueda');
    const input_busqueda = document.getElementById('input-busqueda');

    select_busqueda.addEventListener('change', () =>{
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
            case 'todos':
                input_busqueda.placeholder = 'Dele click al boton para buscar';
                break;
        }
    })

    const buscarHeladeras = () =>{
        const url = '/heladeras/fallas-tecnicas?busqueda=' + select_busqueda.value + '&valor=' + input_busqueda.value;
    }
});