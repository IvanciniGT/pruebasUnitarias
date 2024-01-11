package com.curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

public class DiccionarioTest {

    private static Diccionario miDiccionario;

    @BeforeAll
    static void inicializarDiccionario(){
        miDiccionario = SuministradorDeDiccionariosFactory.getInstance().getDiccionario("ES").get();
    }

    @ParameterizedTest
    @DisplayName("Preguntar por una palabra existente")
    @ValueSource(strings = {"manzana", "Manzana", "MANZANA", "melón"})
    void preguntarPorUnaPalabraExistente(String palabra) {
        boolean respuesta = miDiccionario.existe(palabra);
        Assertions.assertTrue(respuesta);
    }
    @ParameterizedTest
    @DisplayName("Preguntar por una palabra inexistente")
    @ValueSource(strings = {"manana", "melocotón","archilococo"})
    void preguntarPorUnaPalabraInexistente(String palabra) {
        boolean respuesta = miDiccionario.existe(palabra);
        Assertions.assertFalse(respuesta);
    }

    @ParameterizedTest
    @DisplayName("Recuperar significados de una palabra existente")
    @CsvSource({"manzana,1,Fruta del manzano","Perro,2,Animal de compañía"})
    void significadosDeUnaPalabraExistente(String palabra, int numeroDeDefiniciones, String primeraDefinicion) {
        Optional<List<String>> significados = miDiccionario.getDefiniciones(palabra);
        Assertions.assertTrue(significados.isPresent());
        Assertions.assertEquals(numeroDeDefiniciones, significados.get().size());
        Assertions.assertEquals(primeraDefinicion, significados.get().get(0));
    }
    @ParameterizedTest
    @DisplayName("Recuperar significados de una palabra existente (Datos en fichero externo)")
    @CsvFileSource(resources = "/palabrasExistentes.csv", numLinesToSkip = 1)
    void significadosDeUnaPalabraExistente2(String palabra, int numeroDeDefiniciones, String primeraDefinicion) {
        Optional<List<String>> significados = miDiccionario.getDefiniciones(palabra);
        Assertions.assertTrue(significados.isPresent());
        Assertions.assertEquals(numeroDeDefiniciones, significados.get().size());
        Assertions.assertEquals(primeraDefinicion, significados.get().get(0));
    }
    @Test
    @DisplayName("Recuperar significados de una palabra inexistente")
    void significadosDeUnaPalabraInexistente(){
        Optional<List<String>> significados = miDiccionario.getDefiniciones("manana");
        Assertions.assertFalse(significados.isPresent());
    }
}
