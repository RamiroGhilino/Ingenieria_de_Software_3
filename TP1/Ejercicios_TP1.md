# Trabajo Practico 1 - Git Básico

## 4 - Familiarizarse con el concepto de Pull Request

### Pull Request:

Un pull request es una solicitud al dueño del repositorio para aceptar los cambios realizados por otros usuarios. Este comando nos permite contribuir a los proyectos de otras personas.

Crear un branch local y agregar un archivo nuevo (Cambio):
![](/Archivos/Archivos_TP1/New_Branch.png)

Agregando cambios a la branch, creando commit y subiendo al repo remoto:
```
ramiro@ramiro:~/Ingeniería de Software 3$ git add .
ramiro@ramiro:~/Ingeniería de Software 3$ git commit -m "Se agrega un nuevo markdown para subir capturas de pantalla sobre los ejercicios"
[Tp1-Ejercicios 2fcc600] Se agrega un nuevo markdown para subir capturas de pantalla sobre los ejercicios
 2 files changed, 11 insertions(+)
 create mode 100644 Archivos/Archivos_TP1/New_Branch.png
 create mode 100644 ejercicios.md
ramiro@ramiro:~/Ingeniería de Software 3$ git push origin Tp1-Ejercicios 
Enumerando objetos: 7, listo.
Contando objetos: 100% (7/7), listo.
Compresión delta usando hasta 8 hilos
Comprimiendo objetos: 100% (4/4), listo.
Escribiendo objetos: 100% (6/6), 87.68 KiB | 9.74 MiB/s, listo.
Total 6 (delta 0), reusados 0 (delta 0), pack-reusados 0
remote: This repository moved. Please use the new location:
remote:   https://github.com/RamiroGhilino/Ingenieria_de_Software_3.git
remote: 
remote: Create a pull request for 'Tp1-Ejercicios' on GitHub by visiting:
remote:      https://github.com/RamiroGhilino/Ingenieria_de_Software_3/pull/new/Tp1-Ejercicios
remote: 
To https://github.com/RamiroGhilino/Trabajo-Pr-ctico-N-1---Ing-de-Sof-3.git
 * [new branch]      Tp1-Ejercicios -> Tp1-Ejercicios
 ```

Crear Pull Request: 

![](/Archivos/Archivos_TP1/Pull_Request.png)

![](/Archivos/Archivos_TP1/CrearPullRequest.png)

![](/Archivos/Archivos_TP1/AceptarPullRequest.png)



## 5- Mergear código con conflictos

Al final del archivo README se agregaron las siguientes lineas para completar el ejercicio:

```
Estas lineas fueron agregadas desde el clon original para desarrollar el ejercicio 5 del TP1
```

Mientras que en el README del segundo clon se agregó:

```
Estas lineas se agregaron desde el segundo clon para generar conflictos
```


Solucionando conflictos:

![](/Archivos/Archivos_TP1/SolucionMerge.png)

### Versiones Local, Base y Remote:

Local: Son los cambios que nosotros hicimos.

Remote: Los cambios que han sido subidos al repositorio, usualmente hecho por otros usuarios.

Base: Una version de los archivos que tanto local como remote tienen de ancestro.

![](/Archivos/Archivos_TP1/Base%2CLocal%2CRemot.png)

## 6- Algunos ejercicios online

![](/Archivos/Archivos_TP1/Introducci%C3%B3n.png)
