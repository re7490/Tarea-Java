package Mapa;

import Entidades.*;
import Componentes.*;
import java.util.ArrayList;
import java.util.List;

public class NucleoPlaneta extends Zona{
    private int materiasMinimasRequeridas;

    public NucleoPlaneta(String nombre, int nivelRequerido, int materiasMinimasRequeridas){
        super(nombre, nivelRequerido);

        this.materiasMinimasRequeridas = materiasMinimasRequeridas;
    }
    //validacion de materias equipadas, ademas de el nivel requerido
    @Override
    public boolean validarAcceso(Jugador Cloud) {
        boolean nivelSuficiente = super.validarAcceso(Cloud);
        
        int materiasEquipadas = Cloud.getBusterSword().getMateriasEquipadas().size(); //materias equipas en el arma de Cloud 
        
        if (nivelSuficiente && materiasEquipadas >= this.materiasMinimasRequeridas) {
            return true;
        } else {
            System.out.println("Debes equipar al menos " + this.materiasMinimasRequeridas + " materias en tu arma.");
            return false;
        }
    }

    public void iniciarCombate(Jugador Cloud){
        //Sephiroth
        Estadisticas statsSephiroth = new Estadisticas(500, 500, 0, 0, 40, 0);//vida actual, vida maxima, MP actual, MP maximo, fuerza, magia
                ArrayList<Elemento> debilidadesPlanta = new ArrayList<>();
                ArrayList<Elemento> inmunidadesPlanta = new ArrayList<>();
                ArrayList<Elemento> resistenciasPlanta = new ArrayList<>();

                EnemigoSalvaje plantaCarnivora = new EnemigoSalvaje(
                    "Sephiroth",
                    0, 
                    0, 
                    statsSephiroth, 
                    debilidadesPlanta, 
                    resistenciasPlanta, 
                    inmunidadesPlanta
                );
    }
    @Override
    public void accionZona(Jugador Cloud){ return; } //combate
}