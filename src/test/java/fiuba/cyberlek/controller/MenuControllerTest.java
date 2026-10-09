package fiuba.cyberlek.controller;

import static org.junit.Assert.assertEquals;

import fiuba.cyberlek.model.Recurso;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Iterator;
import org.jline.reader.LineReader;
import org.junit.Test;

public class MenuControllerTest {
  @Test
  public void seleccionaUnaOpcionDentroDeUnSubmenu() {
    Menu menu =
        new Menu("Acciones")
            .agregar(
                new Menu("Construcción")
                    .agregar(new MenuOption("Aldea", AccionMenu.CONSTRUIR_ALDEA))
                    .agregar(new MenuOption("Ciudad", AccionMenu.CONSTRUIR_CIUDAD)));
    MenuController menuController = new MenuController(readerWithInputs("1", "2"));

    MenuOption seleccion = menuController.mostrarMenu(menu);

    assertEquals(AccionMenu.CONSTRUIR_CIUDAD, seleccion.getAccion());
  }

  @Test
  public void volverDeUnSubmenuRetornaAlMenuPadre() {
    Menu menu =
        new Menu("Acciones")
            .agregar(
                new Menu("Construcción")
                    .agregar(new MenuOption("Aldea", AccionMenu.CONSTRUIR_ALDEA)))
            .agregar(new MenuOption("Terminar turno", AccionMenu.TERMINAR_TURNO));
    MenuController menuController = new MenuController(readerWithInputs("1", "2", "2"));

    MenuOption seleccion = menuController.mostrarMenu(menu);

    assertEquals(AccionMenu.TERMINAR_TURNO, seleccion.getAccion());
  }

  @Test
  public void conservaElRecursoElegidoEnLaHoja() {
    Menu menu =
        new Menu("Acciones")
            .agregar(
                new Menu("Comerciar")
                    .agregar(new MenuOption("Madera", AccionMenu.COMERCIAR, Recurso.MADERA)));
    MenuController menuController = new MenuController(readerWithInputs("1", "1"));

    MenuOption seleccion = menuController.mostrarMenu(menu);

    assertEquals(Recurso.MADERA, seleccion.getRecurso().orElseThrow());
  }

  private LineReader readerWithInputs(String... inputs) {
    Iterator<String> respuestas = Arrays.asList(inputs).iterator();
    return (LineReader)
        Proxy.newProxyInstance(
            LineReader.class.getClassLoader(),
            new Class<?>[] {LineReader.class},
            (proxy, method, arguments) -> {
              if (method.getName().equals("readLine")) {
                if (!respuestas.hasNext()) {
                  throw new AssertionError("No quedan respuestas de prueba.");
                }
                return respuestas.next();
              }
              throw new UnsupportedOperationException(method.getName());
            });
  }
}
