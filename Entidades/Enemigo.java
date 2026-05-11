package Entidades;

import Componentes.*;

public abstract class Enemigo{
    public String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;

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

    public abstract void atacar(Jugador Cloud);

    public abstract void giveXpRecompensa(Jugador Cloud);
}