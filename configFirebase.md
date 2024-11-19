# Configuracion de firebase

## Enlazar proyecto con android studio y conectar con los servicios necesarios
- Abrimos nuestro proyecto
- Nos vamos a Tools > Firebase
- Seleccionamos Realtime Database
- Seleccionamos **Get Started with Realtime Database (Java)**
- Primero le damos al boton **Connect to Firebase**
- Nos redirigira al console de nuestro firebase seleccionamos el proyecto que va a estar relacionado con nuestra app y le damos en conectar
- Ahora le damos al boton **Add the Realtime Database SDK to your app** lo que hara es agregar las librerias del Realtime Database a nuestro proyecto
- Hacemos el mismo procedimiento anterior con Authentication, Storage y Firestore Database a exepcion del boton connect to firebase ya que ya hemos vinculado nuestro proyecto anteriormente

## Reglas
Para garantizar el envio y recibo de datos en nuestro proyecto se recomienda cambiar las reglas de nuestras herramientas que señalare abajo en firebase para cambiarlas simplemente vas al apartado de reglas que hay en las herramientas que se mencionaran a continuacion

### Reglas de Realtime database
{
  "rules": {
    ".read": true,
    ".write": true  
  }
}

### Reglas de Storage
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if true;
    }
  }
}

### Reglas de FirestoreDatabase
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
