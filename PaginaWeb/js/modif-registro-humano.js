document.addEventListener("DOMContentLoaded", function() {
    const camposActuales = document.getElementById("campos-actuales");
    const formCampo = document.getElementById("form-campo");
    const formTitulo = document.getElementById("form-titulo");
    const cancelarEdicion = document.getElementById("cancelar-edicion");

    let campos = [
        { id: "nombre", label: "Nombre", type: "text", placeholder: "Nombre completo", required: true },
        { id: "apellido", label: "Apellido", type: "text", placeholder: "Apellido", required: true },
        { id: "email", label: "Correo electrónico", type: "email", placeholder: "Correo electrónico", required: false }
    ];

    let campoEditando = null;

    function renderizarCampos() {
        camposActuales.innerHTML = '';

        campos.forEach((campo, index) => {
            const row = document.createElement("tr");
            
            const nombreCol = document.createElement("td");
            nombreCol.textContent = campo.label;

            const tipoCol = document.createElement("td");
            tipoCol.textContent = campo.type;

            const requeridoCol = document.createElement("td");
            requeridoCol.textContent = campo.required ? "Sí" : "No";

            const accionesCol = document.createElement("td");
            const editarBtn = document.createElement("button");
            editarBtn.className = "btn btn-sm btn-primary me-2";
            editarBtn.textContent = "Editar";
            editarBtn.addEventListener("click", function() {
                campoEditando = index;
                cargarCampoEnFormulario(campo);
            });

            const eliminarBtn = document.createElement("button");
            eliminarBtn.className = "btn btn-sm btn-danger";
            eliminarBtn.textContent = "Eliminar";
            eliminarBtn.addEventListener("click", function() {
                eliminarCampo(index);
            });

            accionesCol.appendChild(editarBtn);
            accionesCol.appendChild(eliminarBtn);

            row.appendChild(nombreCol);
            row.appendChild(tipoCol);
            row.appendChild(requeridoCol);
            row.appendChild(accionesCol);

            camposActuales.appendChild(row);
        });
    }

    function cargarCampoEnFormulario(campo) {
        formTitulo.textContent = "Editar Campo";
        formCampo["campo-nombre"].value = campo.label;
        formCampo["campo-tipo"].value = campo.type;
        formCampo["campo-placeholder"].value = campo.placeholder;
        formCampo["campo-requerido"].checked = campo.required;
    }

    function eliminarCampo(index) {
        campos.splice(index, 1);
        renderizarCampos();
    }

    formCampo.addEventListener("submit", function(event) {
        event.preventDefault();

        const nuevoCampo = {
            id: formCampo["campo-nombre"].value.toLowerCase().replace(/\s+/g, '-'),
            label: formCampo["campo-nombre"].value,
            type: formCampo["campo-tipo"].value,
            placeholder: formCampo["campo-placeholder"].value,
            required: formCampo["campo-requerido"].checked
        };

        if (campoEditando !== null) {
            campos[campoEditando] = nuevoCampo;
            campoEditando = null;
        } else {
            campos.push(nuevoCampo);
        }

        formCampo.reset();
        formTitulo.textContent = "Agregar Nuevo Campo";
        renderizarCampos();
    });

    cancelarEdicion.addEventListener("click", function() {
        formCampo.reset();
        formTitulo.textContent = "Agregar Nuevo Campo";
        campoEditando = null;
    });

    renderizarCampos();
});
