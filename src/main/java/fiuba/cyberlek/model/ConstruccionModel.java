package fiuba.cyberlek.model;

public abstract class ConstruccionModel {
  protected JugadorModel jugador;

  public ConstruccionModel(JugadorModel jugador) {
    this.jugador = jugador;
  }

  abstract ConstruccionModel mejorar();

  abstract ConstruccionModel downgradear();

  abstract void asignarRecursoJugador(Recurso recurso);
}
