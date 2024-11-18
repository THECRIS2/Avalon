# TecnoClick
## Descripcion
Es un proyecto de ecommerce que su nombre en clave era **Avalon** el cual esta hecho para android desde la version 6.0 marshmallow en adelante, que trata de la compra y venta de productos electronicos como computadoras, consolas, memorias ram, almacenamiento, etc. Se ha hecho con el ide de android studio con el lenguaje java y kotlin DSL 

## Apis y librerias usadas
- Se utiliza el servicio de storage de firebase para almacenar las imagenes de los usuarios y las imagenes de los productos
- Se usa realtime database de firebase la cual es una base de datos no relacional para el login se encarga de almacenar todos los datos de los usuarios registrados
- Se utiliza authentication de firebase para verificar los usuarios almacenados en la base de datos de realtime para dar acceso a los usuarios registrados a la aplicacion
- Se utiliza firestore database para almacenar todos los datos de los productos de la aplicacion

## Estructura del proyecto
- Se utiliza los models para poder declarar y gestionar las variables que recibimos de la firestore database a traves de constructores y el envio y recibo de cada dato
- Se usa el adapter para poder gestionar las operaciones que se haran con los datos asi como su manera de mostrarlos
- Se utiliza los fragmentos para los distintos apartados de la aplicacion como Categorias, Inicio, Ofertas, Carrito de compra y Orden
- Para nuestro menu principal se usa un Navigation Activity para poder navegar entre los diferentes elementos y categorias
- Se usa el empty views activity para el login, registro y informacion del producto
- Se utiliza las CardViews para poder mostrar una pequeña informacion de nuestros elementos como populares, categorias, ofertas

## Versiones
- 0.1.0 Primera version alpha publica
- 0.1.2 Login y registro completamente funcional
- 0.1.4 Se implementa el activity de navigation correctamente
- 0.1.5 Se hace reestructuraciones con las clases del proyecto, se eliminaron algunas clases generadas por defecto por el navigation activity y las clases de las activitys anteriores se movieron a una carpeta llamada activity
- 0.2.0 Se implementa la interfaz definitiva del menu parcialmente funcional
- 0.2.2 Se ha implementado la informacion en las CardViews de populares
- 0.2.3 Las CardViews de categoria han quedado funcionales
- 0.3.0 Se han corregido errores de la aplicacion como los iconos de las cardviews que no se mostraban correctamente y se ha corregido un error que al presionar las cardview de populares a veces provocaban crasheos
- 0.3.3 Se ha implementado el boton de añadir un objeto al carrito en la informacion del producto y se han añadido botones para aumentar o disminuir la cantidad de un producto
- 0.3.4 Se ha implementado un carrito de compra funcional y se han convertido las variables de precio con una sentencia en int para hacer los calculos y el resultado de ese calculo vuelve a ser String con otra sentencia
- 0.4.0 Se ha implementado las CardView de ordenes y se ha programado correctamente el boton de comprar ahora del carrito
- 0.4.2 Se ha implementado la funcionalidad de la foto de perfil ahora el usuario puede subir su foto a la app
- 0.4.4 Correcciones menores de errores esta es la ultima version que se identifica como Avalon ya que a partir de la siguiente version se identificara con su nombre final
- 0.4.5 (Pre-Beta) Esta es la primera version en llamarse TecnoClick se corrige un error grave que al subir una foto a la app si el usuario no subia una foto hacia que la app se crasheaba, se hacen cambios en la interfaz para hacerla mas consistente
