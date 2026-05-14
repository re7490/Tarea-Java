package Mapa;

import Entidades.*;
import Componentes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class NucleoPlaneta extends Zona{
    private int materiasMinimasRequeridas;

    /**
     * Constructor para Nucleo del Planeta.
     * Ademas de los requisitos de nivel, esta zona impone una restriccion de equipo, exigiendo que el jugador posea un num min de Materias para poder enfrentar el desafio definitivo.
     * 
     * @param nombre El nombre xona.
     * @param nivelRequerido Nivel min para  entrar a la zona
     * @param materiasMinimasRequeridas Cantidad de Materias que el jugador debe tener en su arma para entrar.
     */
    public NucleoPlaneta(String nombre, int nivelRequerido, int materiasMinimasRequeridas){
        super(nombre, nivelRequerido);

        this.materiasMinimasRequeridas = materiasMinimasRequeridas;
    }

    /**
     * Valida si el jugador cumple con los requisitos dobles para entrar a la zona.
     * Primero verifica el nivel mediante la logica de la clase base. Luego, comprueba 
     * si el num de materias equipadas en el arma es suficiente.
     * 
     * @param Cloud El jugador que intenta acceder a la zona.
     * @return true si cumple con el nivel y el equipo min; 
     *         false en caso contrario, mostrando un mensaje de advertencia.
     */
    @Override
    public boolean validarAcceso(Jugador Cloud) { //validacion de materias equipadas, ademas de el nivel requerido
        boolean nivelSuficiente = super.validarAcceso(Cloud);
        
        int materiasEquipadas = Cloud.getBusterSword().getMateriasEquipadas().size(); //materias equipas en el arma de Cloud 
        
        if (nivelSuficiente && materiasEquipadas >= this.materiasMinimasRequeridas) {
            return true;
        } else {
            System.out.println("Debes equipar al menos " + this.materiasMinimasRequeridas + " materias en tu arma.");
            return false;
        }
    }

    /**
     * Gestiona el enfrentamiento final contra Sephiroth.
     * Implementa opcion de combatir o volver despues, un bucle de combate por turnos con estados de victoria/derrota, y un sistema de castigo severo por intentar huir del destino.
     * 
     * @param Cloud El jugador que se enfrenta a Sephirtoh.
     */
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
        String respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            System.out.println("Respuesta no válida. Por favor, ingresa 's' para sí o 'n' para no.");
            System.out.print("Respuesta: ");
            respuesta = scanner.nextLine();
        }
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.println("Aceptas la propuesta de Sephiroth. Te retiras de la Zona del combate para prepararte mejor.\n");
            return; // Salir del método para permitir que el jugador se prepare
        } 
        System.out.println("Rechazas la propuesta... Sephiroth sonrie levemente y avanza lentamente hacia ti... te desea suerte...\n");
        while (Cloud.getStats().getHpActual() > 0 && sephiroth.getStats().getHpActual() > 0) {

            System.out.println(sephiroth.getNombre() + " HP: " + sephiroth.getStats().getHpActual() + "/" + sephiroth.getStats().getHpMaximo() + " | Supernova: " + sephiroth.getContadorSuperNova() + "/10");
            System.out.println("Cloud HP: " + Cloud.getHpActual() + "/" + Cloud.getHpMaximo() + " | MP: " + Cloud.getMpActual() + "/" + Cloud.getMpMaximo() + " | Limite: " + Cloud.getLimiteActual() + "/100");
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

                            if (elegido == Elemento.CURA) {
                                int cura = danoMagico;
                                Cloud.getStats().setHpActual(Math.min(Cloud.getHpActual() + cura, Cloud.getHpMaximo()));
                                System.out.println("Usas Materia de Curación. Recuperas " + cura + " HP.");
                                turnoFinalizado = true;
                            } else if (danoMagico > 0) {
                                sephiroth.getStats().setHpActual(sephiroth.getStats().getHpActual() - danoMagico);
                                System.out.println("¡Lanzas un hechizo de " + elegido + "!");
                                System.out.println("Causas " + danoMagico + " de daño mágico.");
                                turnoFinalizado = true;
                                Cloud.sumarLimite(danoMagico / 2);
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
                    Cloud.getMochila().removeIf(m -> m.getNombre().equalsIgnoreCase("Curacion") || m.getElemento() == Elemento.CURA);
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
                System.out.println("Sephiroth te mira.... y no le agrada que intentes huir.... te ve como alguien cobarde y debil.... aunque igual te dara la oportunidad de escapar....aunque no sera tan facil como antes...\nPresiona Enter para intentar huir...");
                scanner.nextLine();
                System.out.println("Intentas huir...\nPresiona Enter para continuar...");
                scanner.nextLine();
                System.out.println("'...En verdad creiste que podrias huir?... tuve misericordia al ofrecer que te marcharas... y no aceptaste... no eres más que un ser insignificante...'");
                System.out.println("Sephiroth te ataca por intentar huir...quedas tumbado en el suelo... tal parece que no puedes escapar de su alcance...\nRecibes 100 de daño por intentar huir...");
                Cloud.getStats().recibirDMG(100); System.out.println("Presiona Enter para continuar..."); scanner.nextLine();
                if (Cloud.getHpActual() > 0) {
                    System.out.println("Te has salvado de morir por intentar huir... pero Sephiroth te mira fijamente.... '¿Quieres intenarlo otra vez?.... Solo hazlo mejor'... Intentarlo (s) | Rechazar oferta (n)"); String escape = scanner.nextLine(); escape = escape.toLowerCase();
                    if (escape.equals("s")){
                        System.out.println("Te levantas como puedes... tomas tu espada e intentas huir otra vez... corres lo mas rapido que puedes... pero Sephiroth es mas rapido que tu... Recibes 270 de daño por intentar huir otra vez...\n 'Entiende tu lugar, escoria'"); Cloud.getStats().recibirDMG(270); System.out.println("Presiona Enter para continuar..."); scanner.nextLine();
                    } else {
                        System.out.println("\nTomas tu espada y te levantas a duras penas apoyandote en ella... te pones en guardia, levantas tu espada y miras fijamente a Sephiroth sin decir nada... Sephiroth te observa... se le dibuja una leve sonrisa en el rostro... 'Parece que aprendiste...'"); System.out.println("Presiona Enter para continuar el combate..."); scanner.nextLine();
                    }
                }
                System.out.println("El contador de Supernova de Sephiroth se incrementa por tu intento de huida... cuidado con eso...");
                System.out.println("Y por si no fuera suficiente... se cura sus herida");
                sephiroth.getStats().setHpActual(sephiroth.getStats().getHpMaximo());
                sephiroth.setContadorSuperNova(3); //aumentamos en 3 el contador
                turnoFinalizado = true; 
            }
            if (sephiroth.getStats().getHpActual() <= 0) {
                System.out.println("\n¡SEPHIROTH HA SIDO DERROTADO!");
                System.out.println("Cloud mira hacia el cielo... derroto a Septhiroth... ya nadie mas correra peligro.... Sonrie con tranquilidad y se desploma en el suelo del cansancio.");
                System.out.println("¡FELICIDADES! HAS COMPLETADO EL JUEGO.");
                System.exit(0); 
            } else if(Cloud.getStats().getHpActual() <= 0) {
                System.out.println("Has caido en el combate... no has sido lo suficientemente fuerte... pero el destino te da otra oportunidad para lograr tu objetivo");
                sephiroth.reiniciarContador();
                return;
            }
            if (turnoFinalizado && sephiroth.getStats().getHpActual() > 0) {
                sephiroth.atacar(Cloud);
            }
            
        }
    }

    /**
     * Ejecuta el evento final del juego.
     * Al entrar, se activa una secuencia narrativa que desemboca directamente 
     * en el enfrentamiento definitivo con Sephiroth.
     * 
     * @param Cloud El jugador que entra a la zona.
     */
    @Override
    public void accionZona(Jugador Cloud){ 
        System.out.println("¡Has entrado al Núcleo del Planeta! Avanzas por la zona... y a lo lejos ves a Sephiroth.... él te estaba esperando...");    
        iniciarCombate(Cloud);
    }
}