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
public class Marcador implements Serializable {

    //**Creamos los atributos*/
    private int puntos_J1;
    private int puntos_J2;

    //**Creamos el constructor*/
    public Marcador() {
        this.puntos_J1 = 0;
        this.puntos_J2 = 0;
    }

    //**Cremamos los getters y los setters*/
    public int getPuntos_J1() {
        return puntos_J1;
    }

    public int getPuntos_J2() {
        return puntos_J2;
    }

    public void setPuntos_J1(int puntos_J1) {
        this.puntos_J1 = puntos_J1;
    }

    public void setPuntos_J2(int puntos_J2) {
        this.puntos_J2 = puntos_J2;
    }

    //**Metodo para incrementar los puntos del jugador actual*/
    public void incrementarPuntosJugadorActual(Jugador jugador) {
        puntos_J1++;
    }

    //**Sobreescribimos el toString*/
    @Override
    public String toString() {
        return "J1: " + this.getPuntos_J1() + " J2: " + this.getPuntos_J2();
    }
}
