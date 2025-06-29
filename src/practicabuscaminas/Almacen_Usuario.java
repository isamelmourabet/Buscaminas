/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicabuscaminas;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Grupo G
 */
public class Almacen_Usuario implements Serializable {

    //**Creamos el atributo*/
    private ArrayList<Jugador> jugadores;

    //**Creamos el constructor para inicializar el Arraylist*/
    public Almacen_Usuario() {
        this.jugadores = new ArrayList<>();
    }

    //**Creamos el metodo para autenticar(True si el usuario esta en el almacen, false de los contrario)*/
    public boolean autenticar(Jugador j) {
        return this.jugadores.contains(j);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

//**Metodos para dar de alta y de baja a los jugadores*/
    public void alta(Jugador j) {
        this.jugadores.add(j);
    }

    public void baja(Jugador j) {
        this.jugadores.remove(j);
    }

    //**Método para la clasificación de los jugadores en el ranking basado en sus victorias*/
    public ArrayList<Jugador> clasificacion_por_victorias() {
        jugadores.sort((Jugador j1, Jugador j2) -> {
            int victoriasJ1 = j1.getEstadisticaJugador().getGanadas();
            int victoriasJ2 = j2.getEstadisticaJugador().getGanadas();
            return Integer.compare(victoriasJ2, victoriasJ1);
        });
        return new ArrayList<>(jugadores);

        /* Este codigo seria el implementado si huibieramos utilizado las clases CompararVictoria
        ArrayList<Jugador> jugadoresCopia = new ArrayList<>(jugadores);
        Collections.sort(jugadoresCopia, new CompararPorVictoria());
        return jugadoresCopia;*/
    }

    //**Método para la clasificación de los jugadores en el ranking basado en sus nombres*/
    public ArrayList<Jugador> clasificacion_por_nombre() {
        jugadores.sort((Jugador j1, Jugador j2) -> j1.getNombre().compareTo(j2.getNombre()));
        return new ArrayList<>(jugadores);

        /* Este codigo seria el implementado si huibieramos utilizado las clases CompararPorNombre
        ArrayList<Jugador> jugadoresCopia = new ArrayList<>(jugadores);
        Collections.sort(jugadoresCopia, new CompararPorNombre()
        );
        return jugadoresCopia;*/
    }

    //*Cargar usuarios de un fichero de texto/
    public void cargaListado(File f) {
        try (BufferedReader entrada = new BufferedReader(new FileReader(f))) {
            int numeroJugadores = Integer.parseInt(entrada.readLine().trim());

            for (int i = 0; i < numeroJugadores; i++) {
                String[] arrayJugadores = entrada.readLine().split("#");
                String nombre = arrayJugadores[0].trim();
                String clave = arrayJugadores[1].trim();

                Jugador jugador = new Jugador(nombre, clave);

                if (!autenticar(jugador)) {
                    alta(jugador);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de E/S");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error: Formato numérico incorrecto");
            e.printStackTrace();
        }
    }

    //**Carga la informacion de los jugadores desde un fichero binario*/
    public void cargaListadoB(File f) throws Exception {
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(f));
        Almacen_Usuario almacen = (Almacen_Usuario) entrada.readObject();
        jugadores.addAll(almacen.jugadores);
    }

    //**Guarda la informacion de los jugadores en un fichero binario*/
    public void guardarListadoB(File f) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(f))) {
            salida.writeObject(this);
        }
    }

    //**Método que muestran un listado de los usuarios usado por el administrador */
    public String listado() {
        String s = "";

        for (Jugador j : jugadores) {
            s += j.getNombre() + " " + j.getContrasena() + "\n";
        }

        return s;
    }

    //**Método que muestra la clasificacion ordenada alfabéticamente*/
    public String listadoClasificacion(ArrayList<Jugador> clasificacion) {
        StringBuilder resultado = new StringBuilder();

        for (Jugador jugador : clasificacion) {
            resultado.append("Nombre: ").append(jugador.getNombre()).append("\nEstadisticas: ").append(jugador.getEstadisticaJugador().toString()).append("\n");
        }

        return resultado.toString();
    }

}
