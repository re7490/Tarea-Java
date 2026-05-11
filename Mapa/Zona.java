package Mapa;

import Entidades.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Zona{
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;

    public Zona(String nombre, int nivel){
        this.nombre = nombre;
        this.nivelRequerido = nivel;
        this.enemigosDisponibles = new ArrayList<>();
    }
    //Gets
    public String getNombre(){ return nombre; }
    public int getNivelRequerido(){ return nivelRequerido; }
    public List<Enemigo> getEnemigosDisponibles(){ return enemigosDisponibles; }

    public abstract void accionZona(Jugador Cloud);

    public boolean validarAcceso(Jugador Cloud){
        if (Cloud.getNivel() >= nivelRequerido){
            return true;
        } else{
            System.out.println("Acceso denegado a " + this.nombre + ".");
            System.out.println("Necesitas ser al menos Nivel " + this.nivelRequerido + ".");
            return false;
        }
    };
}