document.getElementById('siguiente-btn').addEventListener('click', function() {
    const tipoEntidad = document.querySelector('input[name="tipo-entidad"]:checked').value;
    if (tipoEntidad === 'humano') {
        window.location.href = 'registro-humano.html';
    } else if (tipoEntidad === 'juridico') {
        window.location.href = 'registro-juridico.html';
    }
});