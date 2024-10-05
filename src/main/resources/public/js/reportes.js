document.addEventListener("DOMContentLoaded", function() {
    const btn_todos = document.getElementById("todos");
    const btn_fallas = document.getElementById("fallas");
    const btn_donaciones = document.getElementById("donaciones")
    const btn_movimientos = document.getElementById("movimiento");

    const descargarPdf = (tipo) =>{
        fetch('/heladeras/reportes?tipo=' + tipo)
            .then(response => {
                if (response.ok) {
                    return response.blob(); // Obtener el blob del PDF
                } else {
                    throw new Error('Error al descargar el PDF');
                }
            })
            .then(blob => {
                // Crear una URL para el blob
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;

                // No necesitas cambiar el nombre aquí, el servidor ya lo genera con la fecha
                a.download = 'reporte_' + tipo + '.pdf'; // El servidor ya agregará la fecha

                document.body.appendChild(a);
                a.click();
                a.remove();
                window.URL.revokeObjectURL(url); // Liberar la URL del objeto
            })
            .catch(error => {
                console.error('Error en la solicitud:', error);
            });
    }

    btn_todos.addEventListener('click', () =>{
        descargarPdfTodos('todos');
    })

    btn_fallas.addEventListener('click', () =>{
        descargarPdf('fallas');
    })

    btn_donaciones.addEventListener('click', () =>{
        descargarPdf('donaciones');
    })

    btn_movimientos.addEventListener('click', () =>{
        descargarPdf('movimiento');
    })
});
