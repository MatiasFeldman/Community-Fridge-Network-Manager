document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
console.log(email);

    // Simulación de los datos que vendrían del backend
    const usuariosSimulados = [
        { username:'admin',email: 'admin@gmail.com', password: 'admin123', userRole: 'admin' },
        { username:'humano', email: 'humano@gmail.com', password: 'humano123', userRole: 'humano' },
        { username:'juridico', email: 'juridico@gmail.com', password: 'juridico123', userRole: 'juridico' }
    ];

    // Verificar si las credenciales coinciden con algún usuario simulado
    const usuarioValido = usuariosSimulados.find(usuario => usuario.email === email && usuario.password === password);

    if (usuarioValido) {
        // Guardar el rol del usuario y nombre en el localStorage
        localStorage.setItem('userRole', usuarioValido.userRole); 
        localStorage.setItem('userName', usuarioValido.username);

        // Redireccionar a la página de inicio
        window.location.href = 'index.html'; 
    } else {
        alert('Credenciales incorrectas');
    }
});





