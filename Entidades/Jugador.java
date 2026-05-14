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
    public void añadirChatarra(int chatarra){ this.chatarra += chatarra; }

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
    
    public void sumarLimite(int cantidad) {
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

        public Arma(){
            this.materiasEquipadas = new ArrayList<>();
        }

        //Get
        public List<Materia> getMateriasEquipadas() { return materiasEquipadas; }

        //Funciones 
        public int calcularDanoMagico(Elemento elemento){
            int n = 0;
            for (Materia m: materiasEquipadas){
                if (m.getElemento() == elemento){
                    n++;
                }
            }
            int costeMP = 10 + (5 * n);
            if (costeMP > stats.getMpActual()) return 0;

            int danoMagico = (int) (stats.getMagia() * (1 + (n/2)));
        
            stats.setMpActual(stats.getMpActual() - costeMP); //aplicamos coste  dataque
            return danoMagico;
        }
        public int calcularDanoFisico(){
            int dano = (int) (stats.getFuerza() * 1.25);
            sumarLimite((int) (dano/5));
            return dano;
        }
        
        public int calcularDanoLimite(){ //ataque limite (devastador)
            limiteActual = 0;
            return stats.getFuerza() * 5;
        }

        public void equiparMateria(Materia materia){
            if (materiasEquipadas.size() < 5) {
                materiasEquipadas.add(materia);
                System.out.println(materia.getNombre() + " equipada en " + nombre + ".");
                materia.setEquipado(true);
            } else {
                System.out.println("El arma esta llena!! No puedes equipar más de 5 materias.");
            }
        }

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