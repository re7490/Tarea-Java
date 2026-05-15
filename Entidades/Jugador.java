package Entidades;

import Componentes.*;
import java.util.ArrayList;
import java.util.List;


public class Jugador{
    public String nombre = "Cloud";
    private int nivel;
    private int expActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Materia> mochila;
    private Arma espada;

    /**
     * Constructor inicializa al personaje en el nivel 1, sin experiencia, chatarra ni límite.
     * Define las stats base (Vida, MP, Fuerza, Magia), crea una mochila vacía y equipa un arma inicial.
     */
    public Jugador(){
        this.nivel = 1;
        this.expActual = 0;
        this.chatarra = 0;
        this.limiteActual = 0;
        this.stats = new Estadisticas(200, 200, 50, 50, 15, 15); //vida, vida, MP, MP, fuerza, magia
        this.mochila =  new ArrayList<>();
        this.espada = new Arma();
    }
    //Gets y Sets
    public int getNivel(){ return nivel; }
    public void setNivel(int x) { nivel = x; }

    public int getChatarra(){ return chatarra; }
    public void setChatarra(int y) { chatarra = y; }

    public Estadisticas getStats(){ return stats; }
    public int getHpActual() { return this.stats.getHpActual(); }
    public int getHpMaximo() { return this.stats.getHpMaximo(); }
    public int getMpActual() { return this.stats.getMpActual(); }
    public int getMpMaximo() { return this.stats.getMpMaximo(); }
    public int getFuerza() { return this.stats.getFuerza(); }
    public int getMagia() { return this.stats.getMagia(); }
    public List<Materia> getMochila() { return this.mochila; }
    public Arma getBusterSword() { return this.espada; }
    public int getLimiteActual() { return this.limiteActual; }
    public void setHpActual(int hp) { this.stats.setHpActual(hp); }
    public void setMpActual(int nuevoMp) { this.stats.setMpActual(nuevoMp); }

    //Funciones
    /**
     * Incrementa la cantidad de chatarra acumulada por el jugador..
     * 
     * @param chatarra Cantidad de unidades de chatarra que se sumaran al inventario actual.
     */
    public void añadirChatarra(int chatarra){ this.chatarra += chatarra; } //de por si es un set... pero ya no le cambie el nombre Dx

    /**
     * Procesa la Xp recibida por el jugador y gestiona la subida de nivel.
     * Si la Xp actual supera el umbral requerido (nivel * 10), el jugador sube de nivel y aumenta sus ststas base y regenera una parte de su HP y MP.
     * El excedente de experiencia se mantiene para el siguiente nivel.
     * 
     * @param xp Cantidad de puntos de experiencia a sumar al progreso actual.
     */
    public void recibirXP(int xp){
        this.expActual += xp;
        int xpNecesaria = this.nivel * 10;
        
        while (this.expActual >= xpNecesaria){
            this.expActual -= xpNecesaria;
            this.nivel++;
            //aumento stats
            stats.setHpMaximo(stats.getHpMaximo() + 10);
            stats.setMpMaximo(stats.getMpMaximo() + 5);
            stats.setFuerza(stats.getFuerza() + 4);
            stats.setMagia(stats.getMagia() + 6);
            
            //regeneramos un poco al subir de nivel
            stats.setHpActual(Math.min(stats.getHpActual() + 10, this.getHpMaximo()));
            stats.setMpActual(Math.min(stats.getMpActual() + 5, this.getMpMaximo()));

            System.out.println("¡Subiste al nivel " + this.nivel + "!");
            xpNecesaria = 10 * this.nivel;
        }
    }

    /**
     * Despliega en consola el contenido actual de la mochila del jugador.
     * Si el inventario esta vacío, informa al usuario; de lo contrario, lista cada Materia con su indice, nombre, elemento asociado y su estado de equipamiento actual.
     */
    public void mostrarMochila(){
        if (mochila.isEmpty()) {
            System.out.println("Tu mochila está vacía.");
        } else {
            System.out.println("Mochila:");
            for (int i = 0; i < mochila.size(); i++) {
                Materia materia = mochila.get(i);
                String estado = materia.isEquipado() ? " [EQUIPADA]" : "[NO EQUIPADA]";
                System.out.println((i + 1) + ". " + materia.getNombre() + " (Elemento: " + materia.getElemento() + ")" + estado);
            }
        }
    }
    
    /**
     * Incrementa la barra de limite del jugador tras recibir daño o realizar acciones.
     * El valor no puede pasar de 100. Una vez alcanzado este umbral, el jugador queda habilitado para ejecutar el ataque limite.
     * 
     * @param cantidad Puntos de limite que se añadiran a la barra actual.
     */
    public void sumarLimite(int cantidad) { //creo q tambien contaria como set... pero bueno...
        if (this.limiteActual <= 100) {
            this.limiteActual += cantidad;
            if (this.limiteActual > 100) {
                this.limiteActual = 100;
            }
        }
        else {
            this.limiteActual = 100;
        }
    }

    public class Arma {
        public String nombre = "Buster Sword";
        private List<Materia> materiasEquipadas;

        /**
         * Constructor de la clase Arma.
         * Inicializa la lista de materias equipadas como un ArrayList vacio.
         */
        public Arma(){
            this.materiasEquipadas = new ArrayList<>();
        }

        //Get
        public List<Materia> getMateriasEquipadas() { return materiasEquipadas; }

        //Funciones 
        /**
         * Calcula y ejecuta el daño del ataque magico basado en un elemento especifico.
         * La potencia y el coste de MP escalan segun la cantidad de materias del mismo elemento equipadas en el arma. Si el jugador no tiene suficiente MP, el ataque falla.
         * 
         * @param elemento El tipo elemental del hechizo
         * @return El daño magico total calculado; devuelve 0 si no hay suficiente MP.
         */
        public int calcularDanoMagico(Elemento elemento){
            int n = 0;
            for (Materia m: materiasEquipadas){
                if (m.getElemento() == elemento){
                    n++;
                }
            }
            int costeMP = 10 + (5 * n);
            if (costeMP > stats.getMpActual()) return 0;

            int danoMagico = stats.getMagia() * (1 + (n/2));
        
            stats.setMpActual(stats.getMpActual() - costeMP); //aplicamos coste  dataque
            return danoMagico;
        }

        /**
         * Calcula el daño de un ataque fisico basado en la fuerza del jugador.
         * Al realizar este ataque, el jugador genera puntos de limite equivalentes al 20% del daño infligido.
         * 
         * @return El daño fisico total calculado tras aplicar el multiplicador de fuerza.
         */
        public int calcularDanoFisico(){
            int dano = (int) (stats.getFuerza() * 1.25);
            sumarLimite(dano/5);
            return dano;
        }
        
        /**
         * Ejecuta el ataque limite devastador,reinicia la barra de limite a 0 y calcula un daño masivo basado en fuerza*5.
         * 
         * @return El daño total del ataque limite (Fuerza * 5).
         */
        public int calcularDanoLimite(){ //ataque limite (devastador)
            limiteActual = 0;
            return stats.getFuerza() * 5;
        }

        /**
         * Intenta equipar una materia en la espada, esta tiene un límite de 5 ranuras. Si hay espacio disponible, añade la materia a la lista, marca la materia como equipada y confirma por consola.
         * 
         * @param materia El objeto Materia que se desea insertar en el arma.
         */
        public void equiparMateria(Materia materia){
            if (materiasEquipadas.size() < 5) {
                materiasEquipadas.add(materia);
                System.out.println(materia.getNombre() + " equipada en " + nombre + ".");
                materia.setEquipado(true);
            } else {
                System.out.println("El arma esta llena!! No puedes equipar más de 5 materias.");
            }
        }

        /**
         * Retira una materia especifica del arma.
         * Si la materia se encuentra en el arma, la elimina de la lista, actualiza su estado a no equipada y confirma por consola
         * 
         * @param m El objeto Materia que se desea retirar del equipo.
         */
        public void desequiparMateria(Materia m) {
            if (materiasEquipadas.remove(m)) {
                m.setEquipado(false);
                System.out.println("Has desequipado " + m.getNombre() + " de la " + this.nombre);
            } else {
                System.out.println("Esa materia no esta equipada en el arma.");
            }
        }

    }

}