package com.curso;

//import com.curso.impl.SuministradorDeDiccionariosDesdeFicheros;


public interface SuministradorDeDiccionariosFactory {
    
    static SuministradorDeDiccionarios getInstance(){ 
        throw new RuntimeException("No implementado todavia"); 
        //return new SuministradorDeDiccionariosDesdeFicheros();
    }
}