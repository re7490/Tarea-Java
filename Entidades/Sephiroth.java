package Entidades;

import Componentes.*;
import Entidades.*;
import java.util.ArrayList;
import java.util.List;

public class Sephiroth extends Enemigo{
    private int contadorSuperNova;

    public Sephiroth(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        super(nombre, xpRecompensa, chatarraRecompensa, stats);
        this.contadorSuperNova = 0;
    }

    public void lanzarSuperNova(Jugador Cloud){
        Cloud.getStats().recibirDMG(9999); // Daño masivo
        System.out.println("Sephiroth ataca a Cloud con Supernova, anaiquilando a Cloud");
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
    }
}