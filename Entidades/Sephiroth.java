package Entidades;

import Componentes.*;
import Entidades.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sephiroth extends Enemigo{
    private int contadorSuperNova;

    public Sephiroth(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        super(nombre, xpRecompensa, chatarraRecompensa, stats);
        this.contadorSuperNova = 0;
    }

    public int getContadorSuperNova() { return this.contadorSuperNova; }
    public void setContadorSuperNova(int aumento) { this.contadorSuperNova += aumento; }

    public void lanzarSuperNova(Jugador Cloud){
        Cloud.getStats().recibirDMG(9999); // Daño masivo
        System.out.println("!!! SEPHIROTH LANZA SUPERNOVA !!!");
        System.out.println("La energia colapsa sobre Cloud, aniquilandolo instantaneamente.");
    }
    
    public void reiniciarContador() {
        this.contadorSuperNova = 0;
        System.out.println("¡El ataque de Cloud ha interrumpido la concentracion de Sephiroth! Reiniciando Supernova.");
    } 

    @Override
    public void atacar(Jugador Cloud){
        Random rand = new Random();
        int probAtaque = rand.nextInt(100);
        this.contadorSuperNova++;
        if (this.contadorSuperNova >= 10) {
            lanzarSuperNova(Cloud);
            this.contadorSuperNova = 0;
            return;
        }else if(probAtaque < 90) {
            int danoBase = (int) (this.stats.getFuerza() * 1.25);
            Cloud.getStats().recibirDMG(danoBase);
            System.out.println(this.nombre + " ataca a Cloud causando " + danoBase + " de daño.");
            Cloud.sumarLimite(danoBase / 2);
        } else {
            System.out.println(this.nombre + " intenta atacar a Cloud... pero milagrosamente Cloud logra esquivar el ataque!... Sephiroth queda impresionado ante tal hazaña.... 'Veamos si tienes suerte la proxima vez...'"); //no recibe nada
        }
    }
    @Override
    public void giveXpRecompensa(Jugador Cloud){
        Cloud.recibirXP(this.xpRecompensa); //no recibe nada
    }
}