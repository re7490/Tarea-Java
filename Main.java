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
            System.out.println("Juego no iniciado. ¡Hasta luego!");
            return; //salimos
        }
        System.out.print("\033[H\033[2J");
        System.out.println("¡Bienvenido al juego, Cloud!");
        Jugador Cloud = new Jugador();
        Zona zonaActual = zonaSector7;
        while(true){
            System.out.println("\n---- UBICACION:  " + zonaActual.getNombre() + " ----");
            System.out.println("Nivel: " + Cloud.getNivel() + " | HP: " + Cloud.getHpActual() + "/" + Cloud.getHpMaximo());
            System.out.println("MP: " + Cloud.getMpActual() + "/" + Cloud.getMpMaximo() + " | Fuerza: " + Cloud.getFuerza() + " | Magia: " + Cloud.getMagia());
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
            }else if(eleccion.equals("4") && zonaActual instanceof Sector7){
                System.out.println("Te sientas cerca de una hoguera y duermes... Te despiertas sintiéndote renovado.");
                Cloud.setHpActual(Cloud.getHpMaximo());
                Cloud.setMpActual(Cloud.getMpMaximo());
            } else if (eleccion.equals("0")) {
                System.out.println("¡Gracias por jugar!");
                break;
            }
        }
    }
}