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
    public void recibirDMG(int valor){
        this.hpActual -= valor;
        if (this.hpActual <= 0) this.hpActual = 0;
    }
}