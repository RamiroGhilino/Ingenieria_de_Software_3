# Trabajo Practico 2 - Introducción a Docker 

## 1- Instalar Docker Community Edition

![](/TP2/Archivos_TP2/docker_version.png)

## 3- Obtener la imagen BusyBox

Utilizamos docker pull [nombre_app] para que docker busque en nuestro sistema la imagen y sino la descargue

```
ramiro@ramiro:~/Ingeniería de Software 3$ sudo docker pull busybox 
Using default tag: latest
latest: Pulling from library/busybox
50783e0dfb64: Pull complete 
Digest: sha256:ef320ff10026a50cf5f0213d35537ce0041ac1d96e9b7800bafd8bc9eff6c693
Status: Downloaded newer image for busybox:latest
docker.io/library/busybox:latest
```

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker images
REPOSITORY                      TAG       IMAGE ID       CREATED         SIZE
busybox                         latest    7a80323521cc   2 weeks ago     1.24MB

```
## 4- Ejecutando contenedores

Usamos docker run [nombre_app] para correr esa imagen, se pueden agregar otros parámetros para la ejecución.

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker run busybox
```

### Explicar porque no se obtuvo ningún resultado:

No obtuvimos ningún resultado ya que la imagen que estamos cooriendo, "busybox" es un software que nos permite ejecutar utilidades Unix, al solo pedirle que se ejecute sin otros parametros no obtenemos respuesta.

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker run busybox echo "Hola Mundo"
Hola Mundo
```

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

ramiro@ramiro:~/Ingeniería de Software 3$ docker ps -a
CONTAINER ID   IMAGE                  COMMAND                  CREATED          STATUS                      PORTS     NAMES
6b53cd3a3c55   busybox                "echo 'Hola Mundo'"      59 seconds ago   Exited (0) 58 seconds ago             epic_lederberg
5ec22bb305e4   busybox                "sh"                     6 minutes ago    Exited (0) 6 minutes ago              objective_dewdney
4fb8d4570f05   hello-world            "/hello"                 9 minutes ago    Exited (0) 9 minutes ago              elegant_hawking
975efe96f061   sonarqube:latest       "/opt/sonarqube/bin/…"   2 months ago     Exited (130) 2 months ago             sonarqube
91595b489c1e   appsecco/dvna:sqlite   "npm start"              2 months ago     Exited (0) 2 months ago               dvna
```

## 5- Ejecutando en modo interactivo

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker run -it busybox sh
/ # ps
PID   USER     TIME  COMMAND
1     root      0:00 sh
7     root      0:00 ps
/ # uptime
 18:34:33 up 53 min,  0 users,  load average: 0.28, 0.58, 0.74
/ # free
              total        used        free      shared  buff/cache   available
Mem:       16121252     2354288     9511180      534772     4255784    12887520
Swap:       2097148           0     2097148
/ # ls -l /
total 36
drwxr-xr-x    2 root     root         12288 Jul 29 01:32 bin
drwxr-xr-x    5 root     root           360 Aug 18 18:34 dev
drwxr-xr-x    1 root     root          4096 Aug 18 18:34 etc
drwxr-xr-x    2 nobody   nobody        4096 Jul 29 01:32 home
dr-xr-xr-x  365 root     root             0 Aug 18 18:34 proc
drwx------    1 root     root          4096 Aug 18 18:34 root
dr-xr-xr-x   13 root     root             0 Aug 18 18:34 sys
drwxrwxrwt    2 root     root          4096 Jul 29 01:32 tmp
drwxr-xr-x    3 root     root          4096 Jul 29 01:32 usr
drwxr-xr-x    4 root     root          4096 Jul 29 01:32 var
/ # exit
```

## 6- Borrando contendores terminados 

Para mostrar la lista de contenedores: docker ps -a

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker ps -a
CONTAINER ID   IMAGE                  COMMAND                  CREATED          STATUS                      PORTS     NAMES
8406e4f7e5ba   busybox                "sh"                     5 minutes ago    Exited (0) 5 minutes ago              inspiring_booth
6b53cd3a3c55   busybox                "echo 'Hola Mundo'"      10 minutes ago   Exited (0) 10 minutes ago             epic_lederberg
5ec22bb305e4   busybox                "sh"                     15 minutes ago   Exited (0) 15 minutes ago             objective_dewdney
4fb8d4570f05   hello-world            "/hello"                 18 minutes ago   Exited (0) 18 minutes ago             elegant_hawking
975efe96f061   sonarqube:latest       "/opt/sonarqube/bin/…"   2 months ago     Exited (130) 2 months ago             sonarqube
91595b489c1e   appsecco/dvna:sqlite   "npm start"              2 months ago     Exited (0) 2 months ago               dvna
```

Para borrar un contenedor usamos: docker rm [name], aunque tambien podemos utilizar los primeros 4 digitos del ID en lugar del nombre

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker rm 9159
9159
ramiro@ramiro:~/Ingeniería de Software 3$ docker ps -a
CONTAINER ID   IMAGE              COMMAND                  CREATED          STATUS                      PORTS     NAMES
8406e4f7e5ba   busybox            "sh"                     8 minutes ago    Exited (0) 7 minutes ago              inspiring_booth
6b53cd3a3c55   busybox            "echo 'Hola Mundo'"      12 minutes ago   Exited (0) 12 minutes ago             epic_lederberg
5ec22bb305e4   busybox            "sh"                     17 minutes ago   Exited (0) 17 minutes ago             objective_dewdney
4fb8d4570f05   hello-world        "/hello"                 20 minutes ago   Exited (0) 20 minutes ago             elegant_hawking
975efe96f061   sonarqube:latest   "/opt/sonarqube/bin/…"   2 months ago     Exited (130) 2 months ago             sonarqube
```

Borramos todo los contenedores que ya no se ejecutan:

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker rm $(docker ps -a -q -f status=exited)
8406e4f7e5ba
6b53cd3a3c55
5ec22bb305e4
4fb8d4570f05
975efe96f061
ramiro@ramiro:~/Ingeniería de Software 3$ docker ps -a
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
```

## 7- Montando volúmenes


```
ramiro@ramiro:~/Ingeniería de Software 3$ docker run -it -v /home/ramiro/Ingeniería\ de\ Software\ 3/:/var/escritorio/ busybox /bin/sh
/ # ls -l /var/escritorio
total 20
drwxrwxr-x    4 1000     1000          4096 Aug 18 18:14 Archivos
-rw-rw-r--    1 1000     1000          2714 Aug 18 18:15 Ejercicios_TP1.md
-rw-rw-r--    1 1000     1000          5965 Aug 18 18:46 Ejercicios_TP2.md
-rw-rw-r--    1 1000     1000           200 Aug 18 18:11 ReadMe.md
/ # touch /var/escritorio/hola.txt
/ # ls
bin   dev   etc   home  proc  root  sys   tmp   usr   var
/ # cd var
/var # ls
escritorio  spool       www
/var # cd escritorio/
/var/escritorio # ls
Archivos           Ejercicios_TP2.md  hola.txt
Ejercicios_TP1.md  ReadMe.md
/var/escritorio # 

```

![](/TP2/Archivos_TP2/hola_txt.png)

## 8- Publicando puertos

Ejecutamos la siguiente imagen, usando la flag -d para tener control de la consola.

![](/TP2/Archivos_TP2/nyan-cat-web.png)

```
ramiro@ramiro:~/Ingeniería de Software 3$ docker run -d -p 8080:80 daviey/nyan-cat-web
ff7558e901b8ac872bf9c6f219d36935ce84a582a0bd3f3f8ab024f155f741e1
```

Ahora podemos ver que la imagen de docker tiene asigando el puerto 8080 en la computadora, por los cual al ir a localhost:8080:

![](/TP2/Archivos_TP2/Docker8080.png)

## 9- Utilizando una base de datos

![](/TP2/Archivos_TP2/postgres.png)

![](/TP2/Archivos_TP2/DBeaver.png)



