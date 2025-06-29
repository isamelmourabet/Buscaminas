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
public class Estadisticas implements Serializable {

    //**Creamos los atributos*/
    private int jugadas;
    private int ganadas;
    private int perdidas;
    private int minas_a_favor;
    private int minas_en_contra;

    //**Creamos el constructor e inicializamos las variables*/
    public Estadisticas() {
        this.ganadas = 0;
        this.jugadas = 0;
        this.perdidas = 0;
        this.minas_a_favor = 0;
        this.minas_en_contra = 0;
    }

    //**Creamos los getters*/
    public int getJugadas() {
        return jugadas;
    }

    public int getGanadas() {
        return ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public int getMinas_a_favor() {
        return minas_a_favor;
    }

    public int getMinas_en_contra() {
        return minas_en_contra;
    }

    //**Creamos lso metodos para imcrementar las variables*/
    public void incrementarJugadas() {
        this.jugadas++;
    }

    public void incrementarGanadas() {
        this.ganadas++;
    }

    public void incrementarPerdidas() {
        this.perdidas++;
    }

    public void incrementarMinas_a_favor() {
        this.minas_a_favor++;
    }

    public void incrementarMinas_en_contra() {
        this.minas_en_contra++;
    }

    //**Sobreescribimos el toString*/
    @Override
    public String toString() {
        return "Jugadas: " + this.getJugadas() + "\nGanadas: " + this.getGanadas() + "\nPerdidas: " + this.getPerdidas() + "\nMinas a favor: " + this.getMinas_a_favor() + "\nMinas en contra: " + this.getMinas_en_contra() + "\n";
    }

}
