package Entidades;

import Componentes.*;
import Entidades.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sephiroth extends Enemigo{
    private int contadorSuperNova;

    /**
         * Constructor para Sephiroth. Utiliza la herencia para definir sus recompensas y ststas base, e inicializa un contador especifico para Supernova.
         * 
         * @param nombre nombre enemigo
         * @param xpRecompensa cant de Xp entregada.
         * @param chatarraRecompensa Cant de chatarra entregada.
         * @param stats Objeto de estadisticas.
         */
    public Sephiroth(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        super(nombre, xpRecompensa, chatarraRecompensa, stats);
        this.contadorSuperNova = 0;
    }

    public int getContadorSuperNova() { return this.contadorSuperNova; }
    public void setContadorSuperNova(int aumento) { this.contadorSuperNova += aumento; }

    /**
     * Ejecuta Supernova 
     * Inflige una cantidad de daño fija (9999) que ignora las defensas del jugador,
     * resultando en la derrota inmediata del objetivo.
     * 
     * @param Cloud El jugador sufrira el daño.
     */
    public void lanzarSuperNova(Jugador Cloud){
        Cloud.getStats().recibirDMG(9999); // Daño masivo
        System.out.println("!!! SEPHIROTH LANZA SUPERNOVA !!!");
        System.out.println("La energia colapsa sobre Cloud, aniquilandolo instantaneamente.");
    }

    /**
     * Reinicia el contador de Supernova.
     */
    public void reiniciarContador() {
        this.contadorSuperNova = 0;
        System.out.println("¡El ataque de Cloud ha interrumpido la concentracion de Sephiroth! Reiniciando Supernova.");
    } 

    /**
     * Ejecuta el ataque de Sephiroth.
     * Incrementa el contador de Supernova en cada turno. Si el contador llega a 10, 
     * lanza Supernova. De lo contrario, tiene un 90% de probabilidad 
     * de realizar un ataque y un 10% de fallar, permitiendo 
     * que el jugador esquive el golpe.
     * 
     * @param Cloud El jugador objetivo del ataque.
     */
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

    /**
     * Intenta otorgar una recompensa de experiencia al jugador tras el combate. Aunq no se ejecute luego, se define, ya q esta clase es hoja.
     * 
     * @param Cloud El jugador que finaliza el encuentro con el jefe.
     */
    @Override
    public void giveXpRecompensa(Jugador Cloud){
        Cloud.recibirXP(this.xpRecompensa); //no recibe nada
    }
}