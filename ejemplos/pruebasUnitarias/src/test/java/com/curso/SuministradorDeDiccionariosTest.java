package com.curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class SuministradorDeDiccionariosTest {

    private static SuministradorDeDiccionarios miSuministrador;

    @BeforeAll
    static void inicializarSuministrador(){
        miSuministrador = SuministradorDeDiccionariosFactory.getInstance();
    }

    @Test
    @DisplayName("Preguntar por un diccionario existente")
    void preguntarPorUnDiccionarioExistente() {
        boolean respuesta = miSuministrador.tienesDiccionarioDe("ES");
        Assertions.assertTrue(respuesta);
    }
    @Test
    @DisplayName("Preguntar por un diccionario no existente")
    void preguntarPorUnDiccionarioNoExistente() {
        boolean respuesta = miSuministrador.tienesDiccionarioDe("RUNAS ELFICAS");
        Assertions.assertFalse(respuesta);
    }
    @Test
    @DisplayName("Recuperar un diccionario existente")
    void recuperarUnDiccionarioExistente() {
        String idioma = "ES";
        Optional<Diccionario> diccionario = miSuministrador.getDiccionario(idioma);
        Assertions.assertTrue(diccionario.isPresent());
        Assertions.assertEquals(idioma, diccionario.get().getIdioma());
    }
    @Test
    @DisplayName("Recuperar un diccionario no existente")
    void recuperarUnDiccionarioNoExistente() {
        String idioma = "RUNAS ELFICAS";
        Optional<Diccionario> diccionario = miSuministrador.getDiccionario(idioma);
        Assertions.assertFalse(diccionario.isPresent());
    }
}
