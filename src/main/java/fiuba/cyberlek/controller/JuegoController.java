package fiuba.cyberlek.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import fiuba.cyberlek.model.JuegoModel;
import fiuba.cyberlek.model.JugadorModel;
import fiuba.cyberlek.model.Recurso;
import fiuba.cyberlek.view.JuegoView;

public class JuegoController {
    private JuegoModel juego;
    private JuegoView juegoView;
    private LineReader reader;
    private MenuController menuController;

    public JuegoController() throws IOException {
        Terminal terminal = TerminalBuilder.terminal();
        this.reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .build();
        this.menuController = new MenuController(reader);

        int cantidadDeJugadores = Integer.parseInt(reader.readLine("Ingrese la cantidad de jugadores (2-4): "));
        List<JugadorModel> jugadores = new ArrayList<>();
        for (int i = 0; i < cantidadDeJugadores; i++) {
            String nombreJugador = reader.readLine("Ingrese el nombre del jugador " + (i + 1) + ": ");
            JugadorModel jugador = new JugadorModel(nombreJugador);
            jugadores.add(jugador);
        }
        this.juego = new JuegoModel(jugadores);
        this.juegoView = new JuegoView(juego);
    }

    public void jugarTurno() {
        boolean turnoTerminado = false;
        juego.iniciarTurno();
        juegoView.mostrarJuego();
        while(!turnoTerminado) {
            int opcionSeleccionada = menuController.mostrarMenu("Acciones", List.of("Construir", "Comerciar", "Terminar turno", "Rendirse"));
            switch (opcionSeleccionada) {
                case 0:
                    int opcionSeleccionada2 = menuController.mostrarMenu("Construcción", List.of("Aldea", "Ciudad", "Camino", "Volver"));
                    switch (opcionSeleccionada2) {
                        case 0:
                            int posicionAldea = Integer.parseInt(reader.readLine("Ingrese la posición para construir la aldea: "));
                            // Lógica para construir aldea
                            break;
                        case 1:
                            int posicionCiudad = Integer.parseInt(reader.readLine("Ingrese la posición para construir la ciudad: "));
                            // Lógica para construir ciudad
                            break;
                        case 2:
                            int posicionCamino = Integer.parseInt(reader.readLine("Ingrese la posición para construir el camino: "));
                            // Lógica para construir camino
                            break;
                        case 3:
                            // Volver al menú principal
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:
                    int recursoSeleccionado = menuController.mostrarMenu("Recurso a comerciar", List.of("Madera", "Arcilla", "Trigo", "Mineral", "Mineral", "Volver"));
                    Recurso recurso = Recurso.values()[recursoSeleccionado];
                    // Lógica para comerciar el recurso seleccionado
                case 2:
                    // Lógica para terminar el turno
                    turnoTerminado = true;
                    break;
                case 3:
                    // Lógica para rendirse
                    turnoTerminado = true;
                    juego.rendirse();
                    break;
            }
        }
        juego.terminarTurno();
    }

    public boolean termino() {
        return juego.termino();
    }
}
