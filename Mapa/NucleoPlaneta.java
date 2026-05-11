package Mapa;

import Entidades.*;
import Componentes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

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

        Sephiroth sephiroth = new Sephiroth(
            "Sephiroth",
            0, 
            0, 
            statsSephiroth
        );
        Scanner scanner = new Scanner(System.in);
        System.out.println("¡Sephiroth te mira fijamente... te ofrece la oportunidad de equiparte mejor y volver en otro momento a enfrentarlo.... Aceptas su propuesta? (s/n)");
        System.out.print("Respuesta: ");
        String respuesta = scanner.nextLine();
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            System.out.println("Respuesta no válida. Por favor, ingresa 's' para sí o 'n' para no.");
            System.out.print("Respuesta: ");
            respuesta = scanner.nextLine();
        }
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.println("Aceptas la propuesta de Sephiroth. Te retiras de la Zona para prepararte mejor.");
            return; // Salir del método para permitir que el jugador se prepare
        } 
        System.out.println("Rechazas la propuesta... Sephiroth sonrie levemente y avanza lentamente hacia ti... te desea suerte...");
        while (Cloud.getStats().getHpActual() > 0 && sephiroth.getStats().getHpActual() > 0) {

            System.out.println(sephiroth.getNombre() + " HP: " + sephiroth.getStats().getHpActual() + "/" + sephiroth.getStats().getHpMaximo() + " | Supernova: " + sephiroth.getContadorSuperNova() + "/10");
            System.out.println("Cloud HP: " + Cloud.getHpActual() + "/" + Cloud.getHpMaximo() + " | MP: " + Cloud.getMpActual() + "/" + Cloud.getMpMaximo() + " | Limite: " + Math.min(Cloud.getLimiteActual(), 100) + "/100");
            System.out.println("1. Ataque Físico | 2. Magia "+ (Cloud.getMpActual() >= 10 ? " (Disponible)" : " (No disponible)") + 
            "| 3. Curarse " + (Cloud.getMpActual() >= 15 && Cloud.getMochila().stream().anyMatch(m -> m.getNombre().equalsIgnoreCase("Curacion") || m.getElemento() == Elemento.CURA) ? " (Disponible)" : " (No disponible)") +
            "| 4. Ataque limite" + (Cloud.getLimiteActual() >= 100 ? " (Disponible)" : " (No disponible)") + "| 0. Huir");

            System.out.print("Eleccion: ");
            String eleccion = scanner.nextLine();
            boolean turnoFinalizado = false;

            while (!eleccion.equals("1") && !eleccion.equals("2") && !eleccion.equals("3") && !eleccion.equals("4") && !eleccion.equals("0")) {
                System.out.println("Entrada no válida. Por favor, elige una opción válida.");
                eleccion = scanner.nextLine();
            }
            if (eleccion.equals("1")){
                int dano = Cloud.getBusterSword().calcularDanoFisico();
                sephiroth.getStats().setHpActual(sephiroth.getStats().getHpActual() - dano);
                System.out.println("Cloud ataca con su Buster Sword haciendo: " + dano + " de daño.");
                turnoFinalizado = true;
            }else if (eleccion.equals("2")) {
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

                            if (danoMagico > 0) {
                                sephiroth.getStats().setHpActual(sephiroth.getStats().getHpActual() - danoMagico);
                                System.out.println("¡Lanzas un hechizo de " + elegido + "!");
                                System.out.println("Causas " + danoMagico + " de daño mágico.");
                                turnoFinalizado = true;
                            } else {
                                System.out.println("No tienes suficiente MP para este hechizo.");
                            }
                        } else {
                            System.out.println("Acción cancelada.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                    }
                }
            }else if (eleccion.equals("3")) {
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
                } else if (!tieneCura) {
                    System.out.println("No tienes la Materia de Curación en tu mochila.");
                } else {
                    System.out.println("MP insuficiente para curar.");
                }

            } else if (eleccion.equals("4")) {
                if (Cloud.getLimiteActual() >= 100) {
                    int danoLimite = Cloud.getBusterSword().calcularDanoLimite();
                    sephiroth.getStats().setHpActual(sephiroth.getStats().getHpActual() - danoLimite);
                    System.out.println("Usas Ataque Limite y el daño es devastador: " + danoLimite);
                    sephiroth.reiniciarContador();
                    turnoFinalizado = true;
                } else {
                    System.out.println("Barra de Límite insuficiente.");
                }
            } else if (eleccion.equals("0")){
                Random rand = new Random();
                System.out.println("Sephiroth te mira.... y no le agrada que intentes huir.... te ve como alguien cobarda y debil.... aunque igual te dara la oportunidad de escapar....aunque no sera tan facil como antes...");
                if (rand.nextInt(100) < 30) { 
                    System.out.println("Mientras huyes, Sephiroth lanza un ataque sorpresa que te golpea por la espalda, causandote 65 de daño.");
                    Cloud.getStats().recibirDMG(65);
                    System.out.println("Si bien lograste escapar... no puedes evitar sentir la presencia de Sephirtoh acechandote...tal vez no tendras suerte la proxima vez...");                                        
                    return; 
                } else { 
                    System.out.println("Aunque intentaste huir no lo lograste.... Sephiroth te mira con decepcion.... tal vez esperaba demasiado de ti");
                    System.out.println("El contador de Supernova de Sephiroth se incrementa por tu intento de huida... cuidado con eso...");
                    sephiroth.setContadorSuperNova();
                    turnoFinalizado = true; }
            }
            if (sephiroth.getStats().getHpActual() <= 0) {
                System.out.println("\n¡SEPHIROTH HA SIDO DERROTADO!");
                System.out.println("Cloud mira hacia el cielo... derroto a Septhiroth... ya nadie mas correra peligro.... Sonrie con tranquilidad y se desploma en el suelo del cansancio.");
                System.out.println("¡FELICIDADES! HAS COMPLETADO EL JUEGO.");
                System.exit(0); 
            } else {
                System.out.println("Has caido en el combate... no has sido lo suficientemente fuerte... pero el destino te da otra oportunidad para lograr tu objetivo");
                Cloud.setChatarra(0); 
                Cloud.getMochila().removeIf(m -> !m.isEquipado()); //adios a todo lo que no estaba equipado, excepto armas y materias
                return;
            }
            if (turnoFinalizado && sephiroth.getStats().getHpActual() > 0) {
                sephiroth.atacar(Cloud);
            }
            
        }
    }    
    @Override
    public void accionZona(Jugador Cloud){ 
        System.out.println("¡Has entrado al Núcleo del Planeta! Avanzas por la zona... y a lo lejos ves a Sephiroth.... él te estaba esperando...");    
        iniciarCombate(Cloud);
    }
}