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
public class Usuario implements Serializable {

    //**Creamos los atributos*/
    private String nombre;
    private String contrasena;

    //**Creamos el constructor*/
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    //**Creamos los getters*/
    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    //**Sobreescribimos el toString*/
    @Override
    public String toString() {
        return "Usuario:\nNombre: " + this.getNombre() + " Contraseña: " + this.getContrasena();
    }
}
