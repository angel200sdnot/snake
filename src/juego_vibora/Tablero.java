/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package juego_vibora;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.*;

/**
 *
 * @author jose.ortega
 */
public class Tablero extends JPanel {
    private JPanel panel_central;
    private JPanel panel_inferior;
    private JPanel panel_superior;
    private Configuraciones configuraciones;
    private JButton comenzar_btn;
    private JLabel mensaje_txt;
    private int filas;
    private int columnas;
    private ImageIcon imagenes[][];
    private Pieza piezas[][];
    private Vibora viboras[];
    private ExecutorService threadPool;
    private boolean fin_juego;

    /**
     * Creates new form Tablero
     */
    public Tablero() {
        super();

        panel_central = new JPanel();
        panel_superior = new JPanel();
        panel_inferior = new JPanel();
        configuraciones = new Configuraciones();
        comenzar_btn = new JButton();
        mensaje_txt = new JLabel();
        
        setLayout(new BorderLayout());

        comenzar_btn.setText("Comenzar");
        comenzar_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comenzar_btnActionPerformed(evt);
            }
        });
        panel_superior.add(comenzar_btn);
        add(panel_superior, BorderLayout.PAGE_START);
        
        add(panel_central, BorderLayout.CENTER);
        
        panel_inferior.add(mensaje_txt);
        add(panel_inferior, BorderLayout.PAGE_END);
        
        cargar_imagenes();
        asignar_teclas();
        
        panel_central.setLayout(new GridLayout(1, 1));
        panel_central.add(configuraciones);
        
        mensaje_txt.setText("Ajuste la configuración del juego");
        
        threadPool = Executors.newCachedThreadPool();
    }
    
    private void asignar_teclas() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "INICIAR_JUEGO");
        
        //JUGADOR 1
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "MOVER_ARRIBA_1");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "MOVER_DERECHA_1");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "MOVER_ABAJO_1");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "MOVER_IZQUIERDA_1");
        
        //JUGADOR 2
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), "MOVER_ARRIBA_2");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "MOVER_DERECHA_2");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "MOVER_ABAJO_2");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "MOVER_IZQUIERDA_2");
        
        //JUGADOR 3
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('i'), "MOVER_ARRIBA_3");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('l'), "MOVER_DERECHA_3");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('k'), "MOVER_ABAJO_3");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('j'), "MOVER_IZQUIERDA_3");
        
        //JUGADOR 4
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD8"), "MOVER_ARRIBA_4");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD6"), "MOVER_DERECHA_4");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD5"), "MOVER_ABAJO_4");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD4"), "MOVER_IZQUIERDA_4");
        
        //JUGADOR 5
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('t'), "MOVER_ARRIBA_5");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('h'), "MOVER_DERECHA_5");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('g'), "MOVER_ABAJO_5");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('f'), "MOVER_IZQUIERDA_5");
        
    }
    
    private void cargar_imagenes() {
        imagenes = new ImageIcon[6][];
        
        imagenes[0] = new ImageIcon[3];
        imagenes[0][0] = new ImageIcon(getClass().getResource("/imagenes/manzana.png"));
        imagenes[0][1] = new ImageIcon(getClass().getResource("/imagenes/pared.png"));
        imagenes[0][2] = new ImageIcon(getClass().getResource("/imagenes/grama.png"));
        
        for (int i = 1; i < 6; i++) {
            imagenes[i] = new ImageIcon[14];
            imagenes[i][0] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cabeza_0.png"));
            imagenes[i][1] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cabeza_1.png"));
            imagenes[i][2] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cabeza_2.png"));
            imagenes[i][3] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cabeza_3.png"));
            imagenes[i][4] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/borde_0.png"));
            imagenes[i][5] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/borde_1.png"));
            imagenes[i][6] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/borde_2.png"));
            imagenes[i][7] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/borde_3.png"));
            imagenes[i][8] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cuerpo_0.png"));
            imagenes[i][9] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cuerpo_1.png"));
            imagenes[i][10] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cola_0.png"));
            imagenes[i][11] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cola_1.png"));
            imagenes[i][12] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cola_2.png"));
            imagenes[i][13] = new ImageIcon(getClass().getResource("/imagenes/jugador_" + (i) + "/cola_3.png"));
        }
    }
    
    private void cargar_piezas() {
        filas = columnas = configuraciones.getTablero_size();
        piezas = new Pieza[filas][columnas];
        panel_central.removeAll();
        panel_central.removeAll();
        panel_central.repaint();
        panel_central.setLayout(new GridLayout(filas, columnas));
        for (int i = 0; i < piezas.length; i++) {
            for (int j = 0; j < piezas[0].length; j++) {
                piezas[i][j] = new Pieza(i, j, imagenes);
                if (configuraciones.isBordes() && (i == 0 || j == 0 || i == piezas.length-1 || j == piezas[0].length-1)) {
                    piezas[i][j].setTipo(15);
                }
                panel_central.add(piezas[i][j]);
            }
        }
        panel_central.validate();
    }
    
    private void cargar_vibora(int jugador) {
        ArrayList<Pieza> cuerpo = new ArrayList();
        int cuerpo_inicial = configuraciones.getCuerpo();
        int fila_inicial = 3*(filas/20)*jugador;
        int columna_inicial = columnas-cuerpo_inicial-3;
        cuerpo.add(piezas[fila_inicial][columna_inicial]);
        cuerpo.get(0).setJugador(jugador);
        cuerpo.get(0).setTipo(3);
        for(int i = 1; i < cuerpo_inicial-1; i++) {
            cuerpo.add(piezas[fila_inicial][columna_inicial + i]);
            cuerpo.get(i).setJugador(jugador);
            cuerpo.get(i).setTipo(9);
        }
        cuerpo.add(piezas[fila_inicial][columna_inicial + cuerpo_inicial-1]);
        cuerpo.get(cuerpo_inicial-1).setJugador(jugador);
        cuerpo.get(cuerpo_inicial-1).setTipo(13);
        viboras[jugador-1] = new Vibora("Jugador " + jugador, jugador, cuerpo, 3, configuraciones.getVelocidad(), this);
        getActionMap().put("MOVER_ARRIBA_" + jugador, new CambiarDireccion(viboras[jugador-1], 0));
        getActionMap().put("MOVER_DERECHA_" + jugador, new CambiarDireccion(viboras[jugador-1], 1));
        getActionMap().put("MOVER_ABAJO_" + jugador, new CambiarDireccion(viboras[jugador-1], 2));
        getActionMap().put("MOVER_IZQUIERDA_" + jugador, new CambiarDireccion(viboras[jugador-1], 3));
    }
    
    public void generar_punto() {
        boolean generando = true;
        int numero;
        Pieza pieza;
        while (generando) {
            numero = aleatorio(0, (filas*columnas)-1);
            pieza = piezas[numero/columnas][numero%columnas];
            if (pieza.getTipo() == 17) {
                pieza.setTipo(14);
                generando = false;
            }
        }
    }
    
    public void cargar_juego(int jugadores) {
        viboras = new Vibora[jugadores];
        
        fin_juego = false;
        
        cargar_piezas();

        for (int i = 0; i < jugadores; i++) {
            cargar_vibora(i+1);
        }
        
        generar_punto();
        
        getActionMap().put("INICIAR_JUEGO", new IniciarJuego());
        mensaje_txt.setText("Presione la barra espaciadora para iniciar el juego");
    }
    
    public void reiniciar() {
        viboras = null;
        panel_central.removeAll();
        panel_central.setLayout(new GridLayout(1, 1));
        panel_central.add(configuraciones);
        comenzar_btn.setEnabled(true);
        mensaje_txt.setText("Ajuste la configuración del juego");
        panel_central.repaint();
    }
    
    public void eliminar_jugador(int jugador) {
        int jugadores = configuraciones.getJugadores();
        if (jugadores > 1) {
            int detenidos = 0;
            int destruyendose = 0;
            Vibora viva = null;
            for (int i = 0; i < jugadores; i++) {
                if (viboras[i].isDetenido()) {
                    detenidos++;
                }
                if (viboras[i].isDestruyendo()) {
                    destruyendose++;
                }
                else {
                    viva = viboras[i];
                }
            }
            if (detenidos == jugadores) {
                if (!fin_juego) {
                    fin_juego = true;
                    JOptionPane.showMessageDialog(this, "Empate", "JUEGO FINALIZADO", JOptionPane.INFORMATION_MESSAGE);
                    reiniciar();
                }
            }
            if (detenidos == jugadores-1 && destruyendose != jugadores) {
                if (!fin_juego) {
                    fin_juego = true;
                    viva.setPausado(true);
                    JOptionPane.showMessageDialog(this, "El jugador " + viva.getJugador() + " ha ganado el juego!", "JUEGO FINALIZADO", JOptionPane.INFORMATION_MESSAGE);
                    reiniciar();
                }
            }
        }
        else {
            if (!fin_juego) {
                fin_juego = true;
                JOptionPane.showMessageDialog(this, "Manzanas: " + viboras[jugador-1].getManzanas(), "JUEGO FINALIZADO", JOptionPane.INFORMATION_MESSAGE);
                reiniciar();
            }
        }
    }
    
    private class IniciarJuego extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < configuraciones.getJugadores(); i++) {
                threadPool.execute(viboras[i]);
            }
            if (configuraciones.getJugadores() > 1) {
                mensaje_txt.setText("Sobrevive para ganar");
            }
            else {
                mensaje_txt.setText("Obtén la mayor cantidad de manzanas");
            }
            getActionMap().remove("INICIAR_JUEGO");
        }
        
    }
    
    private class CambiarDireccion extends AbstractAction {
        private Vibora vibora;
        private int direccion;
        
        CambiarDireccion(Vibora vibora, int direccion) {
            this.vibora = vibora;
            this.direccion = direccion;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (direccion != vibora.getDireccion()) {
                switch (direccion) {
                    case 0:
                        if (vibora.getDireccion() != 2) vibora.cambiar_direccion(0);
                        break;
                    case 1:
                        if (vibora.getDireccion() != 3) vibora.cambiar_direccion(1);
                        break;
                    case 2:
                        if (vibora.getDireccion() != 0) vibora.cambiar_direccion(2);
                        break;
                    case 3:
                        if (vibora.getDireccion() != 1) vibora.cambiar_direccion(3);
                }
            }
        }

        /**
         * @return the vibora
         */
        public Vibora getVibora() {
            return vibora;
        }

        /**
         * @param vibora the vibora to set
         */
        public void setVibora(Vibora vibora) {
            this.vibora = vibora;
        }

        /**
         * @return the direccion
         */
        public int getDireccion() {
            return direccion;
        }

        /**
         * @param direccion the direccion to set
         */
        public void setDireccion(int direccion) {
            this.direccion = direccion;
        }
        
    }
    
    public static int aleatorio(int a, int b) {
        return (int) (Math.random() * (b - a + 1) + a);
    }             

    private void comenzar_btnActionPerformed(java.awt.event.ActionEvent evt) {                                             
        cargar_juego(configuraciones.getJugadores());
        comenzar_btn.setEnabled(false);
    }                                                      

    /**
     * @return the panel_central
     */
    public JPanel getPanel_central() {
        return panel_central;
    }

    /**
     * @param panel_central the panel_central to set
     */
    public void setPanel_central(JPanel panel_central) {
        this.panel_central = panel_central;
    }

    /**
     * @return the panel_inferior
     */
    public JPanel getPanel_inferior() {
        return panel_inferior;
    }

    /**
     * @param panel_inferior the panel_inferior to set
     */
    public void setPanel_inferior(JPanel panel_inferior) {
        this.panel_inferior = panel_inferior;
    }

    /**
     * @return the panel_superior
     */
    public JPanel getPanel_superior() {
        return panel_superior;
    }

    /**
     * @param panel_superior the panel_superior to set
     */
    public void setPanel_superior(JPanel panel_superior) {
        this.panel_superior = panel_superior;
    }

    /**
     * @return the configuraciones
     */
    public Configuraciones getConfiguraciones() {
        return configuraciones;
    }

    /**
     * @param configuraciones the configuraciones to set
     */
    public void setConfiguraciones(Configuraciones configuraciones) {
        this.configuraciones = configuraciones;
    }

    /**
     * @return the comenzar_btn
     */
    public JButton getComenzar_btn() {
        return comenzar_btn;
    }

    /**
     * @param comenzar_btn the comenzar_btn to set
     */
    public void setComenzar_btn(JButton comenzar_btn) {
        this.comenzar_btn = comenzar_btn;
    }

    /**
     * @return the mensaje_txt
     */
    public JLabel getMensaje_txt() {
        return mensaje_txt;
    }

    /**
     * @param mensaje_txt the mensaje_txt to set
     */
    public void setMensaje_txt(JLabel mensaje_txt) {
        this.mensaje_txt = mensaje_txt;
    }

    /**
     * @return the filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @param filas the filas to set
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }

    /**
     * @return the columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * @return the imagenes
     */
    public ImageIcon[][] getImagenes() {
        return imagenes;
    }

    /**
     * @param imagenes the imagenes to set
     */
    public void setImagenes(ImageIcon[][] imagenes) {
        this.imagenes = imagenes;
    }

    /**
     * @return the piezas
     */
    public Pieza[][] getPiezas() {
        return piezas;
    }

    /**
     * @param piezas the piezas to set
     */
    public void setPiezas(Pieza[][] piezas) {
        this.piezas = piezas;
    }

    /**
     * @return the viboras
     */
    public Vibora[] getViboras() {
        return viboras;
    }

    /**
     * @param viboras the viboras to set
     */
    public void setViboras(Vibora[] viboras) {
        this.viboras = viboras;
    }

    /**
     * @return the threadPool
     */
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    /**
     * @param threadPool the threadPool to set
     */
    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    /**
     * @return the fin_juego
     */
    public boolean isFin_juego() {
        return fin_juego;
    }

    /**
     * @param fin_juego the fin_juego to set
     */
    public void setFin_juego(boolean fin_juego) {
        this.fin_juego = fin_juego;
    }

   
 }