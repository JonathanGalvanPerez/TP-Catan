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
            int opcionSeleccionada = menuController.mostrarMenu(List.of("Construir", "Comerciar", "Terminar turno", "Rendirse"));
            switch (opcionSeleccionada) {
                case 0:
                    // Lógica para construir
                    break;
                case 1:
                    // Lógica para comerciar
                    break;
                case 2:
                    // Lógica para terminar el turno
                    turnoTerminado = true;
                    break;
                case 3:
                    // Lógica para rendirse
                    turnoTerminado = true;
                    break;
            }
        }
        juego.terminarTurno();
    }

    public boolean termino() {
        return juego.termino();
    }
}
