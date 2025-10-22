package com.mycompany.redneuronal;


import static java.lang.Math.random;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matia
 */
public class RedNeuronal {
    public ArrayList<Capa> capas = new ArrayList();
    private int lastCapa = 0;
    
     
    public RedNeuronal(int entradas, int cantFirstCapa){
        this.AddCapa(cantFirstCapa, entradas); //se crea una primera capa
    }
    
    private void AddCapa(int cantNeuronas, int cantEntradas){
        Capa capa = new Capa();
        for(int i=0; i<cantNeuronas; i++){
            ArrayList<Float> weightList = new ArrayList<>();
            for(int j=0; j<cantEntradas; j++){
                weightList.add( (float) (Math.random() * 2 - 1));
            }
        capa.addNeurona(weightList, (float) (Math.random() * 2 - 1));
        }
        capas.add(capa);
        lastCapa++;
    }
    
    public void AddCapa(int cantNeuronas){
        Capa capa = new Capa();
        for(int i=0; i<cantNeuronas; i++){
            ArrayList<Float> weightList = new ArrayList<>();
            for(int j=0; j<capas.get(lastCapa-1).WeightMatriz.size() ; j++){
                weightList.add((float) (Math.random() * 2 - 1));
            }
            capa.addNeurona(weightList, (float) (Math.random() * 2 - 1));
        }
        capas.add(capa);
        lastCapa++;
    }
    
    public ArrayList<Float> calcSalida(ArrayList<Float> entradas){
        //Calculo salida de primera capa con las entradas de parametros
       
        capas.get(0).calcActivacion(entradas, 0);
        for(int i=1; i<capas.size(); i++){
            if(i==capas.size()-1){
                capas.get(i).calcActivacion(capas.get(i-1).getSalidas(), 1);
            }
            else{
                capas.get(i).calcActivacion(capas.get(i-1).getSalidas(), 0);
            }
        }
        
        return new ArrayList<>(capas.get(lastCapa-1).getSalidas());
        
    }
        
    
}
