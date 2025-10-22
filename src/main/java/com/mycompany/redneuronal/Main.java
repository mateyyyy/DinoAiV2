/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.redneuronal;

import static java.lang.Math.pow;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author matia
 */
public class Main {

    public static void main(String[] args) {    
        
        
        JFrame frame = new JFrame("Juego del Dino");
        DinoGame game = new DinoGame();

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
    }
}
