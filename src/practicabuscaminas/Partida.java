package practicabuscaminas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Grupo G
 */
public class Partida implements Serializable {

    //**Creamos los atributos*/
    private Jugador jugadorActual;
    private Jugador jugadorEsperando;
    private Tablero tablero;
    private Marcador marcador;
    private ArrayList<Movimiento> movimientos;

    //**Creamos el constructor*/
    public Partida(Jugador jugador1, Jugador jugador2) {
        this.jugadorActual = jugador1;
        this.jugadorEsperando = jugador2;
        this.tablero = new Tablero();
        tablero.generarOculto();
        this.marcador = new Marcador();
        this.movimientos = new ArrayList<>();
    }

    //**Metodo para mostrar información de la partida*/
    public String infoPartida() {
        StringBuilder info = new StringBuilder();
        info.append("Estado de la partida:\n");
        info.append("Jugador actual: ").append(jugadorActual.getNombre()).append("\n");
        info.append("Jugador esperando: ").append(jugadorEsperando.getNombre()).append("\n");
        info.append("Marcador - ").append(jugadorActual.getNombre()).append(": ").append(marcador.getPuntos_J1());
        info.append(", ").append(jugadorEsperando.getNombre()).append(": ").append(marcador.getPuntos_J2()).append("\n");

        return info.toString();
    }

    //**Métodos getters*/
    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public Jugador getJugadorEsperando() {
        return jugadorEsperando;
    }

    public Marcador getMarcador() {
        return marcador;
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    //**Método para jugar mediante la interfaz gráfica que se llama cada vez que se toca un botón(mediante un ActionListener*/
    public void jugar(Casilla casilla_boton) {
        //**Creamos un Hashset para almacenar las casillas que se marquen y que no se repitan*/
        HashSet<Casilla> CasillasMarcadas = new HashSet<>();
        Casilla casilla_actual;

        casilla_actual = casilla_boton;

        //**Si es posible añadir dicha casilla entonces podemos continuar , caso contrario sería un movimiento no permitido*/
        if (CasillasMarcadas.add(casilla_actual)) {

            /**
             * Si toca una mina entonces se actualizan las estadisticas del
             * jugador y el marcador se incrementa
             */
            if ("*".equals(casilla_actual.getSimbolo_oculto())) {
                jugadorActual.getEstadisticaJugador().incrementarMinas_a_favor();
                jugadorEsperando.getEstadisticaJugador().incrementarMinas_en_contra();
                marcador.incrementarPuntosJugadorActual(jugadorActual);
                System.out.println("Mina Encontrada");
            }

            /**
             * Si toca un espacio en blanco entonces se extraen las casillas
             * adyacentes a una casilla para luego hacer un set en el tablero
             * cubierto, caso contrario solo se hace un set del actual
             */
            if ("B".equals(casilla_actual.getSimbolo_oculto())) {
                ArrayList<Casilla> adyacentes = tablero.casillasAdyacentes(casilla_actual);

                for (Casilla c : adyacentes) {
                    tablero.setCasilla(c);
                }
            } else {
                tablero.setCasilla(casilla_actual);
            }

            /**
             * Instanciamos un array de movimientos donde se guardan el
             * movimiento de esa casilla junto al nombre de su jugador para
             * luego añadirlo en el array de movimientos
             */
            Movimiento mov = new Movimiento(jugadorActual.getNombre(), casilla_actual.getCoordenadaX(), casilla_actual.getCoordenadaY());
            movimientos.add(mov);

            tablero.imprimirTablero();

            /**
             * Si el jugador no ha encontrado una mina se cambia de turno
             */
            if (!"*".equals(casilla_actual.getSimbolo_oculto())) {
                cambiarTurno();
            }

        } else {
            System.out.println("Movimiento no permitido");
        }
    }

    /**
     * El método jugar() funciona para jugar en una interfaz de texto, y
     * contiene métodos similares el método anterior, salvo que en este caso nos
     * interesa mostrar por pantalla el tablero sin descubrir
     *
     */
    public void jugar() {
        Tablero tablero_inical = new Tablero();
        HashSet<Casilla> CasillasMarcadas = new HashSet<>();

        int x;
        int y;
        Casilla casilla_actual;

        Scanner sc = new Scanner(System.in);

        do {

            System.out.println("Le toca a: " + jugadorActual.getNombre());
            infoPartida();
            System.out.println();
            System.out.println("Dime la coordenada X:  ");
            x = Integer.parseInt(sc.nextLine());

            System.out.println("Dime la coordenada Y: ");
            y = Integer.parseInt(sc.nextLine());

            System.out.println();

            casilla_actual = tablero.getCasilla(x, y);

            if (CasillasMarcadas.add(casilla_actual)) {

                if ("*".equals(casilla_actual.getSimbolo_oculto())) {
                    jugadorActual.getEstadisticaJugador().incrementarMinas_a_favor();
                    jugadorEsperando.getEstadisticaJugador().incrementarMinas_en_contra();
                    marcador.incrementarPuntosJugadorActual(jugadorActual);
                    System.out.println("Mina Encontrada");
                }

                if ("B".equals(casilla_actual.getSimbolo_oculto())) {

                    ArrayList<Casilla> adyacentes = tablero.casillasAdyacentes(casilla_actual);
                    Iterator<Casilla> iterator = adyacentes.iterator();

                    tablero_inical.setCasilla(casilla_actual);

                    while (iterator.hasNext()) {
                        Casilla c = iterator.next();
                        tablero_inical.setCasilla(c);
                    }

                } else {
                    tablero_inical.setCasilla(casilla_actual);
                }
                System.out.println();
                System.out.println("Minas encontradas: " + marcador.getPuntos_J1());
                System.out.println("Minas en contra: " + marcador.getPuntos_J2());

                Movimiento mov = new Movimiento(jugadorActual.getNombre(), casilla_actual.getCoordenadaX(), casilla_actual.getCoordenadaY());
                movimientos.add(mov);

                tablero_inical.imprimirTablero();

                if (!"*".equals(casilla_actual.getSimbolo_oculto())) {
                    cambiarTurno();

                }

                mostrarMovimientos();

            } else {
                System.out.println("Movimiento no permitido");
            }

        } while (jugadorActual.getEstadisticaJugador().getMinas_a_favor() <= 26 && jugadorEsperando.getEstadisticaJugador().getMinas_a_favor() <= 26);

        /**
         * Al terminar la partida se incrementan las estadisticas de los
         * jugadores relacionadas a las jugadas y las ganadas
         */
        jugadorActual.getEstadisticaJugador().incrementarGanadas();
        jugadorActual.getEstadisticaJugador().incrementarJugadas();
        jugadorEsperando.getEstadisticaJugador().incrementarPerdidas();
        jugadorEsperando.getEstadisticaJugador().incrementarJugadas();

    }

    /**
     * Método para mostrar los movimientos por consola
     */
    public void mostrarMovimientos() {
        Iterator<Movimiento> i = movimientos.iterator();

        while (i.hasNext()) {
            System.out.println(i.next().toString());
        }
    }

    //**Metodo para cambiar el jugador*/
    private void cambiarTurno() {
        // Cambiar los roles de los jugadores
        Jugador temp = jugadorActual;
        jugadorActual = jugadorEsperando;
        jugadorEsperando = temp;
        int marcJtemp = marcador.getPuntos_J1();
        marcador.setPuntos_J1(marcador.getPuntos_J2());
        marcador.setPuntos_J2(marcJtemp);
        // Actualizar la información adicional según sea necesario
    }
}
