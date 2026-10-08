package fiuba.cyberlek.controller;

import java.util.List;
import org.jline.reader.LineReader;

public class MenuController {
  private LineReader reader;

  public MenuController(LineReader reader) {
    this.reader = reader;
  }

  public int mostrarMenu(List<String> opciones) {
    System.out.println("=== Menú ===");
    for (int i = 0; i < opciones.size(); i++) {
      System.out.println((i + 1) + ". " + opciones.get(i));
    }
    String opcionSeleccionada =
        reader.readLine("Seleccione una opción (1-" + opciones.size() + "): ");
    int opcion =
        Integer.parseInt(opcionSeleccionada) - 1; // Restamos 1 para obtener el índice correcto
    System.out.println("Opción seleccionada: " + opciones.get(opcion));
    return opcion;
  }
}
