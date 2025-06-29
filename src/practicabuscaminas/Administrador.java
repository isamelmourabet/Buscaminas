package practicabuscaminas;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Grupo G
 */
public class Administrador extends Usuario implements Serializable {

    //**Creamos el atributo almacen para que asi el administrador pueda usar sus metodos*/
    private Almacen_Usuario almacen;

    //**Creamos el constructor*/
    public Administrador(String nombre, String contrasena) {
        super(nombre, contrasena);
        this.almacen = new Almacen_Usuario();
    }

    //**Implementamos todos los metodos que podra utilizar el administrador*/
    public boolean autenticar(Jugador j) {
        return almacen.autenticar(j);
    }

    public void alta(Jugador j) {
        almacen.alta(j);
    }

    public void baja(Jugador j) {
        almacen.baja(j);
    }

    public void cargaListado(File f) throws IOException {
        almacen.cargaListado(f);
    }

    public void cargaListadoB(File f) throws Exception {
        almacen.cargaListadoB(f);
    }

    public void guardarListadoB(File f) throws IOException {
        almacen.guardarListadoB(f);
    }

    public String listado() {
        return almacen.listado();
    }

}
