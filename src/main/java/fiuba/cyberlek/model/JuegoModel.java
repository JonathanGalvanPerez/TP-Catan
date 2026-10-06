package fiuba.cyberlek.model;

import java.util.List;

public class JuegoModel {
    private AdministradorDeTurnosModel administradorDeTurnos;
    private TableroModel tablero;

    public JuegoModel(List<JugadorModel> jugadores) {
        if (jugadores.size() < 2 || jugadores.size() > 4) {
            throw new IllegalArgumentException("La cantidad de jugadores debe ser entre 2 y 4");
        }
        this.administradorDeTurnos = new AdministradorDeTurnosModel(jugadores);
        this.tablero = new TableroModel();
    }

    public void iniciarTurno() {
        this.activarEvento();
        this.producirRecursos();
    }

    private void activarEvento() {
        // Lógica para activar un evento en el juego
        // ...
    }

    private void producirRecursos() {
        // Lógica para producir recursos en el juego
        // ...
    }

    public void jugarTurno() {
        JugadorModel jugadorActual = administradorDeTurnos.obtenerJugadorActual();
        // Lógica para que el jugador actual juegue su turno
        // ...
        administradorDeTurnos.siguienteTurno();
    }
}
