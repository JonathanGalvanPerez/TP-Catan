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
    private Menu menuTurno;

    public JuegoController() throws IOException {
        Terminal terminal = TerminalBuilder.terminal();
        this.reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .build();
        this.menuController = new MenuController(reader);
        this.menuTurno = crearMenuTurno();

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
            MenuOption opcionSeleccionada = menuController.mostrarMenu(menuTurno);
            switch (opcionSeleccionada.getAccion()) {
                case CONSTRUIR_ALDEA:
                    Integer.parseInt(reader.readLine("Ingrese la posición para construir la aldea: "));
                    // Lógica para construir aldea
                    break;
                case CONSTRUIR_CIUDAD:
                    Integer.parseInt(reader.readLine("Ingrese la posición para construir la ciudad: "));
                    // Lógica para construir ciudad
                    break;
                case CONSTRUIR_CAMINO:
                    Integer.parseInt(reader.readLine("Ingrese la posición para construir el camino: "));
                    // Lógica para construir camino
                    break;
                case COMERCIAR:
                    opcionSeleccionada.getRecurso().orElseThrow();
                    // Lógica para comerciar el recurso seleccionado
                    break;
                case TERMINAR_TURNO:
                    turnoTerminado = true;
                    break;
                case RENDIRSE:
                    turnoTerminado = true;
                    juego.rendirse();
                    break;
            }
        }
        juego.terminarTurno();
    }

    private Menu crearMenuTurno() {
        Menu construccion =
                new Menu("Construcción")
                        .agregar(new MenuOption("Aldea", AccionMenu.CONSTRUIR_ALDEA))
                        .agregar(new MenuOption("Ciudad", AccionMenu.CONSTRUIR_CIUDAD))
                        .agregar(new MenuOption("Camino", AccionMenu.CONSTRUIR_CAMINO));
        Menu comercio = new Menu("Recurso a comerciar");
        for (Recurso recurso : Recurso.values()) {
            if (recurso != Recurso.NADA) {
                comercio.agregar(new MenuOption(recurso.getNombre(), AccionMenu.COMERCIAR, recurso));
            }
        }

        return new Menu("Acciones")
                .agregar(construccion)
                .agregar(comercio)
                .agregar(new MenuOption("Terminar turno", AccionMenu.TERMINAR_TURNO))
                .agregar(new MenuOption("Rendirse", AccionMenu.RENDIRSE));
    }

    public boolean termino() {
        return juego.termino();
    }
}
