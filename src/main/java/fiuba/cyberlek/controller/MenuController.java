package fiuba.cyberlek.controller;

public class MenuController {
  private final InputController inputController;

  public MenuController(InputController inputController) {
    this.inputController = inputController;
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
      int indiceSeleccionado =
          inputController.leerNumero("Seleccione una opción (1-" + cantidadOpciones + "): ") - 1;
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

      System.out.println("Opción inválida. Intente nuevamente.");
    }
  }
}
