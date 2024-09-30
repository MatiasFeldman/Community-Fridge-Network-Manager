document.addEventListener("DOMContentLoaded", function() {
    const productList = document.getElementById("product-list");
    const misPuntosElement = document.getElementById("mis-puntos");
    const applyFilterBtn = document.getElementById("apply-filter");

    // Datos simulados del backend
    const datosUsuario = {
        puntos: 5000,
        productos: [
            {
                nombre: "Producto 1",
                puntos: 1000,
                tipo: "electronica",
                imagen: "imagenes/Heleaderas.jpg"
            },
            {
                nombre: "Producto 2",
                puntos: 1500,
                tipo: "gastronomia",
                imagen: "imagenes/producto2.jpg"
            },
            {
                nombre: "Producto 3",
                puntos: 2000,
                tipo: "articulos-hogar",
                imagen: null // si es null lo remplazamos por una imagen default
            }
        ]
    };

    // completar el "Mis puntos"
    misPuntosElement.textContent = datosUsuario.puntos;

    const imagenDefault = "imagenes/default.jpg"; // Ruta de la imagen default

    // Renderizar productos
    function renderizarProductos(productos) {
        productList.innerHTML = ''; 

        productos.forEach(producto => {
            const productItem = document.createElement("div");
            productItem.className = "product-item p-3 mb-4 rounded shadow d-flex";

            const productImage = document.createElement("div");
            productImage.className = "product-image mr-3";

            const img = document.createElement("img");
            img.src = producto.imagen || imagenDefault;
            img.alt = producto.nombre;
            img.className = "img-fluid rounded";

            productImage.appendChild(img);

            const productDetails = document.createElement("div");
            productDetails.className = "product-details";

            const h3 = document.createElement("h3");
            h3.className = "h5";
            h3.textContent = producto.nombre;

            const puntos = document.createElement("p");
            puntos.textContent = `Puntos necesarios: ${producto.puntos}`;

            const tipo = document.createElement("p");
            tipo.textContent = `Tipo: ${producto.tipo}`;

            productDetails.appendChild(h3);
            productDetails.appendChild(puntos);
            productDetails.appendChild(tipo);

            productItem.appendChild(productImage);
            productItem.appendChild(productDetails);

            productList.appendChild(productItem);
        });
    }

    // Mostrar todos los productos al inicio
    renderizarProductos(datosUsuario.productos);

    // Aplicar filtro
    applyFilterBtn.addEventListener("click", function() {
        const checkboxes = document.querySelectorAll('input[name="filtro-rubro"]:checked');
        const filtrosSeleccionados = Array.from(checkboxes).map(checkbox => checkbox.value.toLowerCase());

        // Filtrar productos basados en el tipo seleccionado
        const productosFiltrados = datosUsuario.productos.filter(producto => 
            filtrosSeleccionados.includes(producto.tipo)
        );

        // Renderizar productos filtrados
        renderizarProductos(productosFiltrados.length > 0 ? productosFiltrados : datosUsuario.productos);
    });
});

