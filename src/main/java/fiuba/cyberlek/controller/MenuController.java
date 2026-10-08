package fiuba.cyberlek.controller;

import org.jline.reader.LineReader;

public class MenuController {
  private final LineReader reader;

  public MenuController(LineReader reader) {
    this.reader = reader;
  }

  public MenuOption mostrarMenu(Menu menu) {
    return mostrarMenu(menu, false);
  }

  private MenuOption mostrarMenu(Menu menu, boolean permiteVolver) {
    while (true) {
      System.out.println("=== " + menu.getNombre() + " ===");
      var componentes = menu.getComponentes();
      for (int i = 0; i < componentes.size(); i++) {
        System.out.println((i + 1) + ". " + componentes.get(i).getNombre());
      }
      if (permiteVolver) {
        System.out.println((componentes.size() + 1) + ". Volver");
      }

      int cantidadOpciones = componentes.size() + (permiteVolver ? 1 : 0);
      String opcionSeleccionada =
          reader.readLine("Seleccione una opción (1-" + cantidadOpciones + "): ");
      try {
        int indiceSeleccionado = Integer.parseInt(opcionSeleccionada) - 1;
        if (indiceSeleccionado >= 0 && indiceSeleccionado < componentes.size()) {
          MenuComponent componente = componentes.get(indiceSeleccionado);
          if (componente instanceof Menu submenu) {
            MenuOption opcion = mostrarMenu(submenu, true);
            if (opcion != null) {
              return opcion;
            }
            continue;
          } else if (componente instanceof MenuOption opcion) {
            System.out.println("Opción seleccionada: " + opcion.getNombre());
            return opcion;
          }
        } else if (permiteVolver && indiceSeleccionado == componentes.size()) {
          return null;
        }
      } catch (NumberFormatException ignored) {
      }

      System.out.println("Opción inválida. Intente nuevamente.");
    }
  }
}
