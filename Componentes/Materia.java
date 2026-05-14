package Componentes;

public class Materia {
    private String nombre;
    private Elemento elemento;
    private boolean equipado;

    /**
     * Crea una nueva Materia con su nombre y elemento asociado.
     * Por defecto, la materia se crea en estado no equipado.
     * 
     * @param nombre   El nombre de la materia.
     * @param elemento El tipo elemental al que pertenece (enum Elemento).
     */
    public Materia(String nombre, Elemento elemento) {
        this.nombre = nombre;
        this.elemento = elemento;
        this.equipado = false; 
    }

    public String getNombre() { return nombre; }
    public Elemento getElemento() { return elemento; }
    public boolean isEquipado() { return equipado; }
    //en espada
    public void setEquipado(boolean estado) { this.equipado = estado; }
}