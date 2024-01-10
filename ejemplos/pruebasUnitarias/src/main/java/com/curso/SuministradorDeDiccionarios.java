package com.curso;


import java.util.Optional;

public interface SuministradorDeDiccionarios {
    
    boolean tienesDiccionarioDe(String idioma);
    
    Optional<Diccionario> getDiccionario(String idioma);

}