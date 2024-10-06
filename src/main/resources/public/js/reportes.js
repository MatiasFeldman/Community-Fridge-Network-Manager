document.addEventListener("DOMContentLoaded", function() {
    const btn_todos = document.getElementById("todos");
    const btn_fallas = document.getElementById("fallas");
    const btn_donaciones = document.getElementById("donaciones")
    const btn_movimientos = document.getElementById("movimiento");

    const descargarPdf = (tipo) =>{
        fetch('/heladeras/reportes?tipo=' + tipo)
            .then(response => {
                if (response.ok) {
                    return response.blob();
                } else {
                    throw new Error('Error al descargar el PDF');
                }
            })
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;


                a.download = 'reporte_' + tipo + '.pdf';

                document.body.appendChild(a);
                a.click();
                a.remove();
                window.URL.revokeObjectURL(url);
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
