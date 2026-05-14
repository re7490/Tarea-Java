package Componentes;

public class Mejora{
    private String nombre;
    private int costoChatarra;
    private TipoStat statAfectado;
    private int valorBono;
    
    /**
     * Crea una mejora disponible para ser comprada y aplicada.
     * Define la estadistica afectada y el costo de obtencion.
     * 
     * @param nombre        Nombre de la mejora.
     * @param costoChatarra cantidad de chatarra para adquirirla.
     * @param statAfectado  Que stat de Cloud se ve afectado.
     * @param valorBono     cuanto afecta a la stat correspondiente.
     */
    public Mejora(String nombre, int costoChatarra, TipoStat statAfectado, int valorBono){
        this.nombre = nombre;
        this.costoChatarra = costoChatarra;
        this.statAfectado = statAfectado;
        this.valorBono = valorBono;
    }
    //Gets
    public String getNombre(){ return nombre; }
    public int getCostoChatarra(){ return costoChatarra; }
    public TipoStat getStatAfectado(){ return statAfectado; }
    public int getValorBono(){ return valorBono; }

}