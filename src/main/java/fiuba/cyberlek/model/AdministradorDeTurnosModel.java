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
    int indice = turno % jugadores.size();
    JugadorModel jugador = jugadores.get(indice);
    if (jugador.estaEliminado()) {
      jugadores.remove(indice);
      return obtenerJugadorActual();
    }
    return jugador;
  }

  public void siguienteTurno() {
    turno++;
  }
}
