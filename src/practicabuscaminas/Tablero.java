package practicabuscaminas;

import java.util.ArrayList;
import java.util.Iterator;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Grupo G
 */
public class Tablero {

    //**Creamos los atributos*/
    private final static int MINAS = 51;
    private final static int FILACOLUMNA = 16;

    Casilla[][] casillas;
    int[][] solucion;

    //**Creamos el constructor por defecto*/
    public Tablero() {
        casillas = new Casilla[FILACOLUMNA][FILACOLUMNA];
        solucion = new int[FILACOLUMNA][FILACOLUMNA];
        iniciarTablero();
    }

    //**Creamos el metodo para inicializar el Tablero*/
    public void iniciarTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new Casilla(i, j, "-");
            }
        }
    }

    //**Creamos el tablero oculto*/
    public void generarOculto() {
        generarMinas();
        generarNumeros_y_blancos();

        /*generarNumeros_y_Blancos();*/
    }

    //**Creamos el metodo para colocar las minas de manera aleatoria*/
    public void generarMinas() {
        int minasGeneradas = 0;

        while (minasGeneradas != MINAS) {
            int x = (int) (Math.random() * FILACOLUMNA);
            int y = (int) (Math.random() * FILACOLUMNA);

            if ("-".equals(casillas[x][y].getSimbolo_oculto())) {
                casillas[x][y].setSimbolo_oculto("*");
                minasGeneradas++;
            }
        }
    }

    //**Creamos el metodo para colocar los numeros y los espacios en blanco*/
    public void generarNumeros_y_blancos() {
        for (int i = 0; i < FILACOLUMNA; i++) {
            for (int j = 0; j < FILACOLUMNA; j++) {

                /**
                 * si la casilla no es una mina creamos un contador para saber
                 * el numero de minas alrededor todo esto mediante las el array
                 * de casilla adyacentes
                 */
                if (!"*".equals(casillas[i][j].getSimbolo_oculto())) {
                    int contador = 0;

                    Iterator<Casilla> iterador = casillasAdyacentes(getCasilla(i, j)).iterator();

                    while (iterador.hasNext()) {
                        Casilla c = iterador.next();
                        if ("*".equals(c.getSimbolo_oculto())) {
                            contador++;
                        }
                    }

                    /**
                     * Si hay almenos una mina alrededor de esac asilla
                     * entoncesse hace un set de esa casilla , caso contrario es
                     * un espacio en blanco
                     */
                    if (contador != 0) {
                        casillas[i][j].setSimbolo_oculto(String.valueOf(contador));
                    } else {
                        casillas[i][j].setSimbolo_oculto("B");
                    }
                }
            }
        }
    }

    //**Creamos el metodo de tipo Arraylist para controlar las casillas Adyacentes*/
    public ArrayList<Casilla> casillasAdyacentes(Casilla c) {
        ArrayList<Casilla> adyacentes = new ArrayList<>();

        //Utilizamos estos dos for anidados para recorrer en un área de 3x3 alrededor de la casilla pasada
        for (int i = c.getCoordenadaX() - 1; i <= c.getCoordenadaX() + 1; i++) {
            for (int j = c.getCoordenadaY() - 1; j <= c.getCoordenadaY() + 1; j++) {
                if (i >= 0 && i < casillas.length && j >= 0 && j < casillas[i].length && (i != c.getCoordenadaX() || j != c.getCoordenadaY()))//La ultima parte excluye la propia casilla dada por parametro 
                {
                    adyacentes.add(casillas[i][j]);
                }
            }
        }

        return adyacentes;
    }

    public Casilla getCasilla(int coordenadaX, int coordenadaY) {
        return casillas[coordenadaX][coordenadaY];
    }

    public void setCasilla(Casilla c) {
        int x = c.getCoordenadaX();
        int y = c.getCoordenadaY();
        casillas[x][y] = c;
    }

    public void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getSimbolo_oculto() + "\t");
            }
            System.out.println();  // Agrega esta línea para imprimir un salto de línea después de cada fila
        }
    }
}
