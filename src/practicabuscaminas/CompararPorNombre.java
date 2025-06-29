/*
 
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicabuscaminas;

import java.util.Comparator;

/**
 *
 *
 * @author Grupo G
 */
//ESTA CLASE ES OTRA FORMA PARA PODER IMPLEMENTAR LA ORDENACION DE LAS CLASIFICACIONES
public class CompararPorNombre implements Comparator<Jugador> {

    @Override
    public int compare(Jugador j1, Jugador j2) {
        return j1.getNombre().compareTo(j2.getNombre());
    }
}
