package com.curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class SuministradorDeDiccionariosTest {
    @Test
    @DisplayName("Preguntar por un diccionario existente")
    void preguntarPorUnDiccionarioExistente() {
        SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getInstance();
        boolean respuesta = miSuministrador.tienesDiccionarioDe("ES");
        Assertions.assertTrue(respuesta);
    }
    @Test
    @DisplayName("Preguntar por un diccionario no existente")
    void preguntarPorUnDiccionarioNoExistente() {
        SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getInstance();
        boolean respuesta = miSuministrador.tienesDiccionarioDe("RUNAS ELFICAS");
        Assertions.assertFalse(respuesta);
    }
    @Test
    @DisplayName("Recuperar un diccionario existente")
    void recuperarUnDiccionarioExistente() {
        String idioma = "ES";
        SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getInstance();
        Optional<Diccionario> diccionario = miSuministrador.getDiccionario(idioma);
        Assertions.assertTrue(diccionario.isPresent());
        Assertions.assertEquals(idioma, diccionario.get().getIdioma());
    }
    @Test
    @DisplayName("Recuperar un diccionario no existente")
    void recuperarUnDiccionarioNoExistente() {
        String idioma = "RUNAS ELFICAS";
        SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getInstance();
        Optional<Diccionario> diccionario = miSuministrador.getDiccionario(idioma);
        Assertions.assertFalse(diccionario.isPresent());
    }
}
