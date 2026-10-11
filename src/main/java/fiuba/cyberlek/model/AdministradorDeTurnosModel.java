package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdministradorDeTurnosModel {
  private final List<JugadorModel> jugadores;
  private int turnoActual;

  public AdministradorDeTurnosModel(List<JugadorModel> jugadores) {
    this.jugadores = new ArrayList<>(Objects.requireNonNull(jugadores));
    this.turnoActual = 0;
  }

  public JugadorModel obtenerJugadorActual() {
    while (!jugadores.isEmpty() && jugadores.get(turnoActual).estaEliminado()) {
      jugadores.remove(turnoActual);
      if (jugadores.isEmpty()) {
        throw new IllegalStateException("No quedan jugadores activos.");
      }
      turnoActual %= jugadores.size();
    }

    if (jugadores.isEmpty()) {
      throw new IllegalStateException("No quedan jugadores activos.");
    }

    return jugadores.get(turnoActual);
  }

  public void siguienteTurno() {
    if (jugadores.isEmpty()) {
      throw new IllegalStateException("No quedan jugadores activos.");
    }
    turnoActual = (turnoActual + 1) % jugadores.size();
  }
}
