/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redneuronal;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author matia
 */
public class Cactus {
    public int height = 40;
    public int width = 20;
    public int posY = 260;
    public int posX = 600;
    static int[] alturasPosibles = {35, 40, 50};
    public Image imagen;
    
    public Cactus(){
        this.height = alturasPosibles[(int)(Math.random() * alturasPosibles.length)];
        imagen = new ImageIcon(getClass().getResource("/images/cactus.png")).getImage();

        this.posY = 300 - this.height;  // para mantenerlo apoyado en el suelo
    }
    
    public void Mover(float velocidad){
        this.posX = this.posX - (int) velocidad;
        if(posX <= -20){
            Reset();
        }
    }
    
    public void Reset(){
        this.posX = 800;
        this.height = alturasPosibles[(int)(Math.random() * alturasPosibles.length)];
        this.posY = 300 - this.height;  // para mantenerlo apoyado en el suelo
    }
    
}
