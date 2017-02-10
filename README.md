## UniApi Trabajo fin de grado UNIOVI (STATE: IN_DEVELOP)

# ¿Que es UniApi?
UniApi es un servicio que utiliza las tecnologías WEB para facilitar el despliegue de los programas académicos y científicos en aplicaciones web. Para que desarrolladores de aplicaciones y científicos solo tengan que pensar en optimizar su trabajo. Así de fácil.

# ¿Como funciona?
La primera versión utiliza tres grandes tecnologias para proporcionar el servicio.
* GIT. Para la comunicación de archivos.
* HTTP. Para la comunicación con las aplicaciones web.
* HTML. Para la comunicación con los científicos y desarrolladores.

![](http://imageshack.com/a/img922/4170/JUEwVz.jpg)

Como se ve en la imagen de la arquitectura, la aplicación se divide en cuatro grandes bloques. Cada bloque tiene una funcionalidad única y que esta descrita en diversas hojas de esta wiki, busca y encontraras. Los bloques están orientados a la mejora funcional, es decir, cuando se realicen cambios y mejoras. Estas deberán afectar lo menos posibles a la aplicación. Los diferentes bloques son los siguientes:

1. Execution Enviroment layer: Capa que realiza la ejecución de los diversos proyectos de diferente indole en diferentes sistemas operativos.
2. Bussiness layer: Capa que realiza el tratado de datos y genera ordenes por medio de los usuarios o por eventos autónomos.
3. REST layer: Capa que realiza la comunicación entre diversas aplicaciones web o aplicaciones que utilicen el protocolo web.
4. Servidor web: Capa que gestiona una aplicación web, orientada a la manipulación de este repositorio de información.

#Getting Started
Para poner en ejecución la aplicación en la versión actual. Sera necesario tener instalado Eclipse con la funcionalidad maven operativa y una conexión a internet estable.

1. Clonar el proyecto en un sistema por el momento **Ubuntu** con interfaz grafica.
2. Utilizar la carpeta clonada (uniapi) como workspace para el eclipse.
3. Importar cada uno de los proyectos como proyectos maven. Actualizarlos y utilizar la orden **clean install** como rele de seguridad.
4. Descargar y descomprimir el neo4j en la carpeta clonada (uniapi) de este enlace ![](https://neo4j.com/download/?ref=home).
> Igual hay que actualizar el archivo uniapiStartDB con el nombre de la carpeta de la DB, si han cambiado de versión.
5. Ejecutar el archivo con el comando mostrado a continuación y esperar en torno de 3-5 minutos, si no existen fallos continuar al paso siguiente.

> ./uniapi

*NOTA: Si hay fallo en la compilación y indica que el compildor no se encuentra (JRE Y JDK no coinciden). Sera necesario modificar el JRE del proyecto para equipararlo sobre el JDK de eclipse. O a la inversa actualizar el JDK de eclipse para equipararlo con el JRE de los proyectos.Los proyectos se estan desarrollando con configuraciones identicas, lo normal es que si falla un proyectos los demas fallen, si se arregla uno los demas funcionen.*

6. El servicio web se encuentra en la primera dirección, el servicio REST en la segunda dirección, el servicio de DB en la tercera dirección. Todas accedibles desde el navergador web. Para poder acceder al servicio REST. Es necesario conocer su protocolo.


> http://localhost:3000/
> http://localhost:8080/
> http://localhost:7474/

Si todos los pasos han salido bien, sin encontrarse ningun fallo. Nos podremos conectar a la pagina http://localhost:3000 en nuestro navegador y debera aparecer una pagina de loggin. Si escribimos http://localhost:3000/createAccount podremos realizar una cuenta para acceder al sistema.





