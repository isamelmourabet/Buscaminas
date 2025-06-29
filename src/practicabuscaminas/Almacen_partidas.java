/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicabuscaminas;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Grupo G
 */
public class Almacen_partidas implements Serializable {

    //**Creamos el atributo*/
    private ArrayList<Partida> partidas;
    private static final int FILACOLUMNA = 16;

    //**Creamos el constructor*/
    public Almacen_partidas() {
        this.partidas = new ArrayList<>();
    }

    public void anadirPartida(Partida p) {
        this.partidas.add(p);
    }

    /**
     * Creamos la funcion infopartida para poder imprimir la información de una
     * partida en específico
     */
    public void tablero_movimientos(Partida p, File f) throws IOException {
        Tablero tab = p.getTablero();
        String s = "";

        for (int i = 0; i < FILACOLUMNA; i++) {
            for (int j = 0; j < FILACOLUMNA; j++) {
                Casilla c = tab.getCasilla(i, j);
                s += c.getSimbolo_oculto() + "\t";
            }

            s += "\n";
        }

        ArrayList<Movimiento> movimientos = p.getMovimientos();
        Iterator<Movimiento> i = movimientos.iterator();

        while (i.hasNext()) {
            s += i.next().toString() + "\n";
        }

        try {
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.println(s);
            salida.close();
        } catch (java.io.IOException ioex) {

        }
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    //**Carga la informacion de los jugadores desde un fichero binario*/
    public void cargaListadoB(File f) throws Exception {
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(f));
        Almacen_partidas almacen = (Almacen_partidas) entrada.readObject();
        partidas.addAll(almacen.partidas);
    }

    //**Guarda la informacion de los jugadores en un fichero binario*/
    public void guardarListadoB(File f) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(f))) {
            salida.writeObject(this);
        }
    }

    public void info_partida(Partida p) {
        System.out.println("Información sobre la partida:");
        System.out.println("-----------------------------");
        System.out.println("Jugador 1: " + p.getJugadorActual().getNombre());
        System.out.println("Jugador 2: " + p.getJugadorEsperando().getNombre());
        System.out.println("Marcador: " + p.getJugadorActual().getNombre() + ": " + p.getMarcador().getPuntos_J1() + p.getJugadorEsperando().getNombre() + ": " + p.getMarcador().getPuntos_J2());
        System.out.println("Movimientos: ");
        System.out.println(p.getMovimientos());
    }

}
