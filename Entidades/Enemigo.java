package Entidades;

import Componentes.*;

public abstract class Enemigo{
    public String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;

    /**
     * Construye un nuevo enemigo con su nombre, recompensa y stats base.
     * 
     * @param nombre             nombre enemigo.
     * @param xpRecompensa       Xp entregado al ser derrotado
     * @param chatarraRecompensa "moneda" que se entrega la jugador por derrotarlo.
     * @param stats              Instancia de la clase Estadisticas con las ststs.
     */
    public Enemigo(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        this.nombre = nombre;
        this.xpRecompensa = xpRecompensa;
        this.chatarraRecompensa = chatarraRecompensa;
        this.stats = stats;   
    }
    //Gets y Sets
    public String getNombre(){ return nombre; }
    public int getXpRecompensa(){ return xpRecompensa; }
    public int getChatarraRecompensa(){ return chatarraRecompensa; }
    public Estadisticas getStats(){ return stats; }

    /**
     * Ejecuta logica de ataque del enemigo contra el Cloudd.
     * los subclases implementan su propio ataque y claculo de daño.
     * 
     * @param Cloud El jugador que recibira el impacto del ataque.
     */
    public abstract void atacar(Jugador Cloud);

    /**
     * entrega la Xp al jugador
     * se llama cuando muere el enemigo (HpActual == 0)
     * 
     * @param Cloud el jugador recibira la Xp
     */
    public abstract void giveXpRecompensa(Jugador Cloud);
}