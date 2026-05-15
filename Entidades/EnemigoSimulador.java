package Entidades;

import Componentes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemigoSimulador extends Enemigo{
    /**
     * Crea un enemigo simulador
     * Este constructor inicializa un enemigo estandar utilizando la logica base de la clase superior.
     * 
     * @param nombre             Nombre enemigo
     * @param xpRecompensa       Xp que el jugador obtiene al vencerlo.
     * @param chatarraRecompensa Cantidad de chatarra (moneda) otorgada como botin.
     * @param stats              Instancia de Estadisticas con los atributos de combate.
     */
    public EnemigoSimulador(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        super(nombre, xpRecompensa, chatarraRecompensa, stats);
    }

    //funciones
    /**
     * Verifica si el jugador puede sobrevivir al proximo ataque del enemigo.
     * Calcula el daño proyectado y lo compara con la salud actual del jugador.
     * 
     * @param Cloud El jugador sera evaluado como objetivo.
     * @return true si el jugador tiene suficiente HP para sobrevivir al impacto; 
     *         false si el ataque mataria a Cloud.
     */
    public boolean checkDanoSeguro(Jugador Cloud){
        int danoBase = (int) (this.stats.getFuerza() * 1.25);
        if (Cloud.getHpActual() < danoBase){
            return false;
        }
        return true;
    }

    /**
     * Ataque sobre el jugador, daño es fuerza*1.25
     * el impacto carga barra de limite de Cloud en una proporción del 50% del daño recibido.
     * 
     * @param Cloud El jugador recibira el impacto y cuya barra de limite aumentara.
     */
    @Override
    public void atacar(Jugador Cloud){

        int danoBase = (int) (this.stats.getFuerza() * 1.25);
        Cloud.getStats().recibirDMG(danoBase);
        System.out.println(this.nombre + " ataca a Cloud causando " + danoBase + " de daño.\n----------------------------");
        Cloud.sumarLimite(danoBase / 2);
    }

    /**
     * Otorga la Xp de este enemigo al jugador tras ser derrotado.
     * Actualiza el estado del jugador mediante el método recibirXP y muestra un mensaje de confirmación por consola.
     * 
     * @param Cloud El jugador recibira la recompensa de experiencia.
     */
    @Override
    public void giveXpRecompensa(Jugador Cloud){
        Cloud.recibirXP(this.xpRecompensa);
        System.out.println("Has obtenido: " + this.xpRecompensa + " de experiencia.");
    }
}