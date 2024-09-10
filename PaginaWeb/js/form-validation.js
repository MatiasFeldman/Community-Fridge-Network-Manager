document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("form-humano");
    const passwordField = document.getElementById("password").parentElement; 

    // Simulación de los datos que vienen del backend
    const campos = [
        { id: "nombre", label: "Nombre", type: "text", placeholder: "Nombre completo", required: true },
        { id: "apellido", label: "Apellido", type: "text", placeholder: "Apellido", required: true },
        { id: "email", label: "Correo electrónico", type: "email", placeholder: "Correo electrónico", required: false },
        { id: "telefono", label: "Número de teléfono", type: "tel", placeholder: "Número de teléfono", required: false },
        { id: "whatsapp", label: "Número de WhatsApp", type: "text", placeholder: "Número de WhatsApp", required: false },
        { id: "fecha-nacimiento", label: "Fecha de Nacimiento (Opcional)", type: "date", placeholder: "", required: false },
        { id: "calle", label: "Calle (Opcional)", type: "text", placeholder: "Calle", required: false },
        { id: "altura", label: "Altura (Opcional)", type: "text", placeholder: "Altura", required: false }
    ];

    // Generamos los campos dinámicamente
    for (let i = 0; i < campos.length; i += 2) {
        const formRow = document.createElement("div");
        formRow.className = "form-row";

        for (let j = 0; j < 2; j++) {
            if (i + j < campos.length) {
                const field = campos[i + j];
                const formGroup = document.createElement("div");
                formGroup.className = "form-group"; 

                const label = document.createElement("label");
                label.setAttribute("for", field.id);
                label.textContent = field.label;

                const input = document.createElement("input");
                input.type = field.type;
                input.id = field.id;
                input.name = field.id;
                input.placeholder = field.placeholder;
                if (field.required) {
                    input.required = true;
                }

                formGroup.appendChild(label);
                formGroup.appendChild(input);
                formRow.appendChild(formGroup);
            }
        }

        form.appendChild(formRow);
    }

    passwordField.classList.add("form-group");
    form.appendChild(passwordField);


    const submitButton = document.createElement("button");
    submitButton.type = "submit";
    submitButton.textContent = "Registrar";
    submitButton.classList.add("btn", "btn-success", "w-100", "mt-3");
    form.appendChild(submitButton);

    // validación y envío
    form.addEventListener('submit', function(event) {
        event.preventDefault(); 

        if (!validarMediosContacto()) {
            return;
        }

        const formData = new FormData(this);
        
    });

    // Validación de medios de contacto
    function validarMediosContacto() {
        const email = document.getElementById('email').value.trim();
        const telefono = document.getElementById('telefono').value.trim();
        const whatsapp = document.getElementById('whatsapp').value.trim();

        if (!email && !telefono && !whatsapp) {
            alert('Por favor, proporciona al menos un medio de contacto (Correo electrónico, Teléfono, o WhatsApp).');
            return false;
        }
        return true;
    }
});
