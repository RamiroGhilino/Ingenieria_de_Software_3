# Trabajo Practico 3 - Arquitectura de Sistemas Distribuidos

## 1 - Sistema distribuido simple

Crear Red en Docker:

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker network create -d bridge mybridge
2bc57dc97cfa398b26faf54ff1be5e969147277f7e594da9002891dda16cdf43
```

Instanciar una base de datos Redis conectada a esa Red:

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$  docker run -d --net mybridge --name db redis:alpine
Unable to find image 'redis:alpine' locally
alpine: Pulling from library/redis
213ec9aee27d: Pull complete 
c99be1b28c7f: Pull complete 
8ff0bb7e55e3: Pull complete 
6d80de393db7: Pull complete 
8dbffc478db1: Pull complete 
7402bc4c98a0: Pull complete 
Digest: sha256:dc1b954f5a1db78e31b8870966294d2f93fa8a7fba5c1337a1ce4ec55f311bc3
Status: Downloaded newer image for redis:alpine
56868ad274d17eb20d0b6248e9093627307c016edddb50711bbf998ecc19926c

```

Levantar una aplicacion web, que utilice esta base de datos:

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$   docker run -d --net mybridge -e REDIS_HOST=db -e REDIS_PORT=6379 -p 5000:5000 --name web alexisfr/flask-app:latest
Unable to find image 'alexisfr/flask-app:latest' locally
latest: Pulling from alexisfr/flask-app
f49cf87b52c1: Pull complete 
7b491c575b06: Pull complete 
b313b08bab3b: Pull complete 
51d6678c3f0e: Pull complete 
09f35bd58db2: Pull complete 
1bda3d37eead: Pull complete 
9f47966d4de2: Pull complete 
9fd775bfe531: Pull complete 
2446eec18066: Pull complete 
b98b851b2dad: Pull complete 
e119cb75d84f: Pull complete 
Digest: sha256:250221bea53e4e8f99a7ce79023c978ba0df69bdfe620401756da46e34b7c80b
Status: Downloaded newer image for alexisfr/flask-app:latest
974ee32a2565da433d04e4536161868b83c936cf7269d032c963052829da87a6

```

![](/TP3/Archivos_TP3/redis.png)

Verificar los contenedores con `docker ps -a`:

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker ps -as
CONTAINER ID   IMAGE                       COMMAND                  CREATED          STATUS          PORTS                                       NAMES     SIZE
974ee32a2565   alexisfr/flask-app:latest   "python /app.py"         17 seconds ago   Up 16 seconds   0.0.0.0:5000->5000/tcp, :::5000->5000/tcp   web       286kB (virtual 701MB)
56868ad274d1   redis:alpine                "docker-entrypoint.s…"   6 minutes ago    Up 6 minutes    6379/tcp                                    db        0B (virtual 28.5MB)

```

Podemos observar que los puertos abiertos son el 5000 en nuestro equipo asociado al puerto 5000 del container, así como el puerto 6379 está preparado para escuchar pero no está asociado a un puerto en el equipo.

Ahora, analizando las redes, podemos listar las redes con `docker network ls`y luego obtener información específica con `docker network inspect [nombre red]`

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker network ls
NETWORK ID     NAME       DRIVER    SCOPE
bbabc344a98a   bridge     bridge    local
fe39c81fe60a   host       host      local
2bc57dc97cfa   mybridge   bridge    local
7678207b6537   none       null      local
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker network inspect mybridge 
[
    {
        "Name": "mybridge",
        "Id": "2bc57dc97cfa398b26faf54ff1be5e969147277f7e594da9002891dda16cdf43",
        "Created": "2022-08-25T14:27:07.409672525-03:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "172.18.0.0/16",
                    "Gateway": "172.18.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "56868ad274d17eb20d0b6248e9093627307c016edddb50711bbf998ecc19926c": {
                "Name": "db",
                "EndpointID": "dc37b41baa3e02ae3739d894cc56988412a55f4f0c1b7b18c917d035b7643747",
                "MacAddress": "02:42:ac:12:00:02",
                "IPv4Address": "172.18.0.2/16",
                "IPv6Address": ""
            },
            "974ee32a2565da433d04e4536161868b83c936cf7269d032c963052829da87a6": {
                "Name": "web",
                "EndpointID": "3c324429b0336b94b8dd96a803e62350c3bdf5e417bd99c3138cb0f44a6edceb",
                "MacAddress": "02:42:ac:12:00:03",
                "IPv4Address": "172.18.0.3/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]
```

## 2 - Análisis del sistema

```
import os

from flask import Flask
from redis import Redis


app = Flask(__name__)
redis = Redis(host=os.environ['REDIS_HOST'], port=os.environ['REDIS_PORT'])
bind_port = int(os.environ['BIND_PORT'])


@app.route('/')
def hello():
    redis.incr('hits')
    total_hits = redis.get('hits').decode()
    return f'Hello from Redis! I have been seen {total_hits} times.'


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True, port=bind_port)

```

1) Este código incrementa los hits, los trae y los muestra junto a un mensaje.

2) El parámetro `-e` permite establecer variables de entorno, lo utilizamos para indicar cual va a ser la base de datos y el puerto.

3) No sucede nada, simplemente eliminamos el container y lo volvemos a instalar.

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker rm -f web
web
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker run -d --net mybridge -e REDIS_HOST=db -e REDIS_PORT=6379 -p 5000:5000 --name web alexisfr/flask-app:latest256dc61f2ad612b3d232c6efb45120c11bb9d9a895d9b2e15bedd8f8fc3f83a0
```

4) Debido a que borramos la Base de Datos ahora la aplicación web no puede conectarse a esta.

![](/TP3/Archivos_TP3/falloconexiondb.png)

5) Como levatamos nuevamente la Base de Datos el problema de conexión se resuelve pero los valores de la base de datos se perdieron, por lo que ahora el contador se reinició.

![](/TP3/Archivos_TP3/reiniciodb.png)

6) Para evitar perder los datos de la base de datos tendríamos que indicarle al container (al momento de crearlo) que queremos guardar información en un volumen externo al container, así cuando eliminamos este container y lo volvemos a crear le pedimos que lea la información directamente desde ese volumen, manteniendo así los datos.


## 3- Utilizando docker compose

```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker-compose up -d
Creating network "ingenieria_de_software_3_default" with the default driver
Creating volume "ingenieria_de_software_3_redis_data" with default driver
Creating ingenieria_de_software_3_db_1 ... done
Creating ingenieria_de_software_3_app_1 ... done
```

![](/TP3/Archivos_TP3/dockercompose.png)

Docker Compose, a partir del archivo .yaml suministrado, realizó todas las tareas que habíamos logrado en pasos anteriores, incluyendo la creación de containers, la creación de la red y la configuración de las conexiones y volúmenes.

## 4- Aumentando la complejidad, análisis de otro sistema distribuido.

![](/TP3/Archivos_TP3/CatVsDogVots.png)

### Cofiguración del sistema

![](/TP3/Archivos_TP3/architecture.png)

* **Vote**: aplicación que depende redis, que utiliza el puerto 5000 del equipo pero el container escucha en el puerto 80, se encuentra asociado a las redes de front y back

* **Result**: aplicación que se encarga de mostrar los resultados y por ello depende de la base de datos, utiliza los puertos 5001 del equipo para mostrar la información y el puerto 5858 para comunicarse con la base de datos, al igual que Vote se encuentra asociado a ambas redes.

* **Worker**: aplicación que se encarga de extraer de redis los votos e introducirlos en la base de datos, por lo tanto depende de ambos para funcionar, se encuentra asociado a la red de back.

* **Redis**: motor de base de datos que permite almacenar datos en memoria con la opción de que sean persistentes, se utiliza en este caso para hacer una cola de votos para que el worker pueda llevarlos a la base de datos. Asociado a la red de back

* **DB**: una base de datos de postgresql para almacenar los votos de la aplicación. asociado a la red de network.


En este archivo .yml también se crean las dos redes mencionadas y un volumen para persistir los datos de la base de datos.

### Configuraciones y Tablas

Modificando los puertos, ingresamos a la base de datos y a redis.

![](/TP3/Archivos_TP3/Postgres.png)

### Diagrama de Caso de Uso

![](/TP3/Archivos_TP3/Caso_De_Uso.png)
### Diagrama de Secuencia

![](/TP3/Archivos_TP3/DiagramaSecuencia.png)
