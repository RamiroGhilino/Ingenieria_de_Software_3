# Trabajo Práctico 4 - Arquitectura de Microservicios

## Desarrollo

### 1 -  Instanciación del sistema

Una ves levantados los cotenedores:

![](/TP4/Archivos_TP4/landpage.png)

![](/TP4/Archivos_TP4/Logging.png)

![](/TP4/Archivos_TP4/Loggeado.png)

![](/TP4/Archivos_TP4/busqueda.png)

![](/TP4/Archivos_TP4/prdocuto.png)

![](/TP4/Archivos_TP4/carrito.png)

![](/TP4/Archivos_TP4/carrito%20con%20datos.png)

![](/TP4/Archivos_TP4/orden.png)

### 2 - Investigación de los componentes
1. 
```
ramiro@XENIA-14:~/Ingenieria_de_Software_3$ docker ps
CONTAINER ID   IMAGE                                COMMAND                  CREATED          STATUS          PORTS                                                                          NAMES
80d110c097c9   weaveworksdemos/orders:0.4.7         "/usr/local/bin/java…"   12 minutes ago   Up 12 minutes                                                                                  docker-compose_orders_1
1d1282d3d41c   mongo:3.4                            "docker-entrypoint.s…"   12 minutes ago   Up 12 minutes   27017/tcp                                                                      docker-compose_orders-db_1
9a7778f3a22a   weaveworksdemos/edge-router:0.1.1    "traefik"                12 minutes ago   Up 12 minutes   0.0.0.0:80->80/tcp, :::80->80/tcp, 0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   docker-compose_edge-router_1
eafee0894973   rabbitmq:3.6.8                       "docker-entrypoint.s…"   12 minutes ago   Up 12 minutes   4369/tcp, 5671-5672/tcp, 25672/tcp                                             docker-compose_rabbitmq_1
a477cfc287bb   weaveworksdemos/front-end:0.3.12     "/usr/local/bin/npm …"   12 minutes ago   Up 12 minutes   8079/tcp                                                                       docker-compose_front-end_1
50435c5e5026   weaveworksdemos/catalogue-db:0.3.0   "docker-entrypoint.s…"   12 minutes ago   Up 12 minutes   3306/tcp                                                                       docker-compose_catalogue-db_1
3f280f1fa0b9   weaveworksdemos/payment:0.4.3        "/app -port=80"          12 minutes ago   Up 12 minutes   80/tcp                                                                         docker-compose_payment_1
5132a8800dbe   weaveworksdemos/queue-master:0.3.1   "/usr/local/bin/java…"   12 minutes ago   Up 12 minutes                                                                                  docker-compose_queue-master_1
114536f8ed15   mongo:3.4                            "docker-entrypoint.s…"   12 minutes ago   Up 12 minutes   27017/tcp                                                                      docker-compose_carts-db_1
4d1ef864db05   weaveworksdemos/catalogue:0.3.5      "/app -port=80"          12 minutes ago   Up 12 minutes   80/tcp                                                                         docker-compose_catalogue_1
a9371d1d6803   weaveworksdemos/user:0.4.4           "/user -port=80"         12 minutes ago   Up 12 minutes   80/tcp                                                                         docker-compose_user_1
155c8cd2fa59   weaveworksdemos/carts:0.4.8          "/usr/local/bin/java…"   12 minutes ago   Up 12 minutes                                                                                  docker-compose_carts_1
900c03338f8e   weaveworksdemos/user-db:0.4.0        "/entrypoint.sh mong…"   12 minutes ago   Up 12 minutes   27017/tcp                                                                      docker-compose_user-db_1
3eb35901cc6f   weaveworksdemos/shipping:0.4.8       "/usr/local/bin/java…"   12 minutes ago   Up 12 minutes                                                                                  docker-compose_shipping_1
```

En este caso podemos ver que el único puerto abierto para el usuario es el 80. También tenemos el 8080 abierto para el dashboard.

Por otro lado, los componentes son:
* **Carts**: Servicio de carrito de compras
* **queque-master**: se encarga de procesar los mensajes de la cola de rabbitmq
* **user**: Servicio de usuarios con sus datos
* **catalogue**: Servicio de catalogo
* **shipping**: Servicio de compra
* **front-end**: Interfaz, puerta de interacción con la API, se invocan los diferentes servicios apartir de los protocolos http.
* **user-db**: Base de datos de los usuarios
* **edge-router**: es el equivalente al API gateway en este caso.
* **payment**: Servicio de pago
* **catalogue-db**: Base de datos que contiene los productos
* **orders**: Servicio o logica de negocio de las ordenes
* **orders-db**: Base de datos de las ordenes
* **rabbitmq**: RabbitMQ es un software de negociación de mensajes de código abierto que funciona como un middleware de mensajería. Implementa el estándar Advanced Message Queuing Protocol (middleware de mensajeria).

3. Utilizamos repositorios separados ya que cada contenedor/repositorio corresponde a un proyecto independiente que brinda un servicio específico que podrá ser utilizado por otros proyectos para así lograr brindar una solución en conjunto que cumpla con las necesidades.
Como punto positivo es mas sencillo desarrollar un servicio que solo tiene que hacer una unica cosa (o pocas) que pensar en una aplicación completa con todas las funcionalidades, por esto mismo también resulta mas fácil testearlo, mantenerlo y solucionar fallos que probablemente sean aislados.
Por otro lado, lo "negativo" sería que ahora hay que pensar en la comunicación interna de los microservicios, como uno hace uso del otro y cuales son las dependencias entre si. Desplegar un sistema de microservicios debe ser mas complicado que un monolítico.

![](/TP4/Archivos_TP4/Monolith%20Vs%20Microservice%20image.png)

4. El contenedor que simula ser el API Gateway es el que se denomina `edge-router`: 

Además, si revisamos la explicación de de Traefik que es sobre lo que se basa el `edge-router` : 
*"Traefik es un Edge Router, significa que es la puerta a su plataforma, y ​​que intercepta y enruta cada solicitud entrante: conoce toda la lógica y cada regla que determina qué servicios manejan qué solicitudes (según la ruta, el host , encabezados, etc.)."*

5. Al ejecutar `curl http://localhost/customers` obtenemos la siguiente respuesta:

![](/TP4/Archivos_TP4/JSONCURL.png)

Como podemos observar, obtenemos como respuesta un JSON con los clientes o usuarios que se encuentran en la base de datos.

6. El servicio que ese encarga de obtener la solicitud es `front-end_1 `

Si revisamos los logs del contenedor con el comando `docker logs -f [Nombre Contenedor]` podemos ver la actividad que ocurre cada vez que hacemos una solicitud:

![](/TP4/Archivos_TP4/Front-1_curl.png)

Sin embargo, el servicio que realmente se encarga procesar la solicitud y recabar la información es `user_1`

![](/TP4/Archivos_TP4/Logs-User1.png)

7. En este caso, similar al anterior, `front-end_1 ` obtiene la solicitud y le encarga el trabajo de procesarla a `catalogue-1`, en la siguiente foto podemos ver que frente a las dos consultas el contenedor de catalogue introduce un nuevo log.

![](/TP4/Archivos_TP4/CurlCatalogo.png)

8. Los datos de los servicios persisten en 4 bases de datos, una para cada servicio, los cuales son: Usuarios, Catálogo, Carritos y Órdenes.

Sin embargo, estas bases de datos no tienen asignado un volumen, por lo que los datos persisten siempre y cuando no se den de baja los servicios (Contenedores).

![](/TP4/Archivos_TP4/diagramamicroservicios.png)

9. El componente encargado del procesamiento de la cola de mensajes es `queue-master_1`.

10. En este caso particular los microservicios se comunican haciendo uso de API REST, aunque existen varias maneras para lograr la comunicación, siendo la otra mas común gRPC.