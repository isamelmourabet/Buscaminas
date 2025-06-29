/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package practicabuscaminas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Grupo G
 */
public class VentanaJugar extends javax.swing.JFrame {

    /**
     * Creates new form VentanaJugar
     */
    private Jugador jugador;
    private Jugador jugadorRival;
    private Partida partida;
    private boolean partida_terminada;
    private Almacen_Usuario almacenUsuarios;
    private Almacen_partidas almacenPartidas;
    private ArrayList<Movimiento> movimientos_partida;
    private Cara_a_cara jRivales = new Cara_a_cara();
    private Cara_a_cara rRivales = new Cara_a_cara();
    private boolean partidaIniciada = false;
    private File archivoJugadores;
    private File archivoPartidas;
    private static final int FILA_COLUMNA = 16;

    /*Inicializamos todos los elementos que se usarán, en el constructor*/
    public VentanaJugar() {
        initComponents();
        almacenUsuarios = new Almacen_Usuario();
        almacenPartidas = new Almacen_partidas();
        nombreTextField.setBackground(new java.awt.Color(0, 0, 0, 1));
        PasswordField.setBackground(new java.awt.Color(0, 0, 0, 1));
        this.jPanel1.setLayout(new GridLayout(16, 16));
        archivoJugadores = new File("jugadores.dat");
        archivoPartidas = new File("partidas.dat");

        try {
            almacenUsuarios.cargaListadoB(archivoJugadores);
            almacenPartidas.cargaListadoB(archivoPartidas);

        } catch (Exception e) {
            System.out.println("ERROR E/S\n");
        }

        partida_terminada = false;

    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        marcadorJ1Label.setText(jugador.getNombre() + ": ");
    }

    /*Método para cargar los botones*/
    public void cargarBotones() {

        marcadorJ1Label.setText("Jugando " + partida.getJugadorActual().getNombre() + ":\t" + String.valueOf(partida.getMarcador().getPuntos_J1()) + " puntos");
        marcadorJ2Label.setText("Esperando " + partida.getJugadorEsperando().getNombre() + ":\t" + String.valueOf(partida.getMarcador().getPuntos_J2()) + " puntos");
        JButton[][] botones = new JButton[FILA_COLUMNA][FILA_COLUMNA];

        /*Inicializamos un tablero de 16x16*/
        for (int i = 0; i < FILA_COLUMNA; i++) {
            for (int j = 0; j < FILA_COLUMNA; j++) {

                final int fila = i;
                final int columna = j;

                botones[i][j] = new JButton();
                /*Cambiamos las dimensiones del botón*/
                botones[i][j].setPreferredSize(new Dimension(37, 37));
                /*Creamos un objeto casilla*/
                Casilla casilla_idv = partida.getTablero().getCasilla(i, j);
                /*Añadimos un listener que detecte una actividad del bóton en este caso es al momento de hacer click*/

                botones[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        /*Revelamos dicho botón en la interfaz en su correspondiente simbolo correspondiente
                        a su coordenada en el tablero descubierto*/
                        botones[fila][columna].setText(casilla_idv.getSimbolo_oculto());

                        /*Si la casilla seleccionada es un espacio en blanco entonces se revelaran los 
                        botones que sean adyacentes*/
                        if ("B".equals(casilla_idv.getSimbolo_oculto())) {

                            ArrayList<Casilla> adyacentes = partida.getTablero().casillasAdyacentes(casilla_idv);
                            Iterator<Casilla> iterator = adyacentes.iterator();

                            while (iterator.hasNext()) {
                                Casilla c = iterator.next();
                                botones[c.getCoordenadaX()][c.getCoordenadaY()].setText(c.getSimbolo_oculto());
                                botones[c.getCoordenadaX()][c.getCoordenadaY()].setEnabled(false);
                            }

                        }

                        /*Invocamos al metodo jugar (el correspondiente a la interfaz gráfica) para jugar una úinica vez
                        esta información se actualizará a medida que va avanzando la partida , se explica como termina 
                        al final del método*/
                        partida.jugar(casilla_idv);
                        marcadorJ1Label.setText("Jugando " + partida.getJugadorActual().getNombre() + ":\t" + String.valueOf(partida.getMarcador().getPuntos_J1()) + " puntos");
                        marcadorJ2Label.setText("Esperando " + partida.getJugadorEsperando().getNombre() + ":\t" + String.valueOf(partida.getMarcador().getPuntos_J2()) + " puntos");

                        /*Cambiamos la fuente , y desabilitamos el boton si esta ya se ha presionado*/
                        Font font = botones[fila][columna].getFont();
                        botones[fila][columna].setFont(font.deriveFont(font.getStyle() | Font.BOLD));

                        botones[fila][columna].setEnabled(false);

                        /*Si el marcador del jugador actual(última persona en presionar el botón ganador) es justamente 26 entonces
                        la partida terminará y hará las siguientes cosas 
                         */
                        if (partida.getMarcador().getPuntos_J1() == 26) {

                            /*Actualiza la información de los jugadores tanto el actual como el que perdió*/
                            partida.getJugadorActual().getEstadisticaJugador().incrementarGanadas();
                            partida.getJugadorActual().getEstadisticaJugador().incrementarJugadas();
                            partida.getJugadorEsperando().getEstadisticaJugador().incrementarPerdidas();
                            partida.getJugadorEsperando().getEstadisticaJugador().incrementarJugadas();

                            /*Da de baja al jugador actual para darlo de alta nuevamente con la información actalizada*/
                            almacenUsuarios.baja(partida.getJugadorActual());
                            almacenUsuarios.alta(partida.getJugadorActual());
                            almacenUsuarios.baja(partida.getJugadorEsperando());
                            almacenUsuarios.alta(partida.getJugadorEsperando());

                            /*Añadimos un jugador al hashset de Cara a Cara de rivales*/
                            jRivales.addJugadores(jugadorRival);
                            rRivales.addJugadores(jugador);
                            /*Añadir el hashset de Cara a Cara al jugador*/
                            jugador.getRivales().add(jRivales);
                            jugadorRival.getRivales().add(rRivales);

                            /*Comprobamos que la partida esta terminada en este caso se añade al almacen de partida dichas partidas 
                            ademas escribe un fichero txt de está partida (la mas reciente)*/
                            partida_terminada = true;
                            almacenPartidas.anadirPartida(partida);
                            escribirFichero();

                            resultadoLabel1.setText("Vencedor: " + partida.getJugadorActual().getNombre());
                            jLabel3.setText("Partida terminada:");
                            partidaIniciada = false;
                            //Desabilita todos los botones si la partida termina 
                            for (int k = 0; k < FILA_COLUMNA; k++) {
                                for (int l = 0; l < FILA_COLUMNA; l++) {
                                    botones[k][l].setText(partida.getTablero().getCasilla(k, l).getSimbolo_oculto());
                                    botones[k][l].setEnabled(false);
                                }
                            }

                        }
                    }
                });

                this.jPanel1.add(botones[i][j]);

            }
        }

    }

    /*Escribe en un fichero txt el tablero y los movimientos de una partida*/
    public void escribirFichero() {
        if (partida_terminada) {
            File f = new File("infopartida.txt");
            try {
                almacenPartidas.tablero_movimientos(partida, f);
            } catch (IOException ex) {
                Logger.getLogger(VentanaJugar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Almacen_Usuario getAlmacenUsuarios() {
        return almacenUsuarios;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        partidaLabel1 = new javax.swing.JLabel();
        nombreLabel = new javax.swing.JLabel();
        contrasenaLabel = new javax.swing.JLabel();
        nombreTextField = new javax.swing.JTextField();
        iniciarSesionButton = new javax.swing.JButton();
        marcadorLabel = new javax.swing.JLabel();
        marcadorJ1Label = new javax.swing.JLabel();
        marcadorJ2Label = new javax.swing.JLabel();
        rivalLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        iniciarPartidaButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        resultadoLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        partidaMenu = new javax.swing.JMenu();
        salirMenuItem1 = new javax.swing.JMenuItem();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(200, 250));
        setResizable(false);

        partidaLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        partidaLabel1.setText("PARTIDA");

        nombreLabel.setText("Nombre");

        contrasenaLabel.setText("Contraseña");

        nombreTextField.setBorder(null);
        nombreTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreTextFieldActionPerformed(evt);
            }
        });

        iniciarSesionButton.setBackground(new java.awt.Color(0, 153, 255));
        iniciarSesionButton.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        iniciarSesionButton.setForeground(new java.awt.Color(255, 255, 255));
        iniciarSesionButton.setText("Iniciar sesión como Rival");
        iniciarSesionButton.setBorder(null);
        iniciarSesionButton.setBorderPainted(false);
        iniciarSesionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarSesionButtonActionPerformed(evt);
            }
        });

        marcadorLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        marcadorLabel.setText("MARCADOR");

        marcadorJ1Label.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        marcadorJ2Label.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        rivalLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        rivalLabel.setText("RIVAL");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        iniciarPartidaButton.setBackground(new java.awt.Color(0, 153, 255));
        iniciarPartidaButton.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        iniciarPartidaButton.setForeground(new java.awt.Color(255, 255, 255));
        iniciarPartidaButton.setText("Iniciar Partida");
        iniciarPartidaButton.setBorder(null);
        iniciarPartidaButton.setBorderPainted(false);
        iniciarPartidaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarPartidaButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 10)); // NOI18N

        PasswordField.setBorder(null);
        PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordFieldActionPerformed(evt);
            }
        });

        jPanel1.setMaximumSize(new java.awt.Dimension(32760, 32767));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        resultadoLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        partidaMenu.setText("Partida");

        salirMenuItem1.setText("Salir");
        salirMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirMenuItem1ActionPerformed(evt);
            }
        });
        partidaMenu.add(salirMenuItem1);

        jMenuBar1.add(partidaMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(246, 246, 246)
                                .addComponent(partidaLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(iniciarPartidaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(rivalLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(nombreLabel)
                        .addGap(44, 44, 44)
                        .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(contrasenaLabel)
                        .addGap(24, 24, 24)
                        .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(iniciarSesionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(marcadorLabel))
                    .addComponent(marcadorJ1Label, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(marcadorJ2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultadoLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(partidaLabel1)
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(iniciarPartidaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(487, 487, 487)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rivalLabel)
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombreLabel)
                                    .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contrasenaLabel)
                                    .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(iniciarSesionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(marcadorLabel)
                                .addGap(17, 17, 17)
                                .addComponent(marcadorJ1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(marcadorJ2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(resultadoLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(224, 224, 224))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(10, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreTextFieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_nombreTextFieldActionPerformed

    private void iniciarSesionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarSesionButtonActionPerformed

        /*Creamos un objeto jugadorRival con los datos pasados por Jfield y comprobamos que este en el almacen 
        si lo está, entonces nos devuelve ese jugador , caso contrario se controla los distintos eventos como por ejemplo:
        No poder jugar con uno mismo , usuario inexistente o sesión iniciada en el caso de que el jugador este autenticado*/
        String nombreRival = nombreTextField.getText();
        char[] arrayContrasenaRival = PasswordField.getPassword();
        String contrasenaRival = new String(arrayContrasenaRival);

        jugadorRival = new Jugador(nombreRival, contrasenaRival);

        if (almacenUsuarios.autenticar(jugadorRival) && !jugadorRival.equals(jugador)) {
            ArrayList<Jugador> jugadores = almacenUsuarios.getJugadores();
            int i = jugadores.indexOf(jugadorRival);
            jugadorRival = jugadores.get(i);

            marcadorJ2Label.setText(jugadorRival.getNombre() + ": ");
            jLabel2.setText("sesión iniciada");
            nombreTextField.setText("");
            PasswordField.setText("");

        } else if (jugadorRival.equals(jugador)) {
            jLabel2.setText("No se puede jugar contra uno mismo");
            nombreTextField.setText("");
            PasswordField.setText("");
        } else {
            jLabel2.setText("Usuario inexistente");
            nombreTextField.setText("");
            PasswordField.setText("");
        }
    }//GEN-LAST:event_iniciarSesionButtonActionPerformed

    private void iniciarPartidaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarPartidaButtonActionPerformed
        /*Botón para poder iniciar la partida , verifica que existan dos usuarios , caso contrario no se puede jugar con 1 jugador*/
        if ("".equals(marcadorJ2Label.getText())) {
            jLabel3.setText("No se puede iniciar partida con 1 jugador");
        } else {
            partida = new Partida(jugador, jugadorRival);
            cargarBotones();
            iniciarPartidaButton.setEnabled(false);
            iniciarSesionButton.setEnabled(false);
            jLabel3.setText("Partida iniciada:");
            jLabel2.setText("");
            partidaIniciada = true;

        }

    }//GEN-LAST:event_iniciarPartidaButtonActionPerformed

    private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordFieldActionPerformed

    private void salirMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirMenuItem1ActionPerformed
        /*Al salir del menú se guardan en el archivo binario los datos relacionados con los usuarios y tambien de las partidas*/
        try {

            almacenUsuarios.guardarListadoB(archivoJugadores);
            almacenPartidas.guardarListadoB(archivoPartidas);
        } catch (IOException ex) {
            System.out.println("No se pudo guardar el listado");
        }
        VentanaUser ventanaUser = new VentanaUser();
        ventanaUser.setJugador(jugador);
        ventanaUser.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_salirMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJugar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJugar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JLabel contrasenaLabel;
    private javax.swing.JButton iniciarPartidaButton;
    private javax.swing.JButton iniciarSesionButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel marcadorJ1Label;
    private javax.swing.JLabel marcadorJ2Label;
    private javax.swing.JLabel marcadorLabel;
    private javax.swing.JLabel nombreLabel;
    private javax.swing.JTextField nombreTextField;
    private javax.swing.JLabel partidaLabel1;
    private javax.swing.JMenu partidaMenu;
    private javax.swing.JLabel resultadoLabel1;
    private javax.swing.JLabel rivalLabel;
    private javax.swing.JMenuItem salirMenuItem1;
    // End of variables declaration//GEN-END:variables
}
