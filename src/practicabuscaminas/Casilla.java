package practicabuscaminas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Grupo G
 */
public class Casilla {

    //**Creamos los atributos*/
    private int coordenadaX;
    private int coordenadaY;
    private String simbolo_oculto;

    //**Agregamos un atributo para saber si tiene mina*/
    private boolean tieneMina;

    //**Creamos el constructor*/
    public Casilla(int coordenadaX, int coordenadaY, String simbolo_oculto) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.simbolo_oculto = simbolo_oculto;
        this.tieneMina = false;
    }

    //**Creamos los getters y los setters*/
    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public String getSimbolo_oculto() {
        return simbolo_oculto;
    }

    public boolean getTieneMina() {
        return tieneMina;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public void setSimbolo_oculto(String simbolo_oculto) {
        this.simbolo_oculto = simbolo_oculto;
    }

    public void setTieneMina(boolean tieneMina) {
        this.tieneMina = tieneMina;
    }

    //**Sobreescribimos el toString*/
    @Override
    public String toString() {
        return "X: " + this.getCoordenadaX() + " Y:" + this.getCoordenadaY() + " Tipo: " + this.getSimbolo_oculto();
    }
}
