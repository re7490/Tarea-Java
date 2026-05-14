package Mapa;

import Entidades.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Zona{
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;

    /**
     * Constructor de la clase Zona.
     * Define un area del juego con un requisito de nivel para el acceso e inicializa la lista de enemigos que pueden aparecer.
     * 
     * @param nombre El nombre de la zona
     * @param nivel El nivel minimo que el jugador debe tener para ingresar.
     */
    public Zona(String nombre, int nivel){
        this.nombre = nombre;
        this.nivelRequerido = nivel;
        this.enemigosDisponibles = new ArrayList<>();
    }
    //Gets
    public String getNombre(){ return nombre; }
    public int getNivelRequerido(){ return nivelRequerido; }
    public List<Enemigo> getEnemigosDisponibles(){ return enemigosDisponibles; }

    /**
     * Define la interaccion ppal que ocurre dentro de la zona.
     * Cada zona implementa su propia logica de eventos cuando el jugador entra o interactua con ella.
     * 
     * @param Cloud El jugador experimentara el evento de la zona.
     */
    public abstract void accionZona(Jugador Cloud);

    /**
     * Verifica si el jugador cumple con los requisitos de nivel para ingresar a la zona.
     * Compara el nivel actual del jugador con el nivel requerido de la zona. 
     * Si no se cumple el requisito, muestra un mensaje por consola.
     * 
     * @param Cloud El jugador que intenta acceder a la zona.
     * @return true si el nivel del jugador es suficiente; 
     *         false si el acceso es denegado.
     */
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