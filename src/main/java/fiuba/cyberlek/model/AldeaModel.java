package fiuba.cyberlek.model;

public class AldeaModel extends ConstruccionModel {

  public AldeaModel(JugadorModel jugador) {
    super(jugador);
  }

  @Override
  public void asignarRecursoJugador(Recurso recurso) {
    jugador.agregar(recurso, 1);
  }

  @Override
  public ConstruccionModel mejorar() {
    return new CiudadModel(jugador);
  }

  @Override
  public ConstruccionModel downgradear() {
    return null;
  }

}

