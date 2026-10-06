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

    public JuegoController() throws IOException {
        Terminal terminal = TerminalBuilder.terminal();
        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

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
        juego.iniciarTurno();
        juegoView.mostrarTablero();
        juego.jugarTurno();
    }
}
