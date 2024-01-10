package com.curso;

import java.util.*;

public interface Diccionario {
    
    String getIdioma();
    
    boolean existe(String termino);
    
    Optional<List<String>> getDefiniciones(String termino);

    List<String> getSugerencias(String termino);

}