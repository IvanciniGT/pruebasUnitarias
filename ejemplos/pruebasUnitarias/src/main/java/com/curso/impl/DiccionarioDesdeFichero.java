package com.curso.impl;

import com.curso.Diccionario;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DiccionarioDesdeFichero implements Diccionario {

    private final String idioma;

    private final Map<String, List<String>> terminos;

    public DiccionarioDesdeFichero(String idioma, Map<String, List<String>> terminos){
        this.terminos=terminos;
        this.idioma=idioma;
    }

    @Override
    public String getIdioma() {
        return this.idioma;
    }

    public boolean existe(String palabra){
        return terminos.containsKey(Utilidades.normalizarPalabra(palabra));
    }

    public Optional<List<String>> getDefiniciones(String palabra){
        return Optional.ofNullable(terminos.get(Utilidades.normalizarPalabra(palabra)));
    }

    public List<String> getSugerencias(String palabra){
        final String palabraABuscar = Utilidades.normalizarPalabra(palabra);
        
        return   this.terminos.keySet()             // Tomo las claves (las palabras que existen en mi diccionario)
                              .parallelStream()     // Voy a intentar hacer el mejor uso posible de mis Cores (CPU) ~> Hilos
                              .map(     termino    -> new Sugerencia(Utilidades.distancia(termino, palabraABuscar), termino)    )  // Calcular la distancia para todas las claves del diccionario
                              .filter(  sugerencia -> sugerencia.distancia <= Utilidades.DISTANCIA_MAXIMA_PERMITIDA              )  // Filtrar aquellas que la distancia sea menor o igual que un valor dado.   <= 2
                              .sorted(  Comparator.comparing( sugerencia -> sugerencia.distancia )                              )  // Ordenar de menor a mayor puntuación
                              .limit(   Utilidades.MAXIMOS_A_DEVOLVER                                                           )  // Cortamos en 10
                              .map(     sugerencia -> sugerencia.termino                                                        )  // Me deshago de la distancia
                              .collect( Collectors.toList()                                                                     ); // A una lista

    }
    
    private static class Sugerencia {
        public final int distancia;
        public final String termino;

        Sugerencia(int distancia,String termino){
            this.distancia=distancia;
            this.termino=termino;
        }
    }
}