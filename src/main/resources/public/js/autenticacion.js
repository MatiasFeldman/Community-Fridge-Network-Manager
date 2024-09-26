function checkUserRole(allowedRoles = [], allowUnauthenticated = false, blockLoggedIn = false) {
    const userRole = localStorage.getItem('userRole');
    //console.log('Rol del usuario:', userRole);

    if (!userRole) {
        if (allowUnauthenticated) {
            console.log('Acceso permitido a usuarios no autenticados');
            return;
        } else {
            window.location.href = 'login.html'; // O una página de acceso denegado
            return;
        }
    }

    if (blockLoggedIn) {
        console.log('Acceso bloqueado a usuarios autenticados, redirigiendo...');
        window.location.href = 'access-denied.html';
        return;
    }

    if (!allowedRoles.includes(userRole)) {
        window.location.href = 'access-denied.html';
        return;
    }


    // Si llegamos aquí, el usuario tiene acceso
    console.log(`Bienvenido, tienes acceso con el rol: ${userRole}`);
}