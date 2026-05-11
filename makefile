# Variables
JCC = javac
JFLAGS = -g
JVM = java
MAIN = Main


default: compile

compile:
	$(JCC) $(JFLAGS) Main.java Componentes/*.java Entidades/*.java Mapa/*.java

# Regla para compilar (si hay cambios) y luego ejecutar el juego
run: compile
	$(JVM) $(MAIN)

clean:
	rm -f *.class
	rm -f Componentes/*.class
	rm -f Entidades/*.class
	rm -f Mapa/*.class