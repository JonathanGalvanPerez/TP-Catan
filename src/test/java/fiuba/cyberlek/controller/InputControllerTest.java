package fiuba.cyberlek.controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import org.jline.reader.LineReader;
import org.junit.Test;

public class InputControllerTest {
  @Test
  public void leerNumeroVuelveASolicitarLaEntradaHastaRecibirUnEntero() {
    ByteArrayOutputStream salida = new ByteArrayOutputStream();
    PrintStream salidaOriginal = System.out;
    System.setOut(new PrintStream(salida));
    try {
      InputController inputController = inputControllerWithInputs("no es un número", " 42 ");

      int numero = inputController.leerNumero("Ingrese un número: ");

      assertEquals(42, numero);
      assertEquals(
          "Entrada inválida. Ingrese un número entero." + System.lineSeparator(),
          salida.toString(StandardCharsets.UTF_8));
    } finally {
      System.setOut(salidaOriginal);
    }
  }

  @Test
  public void leerTextoVuelveASolicitarLaEntradaHastaRecibirTextoNoVacio() {
    ByteArrayOutputStream salida = new ByteArrayOutputStream();
    PrintStream salidaOriginal = System.out;
    System.setOut(new PrintStream(salida));
    try {
      InputController inputController = inputControllerWithInputs("   ", "Jugador");

      String texto = inputController.leerTexto("Ingrese un nombre: ");

      assertEquals("Jugador", texto);
      assertEquals(
          "Entrada inválida. Ingrese un texto." + System.lineSeparator(),
          salida.toString(StandardCharsets.UTF_8));
    } finally {
      System.setOut(salidaOriginal);
    }
  }

  private InputController inputControllerWithInputs(String... inputs) {
    Iterator<String> respuestas = Arrays.asList(inputs).iterator();
    LineReader reader =
        (LineReader)
            java.lang.reflect.Proxy.newProxyInstance(
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
    return new InputController(reader);
  }
}
