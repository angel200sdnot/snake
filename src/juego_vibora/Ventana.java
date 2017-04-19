/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego_vibora;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author  
 */
public class Ventana extends JFrame {
    
    public Ventana(){
        super();
        Container contenedor = getContentPane();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contenedor.setLayout( new BorderLayout());
        setSize(600,600);
        setTitle("VIBORA");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(pantalla.width/2-this.getSize().width/2, pantalla.height/2-this.getSize().height/2);
        contenedor.add( new Tablero(), BorderLayout.CENTER);
    }
}
