import com.curso.Diccionario;
import com.curso.SuministradorDeDiccionariosFactory;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import java.util.List;
import java.util.Optional;

@Suite // Esto indica que el fichero es ejecutable con JUnit
// Pero junit platform no sabe a que framework de pruebas debe delegar la ejecución
@IncludeEngines("cucumber") // Deleva la ejecución a cucumber
// En JUnit 4, esta anotacion se llamaba: RunWith(Cucumber.class)
// Tengo que decirle a cucumber qué pruebas debe ejecutar
@SelectClasspathResource("features")
// ^Passale al cucumber todos los archivos de la carpeta features
public class DiccionarioCucumberTest {
    private Diccionario miDiccionario;
    private String palabraABuscar;
    private boolean respuesta;
    private Optional<List<String>> significados;
    @Dado("que tengo un diccionario de idioma {string}")
    public void queTengoUnDiccionarioDeIdioma(String idioma) {
        miDiccionario = SuministradorDeDiccionariosFactory.getInstance().getDiccionario(idioma).get();
    }

    @Cuando("pregunto por esa palabra al diccionario")
    public void preguntoPorEsaPalabraAlDiccionario() {
        respuesta = miDiccionario.existe(palabraABuscar);
    }

    @Entonces("el diccionario me responde que {string} existe")
    public void elDiccionarioMeRespondeQueExiste(String respuestaDelDiccionario) {
        Assertions.assertEquals(respuestaDelDiccionario.equalsIgnoreCase("si"), respuesta);
    }

    @Dado("que la palabra {string} existe en el diccionario")
    @Dado("que la palabra {string} no existe en el diccionario")
    public void dadaUnaPalabra(String palabra) {
        palabraABuscar = palabra;
    }

    @Cuando("solicito los significados de esa palabra al diccionario")
    public void solicitoLosSignificadosDeEsaPalabraAlDiccionario() {
        significados = miDiccionario.getDefiniciones(palabraABuscar);
    }

    @Y("el significado número {int} que aparece en esa lista es {string}")
    public void elSignificadoNúmeroPosicionQueApareceEnEsaListaEs(int posicion, String significado) {
        Assertions.assertEquals(significado, significados.get().get(posicion-1));
    }

    @Entonces("el diccionario me devuelve una lista con los significados de esa palabra, en total {int}")
    public void elDiccionarioMeDevuelveUnaListaConLosSignificadosDeEsaPalabraEnTotalNumeroSignificados(int numeroSignificados) {
        Assertions.assertTrue(significados.isPresent());
        Assertions.assertEquals(numeroSignificados, significados.get().size());
    }
}
