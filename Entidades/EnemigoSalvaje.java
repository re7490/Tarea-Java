package Entidades;

import Componentes.*;
import Entidades.*;
import java.util.ArrayList;
import java.util.List;

public class EnemigoSalvaje extends Enemigo implements Vulnerable{
    private List<Elemento> debilidades;
    private List<Elemento> resistencias;
    private List<Elemento> inmunidades;

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
    @Override
    public void atacar(Jugador Cloud){
        int danoBase = (int) (this.stats.getFuerza() * 1.25);
        Cloud.getStats().recibirDMG(danoBase);
        System.out.println(this.nombre + " ataca a Cloud causando " + danoBase + " de daño.");
    }
    @Override
    public void giveXpRecompensa(Jugador Cloud){
        Cloud.recibirXP(this.xpRecompensa);
        System.out.println("Has obtenido: " + this.xpRecompensa + " de experiencia.");
    }

}