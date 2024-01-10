package com.curso.impl;

import com.curso.Diccionario;
import com.curso.SuministradorDeDiccionarios;

import java.util.*;

public class SuministradorDeDiccionariosDesdeFicheros implements SuministradorDeDiccionarios {

    // cache
    // Tardar menos en recuperar un dato
    // Y si voy cortito de memoria? Se va la cache a tomar por culo... y cuando haga falta el diccionario lo vuelvo a cargar
    private final Map<String, Diccionario> diccionarios = new WeakHashMap<>();

    @Override
    public boolean tienesDiccionarioDe(String idioma) {
        return diccionarios.containsKey(Objects.requireNonNull(idioma)) || Utilidades.getRutaDiccionario(idioma).isPresent();
    }

    public Optional<Diccionario> getDiccionario(String idioma){
        if (!diccionarios.containsKey(Objects.requireNonNull(idioma)))  {                    // Si no tengo el diccionario en cache
            Optional<Map<String,List<String>>> terminos = Utilidades.cargarDiccionario(idioma);
            if(terminos.isPresent())
                diccionarios.put(idioma, new DiccionarioDesdeFichero(idioma, terminos.get()));   // Lo cargo en cache. LOS DICCIONARIOS SE CARGAN EN MODO LAZY
        }
        return Optional.ofNullable(diccionarios.get(idioma));                               // Devuelvo de la cache
    }
}