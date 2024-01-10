package com.curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//import org.junit.Test;
//import org.junit.Assert;

class AppTest {

    @Test // Posteriormente, vamos a pedir a JUnit que ejecute los ficheros compilados con clases que tengan funciones que estén anotadas con @Test
        // JUnit creará instancias automáticamente de estas clases... e invocará los métodos que tengan anotados con @Test
    void testSumar() {
        int numero1 = 10;
        int numero2 = 20;
        int resultado = App.sumar(numero1, numero2);
        // En JUnit, las funciones que están anotadas con @Test se entiende que son pruebas.
        // Y Una prueba puede acabar en:
        //  - Success		Si la prueba acaba (el código se ejecuta) sin inconvenientes
        //  - Error			Si ocurre una exception durante la ejecución del código de la función que se esté ejecutando
        //  - Failure		Si se invoca el método Assert.fail de la librería JUnit
        // Habitualmente no invocamos nunca directamente el método Assert.fail... aunque podríamos
        // Junit , dentro de esa clase Assert, incluye un MONTON de funciones, llamadas Assertions, que internamente invocan a ea función(fail)
        // Siempre escribiremos al menos 1 Assertion dentro de una función de pruebas
        // Las assertions son las PRUEBAS en si
        int valorEsperado = 30;
        Assertions.assertEquals(valorEsperado,resultado); // El conveio es poner SIEMPRE PRIMERO el valor esperado
    }

    @Test
    void testRestar() {
        // GIVEN
        int numero1 = 10;
        int numero2 = 20;
        // WHEN
        int resultado = App.restar(numero1, numero2);
        int valorEsperado = -10;
        // ENTONCES: THEN
        Assertions.assertEquals(valorEsperado,resultado);
    }

}
