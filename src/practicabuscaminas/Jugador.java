package practicabuscaminas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Grupo G
 */
public class Jugador extends Usuario implements Serializable {

    //**Creamos el atributo para ver las estadisticas*/
    private Estadisticas EstadisticaJugador;
    private ArrayList<Cara_a_cara> rivales;

    //**Creamos el constructor*/
    public Jugador(String nombre, String contrasena) {
        super(nombre, contrasena);
        this.EstadisticaJugador = new Estadisticas();
        this.rivales = new ArrayList<>();
    }

    ///**Creamos los gets*/
    public Estadisticas getEstadisticaJugador() {
        return EstadisticaJugador;
    }

    public ArrayList<Cara_a_cara> getRivales() {
        return this.rivales;
    }

    //**Sobreescribimos el toString*/
    @Override
    public String toString() {
        return "Usuario:\nNombre: " + this.getNombre() + "\nContraseña: " + this.getContrasena() + "\nEstadistica" + this.EstadisticaJugador.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        Jugador jugador = (Jugador) o;
        return getNombre().equals(jugador.getNombre()) && getContrasena().equals(jugador.getContrasena());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getContrasena());
    }
}
