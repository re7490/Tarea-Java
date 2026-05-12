Tarea 3 - Lenguajes de Programacion
Nombre: Renato Padilla -- Rol: 202473641-8

(Alt+z)-->Recomendacion

El programa adjunto implementa una versión bastante simplificada de Final Fantasy VII.

En este, el jugador controla a Cloud, quien debera abrirse camino a traves de distintas zonas y enemigos con tal de llegar al nucleo del planeta para derrotar a Sephiroth, quien esta reuniendo suficiente energia para lanzar un hechizo devastador que destruira todo. 

El juego es por turnos y consta de 3 Zonas (Sector7, Gongaga, Nuecleo del Planeta), en cada zona el jugador debera elegir que hacer: explorar la zona, viajar a otra zona, revisar su mochila (inventario), descansar (exclusivo de Sector7) o cerrar el juego.


Para el correcto funcionamiento del programa (compilación y ejecución), todos los archivos adjuntos (.java, Makefile y carpetas) a este README deben estar en la misma carpeta, además de ser operado en Linux Debian o WSL Debian. Luego, abra la terminal dentro de la misma carpeta y ejecute lo siguiente: 
    
    java -version
    make -version

En caso que alguno de estos no muestre versión, descarguelos a traves de la terminal (caso contrario ignore esto).

Una vez este todo listo, ejecute: 
    make compile
    make run

Al ejecutar el segundo comando anterior, se te dara la bienvenida al juego y se te preguntara si quieres iniciarlo (1) o no (0). (en caso de seleccionar 0, presionar Enter para pasar los mensajes que se muestran)
Una vez iniciado el juego, se mostrara el siguiente menu:

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

    1. Explorar los alrededores: Le permite al jugador adentrarse en la zona actual, mostrando los eventos que existen en la zona. (Sector7 mostrara al Caballero y la tienda, Gongaga se eligira un evento aleatorio entre los dos que tiene y en Nucleo del Planeta el evento de la zona)
    2. Viajar a otra zona: Abrira un menu para que el jugador decida moverse entre las zonas existentes, si se selecciona una zona inaccecible, el juego lo indicara y ls condicion para acceder, además, el jugador se mantendra en la zona actual.
    3. Ver Mochila: Muestra el Inventario del jugador, mostrando las Materias encontradas en Gongaga indicando si estan equipadas o no. Tambien se le permitira al jugador equiparlas y desequiparlas en la espada que porta. En caso de estar vacio, se indicara que la mochila esta vacia, por lo que no se mostraran las interacciones posibles con las materias
    4. Descansar: Opcion exclusiva de Sector 7, el jugador al acceder regenerara su vida y Mp.
    0. Salir del juego: Cierra el juego.

Al momento de entrar en combate se mostrara lo siguiente:

1. Enemigo XXX HP: 50/50
2. Enemigo X HP: 70/70
Cloud HP: 200/200 | MP: 50/50 | Limite: 0/100
1. Ataque Físico | 2. Magia  (Disponible)| 3. Curarse  (No disponible)| 4. Ataque limite (No disponible)| 0. Huir
Eleccion: 

Mostrando la lista de enemigos del combate, la vida de cada enemigo, ademas de mostrar la vida, MP y la carga del ataque Limite del jugador.
Tambien se muestran las opciones que posee el jugador para avanzar en el combate, cada opcion consumira turno en caso de ser una accion valida.
En caso que el combate tenga a varios enemigos (en Gongaga) eljugador debera elegir el ataque que se realizara, y luego seleccionar al enemigo que desea atacar. Los enemigos al llegar su turno, podran atacar todos en conjunto, o solo uno atacara (como se indico en el pdf de la tarea 3)
Existen dos formas de curarse:
    1. Equipar una materia CURA en la espada, y curarse usando la magia. (2)
    2. Usando directamente la materia CURA, en caso de no querer equiparla en la espada, pero al momento de usarla, la materia se gastara. (3)
Los ataques tanto fisicos como de magia cargaran la barra del Ataque Limite (el mismo ataque no se carga a si mismo... por temas de que lo tendrias en cada turno en la parte inicial del juego)