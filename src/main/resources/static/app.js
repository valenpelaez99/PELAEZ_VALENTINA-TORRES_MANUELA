document.getElementById('odontologoForm').addEventListener('submit', function (e) {
    e.preventDefault();

    // Obtener los valores del formulario
    const matricula = document.getElementById('matricula').value;
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;

    // Crear el objeto de odontólogo
    const odontologo = {
        matricula: matricula,
        nombre: nombre,
        apellido: apellido
    };

    // Configurar el request para hacer el POST
    fetch('http://localhost:8080/odontologos/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(odontologo),
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('responseMessage').innerText = "Odontólogo registrado exitosamente";
        document.getElementById('odontologoForm').reset();
    })
    .catch((error) => {
        console.error('Error:', error);
        document.getElementById('responseMessage').innerText = "Error al registrar el odontólogo";
    });
});
