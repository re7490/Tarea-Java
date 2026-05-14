package Mapa;

import Componentes.*;
import Entidades.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gongaga extends Zona{
    private List<Materia> poolMaterias;

    /**
     * Constructor para la zona de Gongaga.
     * 
     * @param nombre Nombre de la zona.
     * @param nivelRequerido Nivel minimo para acceder
     * @param poolMaterias Una lista de materias que pueden ser encontradas al explorar.
     */
    public Gongaga(String nombre, int nivelRequerido, List<Materia> poolMaterias){
        super(nombre, nivelRequerido);
        this.poolMaterias = poolMaterias;
    }

    /**
     * Genera un grupo de enemigos aleatorios para un encuentro en la zona.
     * Calcula la cantidad de oponentes (1 a 3) y el tipo de criatura mediante probabilidades, asignando stats, debilidades y recompensas aleatorias.
     * 
     * @return List de objetos Enemigo listos para iniciar un combate.
     */
    public List<Enemigo> generarGrupoEnemigo(){
        List<Enemigo> grupoEmboscada = new ArrayList<>();
        Random rand = new Random();

        //cantidad de enemigos en la emboscada
        int probCantidad = rand.nextInt(100);
        int cantidad = 1; // 60% de probabilidad

        if (probCantidad >= 60 && probCantidad < 90){ // 30%
            cantidad = 2;
        } else if (probCantidad >= 90){ // 10%
            cantidad = 3;
        }

        for (int i = 0; i < cantidad; i++){
            int tipoMonstruo = rand.nextInt(3);

            //XP y chatarra random
            int xpAleatoria = rand.nextInt(100 - 80 + 1) + 80;
            int chatarraAleatoria = rand.nextInt(((75 - 50) + 1) + 50);
            if (tipoMonstruo == 0){ //Planta Carnivora
                Estadisticas statsPlanta = new Estadisticas(80, 80, 0, 0, 15, 0);//vida actual, vida maxima, MP actual, MP maximo, fuerza, magia
                ArrayList<Elemento> debilidadesPlanta = new ArrayList<>();
                debilidadesPlanta.add(Elemento.FUEGO);
                debilidadesPlanta.add(Elemento.HIELO);

                ArrayList<Elemento> inmunidadesPlanta = new ArrayList<>();
                inmunidadesPlanta.add(Elemento.RAYO);

                ArrayList<Elemento> resistenciasPlanta = new ArrayList<>();

                EnemigoSalvaje plantaCarnivora = new EnemigoSalvaje(
                    "Planta Carnivora",
                    xpAleatoria, 
                    chatarraAleatoria, 
                    statsPlanta, 
                    debilidadesPlanta, 
                    resistenciasPlanta, 
                    inmunidadesPlanta
                );
                grupoEmboscada.add(plantaCarnivora);
            } else if (tipoMonstruo == 1){ //Sapo de la jungla
                Estadisticas statsSapo = new Estadisticas(60, 60, 0, 0, 12, 0);//vida actual, vida maxima, MP actual, MP maximo, fuerza, magia
                ArrayList<Elemento> debilidadesSapo = new ArrayList<>();
                debilidadesSapo.add(Elemento.RAYO);
                debilidadesSapo.add(Elemento.HIELO);

                ArrayList<Elemento> inmunidadesSapo = new ArrayList<>();

                ArrayList<Elemento> resistenciasSapo = new ArrayList<>();
                resistenciasSapo.add(Elemento.FUEGO);

                EnemigoSalvaje sapoJungla = new EnemigoSalvaje(
                    "Sapo de la Jungla",
                    xpAleatoria, 
                    chatarraAleatoria, 
                    statsSapo, 
                    debilidadesSapo, 
                    resistenciasSapo, 
                    inmunidadesSapo
                );
                grupoEmboscada.add(sapoJungla);
            }else if (tipoMonstruo == 2){ //Robot Centinela
                Estadisticas statsRobot = new Estadisticas(100, 100, 0, 0, 20, 0);//vida actual, vida maxima, MP actual, MP maximo, fuerza, magia
                ArrayList<Elemento> debilidadesRobot = new ArrayList<>();
                debilidadesRobot.add(Elemento.RAYO);

                ArrayList<Elemento> inmunidadesRobot = new ArrayList<>();

                ArrayList<Elemento> resistenciasRobot = new ArrayList<>();
                resistenciasRobot.add(Elemento.FISICO); //mas q nada visual... pq el enemigo tiene resistencia a ataques fisicos (1)
                resistenciasRobot.add(Elemento.HIELO);

                EnemigoSalvaje robotCentinela = new EnemigoSalvaje(
                    "Robot Centinela",
                    xpAleatoria, 
                    chatarraAleatoria, 
                    statsRobot, 
                    debilidadesRobot, 
                    resistenciasRobot, 
                    inmunidadesRobot
                );
                grupoEmboscada.add(robotCentinela);
            } 
        }
        return grupoEmboscada;
    }

    /**
     * Ejecuta la dinamica de exploracion en Gongaga.
     * Utiliza un sistema de probabilidades para determinar si el jugador encuentra una Materia aleatoria (30%) o cae en una emboscada de enemigos salvajes (70%).
     * 
     * @param Cloud El jugador que explora la zona.
     */
    @Override
    public void accionZona(Jugador Cloud){
        boolean explorar = true;
        while (explorar) {
            Random rand = new Random();
            int evento = rand.nextInt(100);
            if (evento < 30) {
                //botin/exploracion
                Elemento[] opciones = {Elemento.FUEGO, Elemento.HIELO, Elemento.RAYO, Elemento.CURA};
                Elemento aleatorio = opciones[rand.nextInt(opciones.length)];
                Materia nuevaMateria = new Materia("Materia " + aleatorio, aleatorio);
                Cloud.getMochila().add(nuevaMateria);
                System.out.println("Explorando por la zona has encontrado una materia de " + aleatorio + " que suerte que tienes. La guardas en tu mochila por si te sirve para algo...");
                break; // Salimos del ciclo de exploración después de encontrar botín
            } else {
                //emboscada
                List<Enemigo> enemigos = generarGrupoEnemigo();
                System.out.println("Exploras por la zona y de repente... ¡Una Emboscada! Te enfrentas a " + enemigos.size() + " enemigos.");
                iniciarCombate(Cloud, enemigos);
                break; // Salimos del ciclo de exploración después del combate
            }
        }
    }

    /**
     * Gestiona un encuentro de combate completo contra un grupo de enemigos salvajes.
     * Implementa la logica de seleccion de objetivos, calculo de daño físico y mágico 
     * (con multiplicadores elementales), uso de ataque limite, curación y gestion de turnos 
     * de enemigos y ataques coordinados
     * 
     * @param Cloud El jugador que participa en el combate.
     * @param enemigos Lista de enemigos que forman parte del encuentro.
     */
    public void iniciarCombate(Jugador Cloud, List<Enemigo> enemigos) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("\n--- COMBATE INICIADO ---");

        while (Cloud.getHpActual() > 0 && !enemigos.isEmpty()) {
            System.out.println("\nEnemigos frente a ti: ");
            for (int i = 0; i < enemigos.size(); i++) {
                Enemigo e = enemigos.get(i);
                System.out.println((i + 1) + ". " + e.getNombre() + " (HP: " + e.getStats().getHpActual() + "/" + e.getStats().getHpMaximo() + ")");
            }
            System.out.println("Cloud HP: " + Cloud.getHpActual() + "/" + Cloud.getHpMaximo() + " | MP: " + Cloud.getMpActual() + "/" + Cloud.getMpMaximo() + " | Limite: " + Cloud.getLimiteActual() + "/100");
            System.out.println("1. Ataque Físico | 2. Magia "+ (Cloud.getMpActual() >= 10 && !Cloud.getBusterSword().getMateriasEquipadas().isEmpty() ? " (Disponible)" : " (No disponible)") + 
            "| 3. Curarse " + (Cloud.getMpActual() >= 15 && Cloud.getMochila().stream().anyMatch(m -> m.getNombre().equalsIgnoreCase("Curacion") || m.getElemento() == Elemento.CURA) ? " (Disponible)" : " (No disponible)") +
            "| 4. Ataque limite" + (Cloud.getLimiteActual() >= 100 ? " (Disponible)" : " (No disponible)") + "| 0. Huir");
            System.out.print("Elección: ");
            String accion = scanner.nextLine();
            boolean turnoFinalizado = false;

            while (!accion.equals("1") && !accion.equals("2") && !accion.equals("3") && !accion.equals("4") && !accion.equals("0")) {
                System.out.println("Entrada no válida. Por favor, elige una opción válida.");
                accion = scanner.nextLine();
            }
            if (accion.equals("1") || accion.equals("2") || accion.equals("4")) {
                System.out.print("¿A qué enemigo quieres atacar? (Número): ");
                try {
                    int indice = Integer.parseInt(scanner.nextLine()) - 1;
                    if (indice >= 0 && indice < enemigos.size()) {
                        Enemigo objetivo = enemigos.get(indice);
                        
                        if (accion.equals("1")) {
                            int dano = Cloud.getBusterSword().calcularDanoFisico();
                            if (objetivo.getNombre().equals("Robot Centinela")) {
                                dano = (int) (dano/2); // Resistencia a fisico
                                System.out.println("¡" + objetivo.getNombre() + " resiste el daño físico! Causas solo " + dano + " de daño.");
                            } else {
                                 System.out.println("Atacas a " + objetivo.getNombre() + " causando " + dano + " de daño.");
                            }
                            objetivo.getStats().setHpActual(objetivo.getStats().getHpActual() - dano);
                            turnoFinalizado = true;
                        } else if (accion.equals("2")) {
                            List<Materia> equipadas = Cloud.getBusterSword().getMateriasEquipadas();

                            if (equipadas.isEmpty()) {
                                System.out.println("No tienes materias equipadas en tu arma. ¡No puedes usar magia!");
                            } else {
                                System.out.println("\n--- SELECCIONAR ELEMENTO ---");
                                //lista elementos unicos disponibles para no repetir
                                List<Elemento> elementosDisponibles = new ArrayList<>();
                                
                                for (Materia m : equipadas) {
                                    if (!elementosDisponibles.contains(m.getElemento())) {
                                        elementosDisponibles.add(m.getElemento());
                                    }
                                }

                                for (int i = 0; i < elementosDisponibles.size(); i++) {
                                    System.out.println((i + 1) + ". " + elementosDisponibles.get(i));
                                }
                                System.out.println("0. Cancelar");
                                System.out.print("Elección de elemento: ");
                                
                                try {
                                    int selElemento = Integer.parseInt(scanner.nextLine());
                                    
                                    if (selElemento > 0 && selElemento <= elementosDisponibles.size()) {
                                        Elemento elegido = elementosDisponibles.get(selElemento - 1);
                                        
                                        int danoMagico = Cloud.getBusterSword().calcularDanoMagico(elegido);
                                        EnemigoSalvaje enemigoObjetivo = (EnemigoSalvaje) objetivo; //casteo a EnemigoSalvaje netamente para acceder a evaluarDebilidad 
                                        double multiplicador = enemigoObjetivo.evaluarDebilidad(elegido);

                                        if (elegido == Elemento.CURA) {
                                            int cura = danoMagico;
                                            Cloud.getStats().setHpActual(Math.min(Cloud.getHpActual() + cura, Cloud.getHpMaximo()));
                                            System.out.println("Usas Materia de Curación. Recuperas " + cura + " HP.");
                                            turnoFinalizado = true;
                                        } else if (danoMagico > 0) {
                                            int danoFinal = (int) (danoMagico * multiplicador); //daño final con multiplicador de debilidad/resistencia/inmunidad
                                            objetivo.getStats().setHpActual(objetivo.getStats().getHpActual() - danoFinal);
                                            System.out.println("¡Lanzas un hechizo de " + elegido + "!");
                                            if (multiplicador == 0.0) {
                                                System.out.println("¡" + objetivo.getNombre() + " es inmune a este elemento! No le haces daño.");
                                            } else if (multiplicador == 0.5) {
                                                System.out.println("¡" + objetivo.getNombre() + " resiste este elemento! Le haces menos daño de lo normal...Causas " + danoFinal + " de daño mágico.");
                                            } else if (multiplicador == 2.0) {
                                                System.out.println("¡" + objetivo.getNombre() + " es debil a este elemento! Le haces más daño de lo normal...Causas " + danoFinal  + " de daño mágico.");
                                            }
                                            Cloud.sumarLimite(danoFinal / 2);
                                            turnoFinalizado = true;
                                        } else {
                                            System.out.println("No tienes suficiente MP para este hechizo.");
                                        }
                                    } else {
                                        System.out.println("Acción cancelada.");
                                    }
                                } catch (NumberFormatException e) { System.out.println("Entrada inválida."); }
                            } 
                        }  else if (accion.equals("4")) {
                            if (Cloud.getLimiteActual() >= 100) {
                                int danoLimite = Cloud.getBusterSword().calcularDanoLimite();
                                objetivo.getStats().setHpActual(objetivo.getStats().getHpActual() - danoLimite);
                                System.out.println("Usas Ataque Limite y el daño es devastador: " + danoLimite);
                                turnoFinalizado = true;
                            } else {
                                System.out.println("Barra de Límite insuficiente.");
                            }
                        }
                    }
                } catch (Exception e) { System.out.println("Objetivo no válido."); continue; }
            } else if (accion.equals("0")) {
                if (rand.nextInt(100) < 50) { System.out.println("¡Escapaste!"); return; 
                } else { System.out.println("¡No pudiste escapar!"); turnoFinalizado = true;} //evitamos q siempre se escape antes de morir
            } else if (accion.equals("3")) {
                boolean tieneCura = false;
                for (Materia m : Cloud.getMochila()) {
                    if (m.getNombre().equalsIgnoreCase("Curacion") || m.getElemento() == Elemento.CURA) {
                        tieneCura = true;
                        break;
                    }
                }

                if (tieneCura && Cloud.getMpActual() >= 15) {
                    int cura = (int) (Cloud.getMagia() * 1.5);
                    Cloud.getStats().setHpActual(Math.min(Cloud.getHpActual() + cura, Cloud.getHpMaximo()));
                    Cloud.getStats().setMpActual(Cloud.getMpActual() - 15);
                    System.out.println("Usas Materia de Curación. Recuperas " + cura + " HP.");
                    turnoFinalizado = true;
                    Cloud.getMochila().removeIf(m -> m.getNombre().equalsIgnoreCase("Curacion") || m.getElemento() == Elemento.CURA);
                } else if (!tieneCura) {
                    System.out.println("No tienes la Materia de Curación en tu mochila.");
                } else {
                    System.out.println("MP insuficiente para curar.");
                }

            }

            //limpiamos muertos
            for (int i = enemigos.size() - 1; i >= 0; i--) {
                if (enemigos.get(i).getStats().getHpActual() <= 0) {
                    Enemigo muerto = enemigos.remove(i);
                    System.out.println("¡" + muerto.getNombre() + " ha sido derrotado!");
                    Cloud.recibirXP(muerto.getXpRecompensa());
                    Cloud.setChatarra(Cloud.getChatarra() + muerto.getChatarraRecompensa());
                }
            }

            //turno enemigos vivos
            if (!enemigos.isEmpty() && turnoFinalizado) {
                System.out.println("\n--- Turno de los enemigos ---");
                Random r = new Random();
                int enemigosAtacan = r.nextInt(100);
                boolean ataqueConjunto = false;
                if (enemigosAtacan < 50 && enemigos.size() == 2) {
                    ataqueConjunto = true;
                    System.out.println("¡Los enemigos se coordinan para un ataque!");
                } else if (enemigosAtacan < 33 && enemigos.size() == 3) {
                    ataqueConjunto = true;
                    System.out.println("¡Los enemigos se coordinan para un ataque grupal!");
                }
                if (ataqueConjunto){
                    for (Enemigo e : enemigos) {
                        e.atacar(Cloud); // Cada enemigo ataca a Cloud
                        Cloud.sumarLimite((int) (e.getStats().getFuerza() / 2));
                    }
                } else {
                    int atacante = r.nextInt(enemigos.size());
                    Enemigo atacanteEnemigo = enemigos.get(atacante);
                    System.out.println(atacanteEnemigo.getNombre() + " ataca a Cloud!");
                    atacanteEnemigo.atacar(Cloud);
                    Cloud.sumarLimite((int) (atacanteEnemigo.getStats().getFuerza() / 2));
                }   
            }
        }

        if (Cloud.getHpActual() <= 0) {
            System.out.println("Has caído ante la emboscada... Pierdes todo lo de tu mochila... excepto la Buster Sword y las materias equipadas");
            return;
        }
    }
}