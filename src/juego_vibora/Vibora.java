/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package juego_vibora;
import java.util.ArrayList;

/**
 *
 * @author jose.ortega
 */
public class Vibora extends Thread {
    private ArrayList<Pieza> cuerpo;
    //Direcciones:
    //0 = ARRIBA
    //1 = DERECHA
    //2 = ABAJO
    //3 = IZQUIERDA
    private int direccion;
    private int direccion_anterior;
    private int velocidad;
    private Tablero tablero;
    private boolean detenido, pausado;
    private boolean direccion_cambiada;
    private int longitud;
    private boolean destruyendo;
    private int jugador;
    private int manzanas;
    
    public Vibora(String id, int jugador, ArrayList<Pieza> cuerpo, int direccion, int velocidad, Tablero tablero) {
        super(id);
        this.jugador = jugador;
        this.cuerpo = cuerpo;
        longitud = cuerpo.size();
        this.direccion = direccion;
        direccion_anterior = direccion;
        direccion_cambiada = false;
        this.velocidad = velocidad;
        this.tablero = tablero;
        detenido = false;
        pausado = false;
        destruyendo = false;
        manzanas = 0;
    }

    @Override
    public void run() {
        while (!detenido) {
            if (!pausado) {
                try {
                    if (!destruyendo) {
                        mover(direccion);
                    } else {
                        destruir();
                    }
                    Thread.sleep(1000/velocidad);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }
    
    public void destruir() {
        int tipo_cola = cuerpo.get(longitud-1).getTipo();
        cuerpo.get(longitud-1).setJugador(0);
        cuerpo.get(longitud-1).setTipo(17);
        cuerpo.remove(longitud-1);
        if (longitud >= 3) {
            cuerpo.get(longitud-2).setTipo(buscar_tipo_cola(tipo_cola));
        }
        longitud--;
        if (longitud == 0) {
            setDetenido(true);
            tablero.eliminar_jugador(jugador);
        }
    }
    
    private void mover(int direccion) {
        Pieza aux = cuerpo.get(0);
        int fila = aux.getFila(), columna = aux.getColumna();
        switch (direccion) {
            case 0:
                fila -= 1;
                break;
            case 1:
                columna += 1;
                break;
            case 2:
                fila += 1;
                break;
            case 3:
                columna -= 1;
                break;
            default:
                return;
        }
        Pieza piezas[][] = tablero.getPiezas();
        if (tablero.getConfiguraciones().isBordes()) {
            if (fila == 0 || columna == 0 || fila == piezas.length-1 || columna == piezas[0].length-1) {
                destruyendo = true;
            }
        }
        else {
            if (fila == -1) {
                fila = piezas.length-1;
            }
            else if (columna == -1) {
                columna = piezas[0].length-1;
            }
            else if (fila == piezas.length) {
                fila = 0;
            }
            else if (columna == piezas[0].length) {
                columna = 0;
            }
        }
        if (!detenido && !pausado && !destruyendo) {
            aux = piezas[fila][columna];
            switch (aux.getTipo()) {
                case 0:
                    destruyendo = true;
                    tablero.getViboras()[aux.getJugador()-1].setDestruyendo(true);
                    break;
                case 14:
                    aux.setJugador(jugador);
                    aux.setTipo(direccion);
                    cuerpo.add(0, aux);
                    if (direccion != direccion_anterior) {
                        cuerpo.get(1).setTipo(buscar_tipo_borde(direccion));
                    }
                    else {
                        cuerpo.get(1).setTipo(buscar_tipo_cuerpo(direccion));
                    }
                    tablero.generar_punto();
                    longitud++;
                    manzanas++;
                    if (tablero.getConfiguraciones().isAumento_velocidad()) {
                        velocidad++;
                    }
                    break;
                case 10:
                case 11:
                case 12:
                case 13:
                    if (cuerpo.get(longitud-1) != aux) {
                        destruyendo = true;
                        break;
                    }
                case 17:
                    int tipo_cola = cuerpo.get(longitud-1).getTipo();
                    cuerpo.get(longitud-1).setJugador(0);
                    cuerpo.get(longitud-1).setTipo(17);
                    cuerpo.remove(longitud-1);
                    cuerpo.get(longitud-2).setTipo(buscar_tipo_cola(tipo_cola));
                    if (direccion != direccion_anterior) {
                        cuerpo.get(0).setTipo(buscar_tipo_borde(direccion));
                    }
                    else {
                        cuerpo.get(0).setTipo(buscar_tipo_cuerpo(direccion)); 
                    }
                    cuerpo.add(0, piezas[fila][columna]);
                    cuerpo.get(0).setJugador(jugador);
                    cuerpo.get(0).setTipo(direccion);
                    break;
                default:
                    destruyendo = true;
            }
            direccion_anterior = direccion;
            direccion_cambiada = false;
        }
    }
    
    public void cambiar_direccion(int direccion) {
        if (!direccion_cambiada) {
            direccion_anterior = this.direccion;
            this.direccion = direccion;
            direccion_cambiada = true;
        }
    }
    
    private int buscar_tipo_borde(int direccion) {
        switch (direccion) {
            case 0:
                if (direccion_anterior == 1) {
                    return 4;
                }
                else {
                    return 6;
                }
            case 1:
                if (direccion_anterior == 0) {
                    return 7;
                }
                else {
                    return 6;
                }
            case 2:
                if (direccion_anterior == 1) {
                    return 5;
                }
                else {
                    return 7;
                }
            case 3:
                if (direccion_anterior == 0) {
                    return 5;
                }
                else {
                    return 4;
                }
        }
        return 0;
    }
    
    private int buscar_tipo_cuerpo(int direccion) {
        if (direccion == 0 || direccion == 2) {
            return 8;
        }
        else {
            return 9;
        }
    }
    
    private int buscar_tipo_cola(int tipo_actual) {
        int tipo_siguiente = cuerpo.get(longitud-2).getTipo();
        switch (tipo_actual) {
            case 10:
                switch (tipo_siguiente) {
                    case 0:
                    case 8:
                        return 10;
                    case 5:
                        return 13;
                    case 7:
                        return 11;
                }
            case 11:
                switch (tipo_siguiente) {
                    case 1:
                    case 9:
                        return 11;
                    case 4:
                        return 10;
                    case 5:
                        return 12;
                }
            case 12:
                switch (tipo_siguiente) {
                    case 2:
                    case 8:
                        return 12;
                    case 4:
                        return 13;
                    case 6:
                        return 11;
                }
            case 13:
                switch (tipo_siguiente) {
                    case 3:
                    case 9:
                        return 13;
                    case 6:
                        return 10;
                    case 7:
                        return 12;
                }
        }
        return 0;
    }

    /**
     * @return the cuerpo
     */
    public ArrayList<Pieza> getCuerpo() {
        return cuerpo;
    }

    /**
     * @param cuerpo the cuerpo to set
     */
    public void setCuerpo(ArrayList<Pieza> cuerpo) {
        this.cuerpo = cuerpo;
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

    /**
     * @return the direccion_anterior
     */
    public int getDireccion_anterior() {
        return direccion_anterior;
    }

    /**
     * @param direccion_anterior the direccion_anterior to set
     */
    public void setDireccion_anterior(int direccion_anterior) {
        this.direccion_anterior = direccion_anterior;
    }

    /**
     * @return the velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * @param velocidad the velocidad to set
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * @return the tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * @param tablero the tablero to set
     */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * @return the detenido
     */
    public boolean isDetenido() {
        return detenido;
    }

    /**
     * @param detenido the detenido to set
     */
    public void setDetenido(boolean detenido) {
        this.detenido = detenido;
    }

    /**
     * @return the pausado
     */
    public boolean isPausado() {
        return pausado;
    }

    /**
     * @param pausado the pausado to set
     */
    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }

    /**
     * @return the direccion_cambiada
     */
    public boolean isDireccion_cambiada() {
        return direccion_cambiada;
    }

    /**
     * @param direccion_cambiada the direccion_cambiada to set
     */
    public void setDireccion_cambiada(boolean direccion_cambiada) {
        this.direccion_cambiada = direccion_cambiada;
    }

    /**
     * @return the longitud
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the destruyendo
     */
    public boolean isDestruyendo() {
        return destruyendo;
    }

    /**
     * @param destruyendo the destruyendo to set
     */
    public void setDestruyendo(boolean destruyendo) {
        this.destruyendo = destruyendo;
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

    /**
     * @return the manzanas
     */
    public int getManzanas() {
        return manzanas;
    }

    /**
     * @param manzanas the manzanas to set
     */
    public void setManzanas(int manzanas) {
        this.manzanas = manzanas;
    }
}
