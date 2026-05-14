package Componentes;

import java.util.ArrayList;
import java.util.List;

public class Estadisticas {
    private int hpActual;
    private int hpMaximo;
    private int mpActual;
    private int mpMaximo;
    private int fuerza;
    private int magia;
    
    /**
     * Crea una nueva instancia de Estadisticas con todos los atributos base definidos.
     * 
     * @param hpMaximo Vida maxima
     * @param hpActual Vida actual
     * @param mpMaximo Mp maximo
     * @param mpActual Mp actual
     * @param fuerza   Fuerza para ataques
     * @param magia    Magia para ataques magicos
     */
    public Estadisticas(int hpMaximo, int hpActual, int mpMaximo, int mpActual, int fuerza, int magia){ //vida max, vida actual, MP max, MP actual, fuerza, magia
        this.hpMaximo = hpMaximo;
        this.mpMaximo = mpMaximo;
        this.hpActual = hpActual;
        this.mpActual = mpActual;
        this.fuerza = fuerza;
        this.magia = magia;

    }
    //Gets
    public int getHpActual(){ return this.hpActual; }
    public int getHpMaximo(){ return this.hpMaximo; }
    public int getMpActual(){ return this.mpActual; }
    public int getMpMaximo(){ return this.mpMaximo; } 
    public int getMagia(){ return this.magia; }
    public int getFuerza(){ return this.fuerza; }

    //Sets
    public void setHpMaximo(int Hp){ this.hpMaximo = Hp; }
    public void setMpMaximo(int Mp){ this.mpMaximo = Mp; }
    public void setFuerza(int fuerza){ this.fuerza = fuerza; }
    public void setMagia(int magia){ this.magia = magia; }
    public void setMpActual(int mp) { this.mpActual = mp;}
    public void setHpActual(int hp){ this.hpActual = hp; }

    
    
    //Funcion
    /**
     * Reduce la salud actual de Cloud segun el daño recibido.
     * Asegura que los puntos de vida no sean menores a cero.
     * 
     * @param valor Cantidad de puntos de daño a restar de la salud.
     */
    public void recibirDMG(int valor){
        this.hpActual -= valor;
        if (this.hpActual <= 0) this.hpActual = 0;
    }
}