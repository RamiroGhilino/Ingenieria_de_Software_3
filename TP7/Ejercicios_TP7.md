# Trabajo Práctico 7 - Servidor de Build (de integración continua)

## 1 - Poniendo en funcionamiento Jenkins

Luego de completar los pasos:

![](./Archivos_TP7/JenkinsLandPage.png)

## 2 - Conceptos Generales

Preguntar si se completa con el profe o solos.

## 3 - Instalando Plugins y configurando herramientas

Instalando el Plugin:

![](./Archivos_TP7/Plugins.png)

Instalando Maven:

![](./Archivos_TP7/Maven.png)

## 4 - Creando el primer Pipeline Job

Creo el Pipeline Hello-World y cerca del final de la página elijo el sample de código hello-world:

![](./Archivos_TP7/PipelineHelloWorld.png)

Ahora lo ejecuto:

![](./Archivos_TP7/PipelineHelloWorldRun.png)

Y si observamos las salidas de consola:

![](./Archivos_TP7/ConsoleOutputHelloWorld.png)

Como podemos observar, Jenkins de manera automática ejecutó línea por línea nuestro código.

## 5 - Creando un Pipeline Job con Git y Maven

Modifiqué el repositorio que se utiliza por uno propio con el pom modificado: https://github.com/RamiroGhilino/Jenkins-simple-maven-solution.git 

![](./Archivos_TP7/Simple-Maven.png)

