package practicabuscaminas;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Grupo G
 */
public class Main {

    public static void main(String[] args) {

        int mainSelector;
        File f = new File("/Users/isamelmourabet/Desktop/usuarios.txt");
        Almacen_Usuario almacenUsuarios = new Almacen_Usuario();
        Almacen_partidas almacenPartidas = new Almacen_partidas();
        Administrador admin = new Administrador("root", "root");
        Jugador j23 = new Jugador("1", "1");
        almacenUsuarios.alta(j23);

        do {
            System.out.println("--Bienvenido al BUSAMINAS--");
            System.out.println("---------------------------");
            System.out.println("Selecciona una opción");
            System.out.println("1. Entrar como Administrador");
            System.out.println("2. Jugar");
            System.out.println("3. Ver clasificacion y estadísticas");
            System.out.println("4. Salir");
            Scanner sc = new Scanner(System.in);
            mainSelector = Integer.parseInt(sc.nextLine());

            switch (mainSelector) {
                case 1:
                    System.out.println("Iniciar sesión como administrador.");
                    System.out.println("----------------------------------");
                    System.out.println("Usuario: ");
                    String name = sc.nextLine();
                    System.out.println("Contraseña: ");
                    String password = sc.nextLine();

                    if ("root".equals(name) && "root".equals(password)) {
                        int adminSelector;

                        do {
                            System.out.println("--Bienvenido ADMIN--");
                            System.out.println("--------------------");
                            System.out.println("Selecciona una opción");
                            System.out.println("1. Dar de alta");
                            System.out.println("2. Dar de baja");
                            System.out.println("3. Ver informacion sobre las partidas");
                            System.out.println("4. Cargar Listado");
                            System.out.println("5. Salir del modo Administrador");
                            adminSelector = Integer.parseInt(sc.nextLine());

                            switch (adminSelector) {
                                case 1:
                                    System.out.println("Nombre del Jugador: ");
                                    name = sc.nextLine();
                                    System.out.println("Contraseña del Jugador: ");
                                    password = sc.nextLine();

                                    Jugador darAlta = new Jugador(name, password);

                                    if (admin.autenticar(darAlta)) {
                                        System.out.println("ERROR. Usuario existente.");
                                        System.out.println(admin.autenticar(darAlta));
                                        admin.listado();

                                    } else {
                                        admin.alta(darAlta);
                                        System.out.println("USUARIO DADO DE ALTA.");
                                        System.out.println(admin.autenticar(darAlta));
                                        admin.listado();

                                    }
                                    break;

                                case 2:
                                    System.out.println("Nombre del Jugador: ");
                                    name = sc.nextLine();
                                    System.out.println("Contraseña del Jugador: ");
                                    password = sc.nextLine();

                                    Jugador darBaja = new Jugador(name, password);

                                    if (!admin.autenticar(darBaja)) {
                                        System.out.println("ERROR. Usuario no encontrado.");
                                        admin.listado();

                                    } else {
                                        admin.baja(darBaja);
                                        System.out.println("USUARIO DADO DE BAJA.");
                                        admin.listado();
                                    }
                                    break;
                                case 3:
                                    //Ver info partidas.
                                    break;

                                case 4: {
                                    try {
                                        admin.cargaListado(f);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                break;

                                case 5:

                                    break;
                                default:
                                    System.out.println("ERROR");
                                    break;
                            }

                        } while (adminSelector != 5);

                    }

                    break;

                case 2:
                    boolean b1;
                    boolean b2;
                    System.out.println("Nombre J1: ");
                    name = sc.nextLine();
                    System.out.println("Contraseña J1: ");
                    password = sc.nextLine();

                    Jugador j1 = new Jugador(name, password);
                    if (almacenUsuarios.autenticar(j1)) {
                        b1 = true;
                        System.out.println("Usuario correcto");
                    } else {
                        System.out.println("Usuario/s no registrados.");
                        b1 = false;
                    }

                    System.out.println("Nombre J2: ");
                    name = sc.nextLine();
                    System.out.println("Contraseña J2: ");
                    password = sc.nextLine();

                    Jugador j2 = new Jugador(name, password);

                    if (almacenUsuarios.autenticar(j2)) {
                        b2 = true;
                        System.out.println("Usuario correcto");
                    } else {
                        System.out.println("Usuario/s no registrados.");
                        b2 = false;
                    }

                    if (b1 == true && b2 == true) {
                        Partida p1 = new Partida(j1, j2);
                        almacenPartidas.anadirPartida(p1);
                        p1.jugar();
                    } else {
                        System.out.println("No se ha podido iniciar la partida");
                    }

                    break;
                case 3:
                    System.out.println("Nombre del Jugador: ");
                    name = sc.nextLine();
                    System.out.println("Contraseña del Jugador: ");
                    password = sc.nextLine();

                    Jugador jugadorEstadisticas = new Jugador(name, password);

                    int staticsSelector;

                    if (almacenUsuarios.autenticar(jugadorEstadisticas)) {
                        System.out.println("Usuario correcto \n");

                        do {
                            System.out.println("---ESTADÍSTICAS---");
                            System.out.println("------------------");
                            System.out.println("Seleciona una opción");
                            System.out.println("1. Ver clasificación completa");
                            System.out.println("2. Ver estadísticas del Jugador");
                            System.out.println("3. Salir del modo de Estadisticas");
                            staticsSelector = Integer.parseInt(sc.nextLine());

                            switch (staticsSelector) {
                                case 1:

                                    break;
                                case 2:
                                    System.out.println("--Estadísticas del Jugador--");
                                    System.out.println("----------------------------");
                                    jugadorEstadisticas.getEstadisticaJugador().incrementarGanadas();
                                    jugadorEstadisticas.getEstadisticaJugador().incrementarMinas_a_favor();
                                    System.out.println(jugadorEstadisticas.getEstadisticaJugador().toString());

                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("ERROR");
                            }

                        } while (staticsSelector != 3);

                    } else {
                        System.out.println("Usuario no existente.");

                    }
                    break;
                case 4:

                    break;
                default:
                    System.out.println("ERROR");
                    break;
            }

        } while (mainSelector != 4);

    }
}
