package com.mycompany.redneuronal;


import static java.lang.Float.max;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matia
 */
public class Capa {
    public ArrayList<ArrayList<Float>> WeightMatriz; //Cada fila representa una neurona
    public ArrayList<Float> BiasList; //Cada elemento representa a una neurona
    public ArrayList<Float> Salidas; //Cada elemento representa la salida de la neurona
    
    public Capa(){
        WeightMatriz = new ArrayList();
        BiasList = new ArrayList();
        Salidas = new ArrayList();
    }
    
    public void addNeurona(ArrayList<Float> WeightList, float Bias){
        WeightMatriz.add(WeightList);
        BiasList.add(Bias);
    }
    
    public void calcActivacion(ArrayList<Float> values, int salida){ //recibe las salidas de la capa anterior. //Salida = 1 = SI, 0 = NO
        //cada valor es una columna de la matriz de pesos
        this.Salidas.clear();
        for(int i = 0; i<WeightMatriz.size(); i++){
            float total = 0;
            for(int j = 0; j < WeightMatriz.get(i).size(); j++){
                total += values.get(j)*WeightMatriz.get(i).get(j);
            }
            total += BiasList.get(i);
            if(salida==0){
                Salidas.add(funcActivacion(total));
            }
            else{
                Salidas.add(funcActivacionSigmoide(total));
            }

        }
    }
    
    public float funcActivacion(float total){
        return max(0, total);//ReLu
    }
    
    public float funcActivacionSigmoide(float total) {
        return (float) (1.0 / (1.0 + Math.exp(-total)));
    }
    
    public ArrayList<Float> getSalidas(){
        return (ArrayList<Float>) this.Salidas.clone();
    }
    
    public void printSalidas(){
        for(int i = 0; i<Salidas.size(); i++){
            System.out.println("Salida neurona " + (i + 1) + " : " + Salidas.get(i) );
        }
    }
            
            
    public void MostrarCapa(){
        System.out.println("Bias list");
        for(int i = 0; i<BiasList.size(); i++){
            System.out.println("Neurona :  " + i + " Bias : " + BiasList.get(i)); 
        }
        System.out.println("Weight Matriz : " + WeightMatriz);
        System.out.println("Weight list");
        for(int i = 0; i<WeightMatriz.size(); i++){
            for(int j = 0; j < WeightMatriz.get(i).size(); j++){
                System.out.println("Neurona :  " + i + " Weight : " + WeightMatriz.get(i).get(j));
            }
        }
    }
    
    public void mutar(float tasaMutacion, float rangoMutacion) {
        for(int i = 0; i < (int) BiasList.size(); i++){
            if(Math.random()<tasaMutacion){
                BiasList.set(i, BiasList.get(i) + (float)(Math.random() * rangoMutacion * 2 - rangoMutacion)); 
            }
        }

        for(int i = 0; i < (int) WeightMatriz.size(); i++){
            ArrayList<Float> neurona = WeightMatriz.get(i);
            for(int j = 0; j < neurona.size(); j++){
                if(Math.random()<tasaMutacion){
                    neurona.set(j, neurona.get(j) + (float)(Math.random() * rangoMutacion * 2 - rangoMutacion));
                }
            }
        }
    }
    public Capa clonar() {
        Capa nuevaCapa = new Capa();

        // Copiar biases
        for (Float bias : this.BiasList) {
            nuevaCapa.BiasList.add(bias);
        }

        // Copiar pesos
        for (ArrayList<Float> neurona : this.WeightMatriz) {
            ArrayList<Float> pesosHijo = new ArrayList<>(neurona);
            nuevaCapa.WeightMatriz.add(pesosHijo);
        }

        return nuevaCapa;
    }
}
