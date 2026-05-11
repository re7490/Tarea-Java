package Entidades;

import Componentes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemigoSimulador extends Enemigo{
    public EnemigoSimulador(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        super(nombre, xpRecompensa, chatarraRecompensa, stats);
    }

    //funciones
    public boolean checkDanoSeguro(Jugador Cloud){
        int danoBase = (int) (this.stats.getFuerza() * 1.25);
        if (Cloud.getHpActual() < danoBase){
            return false;
        }
        return true;
    }

    @Override
    public void atacar(Jugador Cloud){

        int danoBase = (int) (this.stats.getFuerza() * 1.25);
        Cloud.getStats().recibirDMG(danoBase);
        System.out.println(this.nombre + " ataca a Cloud causando " + danoBase + " de daño.\n----------------------------");
        Cloud.sumarLimite((int) (danoBase / 2));
    }
    @Override
    public void giveXpRecompensa(Jugador Cloud){
        Cloud.recibirXP(this.xpRecompensa);
        System.out.println("Has obtenido: " + this.xpRecompensa + " de experiencia.");
    }
}