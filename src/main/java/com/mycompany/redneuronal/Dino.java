/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redneuronal;
import java.awt.Color;
import java.awt.Image;
import java.lang.Math;
import static java.lang.Math.pow;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author matia
 */
public class Dino {
    public int height = 40;
    public int width = 40;
    public int posY = 260;
    public int posX = 100;
    public int lastXFuncJump = 0;
    public boolean alive = true;
    public RedNeuronal cerebro;
    public int puntuacion = 0;
    private boolean jump = false;
    private boolean shift = false;
    public Color color;
    public Image imagen;
    
    private static final float MAX_DISTANCE = 800f;
    private static final float MAX_VELOCITY = 30f;
    private static final float MIN_VELOCITY = 5f;
    private static final float MAX_HEIGHT_CACTUS = 80f;
    private static final float MIN_HEIGHT_CACTUS = 40f;
    
    public Dino(){
        cerebro = new RedNeuronal(3, 6);
        this.cerebro.AddCapa(6);
        cerebro.AddCapa(2);
        imagen = new ImageIcon(getClass().getResource("/images/dino.png")).getImage();
        this.color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
    }
    
    public boolean isJump(){ 
        return this.jump;
    }
    
    public boolean isShift(){ 
        return this.shift;
    }
    
    public void Jump(){
        this.posY = 260 - funcionJump(this.lastXFuncJump);
        this.lastXFuncJump++;
        if(this.posY > 260){
            this.jump = false;
            this.lastXFuncJump = 0;
            this.posY = 260;
        }
    }
    
    public void setJump(){
        this.jump = true;
    }
    
    public int funcionJump(int y){
        int resultado = (int) - pow((y/1.5) - Math.sqrt(100.0),2.0) + 100;
        return resultado;
    }
    
    public void kill(){
        this.alive = false;
    }
    
    public void setShift() {
        this.shift = true;
        this.jump = false;
        this.lastXFuncJump = 0;
    }
    
   
    
     public void releaseShift() {
        this.shift = false;
        this.height = 40;
        imagen = new ImageIcon(getClass().getResource("/images/dino.png")).getImage();
        if(posY==280){
            posY=260;
        }
    }

    public void shift() {
        if (shift) {
            if (posY < 280) posY += 30;
            if (posY > 280) {
                posY = 280;
                shift = false;
            }
            this.height = 20;
            imagen = new ImageIcon(getClass().getResource("/images/dinoCrouch.png")).getImage();

        } else {
            if (posY > 260) posY -= 5;
            if (posY < 260) posY = 260;
        }
    }


    public boolean isAlive(){
        return this.alive;
    }
    
    public void cerebroCalcSalidas(int distance, float velocidad, int heightCac){
        float distNorm = Math.min(1.0f, (float)distance / MAX_DISTANCE);
        float velNorm = (velocidad - MIN_VELOCITY) / (MAX_VELOCITY - MIN_VELOCITY);
        velNorm = Math.max(0f, Math.min(1f, velNorm)); // Clamp     
        float heightNorm = (heightCac - MIN_HEIGHT_CACTUS) / (MAX_HEIGHT_CACTUS - MIN_HEIGHT_CACTUS);
        heightNorm = Math.max(0f, Math.min(1f, heightNorm)); // Clamp
        
        ArrayList<Float> entradas = new ArrayList();
        entradas.add((float) distNorm);
        entradas.add(velNorm);
        entradas.add((float) heightNorm);
        
        ArrayList<Float> salidas = new ArrayList();
        salidas = cerebro.calcSalida(entradas);
        
        if (salidas.get(0) > 0.5) {
            this.jump = true;
        }
        if (salidas.get(1) > 0.5) {
            setShift();
        }
        if (salidas.get(1) < 0.5 && posY==280) {
            releaseShift();
        }
        
    }
    
    public void sumarPuntuacion(){
        puntuacion++;
    }
    
    public void revive(){
        puntuacion = 0;
        alive = true;
        posY = 260;
        posX = 100; 
        jump = false;
    }
    
    public void newBrain(){
        this.cerebro = new RedNeuronal(3, 6);
        this.cerebro.AddCapa(6);
        this.cerebro.AddCapa(2);
        
    }
    
    public RedNeuronal reproduce(){
        RedNeuronal cerebroClon = cerebro;
        
        return cerebroClon;
    }
    
    public int getPuntuacion(){
        return this.puntuacion;
    }
}
