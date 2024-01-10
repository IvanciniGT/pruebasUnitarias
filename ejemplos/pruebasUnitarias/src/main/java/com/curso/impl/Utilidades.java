package com.curso.impl;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Utilidades {

    int DISTANCIA_MAXIMA_PERMITIDA  = 2;
    int MAXIMOS_A_DEVOLVER          = 10;
    
    static Optional<Map<String,List<String>>> cargarDiccionario(String idioma){
        Optional<URL> pathDelFichero = getRutaDiccionario(idioma);

        if(pathDelFichero.isPresent())
                try (Stream<String> stream = Files.lines(Paths.get(pathDelFichero.get().toURI()))) {
                Map<String,List<String>> resultado=stream
                                                                                    // Parte por \n \r \n\r
                                                                                    // Que tengo aquí? Un Stream<String>
                                //.filter(    linea -> !linea.isBlank()         )   // JAVA 11: solo proceso lineas con contenido. Con la de abajo ya no tiene sentido
                                .filter(    linea -> linea.contains("="))   // JAVA 11: solo proceso lineas con el signo igual !
                                .map(       linea -> linea.split("=")           )   // Partiendo por el signo igual
                                                                                    // Que tengo aquí? Un Stream<String[]>
                                .collect(   Collectors.toMap( 
                                                            partes -> normalizarPalabra(partes[0]),          // La funcion que define del array como paso a la clave
                                                            partes -> Arrays.asList(partes[1].split("\\|")), // La funcion que define del array como paso al valor
                                                            // En el caso de que se encuentren 2 entradas con la misma clave, con que valor me quedo
                                                            (definicion1, definicion2) -> Stream.of(      definicion1, definicion2    )  // Monto un Stream con 2 Listas de definiciones // Stream<List<String>>
                                                                                                .flatMap( Collection::stream          )  // Cada lista de definiciones la convierto en un stream, que desempaqueto (flatmap)
                                                                                                .collect( Collectors.toList()         )  // el stream con todas las definiciones lo convierto a una lista
                                            )
                                        );
                return Optional.of(resultado);
            }catch(IOException e){
                // Avisar que hay un error al acceder al fichero
                System.err.println("Error al acceder al fichero");              // Lo tendríamos que hacer a través de un logger
            }catch(Exception e){
                // Avisar que hay un error al acceder al fichero
                System.err.println("Error en la sintaxis del fichero");         // Lo tendríamos que hacer a través de un logger
            }
        return Optional.empty();
    }
    
    static Optional<URL> getRutaDiccionario(String idioma){
        URL urlDelFichero = Utilidades.class.getClassLoader().getResource("diccionario."+idioma+".txt");
        if (urlDelFichero != null )
            return Optional.of(urlDelFichero);
        return Optional.empty();
    }
    
    static int distancia(String str1, String str2) {
        if ( Math.abs(str1.length() - str2.length()) > DISTANCIA_MAXIMA_PERMITIDA ) return DISTANCIA_MAXIMA_PERMITIDA+1;
        return computeLevenshteinDistance(str1.toCharArray(),
                                          str2.toCharArray());
    }
    
    static int minimum(int a, int b, int c) {
         return Math.min(a, Math.min(b, c));
    }

    static int computeLevenshteinDistance(char [] str1, char [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];

        for(int i=0;i<=str1.length;i++){
                distance[i][0]=i;
        }
        for(int j=0;j<=str2.length;j++){
                distance[0][j]=j;
        }
        for(int i=1;i<=str1.length;i++){
            for(int j=1;j<=str2.length;j++){ 
                  distance[i][j]= minimum(distance[i-1][j]+1,
                                        distance[i][j-1]+1,
                                        distance[i-1][j-1]+
                                        ((str1[i-1]==str2[j-1])?0:1));
            }
        }
        return distance[str1.length][str2.length];
    }
    
    static String normalizarPalabra(String palabra){
        return Objects.requireNonNull(palabra).toLowerCase();
    }
}