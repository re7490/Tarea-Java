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
        
        List<EnemigoSimulador> enemigos = new ArrayList<>();
        int probCantidad = rand.nextInt(100);
        int cantidad = (probCantidad < 30) ? 2 : 1;

        for(int i = 0; i < cantidad; i++) {
            int xpAleatoria = rand.nextInt(20 - 15 + 1) + 15;
            Estadisticas statsSoldado = new Estadisticas(50, 50, 0, 0, 15, 0);
            EnemigoSimulador soldadoComun = new EnemigoSimulador("Soldado Comun", xpAleatoria, 0, statsSoldado);
            enemigos.add(soldadoComun);
        }
        if (cantidad == 1){
            System.out.println("\nTe has encontrado con un Soldado Comun!");
            System.out.println("El Soldado te saluda: 'Hola quien quiera que seas, te ves algo fuerte");
            System.out.println("lo suficiente para poder practicar mis nuevas habilidades de combate. Peleemos, te dare la ventaja de atacar primero.'");
        } else{
            System.out.println("\nTe has encontrado con " + cantidad + " " + enemigos.get(0).getNombre() + "es!");
            System.out.println("Los Soldados te miran y uno de ellos dice: 'Mira, mira, por fin tendremos con quien practicar lo que hemos aprendido en nuestro entrenamiento.'\n'Siii!! ya estaba aburrido de practicar siempre contigo.... era muy facil ganarte Jajajaja!'");
            System.out.println("'¿Pero que dices?? Deja de mentirte a ti mismo.... en fin.... Como nosotros tenemos mas entrenamiento que tú... ehh... joven de pelo raro... te daremos la ventaja de atacar primero... aunque no creo que te sirva de mucho... Jajajaja!'");
        }
        Scanner scanner = new Scanner(System.in);
        while (Cloud.getHpActual() > 0 && !enemigos.isEmpty()) {

            System.out.println("\n--- ENEMIGOS ---");
            for (int i = 0; i < enemigos.size(); i++) {
                EnemigoSimulador e = enemigos.get(i);
                System.out.println((i + 1) + ". " + e.getNombre() + " HP: " + e.getStats().getHpActual() + "/" + e.getStats().getHpMaximo());
            }
            System.out.println("Cloud HP: " + Cloud.getHpActual() + "/" + Cloud.getHpMaximo() + " | MP: " + Cloud.getMpActual() + "/" + Cloud.getMpMaximo() + " | Limite: " + Cloud.getLimiteActual() + "/100");
            System.out.println("1. Ataque Físico | 2. Magia "+ (Cloud.getMpActual() >= 10 && !Cloud.getBusterSword().getMateriasEquipadas().isEmpty() ? " (Disponible)" : " (No disponible)") + 
            "| 3. Curarse " + (Cloud.getMpActual() >= 15 && Cloud.getMochila().stream().anyMatch(m -> m.getNombre().equalsIgnoreCase("Curacion") || m.getElemento() == Elemento.CURA) ? " (Disponible)" : " (No disponible)") +
            "| 4. Ataque limite" + (Cloud.getLimiteActual() >= 100 ? " (Disponible)" : " (No disponible)") + "| 0. Huir");

            System.out.print("Eleccion: ");
            String eleccion = scanner.nextLine();
            boolean turnoFinalizado = false;

            while (!eleccion.equals("1") && !eleccion.equals("2") && !eleccion.equals("3") && !eleccion.equals("4") && !eleccion.equals("0")) {
                System.out.println("Entrada no válida. Por favor, elige una opción válida.");
                eleccion = scanner.nextLine();
            }
            if (eleccion.equals("1") || eleccion.equals("2") || eleccion.equals("4")) {
                System.out.print("¿A quién atacas? (Número): ");
                int indice = Integer.parseInt(scanner.nextLine()) - 1;
                if (indice >= 0 && indice < enemigos.size()) {
                    EnemigoSimulador objetivo = enemigos.get(indice);
                    if (eleccion.equals("1")){
                        int dano = Cloud.getBusterSword().calcularDanoFisico();
                        objetivo.getStats().setHpActual(objetivo.getStats().getHpActual() - dano);
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
                                        objetivo.getStats().setHpActual(objetivo.getStats().getHpActual() - danoMagico);
                                        System.out.println("¡Lanzas un hechizo de " + elegido + "!");
                                        System.out.println("Causas " + danoMagico + " de daño mágico.");
                                        Cloud.sumarLimite(danoMagico / 2);
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
                    } else if (eleccion.equals("4")) {
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
            }else if (eleccion.equals("0")){
                if (rand.nextInt(100) < 50) { System.out.println("¡Escapaste!"); return; }
                else { System.out.println("¡No pudiste escapar! El combate continúa."); turnoFinalizado = true; }
            }

            if (turnoFinalizado && !enemigos.isEmpty()) {  
                //limpieza muertos              
                for (int i = enemigos.size() - 1; i >= 0; i--) {
                    if (enemigos.get(i).getStats().getHpActual() <= 0) {
                        EnemigoSimulador caido = enemigos.get(i);
                        System.out.println(caido.getNombre() + " tambalea y cae al suelo.");
                        
                        if (enemigos.size() == 2) {
                            System.out.println("El Soldado sobreviviente grita: '¡Nooo, " + caido.getNombre() + "!'... pobrecito... ha perdido a su amigo...");
                        }
                        
                        caido.giveXpRecompensa(Cloud);
                        enemigos.remove(i);
                    }
                }
                if (!enemigos.isEmpty()) {
                    System.out.println("\n--- Turno de los enemigos ---");
                    Random r = new Random();
                    boolean ataqueConjunto = false;

                    if (enemigos.size() == 2) {
                        System.out.println("Los soldados se miran y deciden coordinar su ataque...");
                        int probCoordinacion = r.nextInt(100);
                        ataqueConjunto = (probCoordinacion < 50) ? true : false;
                    }

                    if (ataqueConjunto) {
                        System.out.println("¡Los soldados increíblemente se coordinan para un ataque combinado!");
                        for (EnemigoSimulador e : enemigos) {
                            ataqueIndividualSoldado(e, Cloud);
                        }
                    } else {
                        if (enemigos.size() == 2) {
                            System.out.println("Los soldados no logran coordinarse... ¡lo deciden con un cachipún!");
                        }
                        int indiceAtacante = r.nextInt(enemigos.size());
                        EnemigoSimulador atacanteEnemigo = enemigos.get(indiceAtacante);
                        System.out.println(atacanteEnemigo.getNombre() + " se prepara para atacar.");
                         if (ataqueIndividualSoldado(atacanteEnemigo, Cloud)) {
                            System.out.println("El combate ha terminado.");
                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean ataqueIndividualSoldado(EnemigoSimulador soldado, Jugador Cloud){
        Random r = new Random();
        int probAtaque = r.nextInt(100);
        if (probAtaque <= 15){
            System.out.println(soldado.getNombre() + " ha fallado su ataque!\n-----------------------------");
            return false;
        } else if (!soldado.checkDanoSeguro(Cloud)){
            System.out.println("El " + soldado.getNombre() + " se da cuenta de que puede derrotarte con un solo ataque, así que decide no atacarte y se retira del combate.\n----------------------------");
            Cloud.setHpActual(1);
            return true; //identificar q hay q salir del combate
        }else {
            System.out.println("\n ---TURNO ENEMIGO ---");
            soldado.atacar(Cloud);
            return false;
        }
    }


    public void abrirTienda(Jugador Cloud) {
        Scanner scanner = new Scanner(System.in);
        boolean enTienda = true;

        System.out.println("\nEntras a la tienda, te recibe un vendedor alto y grande y con un puro encendido en la boca.\n'Bienvenido a mi tienda joven. Aquí encontrarás mejoras para ayudarte en tu camino... espero tengas suficiente chatarra Jejeje.'");

        while (enTienda) {
            System.out.println("\n--- TIENDA DE MEJORAS - SECTOR 7 ---");

            System.out.println("\nNivel: " + Cloud.getNivel() + " | HP: " + Cloud.getHpActual() + "/" + Cloud.getHpMaximo());
            System.out.println("MP: " + Cloud.getMpActual() + "/" + Cloud.getMpMaximo() + " | Fuerza: " + Cloud.getFuerza() + " | Magia: " + Cloud.getMagia() + " | Chatarra: " + Cloud.getChatarra());
            
            System.out.println("\nMejoras disponibles:");

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
            System.out.println("\nNivel: " + j.getNivel() + " | HP: " + j.getHpActual() + "/" + j.getHpMaximo());
            System.out.println("MP: " + j.getMpActual() + "/" + j.getMpMaximo() + " | Fuerza: " + j.getFuerza() + " | Magia: " + j.getMagia() + " | Chatarra: " + j.getChatarra());
            System.out.println("\nExplorando el Sector 7...");
            System.out.println("Por un lado divisas unas siluetas... por otro lado hay una tienda local que se ve interesante... \n¿Que deseas hacer?");
            System.out.println("1. Ir a ver que son las siluetas");
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