/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicabuscaminas;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Grupo G
 */
public class Cara_a_cara implements Serializable {

    //**Creamos los atributos*/ 
    private ArrayList<Jugador> jugadoresRivales;
    private Estadisticas estadisticasRival;

    public Cara_a_cara() {
        this.jugadoresRivales = new ArrayList<>();
        this.estadisticasRival = new Estadisticas();
    }

    public ArrayList<Jugador> getJugadores() {
        return this.jugadoresRivales;
    }

    public void addJugadores(Jugador j) {
        this.jugadoresRivales.add(j);
    }

    public void removeJugadores(Jugador j) {
        this.jugadoresRivales.remove(j);
    }

    public Estadisticas getEstadisticaJugador() {
        return estadisticasRival;
    }

    @Override
    public String toString() {
        for (Jugador jugador : jugadoresRivales) {
            return """
                   Jugadores: 
                   Nombre: """ + jugador.getNombre() + "\n" + jugador.getEstadisticaJugador() + "\n";
        }

        return "\n";
    }

}
