Tarea 3 - Lenguajes de Programacion
Nombre: Renato Padilla -- Rol: 202473641-8

(Alt+z)-->Recomendacion

El programa adjunto implementa una versión bastante simplificada de Final Fantasy VII.

En este, el jugador controla a Cloud, quien debera abrirse camino a traves de distintas zonas y enemigos con tal de llegar al nucleo del planeta para derrotar a Sephiroth, quien esta reuniendo suficiente energia para lanzar un hechizo devastador que destruira todo. 

El juego es por turnos y consta de 3 Zonas (Sector7, Gongaga, Nuecleo del Planeta), en cada zona el jugador debera elegir que hacer: explorar la zona, viajar a otra zona, revisar su mochila (inventario), descansar (exclusivo de Sector7) o cerrar el juego.


Para el correcto funcionamiento del programa (compilación y ejecución), todos los archivos adjuntos (.java, Makefile y carpetas) a este README deben estar en la misma carpeta, además de ser operado idealmente en WSL Debian (donde se trabajo y ejecuto). Luego, abra la terminal dentro de la misma carpeta y ejecute lo siguiente: 
    
    java -version
    make -version

En caso que alguno de estos no muestre versión, descarguelos a traves de la terminal (caso contrario ignore esto).

Una vez este todo listo, ejecute: 
    make compile
    make run

Al ejecutar el segundo comando anterior, se te dara la bienvenida al juego y se te preguntara si quieres iniciarlo (1) o no (0). (en caso de seleccionar 0, presionar Enter para pasar los mensajes que se muestran, además de "interactuar" cuando sea pedido)

-------------MENUS---------------------

Una vez iniciado el juego, se mostrara el siguiente menu (*):

---- UBICACION:  Sector 7 ----
Nivel: 1 | HP: 200/200
MP: 50/50 | Fuerza: 15 | Magia: 15 | Chatarra: 0
¿Que deseas hacer?
1. Explorar los alrededores
2. Viajar a otra zona
3. Ver Mochila
4. Descansar
0. Salir del juego
Eleccion: 

Mostrando las stats del jugador y las opciones posibles. A continuacion se dira que hace cada una:

    1. Explorar los alrededores: Le permite al jugador adentrarse en la zona actual, mostrando los eventos que correspondientes a cada zona.
    2. Viajar a otra zona: Abrira un menu para que el jugador decida moverse entre las zonas existentes, se indicara si la zona es accesible o no, además de indicar en que zona se encuentra el jugador. En caso de seleccionar una zona inaccecible, se indicara eso y la condicion para acceder, además, el jugador se mantendra en la zona actual.
    3. Ver Mochila: Muestra el Inventario del jugador, mostrando las Materias encontradas en Gongaga indicando si estan equipadas o no. Tambien se le permitira al jugador equiparlas y desequiparlas en la espada que porta. En caso de estar vacio, se indicara que la mochila esta vacia, por lo que no se mostraran las interacciones posibles con las materias
    4. Descansar: Opcion exclusiva de Sector 7, el jugador al acceder regenerara su vida y Mp.
    0. Salir del juego: Cierra el juego.

Al momento de entrar en combate se mostrara lo siguiente (#):

1. Enemigo XXX HP: 50/50
2. Enemigo X HP: 70/70
Cloud HP: 200/200 | MP: 50/50 | Limite: 0/100
1. Ataque Físico | 2. Magia  (Disponible)| 3. Curarse  (No disponible)| 4. Ataque limite (No disponible)| 0. Huir
Eleccion: 

Mostrando la lista de enemigos del combate, la vida de cada enemigo, ademas de mostrar la vida, MP y la carga del ataque Limite del jugador.
Tambien se muestran las opciones que posee el jugador para avanzar en el combate, cada opcion consumira turno en caso de ser una accion valida.
En caso que el combate tenga a varios enemigos (en Gongaga) el jugador debera elegir el ataque que se realizara, y luego seleccionar al enemigo que desea atacar. Los enemigos al llegar su turno, podran atacar todos en conjunto, o solo uno atacara (como se indico en el pdf de la tarea 3)
Existen dos formas de curarse:
    1. Equipar una materia CURA en la espada, y curarse usando la magia. (2)
    2. Usando directamente la materia CURA, en caso de no querer equiparla en la espada, pero al momento de usarla, la materia se gastara. (3)
Los ataques tanto fisicos como de magia cargaran la barra del Ataque Limite (el mismo ataque no se carga a si mismo... por temas de que lo tendrias en cada turno en la parte inicial del juego)
Al seleccionar Huir, tendras un 50% de probabilidades de que funcione, si no logras huir, el turno sera consumido de todas formas.

-------------ZONAS e INTERACCIONES---------------------

Todas las zonas mostraran el menu (*), con las mismas opciones, a excepcion de la opcion 4, como ya se dijo.

Sector 7: Hay dos opciones de accion para el jugador: combate de simulacion y acceder a la tienda. El combate mostrara el menu de combate (#) y tendra 1 o 2 enemigos (30% de probabilidad que hayan dos enemigos) si son dos enemigos, existe la posibilidad que se coordinen para atacar. La tienda tiene su propia interfaz, que indicara las mejoras que se pueden comprar, y al comprarlas se actualizaran las estadisticas y se descontara el costo a la chatarra.

Gongaga: Al explorar la zona, existe la posibilidad de sufrir una emboscada de hasta 3 enemigos (distintos o identicos), en caso de ser mas de 1 enemigo, existe la posibilidad de que se coordinen y ataquen a Cloud en el mismo turno. Si no se sufre la emboscada, el jugador encontrara Materias, que son equipables en el arma.

Nucleo del Planeta: Al explorar la zona, el jugador se encontrara con Sephiroth, antes de iniciar el combate, este le dara a Cloud la opcion de entrar al combate contra él o volver en otro momento. En caso de acceder al combate no se podra Huir, en caso que el jugador quiera hacerlo, sera penalizado: recibiendo daño por parte de Sephiroth, Sephiroth recuperara su vida perdida y aumentara el contador de ataque supernova en 3, además Sephirtoh luego de todo eso, aun tendra su turno para atacar. En caso de aceptar la oferta de volver en otro momento, Cloud volvera al menu principal (*).

-------------STATS y SUBIDA DE NIVEL---------------------

El jugador iniciara con nivel 1 y estadisticas base: 200, 50, 15, 15 (vida actual y maxima, MP actual y maximo, fuerza, magia);
Al derrotar enemigos, conseguira Xp, que lo ayudaran a subir de nivel y subir sus estadisticas, al momento de subir de nivel, Cloud recuperara un poco de vida y Mp.

-------------ENEMIGOS---------------------

Existen 5 tipos de enemigos (1 en Sector7, 3 en Gongaga y 1 en Nucleo del Planeta), solo los de Gongaga tienen debilidad, resistencia e inmunidad a algunos ataques magicos. Cabe recalcar que se considero que el "Robot Centinela" posee resistencia a los ataques fisicos (atatque 1 de Cloud).
Todos lo enemigos tienen la posibilidad de fallar su ataque (todos 85% de precision, excepto Sephirtoh con un 90%).
El enemigo de Sector7 es el unico que no puede matar a Cloud, dejandolo a 1 de vida en vez de matarlo y dejarlo a 0 como los demas, además perder en este combate no penaliza a Cloud con perdida de chatarra y/o Materia.
Sephiroth posee un contador de SuperNova, el cual aumenta en 1 con cada turno, este ataque acaba instantaneamente con Cloud en caso de llegar a 10, la unica forma de hacer que el contador vuelva a 0 (ademas de ser derrotado en su combate) es atacarlo con Ataque Limite.

-------------DERROTA---------------------

El jugador al ser eliminado regresara a Sector7, con su vida al maximo, ademas de perder toda la chatarra y las materias que no encontraban equipadas en la espada.
Si eres eliminado por Sephiroth, el contador de SuperNova regresara a 0.

-------------VICTORIA---------------------

Si la vida de Sephiroth llega a un valor inferior o igual a 0, el juego terminara automaticamente, indicando que el jugador ha ganado.