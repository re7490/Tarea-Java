package Componentes;

public class Materia {
    private String nombre;
    private Elemento elemento;
    private boolean equipado;

    public Materia(String nombre, Elemento elemento) {
        this.nombre = nombre;
        this.elemento = elemento;
        this.equipado = false; 
    }

    public String getNombre() { return nombre; }
    public Elemento getElemento() { return elemento; }
    public boolean isEquipado() { return equipado; }
    //en espada
    public void setEquipado(boolean estado) { 
        this.equipado = estado; 
    }
}