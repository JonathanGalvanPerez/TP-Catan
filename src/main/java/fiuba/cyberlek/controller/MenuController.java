package fiuba.cyberlek.controller;

import java.util.List;

import org.jline.reader.LineReader;

public class MenuController {
    private LineReader reader;

    public MenuController(LineReader reader) {
        this.reader = reader;
    }

    public int mostrarMenu(String titulo, List<String> opciones) {
        while (true) {
            System.out.println("=== " + titulo + " ===");
            for (int i = 0; i < opciones.size(); i++) {
                System.out.println((i + 1) + ". " + opciones.get(i));
            }

            String opcionSeleccionada = reader.readLine("Seleccione una opción (1-" + opciones.size() + "): ");
            try {
                int opcion = Integer.parseInt(opcionSeleccionada) - 1;
                if (opcion >= 0 && opcion < opciones.size()) {
                    System.out.println("Opción seleccionada: " + opciones.get(opcion));
                    return opcion;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("Opción inválida. Intente nuevamente.");
        }
    }
}
