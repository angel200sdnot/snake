/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package juego_vibora;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 *
 * @author jose.ortega
 */
public class Pieza extends JLabel {
    private int fila;
    private int columna;
    private ImageIcon imagenes[][];
    private Color fondo;
    
    //// TIPOS DE PIEZAS ////
    
    // Vibora
    //0 = CABEZA ARRIBA
    //1 = CABEZA DERECHA
    //2 = CABEZA ABAJO
    //3 = CABEZA IZQUIERDA
    //4 = BORDE DERECHA ARRIBA/ABAJO IZQUIERDA
    //5 = BORDE DERECHA ABAJO/ARRIBA IZQUIERDA
    //6 = BORDE IZQUIERDA ARRIBA/ABAJO DERECHA
    //7 = BORDE IZQUIERDA ABAJO/ARRIBA DERECHA
    //8 = CUERPO ARRIBA/ABAJO
    //9 = CUERPO DERECHA/IZQUIERDA
    //10 = COLA ARRIBA
    //11 = COLA DERECHA
    //12 = COLA ABAJO
    //13 = COLA IZQUIERDA
    
    // Tablero
    //14 = PUNTO
    //15 = PARED
    //17 = VACIO
    private int tipo;
    
    private int jugador;
    
    public Pieza(int fila, int columna, ImageIcon imagenes[][]) {
        super();
        this.fila = fila;
        this.columna = columna;
        this.imagenes = imagenes;
        tipo = 17;
        fondo = null;
        jugador = 0;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(imagenes[0][2].getImage(), 0, 0, getWidth(), getHeight(), this);
        
        if (fondo != null) {
            grphcs.setColor(fondo);
            grphcs.fillRect(0, 0, getWidth(), getHeight());
        }
        if (jugador > 0) {
            grphcs.drawImage(imagenes[jugador][tipo].getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        else if (tipo == 14) {
            grphcs.drawImage(imagenes[0][0].getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        else if (tipo == 15) {
            grphcs.drawImage(imagenes[0][1].getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        else if (tipo == 16) {
            grphcs.setColor(Color.green);
            grphcs.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
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
     * @return the fondo
     */
    public Color getFondo() {
        return fondo;
    }

    /**
     * @param fondo the fondo to set
     */
    public void setFondo(Color fondo) {
        this.fondo = fondo;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
        repaint();
    }

    /**
     * @return the jugador
     */
    public int getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    
    
}
