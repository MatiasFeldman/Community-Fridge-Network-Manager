document.getElementById('recomendarPuntosBtn').addEventListener('click', function () {
    // los datos que enviariamos al backend para generar los putnos recomendados
    const latitud = document.getElementById('latitudRecomendacion').value;
    const longitud = document.getElementById('longitudRecomendacion').value;
    const radio = document.getElementById('radio').value;

});
document.getElementById('recomendarPuntosBtn').addEventListener('click', function () {
    document.getElementById('resultadosContainer').style.display = 'block';


    const listaPuntos = document.getElementById('listaPuntosRecomendados');
    listaPuntos.innerHTML = '';

    //punto de ejemplo
    const dummyPoint = document.createElement('li');
    dummyPoint.className = "list-group-item";
    dummyPoint.innerHTML = `
        <span>Latitud: -34.6037</span> 
        <span>Longitud: -58.3816</span>
    `;
    listaPuntos.appendChild(dummyPoint);
});
