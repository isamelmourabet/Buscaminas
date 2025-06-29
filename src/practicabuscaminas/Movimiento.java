package practicabuscaminas;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Grupo G
 */
public class Movimiento implements Serializable {

    //**Creamos los atributos*/
    private String nombreJugador;
    private int coordenadaX;
    private int coordenadaY;

    //**Creamos un atributo para controlar el numero de movimientos que hemos realizado*/
    private static int numMov = 0;

    //**Creamos el constructor*/
    public Movimiento(String nombreJugador, int coordenadaX, int coordenadaY) {
        this.nombreJugador = nombreJugador;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        numMov++;
    }

    //**Creamos los getters y los setters*/
    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    //**Sobreescribimos el toString*/
    @Override
    public String toString() {
        return "Jugador: " + this.getNombreJugador() + " (" + this.getCoordenadaX() + ", " + this.getCoordenadaY() + ")";
    }
}
