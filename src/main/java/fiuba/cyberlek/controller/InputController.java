package fiuba.cyberlek.controller;

import java.io.IOException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class InputController {
  private final LineReader reader;

  public InputController() throws IOException {
    Terminal terminal = TerminalBuilder.terminal();
    this.reader = LineReaderBuilder.builder().terminal(terminal).build();
  }

  public InputController(LineReader reader) {
    this.reader = reader;
  }

  public String leerTexto(String mensaje) {
    while (true) {
      String texto = reader.readLine(mensaje);
      if (texto != null && !texto.isBlank()) {
        return texto;
      }
      System.out.println("Entrada inválida. Ingrese un texto.");
    }
  }

  public int leerNumero(String mensaje) {
    while (true) {
      String entrada = reader.readLine(mensaje);
      try {
        return Integer.parseInt(entrada.trim());
      } catch (NumberFormatException exception) {
        System.out.println("Entrada inválida. Ingrese un número entero.");
      }
    }
  }
}
