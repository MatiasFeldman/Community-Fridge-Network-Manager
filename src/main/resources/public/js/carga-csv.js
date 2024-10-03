document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("carga-masiva-form");
    const archivoCsv = document.getElementById("archivoCsv");

    form.addEventListener("submit", function(event) {
        const file = archivoCsv.files[0];

        if (file) {
            const fileExtension = file.name.split('.').pop().toLowerCase();
            if (fileExtension !== 'csv') {
                alert("Por favor, suba un archivo en formato .csv");
                event.preventDefault();
            }
        } else {
            alert("Por favor, seleccione un archivo para subir.");
            event.preventDefault();
        }
    });
});
