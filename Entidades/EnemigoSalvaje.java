package Entidades;

import Componentes.*;
import Entidades.*;
import java.util.ArrayList;
import java.util.List;

public class EnemigoSalvaje extends Enemigo implements Vulnerable{
    private List<Elemento> debilidades;
    private List<Elemento> resistencias;
    private List<Elemento> inmunidades;

    /**
     * Crea enemigo  salvaje con un sistema de relacion con los elementos.
     * Utiliza el constructor de la superclase para los datos basicos y define sus tablas de tipos para el calculo de daño.
     * 
     * @param nombre             Nombre del enemigo salvaje.
     * @param xpRecompensa       Xp que otorga al ser derrotado.
     * @param chatarraRecompensa Cantidad de chatarra que suelta al morir.
     * @param stats              Objeto con los atributos del enemigo (stats)
     * @param debilidades        Lista de elementos que causan daño aumentado.
     * @param resistencias       Lista de elementos que causan daño reducido.
     * @param inmunidades        Lista de elementos que no causan daño.
     */
    public EnemigoSalvaje(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats, List<Elemento> debilidades, List<Elemento> resistencias, List<Elemento> inmunidades){
        super(nombre, xpRecompensa, chatarraRecompensa, stats);
        
        this.debilidades =  debilidades;
        this.resistencias =  resistencias;
        this.inmunidades =  inmunidades;
    }
    //Gets
    public List<Elemento> getDebilidades(){ return debilidades; }
    public List<Elemento> getResistencias(){ return resistencias; }
    public List<Elemento> getInmunidades(){ return inmunidades; }

    //Funciones
    public void giveChatarraRecompensa(Jugador Cloud){
        Cloud.añadirChatarra(this.chatarraRecompensa);
        System.out.println("Has obtenido: " + this.chatarraRecompensa + " de chatarra.");
    }

    /**
     * Determina el multiplicador de daño comparando el elemento atacante con las listas del enemigo.
     * 
     * El orden de evaluacion es: Inmunidad (0%) > Resistencia (50%) > Debilidad (200%).
     * Si el elemento no se encuentra en ninguna lista, el daño es neutral (100%).
     * 
     * @param elementoMagia El elemento de la magia que impacta al enemigo.
     * @return Multiplicador de daño (0.0, 0.5, 2.0 o 1.0).
     */
    @Override
    public double evaluarDebilidad(Elemento elementoMagia){
        for (Elemento Inmune: inmunidades){
            if (elementoMagia == Inmune){
                return 0.0;
            }
        }
        for (Elemento resiste: resistencias){
            if (elementoMagia == resiste){
                return 0.5;
            }
        }
        for (Elemento debil: debilidades){
            if (elementoMagia == debil){
                return 2.0;            }
        }
        return 1.0;
    }

    /**
     * Realiza un ataque contra Clous.
     * El daño se calcula multiplicando la fuerza del enemigo por un factor de 1.25.
     * 
     * @param Cloud jugador recibira el daño resultante del ataque.
     */
    @Override
    public void atacar(Jugador Cloud){
        int danoBase = (int) (this.stats.getFuerza() * 1.25);
        Cloud.getStats().recibirDMG(danoBase);
        System.out.println(this.nombre + " ataca a Cloud causando " + danoBase + " de daño.");
    }

    /**
     * Entrega la Xp al jugador tras derrotar al enemigo.
     * Imprime un mensaje en consola confirmando la cantidad obtenida.
     * 
     * @param Cloud jugadoor recibe los puntos de experiencia.
     */
    @Override
    public void giveXpRecompensa(Jugador Cloud){
        Cloud.recibirXP(this.xpRecompensa);
        System.out.println("Has obtenido: " + this.xpRecompensa + " de experiencia.");
    }

}