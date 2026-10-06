package fiuba.cyberlek.model;

import java.util.List;

public class AdministradorDeTurnosModel {
    private List<JugadorModel> jugadores;
    private int turno;

    public AdministradorDeTurnosModel(List<JugadorModel> jugadores) {
        this.jugadores = jugadores;
        this.turno = 0;
    }

    public JugadorModel obtenerJugadorActual() {
        return jugadores.get(turno);
    }

    public void siguienteTurno() {
        turno = (turno + 1) % jugadores.size();
    }
}
