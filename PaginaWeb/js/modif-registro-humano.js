document.addEventListener("DOMContentLoaded", function () {
  const camposActuales = document.getElementById("campos-actuales");
  const formCampo = document.getElementById("form-campo");
  const formTitulo = document.getElementById("form-titulo");
  const cancelarEdicion = document.getElementById("cancelar-edicion");

  let campos = [
    {
      id: "nombre",
      label: "Nombre",
      type: "text",
      placeholder: "Nombre completo",
      required: true,
    },
    {
      id: "apellido",
      label: "Apellido",
      type: "text",
      placeholder: "Apellido",
      required: true,
    },
    {
      id: "email",
      label: "Correo electrónico",
      type: "email",
      placeholder: "Correo electrónico",
      required: false,
    },
  ];

  let campoEditando = null;

  function renderizarCampos() {
    camposActuales.innerHTML = "";

    campos.forEach((campo, index) => {
      const row = document.createElement("tr");

      const nombreCol = document.createElement("td");
      nombreCol.textContent = campo.label;

      const tipoCol = document.createElement("td");
      tipoCol.textContent = campo.type;

      const requeridoCol = document.createElement("td");
      requeridoCol.textContent = campo.required ? "Sí" : "No";

      const accionesCol = document.createElement("td");
      accionesCol.className = "d-flex";
      const editarBtn = document.createElement("button");
      editarBtn.className = "me-2 d-flex align-items-center rounded-circle p-2 btn-editar";
      editarBtn.innerHTML = `
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-pencil" viewBox="0 0 16 16">
                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325"/>
            </svg>
            `;
      editarBtn.addEventListener("click", function () {
        campoEditando = index;
        cargarCampoEnFormulario(campo);
      });

      const eliminarBtn = document.createElement("button");
      eliminarBtn.className = "btn btn-sm btn-danger d-flex align-items-center rounded-circle p-2";
      eliminarBtn.innerHTML = ` 
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
        <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
      </svg> 
    `;
      eliminarBtn.addEventListener("click", function () {
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

  formCampo.addEventListener("submit", function (event) {
    event.preventDefault();

    const nuevoCampo = {
      id: formCampo["campo-nombre"].value.toLowerCase().replace(/\s+/g, "-"),
      label: formCampo["campo-nombre"].value,
      type: formCampo["campo-tipo"].value,
      placeholder: formCampo["campo-placeholder"].value,
      required: formCampo["campo-requerido"].checked,
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

  cancelarEdicion.addEventListener("click", function () {
    formCampo.reset();
    formTitulo.textContent = "Agregar Nuevo Campo";
    campoEditando = null;
  });

  renderizarCampos();
});
