## UniApi Trabajo fin de grado UNIOVI

#Getting Started
Para poner en ejecución la aplicación en la versión actual. Sera necesario tener instalado Eclipse con la funcionalidad maven operativa y una conexión a internet estable.

1. Clonar el proyecto en una carpeta y importar cada carpeta de manera individual como proyectos maven.
2. Utilizar la instrucción **"clean install package"** en los proyectos *uniapi_backend_business* y *uniapi_backend_enviroment_execution*.
3. Añadir los jar existentes en las carpetas target de los proyectos mencionados en el punto anterior al **Java Build Path** del proyecto *uniapi_backend_apirest*.
4. Utilizar la instrucción **"clean install package spring-boot:run"** en el proyecto uniapi_backend_apirest.

*NOTA: Si hay fallo en la compilación y indica que el compildor no se encuentra (JRE Y JDK no coinciden). Sera necesario modificar el JRE del proyecto para equipararlo sobre el JDK de eclipse. O a la inversa actualizar el JDK de eclipse para equipararlo con el JRE de los proyectos.Los proyectos se estan desarrollando con configuraciones identicas, lo normal es que si falla un proyectos los demas fallen, si se arregla uno los demas funcionen.*

Si todos los pasos han salido bien, sin encontrarse ningun fallo. Nos podremos conectar a la pagina http://localhost:8080 en nuestro navegador y debera aparecer un mensaje de bienvenida.

> {id:1,message:'¿Hola que tal? parece que esto funciona! : )'}




