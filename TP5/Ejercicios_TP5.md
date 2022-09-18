# Trabajo Práctico 5 - Herramientas de construcción de software

## Desarrollo 

### Introducción a Maven

1. ¿Qué es Maven?

Maven es una herramienta que se utiliza para la construcción  y administración de proyectos basados en Java.

El objetivo principal de Maven es permitir que un desarrollador comprenda el estado completo de un esfuerzo de desarrollo en el menor tiempo posible. Para lograr este objetivo, Maven se ocupa de varias áreas de preocupación:

* Facilitando el proceso de construcción
* Proporcionar un sistema de construcción uniforme
* Proporcionar información de proyectos de calidad.
* Fomentar mejores prácticas de desarrollo

2. ¿Qué es el archivo POM?

POM (Project Object Model) es la unidad de trabajo fundamental en Maven. Es un archivo XML que contiene información sobre el proyecto y los detalles de configuración utilizados por Maven para construir el proyecto. 

- ModelVersion: Especifica el ModelVersion del archivo POM. Esta version depende de la version de Maven, ya que Maven 1.x usa un model version 3.0.0 y tanto Maven 2.x y 3.x usan un model version 4.0.0

- groupId: Define el dominio, el proyecto real al que pertenece el proyecto Maven actual, suele ser único en una organización o proyecto. Cada artifact tiene un groupId.

- artifacId: Define un módulo maven, nombre del artifact sin la versión, generalmente del tipo jar ya que se trata del ejecutable del projecto.

- versionId: especifica la version del artifact.

groupId, artifactId y versionId en conjunto identifican un artifact.

**Nota**: El artifact o artefacto es el ejecutable que se obtiene al finalizar la construcción del proyecto.

3. Repositorios

**Repositorio local:** reside en la computadora donde se ejecuta Maven. Cachea descargas remotas y contiene comtiene artifacts de compilación temporales.

**Repositorio central:** localizado en `http://repo.maven.apache.org/maven2/`. Cuando se compila, maven primero intenta encontrar la dependencia en el repositorio local. Si no esta ahí, por defecto, activa la descarga desde este repositorio central. Es el repositorio remoto por defecto.

**Repositorio remoto:** cualquier otro repositorio al que se accede mediante protocolos como `file://` o `https://`. Siempre que se necesita un artifact de estos repositorios, primero se descarga al repositorio local del desarrollador y luego se utiliza.

4. Entender Ciclos de vida de build

    - default: Maneja el deployment del proyecto

    - clean: Maneja la limpieza del proyecto, elimina los archivos generados.
    
    - site: Maneja la cración del sitio web del proyecto.

5. Luego de crear el archivo pom.xml y correr `mvn clean install` concluyo que este comando ejecuta la fase clean y luego compienza la compilación desde un estado limpio. Como resultado se instalaron una serie de archivos jar desde el repositorio central y se creó el proyecto maven y el ejecuteble .jar correspondiente a este.

## 4 - Maven Continuación

Luego de correr el comando `mvn archetype:generate -DgroupId=ar.edu.ucc -DartifactId=ejemplo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false`se crearon los siguientes directorios:

![](./Archivos_TP5/DirectoriosMavenContinuacion.png)

Podemos obsevar que el directorio principal es el Id del artifact y que se crearon directorios siguiendo los parámetros de DgroupId.

A la salida de la ejecución del comando `mvn clean package` obtenemos:

![](./Archivos_TP5/SalidaContinuacio.png)

Podemos observar como se crea .jar de manera correcta y supera el test preparado.

Al correr el .jar creado anteriormente:

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3/TP5/MavenContinuacion/ejemplo$ java -cp target/ejemplo-1.0-SNAPSHOT.jar ar.edu.ucc.App
Hello World!
```
Podemos observar como creamos un Hello World! ejecutable con la ayuda de Maven.

## 6- Manejo de dependencias

![](./Archivos_TP5/ErrorUber.png)

En este caso, el código no compila debido a un error de dependencias de librerias.

![](./Archivos_TP5/ErrorLogget.png)

Aún añadiendo las dependencias en el pom.xml la clase que se intenta importar (LoggerFactory) no es encontrada porque esta especificación es útil en el momento de la compilación (lo que justifica que antes de añadir las dependencias a pom.xml no era posible la compilación) pero al momento de linkear la clase no puede ser alcanzada.

![](./Archivos_TP5/SolucionErrorLogger.png)

Implementando la segunda solución:

![](./Archivos_TP5/SegundaSolucionUber.png)

## 7 - Utilizar una IDE

Una vez importado el proyecto como se indicó en los enunciados, podemos ejecutar la aplicación con el botón verde con el logo de "play" en su interior, que se denomina Run.

![](./Archivos_TP5/RunEclipse.png)

Para depurar una aplicación, utilizamos el botón que se encuentra a la izquierda del botón Run, que se denomina Debugg en el IDE.

![](./Archivos_TP5/Debbug.png)

El botón para correr Units Test y Coverage utilizamos el botón que se encuentra a la derecha del botón Run que ubicamos en primer lugar.

![](./Archivos_TP5/CoverageYTests.png)

Como podemos ver, la gran mayoría de las acciones mas utilizadas se pueden encontrar en la misma barra de navegación y si no la encontramos allí la encontraremos dando click derecho sobre los archivos que vemos a la izquierda.

## 8 - Ejemplo con nodejs

En mi caso, ya tengo instalado nodejs:

![](./Archivos_TP5/nodejsversion.png)

Después de instalar express, creo la aplicación y la corro:

![](./Archivos_TP5/NodeJSHelloWorld.png)

![](./Archivos_TP5/ExpressLocalHost.png)

**Análisis Sobre NPM**

NPM consiste de dos partes principales:

- Una herramienta CLI (interfaz de línea de comandos) para la publicación y descarga de paquetes, y
- Un repositorio en línea que alberga paquetes de JavaScript
Podemos pensar en el repositorio npmjs.com como un centro de cumplimiento que recibe paquetes de bienes de los vendedores (autores de paquetes npm) y los distribuye a los compradores (usuarios de paquetes npm).

Para facilitar este proceso, el centro de cumplimiento npmjs.com emplea un ejército de trabajadores (npm CLI) que serán asignados como asistentes personales a cada cliente individual de npmjs.com.*

Cada proyecto en JavaScript puede ser enfocado como un paquete npm con su propia información de paquete y su archivo package.json para describir el proyecto.

package.json se generará cuando se ejecute npm init para inicializar un proyecto JavaScript/Node.js, con estos metadatos básicos proporcionados por los desarrolladores: name, version, description y license.

En nuestro caso el contenido es:

![](./Archivos_TP5/Package.png)

## 9 - Ejemplo con python

![](./Archivos_TP5/cookiecutter.png)

**Explicación de cookiecutter, make y pip**

- *cookiecutter*: proporciona una interfaz gráfica de usuario para descubrir plantillas, opciones de plantilla de entrada y crear proyectos y archivos. Crea proyectos a partir de plantillas de proyectos.

- *make*: Utilidad para construir y mantener grupos de programas. El propósito de la utilidad make es determinar automáticamente qué partes de un programa necesitan ser recompiladas y emitir los comandos para recompilarlas. Puede usar make con cualquier lenguaje de programación cuyo compilador se pueda ejecutar con un comando de shell. Puede usarlo para describir cualquier tarea en la que algunos archivos deban actualizarse automáticamente desde otros siempre que los otros cambien. Debe especificar un archivo makefile que describa las relaciones entre los archivos en su programa y los estados de los comandos para actualizar cada archivo. En un programa, normalmente el archivo ejecutable se actualiza a partir de archivos objeto, que a su vez se compilan mediante la compilación de archivos fuente. Se usa para compilar y linkear codigo, hace más facil la compilación.

- *pip*: es una herramienta de línea de comandos que permitirá instalar, reinstalar y desinstalar paquetes de Python, además de resolver automáticamente las dependencias de cada uno de estos paquetes.

## 10 - Build tools para otros lenguajes

- C -> Cheesemake,
- C++ -> Make, CMake, Ninja.
- C# -> Cake (C# Make), MSBuild.
- JS -> Webpack, Grunt, Gulp, Browserify,Brunch, Yeoman.
- Perl -> Perl Build System.
- php -> Phing.
- Swift -> llbuild, Swift Package Manager.
- Go -> Task, Make.
- Visual Basic -> Microsoft Build Engine (MSBuild).
- Groovy -> Gradle.
