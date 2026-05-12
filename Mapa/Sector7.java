package Mapa;

import Componentes.*;
import Entidades.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sector7 extends Zona{
    private List<Mejora> tiendaLocal;

    public Sector7(String nombre, int nivelRequerido, List<Mejora> tiendaLocal){
        super(nombre, nivelRequerido);
        this.tiendaLocal = tiendaLocal;
    }

    public void iniciarSimulacion(Jugador Cloud){
        Random rand = new Random();
        int xpAleatoria = rand.nextInt(((20 - 15) + 1) + 15);

        Estadisticas statsSoldado = new Estadisticas(50, 50, 0, 0, 15, 0);
        EnemigoSimulador soldadoComun = new EnemigoSimulador("Soldado Comun", xpAleatoria, 0, statsSoldado);

        System.out.println("\nTe has encontrado con un " + soldadoComun.getNombre() + "!");

        System.out.println("El " + soldadoComun.getNombre() + " te saluda: 'Hola quien quiera que seas, te ves algo fuerte");
        System.out.println("lo suficiente para poder practicar mis nuevas habilidades de combate. Peleemos, te dare la ventaja de atacar primero.'");

        Scanner scanner = new Scanner(System.in);
        while (Cloud.getHpActual() > 0 && soldadoComun.getStats().getHpActual() > 0) {

            System.out.println(soldadoComun.getNombre() + " HP: " + soldadoComun.getStats().getHpActual() + "/" + soldadoComun.getStats().getHpMaximo());
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
                soldadoComun.getStats().setHpActual(soldadoComun.getStats().getHpActual() - dano);
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
                                soldadoComun.getStats().setHpActual(soldadoComun.getStats().getHpActual() - danoMagico);
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
                    Cloud.getMochila().removeIf(m -> m.getNombre().equalsIgnoreCase("Curacion") || m.getElemento() == Elemento.CURA);
                } else if (!tieneCura) {
                    System.out.println("No tienes la Materia de Curación en tu mochila.");
                } else {
                    System.out.println("MP insuficiente para curar.");
                }
            } else if (eleccion.equals("4")) {
                if (Cloud.getLimiteActual() >= 100) {
                    int danoLimite = Cloud.getBusterSword().calcularDanoLimite();
                    soldadoComun.getStats().setHpActual(soldadoComun.getStats().getHpActual() - danoLimite);
                    System.out.println("Usas Ataque Limite y el daño es devastador: " + danoLimite);
                    turnoFinalizado = true;
                } else {
                    System.out.println("Barra de Límite insuficiente.");
                }
            } else if (eleccion.equals("0")){
                if (rand.nextInt(100) < 50) { System.out.println("¡Escapaste!"); return; }
                else { System.out.println("¡No pudiste escapar! El combate continúa."); turnoFinalizado = true; }
            }

            if (turnoFinalizado) {
                if (soldadoComun.getStats().getHpActual() > 0){
                    Random r = new Random();
                    int probAtaque = r.nextInt(100);
                    if (probAtaque <= 15){
                        System.out.println(soldadoComun.getNombre() + " ha fallado su ataque!\n-----------------------------");
                    } else if (!soldadoComun.checkDanoSeguro(Cloud)){
                        System.out.println("El " + soldadoComun.getNombre() + " se da cuenta de que puede derrotarte con un solo ataque, así que decide no atacarte y se retira del combate.\n----------------------------");
                        Cloud.setHpActual(1);
                        break;
                    }else {
                        System.out.println("\n ---TURNO ENEMIGO ---");
                        soldadoComun.atacar(Cloud);
                    }
                } else {
                    System.out.println(soldadoComun.getNombre() + " tambalea y cae al suelo\n----------------------------");
                    soldadoComun.giveXpRecompensa(Cloud);
                }
            }
        }
    };


    public void abrirTienda(Jugador Cloud) {
        Scanner scanner = new Scanner(System.in);
        boolean enTienda = true;

        System.out.println("Entras a la tienda, te recibe un vendedor alto y grande y con un puro encendido en la boca.\n'Bienvenido a mi tienda joven. Aquí encontrarás mejoras para ayudarte en tu camino... espero tengas suficiente chatarra Jejeje.'");

        while (enTienda) {
            System.out.println("\n--- TIENDA DE MEJORAS - SECTOR 7 ---");
            System.out.println("Chatarra actual: " + Cloud.getChatarra() + " G");
            System.out.println("Mejoras disponibles:");

            //lista mejoras con formato: nombre, bono, stat afectado, costo
            for (int i = 0; i < tiendaLocal.size(); i++) {
                Mejora m = tiendaLocal.get(i);
                System.out.println((i + 1) + ". " + m.getNombre() + 
                                " [+" + m.getValorBono() + " " + m.getStatAfectado() + "]" +
                                " - Costo: " + m.getCostoChatarra() + " G");
            }
            System.out.println("0. Salir de la tienda");
            System.out.print("¿Qué deseas comprar?: ");

            String entrada = scanner.nextLine();
            
            if (entrada.equals("0")) {
                enTienda = false;
                System.out.println("'Espero vuelvas pronto para comprar algo muchacho, no dejes que esa cara de pobre te detenga jejeje.'\n");
            } else {
                try {
                    int seleccion = Integer.parseInt(entrada) - 1;

                    if (seleccion >= 0 && seleccion < tiendaLocal.size()) {
                        Mejora m = tiendaLocal.get(seleccion);

                        //chatarra suficiente?
                        if (Cloud.getChatarra() >= m.getCostoChatarra()) {
                            //cobro
                            Cloud.setChatarra(Cloud.getChatarra() - m.getCostoChatarra());

                            //aplicamos mejora
                            if (m.getStatAfectado() == TipoStat.HP_MAX) {
                                Cloud.getStats().setHpMaximo(Cloud.getStats().getHpMaximo() + m.getValorBono());
                                //curamos al comprar mejora de vida
                                Cloud.getStats().setHpActual(Cloud.getStats().getHpActual() + m.getValorBono());
                            } 
                            else if (m.getStatAfectado() == TipoStat.MP_MAX) {
                                Cloud.getStats().setMpMaximo(Cloud.getStats().getMpMaximo() + m.getValorBono());
                                Cloud.getStats().setMpActual(Cloud.getStats().getMpActual() + m.getValorBono());
                            } 
                            else if (m.getStatAfectado() == TipoStat.FUERZA) {
                                Cloud.getStats().setFuerza(Cloud.getStats().getFuerza() + m.getValorBono());
                            }

                            System.out.println("\n¡Éxito! Has adquirido: " + m.getNombre());
                            System.out.println("Tus estadísticas han sido actualizadas.");
                        } else {
                            System.out.println("\nEl vendedor te mira con desconfianza... No puede creer que seas tan pobre como para no poder comprar.");
                            System.out.println("'Consigue algo de chatarra jovencito, no estorbes a mis clientes ricos jejeje.'");
                        }
                    } else {
                        System.out.println("\nSelección inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nPor favor, introduce un número válido.");
                }
            }
        }
    }
    @Override
    public void accionZona(Jugador j) { 
        boolean explorar = true;
        Scanner scanner = new Scanner(System.in);

        while (explorar) {
            System.out.println("Explorando el Sector 7...");
            System.out.println("Encuentras a un soldado por la zona.... por otro lado hay una tienda local que se ve interesante... \n¿Que deseas hacer?");
            System.out.println("1. Enfrentar al soldado comun");
            System.out.println("2. Visitar la tienda local");
            System.out.println("0. Dejar de explorar");
            System.out.print("Eleccion: ");
            String eleccion = scanner.nextLine();
            while (!eleccion.equals("1") && !eleccion.equals("2") && !eleccion.equals("0")) {
                System.out.println("Entrada no válida. Por favor, elige una opción válida.");
                eleccion = scanner.nextLine();
            }
            if (eleccion.equals("1")) {
                iniciarSimulacion(j);
            } else if (eleccion.equals("2")) {
                abrirTienda(j);
            } else {
                System.out.println("Dejaste de explorar el Sector 7.");
                explorar = false;
            }
        }
    }
}