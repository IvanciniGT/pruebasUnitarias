package com.curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class DiccionarioTest {

    @Test
    @DisplayName("Preguntar por una palabra existente")
    void preguntarPorUnaPalabraExistente() {
        Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getInstance().getDiccionario("ES").get();
        boolean respuesta = miDiccionario.existe("manzana");
        Assertions.assertTrue(respuesta);
    }
    @Test
    @DisplayName("Preguntar por una palabra inexistente")
    void preguntarPorUnaPalabraInexistente() {
        Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getInstance().getDiccionario("ES").get();
        boolean respuesta = miDiccionario.existe("manana");
        Assertions.assertFalse(respuesta);
    }

    @Test
    @DisplayName("Recuperar significados de una palabra existente")
    void significadosDeUnaPalabraExistente() {
        Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getInstance().getDiccionario("ES").get();
        Optional<List<String>> significados = miDiccionario.getDefiniciones("manzana");
        Assertions.assertTrue(significados.isPresent());
        Assertions.assertEquals(1, significados.get().size());
        Assertions.assertEquals("Fruta del manzano", significados.get().get(0));
    }
    @Test
    @DisplayName("Recuperar significados de una palabra inexistente")
    void significadosDeUnaPalabraInexistente(){
        Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getInstance().getDiccionario("ES").get();
        Optional<List<String>> significados = miDiccionario.getDefiniciones("manana");
        Assertions.assertFalse(significados.isPresent());
    }
}
