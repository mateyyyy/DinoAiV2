package com.mycompany.redneuronal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class DinoGame extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Cactus cactus = new Cactus();
    private ArrayList<Dino> dinos = new ArrayList();
    private int cantDinos = 1000;
    public int dinoWidth = 40;
    public int cantVivos = 0;
    public int lastCantVivos = 0;
    public float velocidadJuego = 5;
    public int generacion = 0;
    private static final float MAX_VELOCITY = 30f;

    public DinoGame() {
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        
        //Agregamos los dinos
        for(int i=0; i<cantDinos; i++){
            dinos.add(new Dino());
        }

        // 60 FPS -> 1000ms / 60 ≈ 16ms
        timer = new Timer(16, e -> this.actionPerformed(e));
        timer.start();
  
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica del juego (actualizar posiciones, colisiones, etc.)
        cantVivos = 0;
        for(Dino dino: dinos){
            
            if(dino.isAlive()){
                cantVivos++;
                if(dino.isJump()){
                dino.Jump();
                }
                if(dino.isShift()){
                    dino.shift();
                }
                
                calcJump(dino, cactus);
                detectColision(dino, cactus);
                sumarPuntuacion(dino);
            }
            repaint();    
        }
        cactus.Mover(velocidadJuego);
        if(cantVivos > 0){
            if(velocidadJuego<MAX_VELOCITY){
                velocidadJuego = velocidadJuego + 0.01f;
            }
        }
        else{
            this.resetGame();
        }
        if(cantVivos!=lastCantVivos){
            System.out.println("Cantidad vivos : " + cantVivos);
            lastCantVivos = cantVivos;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String cantVivosStr = "Cantidad vivos : " + cantVivos;
        g.drawString(cantVivosStr, 650, 20);
        String generacionStr = "Generacion : " + generacion;
        g.drawString(generacionStr, 650, 40);
        String velocidadStr = "Velocidad : " + velocidadJuego;
        g.drawString(velocidadStr, 650, 60);
        // Dibujado del fondo
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 300, getWidth(), 5); // suelo

        // Ejemplo de dino
        for(Dino dino : dinos){
            if(dino.isAlive()){
                g.drawImage(dino.imagen, dino.posX, dino.posY, dino.width, dino.height, this);
               // g.setColor(dino.color);
                //g.fillRect(dino.posX, dino.posY, dinoWidth, dino.height);
            }
        }
        // Ejemplo de obstáculo
        g.drawImage(cactus.imagen, cactus.posX, cactus.posY, cactus.width, cactus.height, this);

        //g.setColor(Color.RED);
        //g.fillRect(cactus.posX, cactus.posY, cactus.width, cactus.height); // cactus (placeholder)
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            dinos.get(0).setJump();
        }
        
        if (key == KeyEvent.VK_DOWN) {
            dinos.get(0).setShift();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { 
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_DOWN) {
            dinos.get(0).releaseShift();
        }
        }

    @Override
    public void keyTyped(KeyEvent e) { }
    
    public void detectColision(Dino dino,Cactus cactus){
        if((dino.posX + dinoWidth) >= cactus.posX && dino.posX <= (cactus.posX + cactus.width) ){ //verificamos pos X
            if(dino.posY >= (cactus.posY - cactus.height)){
                dino.kill();
            }
        }
    }
    
    public void calcJump(Dino dino, Cactus cactus){
        dino.cerebroCalcSalidas(cactus.posX - dino.posX, velocidadJuego, cactus.height);
    }
    
    public void sumarPuntuacion(Dino dino){
        dino.sumarPuntuacion();
    }
    
    public void ordenarMayor() {
        // Ordenar la lista original de mayor a menor
        Collections.sort(dinos, Comparator.comparingInt(Dino::getPuntuacion).reversed());

        // El primero ahora es el que tiene más puntos
        Dino mayorDino = dinos.get(0);
        System.out.println("El dino con mas puntos fue: " + mayorDino.puntuacion);
    }
    
    public void resetGame(){
        generacion++;
        cactus.Reset();
        ordenarMayor();
        for(Dino dino: dinos) {
            dino.revive();
        }
        //del dino 5 al 19 clono los genes de los primeros 5
        int index = 0; //va del 0 al 4. los top 5 dinos
        for(int i=5 ; i<20; i++){ 
            Dino padre = dinos.get(index);
            Dino hijo = dinos.get(i);
            hijo.cerebro.capas = new ArrayList<>();

            for (Capa capaPadre : padre.cerebro.capas) {
                Capa capaHijo = capaPadre.clonar();
                capaHijo.mutar(0.2f, 0.3f);
                hijo.cerebro.capas.add(capaHijo);
            }
            index++;
            if(index == 5){
                index = 0;
            }
        }
        
        for(int i=20 ; i<45; i++){
            Dino padre = dinos.get(index);
            Dino hijo = dinos.get(i);
            hijo.cerebro.capas = new ArrayList<>();

            for (Capa capaPadre : padre.cerebro.capas) {
                Capa capaHijo = capaPadre.clonar();
                capaHijo.mutar(0.4f, 0.6f);
                hijo.cerebro.capas.add(capaHijo);
            }
            index++;
            if(index == 5){
                index = 0;
            }
        }
        
         for(int i=45; i<cantDinos; i++){
            dinos.get(i).newBrain();
        }
        
        this.velocidadJuego = 5;
        //Calcular los mejores
        
    }
}
