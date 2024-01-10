package com.curso;

import org.junit.jupiter.api.*;

//import org.junit.Test;
//import org.junit.Assert;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppTest {

    // En ocasiones nos interesa que haya una serie de trabajos que se ejecuten:

    // Antes de que comiencen las pruebas
    @BeforeAll
    static void antesDeLasPruebas() {
        System.out.println("Me ejecuto antes de las pruebas");
    }
    // Antes de cada prueba
    @BeforeEach
    void antesDeCadaPrueba()  {
        System.out.println("Me ejecuto antes de cada prueba");
    }
    // Despues de cada prueba
    @AfterEach
    void despuesDeCadaPrueba()  {
        System.out.println("Me ejecuto despues de cada prueba");
    }
    // Codigo que e ejecuta después de Todas las pruebas(cuando todas han acabado)
    @AfterAll
    void despuesDeLasPruebas()  {
        System.out.println("Me ejecuto despues de las pruebas");
    }
    // IMPORTANTE, las 2 funciones @XXXXAll deben ser estáticas
    // Nota, en ocasiones (un poco raras... pero ocurren) Me putea mogollón que esas funciones deban ser estáticas.
    // En ese caso, puedo definir las funciones sin ser estáticas, siempre que añada la anotación:
    // @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    // Antes de la clase
    // Junit crea varias instancias de mi clase. De hecho puede ejecutar varias pruebas en paralelo....
    // Y cada prueba la ejecutaría en una instancia diferente de la clase de pruebas
    // Este es el comportamiento por defecto. Para aegurarse que las funciones @XXXXAll cargan datos(que suele ser el uso típico) 1 sola vez, antes de todas las pruebas
    // Y que esos datos están disponibles en todas las instancias, se me obliga a que sean funciones estáticas.
    // Con la anotación anterior: @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    // Obligo a JUnit a que ejecute todas las funciones de prueba sobre la misma instancia de la clase de pruebas

    // Al ejecutar varias pruebas en paralelo, JUnit no me asgura el orden de ejecución de las pruebas-
    // En ocasiones... SI SIGO UNAS MUY MALAS ABERRANTES PRACTICAS de diseño de pruebas, necesito forzar el orden de ejecución de las pruebas.
    // NOTA: Os recuerdo que en los principios FIRST De desarrollo de pruebas, tenemos la I: Independientes
    // Por tanto nunca debería afectarme el ORDEN de ejecución de las pruebas
    // No obstante en ocasiones (sobre to do proyectos legacy, esto mew lo encuentro, y JUnit me ayuda a resolverlo:

    @Test
    @DisplayName("Test 1")
    @Order(2)
    void test1() {
        System.out.println("Test 1");
    }

    @Test
    @DisplayName("Test 2")
    @Order(1)
    void test2() {
        System.out.println("Test 2");
    }
    // Usar la anotación Order, requiere que anotemos la clase con la anotación @TestMethodOrder(MethodOrderer.OrderAnnotation.class)

    @Test // Posteriormente, vamos a pedir a JUnit que ejecute los ficheros compilados con clases que tengan funciones que estén anotadas con @Test
        // JUnit creará instancias automáticamente de estas clases... e invocará los métodos que tengan anotados con @Test
    @DisplayName("Test de la función sumar")
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
    @DisplayName("Test de la función restar")
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
