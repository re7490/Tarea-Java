import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import Mapa.*;
import Entidades.*;
import Componentes.*;


public class Main{
    public static void main(String[] args){

        //Iniciamos tienda y zonas antes de logica jugable
        //instancia tienda
        List<Mejora> inventarioTienda = new ArrayList<>();
        inventarioTienda.add(new Mejora("Mejora de Vitalidad", 100, TipoStat.HP_MAX, 20));
        inventarioTienda.add(new Mejora("Mejora de Éter", 120, TipoStat.MP_MAX, 10));
        inventarioTienda.add(new Mejora("Mejora Física", 150, TipoStat.FUERZA, 10));

        List<Materia> poolGongaga = new ArrayList<>();
        // materia al azar

        //instancia zonas
        Sector7 zonaSector7 = new Sector7("Sector 7", 1, inventarioTienda);
        Gongaga zonaGongaga = new Gongaga("Gonagaga", 5, poolGongaga);
        NucleoPlaneta zonaNucleo = new NucleoPlaneta("Nucleo Planeta", 20, 2);

        //logica juego
        System.out.println("¡Bienvenido al juego.... presiona 1 para iniciar!.... si por alguna razón no quieres iniciar.... presiona 0");
        Scanner scanner = new Scanner(System.in);
        char input = scanner.nextLine().charAt(0);
        while (input != '0' && input != '1') {
            System.out.println("Entrada no válida. Por favor, presiona 1 para iniciar o 0 para salir.");
            input = scanner.nextLine().charAt(0);
        }
        if (input == '0') {
            System.out.println("Por alguna extraña razon decidiste.... no iniciar el juego... ¿¡¡Por que harias eso!!?....presiona Enter para salir..."); scanner.nextLine();
            System.out.println("...Bueno........ ehhh, en verdad no entiendo por que..... por que elegir esta opcion??...presiona Enter para salir"); scanner.nextLine();
            System.out.println("...En fin... supongo que no quieres jugar... bueno, adios... Enter para salir"); scanner.nextLine();
            System.out.println("... Pero a veces uno comete errores... no?... seguramente te equivocaste al presionar las teclas.... despues de todo el 1 y el 0 estan muy juntos.... y se parecen...\nA quien engañamos!?!? Por que no querer ejecutar el juego? :,(   Enter para salir"); scanner.nextLine();
            System.out.println(".............aahhhh........ esperas que en verdad se cierre el juego..... pero no lo has jugado.... mmhhh..... dejame pensarlo...Presiona Enter para continuar...(veamos cuando me aburro de escribir)"); scanner.nextLine();            
            System.out.println(".........."); scanner.nextLine();
            System.out.println("........."); scanner.nextLine();
            System.out.println("......."); scanner.nextLine();
            System.out.println("....."); scanner.nextLine();
            System.out.println("..."); scanner.nextLine();
            System.out.println(".."); scanner.nextLine();
            System.out.println("."); scanner.nextLine();
            System.out.println("...Bueno.... si no querias comerte eso.... haber apretado 1.... no?"); scanner.nextLine();
            System.out.println("....."); scanner.nextLine();
            System.out.println("...si...... me estoy quedando sin ideas....aahh, ya se..... que tal una trivia de... una pregunta...."); scanner.nextLine();
            System.out.println("¿Te quivocaste al presionar el numero? (s/n)\nRespuesta: "); String respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
            while (!respuesta.equals("s") && !respuesta.equals("n")) {
                System.out.println("Eeehhh.... es s para si y n para no.... no es tan complicado.... creo.... no soy quien para juzgar a nadie....");scanner.nextLine();
                System.out.println("bueno, te lo preguntare de nuevo.... ¿Te quivocaste al presionar el numero? (s/n)\nRespuesta: ");
                respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase(); 
            }
            if (respuesta.equals("s")) {
                System.out.println("¡Ahhh!.... ves como lo sabia?? (es obvio que no te equivocaste.... pero bueno....) entonces... supondre que quieres jugar el juego, no es cierto? (s/n)"); respuesta = scanner.nextLine();
                while (!respuesta.equals("s") && !respuesta.equals("n")) {
                    System.out.println("Eeehhh.... es s para si y n para no....\nrespuesta: ");
                    respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
                }
                if (respuesta.equals("s")) {
                    System.out.println("¡Perfecto!.... entonces... dejemos los rodeos"); 
                } else {
                    System.out.println("Oh... veo que aun no quieres iniciar el juego.... dejame pensar que hago...... deberia permitirte Salir de esto? (s/n)"); respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
                    if (respuesta.equals("s")) {
                        System.out.println(".... por que?.... me ofende que no quieras iniciar el juego..... me entristece... asterisco se entristece asterisco...."); scanner.nextLine();
                        System.out.println("...era asi como era no? asterisco pone cara de duda asterisco"); scanner.nextLine();
                        System.out.println("..... buee.... para que engañarnos... eso fue raro... fue..... nose...."); scanner.nextLine();
                        System.out.println("...en fin... ultima oportunidad para iniciar el juego.... quieres hacerlo? (s/n)"); respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
                        while (!respuesta.equals("s") && !respuesta.equals("n")) {
                            System.out.println("Eeehhh.... es s para si y n para no.... recuerdalo -_- .... es como la 3ra vez....");scanner.nextLine();
                            respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
                        }
                        if (respuesta.equals("s")) {
                            System.out.println("¡Genial!.... entonces no te molesto mas con esto"); 
                        } else {
                            System.out.println("Bueno... no digamos que no lo intente... supongo que... respetare tu decision...  Presiona Enter para salir..."); System.exit(0); ;
                        }
                    } else {
                        System.out.println("...ehhh.... la verdad... no esperaba esa respuesta... ehhh... bueno... me quede sin ideas... adios  "); System.exit(0); ;
                    }
                }
            } else {
                System.out.println("Oh... entonces de verdad que no quieres iniciarlo..."); scanner.nextLine();
                System.out.println("...Bueno, supongo que... no puedo obligarte... verdad?"); scanner.nextLine();
                System.out.println("Bueno... tecniamente puedo hacerlo jsjsjs.... ahora la pregunta es por que lo haria? cuando puedo mantenerte en un bucle infinito (o hasta que me aburra la verdad) donde tengas que presionar Enter por siempre Wuajajajaj"); scanner.nextLine();
                System.out.println("¿Quieres entrar en el bucle infinito? (s/n)"); respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
                while (!respuesta.equals("s") && !respuesta.equals("n")) {
                    System.out.println("Eeehhh.... es s para si y n para no....\nrespuesta: ");
                    respuesta = scanner.nextLine(); respuesta = respuesta.toLowerCase();
                }
                if (respuesta.equals("s")) {
                    System.out.println("¡Perfecto!.... entonces... vayamos a ese bucle infinito (seguramente te arrepentiras de esto.... o quizas yo lo haga primero)"); 
                    for(int i=0; i<10; i++) {
                        System.out.println("Estas atrapado en el bucle infinito... no puedes salir de aqui... cada vez te sientes mas desesperado por salir... pero no puedes... cada vez te arrepientes mas de haber elegido esta opcion..."); scanner.nextLine();
                    }
                    System.out.println("...bueno... creo que ya es suficiente por hoy... creo... puedo ser peor... pudo ser un while(true)... espero que hayas reflexionado sobre tus decisiones... y que hayas aprendido algo de esto... (inicia el juego -_-)"); scanner.nextLine();
                    System.exit(0); ;
                } else {
                    System.out.println("Bueno... decision sabia la verdad... te dejare en paz.... a menos que luego prefieras no iniciar el juego.... Presiona Enter para salir... (ahora si de verdad)"); scanner.nextLine(); System.exit(0); ;
                }
            }
        }
        System.out.println("¡Bienvenido al juego, Cloud!");
        Jugador Cloud = new Jugador();
        Zona zonaActual = zonaSector7;
        while(true){
            System.out.println("\n---- UBICACION:  " + zonaActual.getNombre() + " ----");
            System.out.println("Nivel: " + Cloud.getNivel() + " | HP: " + Cloud.getHpActual() + "/" + Cloud.getHpMaximo());
            System.out.println("MP: " + Cloud.getMpActual() + "/" + Cloud.getMpMaximo() + " | Fuerza: " + Cloud.getFuerza() + " | Magia: " + Cloud.getMagia() + " | Chatarra: " + Cloud.getChatarra());
            System.out.println("¿Que deseas hacer?");
            System.out.println("1. Explorar los alrededores");
            System.out.println("2. Viajar a otra zona");
            System.out.println("3. Ver Mochila");
            if (zonaActual instanceof Sector7) {
                System.out.println("4. Descansar");
            }
            System.out.println("0. Salir del juego");
            System.out.print("Eleccion: ");
            String eleccion = scanner.nextLine();
            while (!eleccion.equals("0") && !eleccion.equals("1") && !eleccion.equals("2") && !eleccion.equals("3") && !eleccion.equals("4")) {
                System.out.println("Entrada no válida. Por favor, elige una opción válida.");
                eleccion = scanner.nextLine();
            }

            if (eleccion.equals("1")) {
                zonaActual.accionZona(Cloud);
            } else if (eleccion.equals("2")) {
                System.out.println("¿A qué zona deseas viajar?");
                System.out.println("1. Sector 7");
                System.out.println("2. Gongaga");
                System.out.println("3. Nucleo Planeta");
                System.out.print("Eleccion: ");
                String zonaEleccion = scanner.nextLine();
                Zona zonaOrigen = zonaActual;
                if (zonaEleccion.equals("1")) {
                    zonaActual = zonaSector7;
                } else if (zonaEleccion.equals("2")) {
                    zonaActual = zonaGongaga;
                    if (!zonaActual.validarAcceso(Cloud)) {
                        zonaActual = zonaOrigen;
                    }
                } else if (zonaEleccion.equals("3")) {
                    zonaActual = zonaNucleo;
                    if (!zonaActual.validarAcceso(Cloud)) {
                        zonaActual = zonaOrigen;
                    }
                } else {
                    System.out.println("Zona no válida. Permanece en la zona actual.");
                }
            } else if (eleccion.equals("3")) {
                Cloud.mostrarMochila();
                if (!Cloud.getMochila().isEmpty()) {
                    System.out.println("\n¿Que deseas hacer?");
                    System.out.println("1. Equipar una materia | 2. Desequipar una materia | 0. Volver");
                    System.out.print("Eleccion: ");
                    String subEleccion = scanner.nextLine();

                    if (subEleccion.equals("1") || subEleccion.equals("2")) {
                        System.out.print("Selecciona el número de la materia: ");
                        try {
                            int idx = Integer.parseInt(scanner.nextLine()) - 1;
                            if (idx >= 0 && idx < Cloud.getMochila().size()) {
                                Materia seleccionada = Cloud.getMochila().get(idx);

                                if (subEleccion.equals("1")) { //equipar
                                    if (!seleccionada.isEquipado()) {
                                        Cloud.getBusterSword().equiparMateria(seleccionada);
                                    } else {
                                        System.out.println("La materia ya esta equipada.");
                                    }
                                } else { //desequipar
                                    if (seleccionada.isEquipado()) {
                                        Cloud.getBusterSword().desequiparMateria(seleccionada);
                                    } else {
                                        System.out.println("La materia no estaa equipada.");
                                    }
                                }
                            } else {
                                System.out.println("Numero fuera de rango.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada no valida. Por favor, ingresa un numero.");
                        }
                    }
                }
            }else if(eleccion.equals("4") && zonaActual instanceof Sector7){
                System.out.println("Te sientas cerca de una hoguera y duermes... Te despiertas sintiéndote renovado.");
                Cloud.setHpActual(Cloud.getHpMaximo());
                Cloud.setMpActual(Cloud.getMpMaximo());
            } else if (eleccion.equals("0")) {
                System.out.println("¡Gracias por jugar!");
                break;
            }
            if (Cloud.getHpActual() <= 0) {
                zonaActual = zonaSector7;
                Cloud.setHpActual(Cloud.getHpMaximo());
                System.out.println("Has sido rescatado con diversas heridas... regresas al Sector 7... afortunadamente alli curan tus heridas... pero perdiste toda tu chatarra... y las materias que no tenias equipadas... que triste...");
                Cloud.setChatarra(0); 
                for (Materia m : Cloud.getMochila()) {
                    if (!m.isEquipado()) {
                        System.out.println("Has perdido " + m.getNombre() + "...");
                    }
                }
            }

        }
    }
}