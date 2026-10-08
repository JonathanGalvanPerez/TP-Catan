package fiuba.cyberlek.view;

import fiuba.cyberlek.model.JuegoModel;
import fiuba.cyberlek.model.JugadorModel;

public class JuegoView {
    private JuegoModel juego;

    public JuegoView(JuegoModel juego) {
        this.juego = juego;
    }

    public void mostrarJuego() {
        // Lógica para mostrar el tablero del juego
        // ...
        JugadorModel jugadorActual = juego.obtenerJugadorActual();
        System.out.println("=== Estado del Juego ===");
        System.out.println("Jugador actual: " + jugadorActual.getNombre());
        System.out.println("Recursos del jugador: " + jugadorActual.getRecursos());
        System.out.println("{{Inserte el tablero aqui}}");
    }
}
