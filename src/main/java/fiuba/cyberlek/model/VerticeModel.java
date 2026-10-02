package fiuba.cyberlek.model;

public class VerticeModel implements VerticeConstruidoModel {
  private int id;
  private String posicion;
  private ConstruccionModel construccion;

  @Override
  public void recibirRecurso(Recurso recurso) {
    construccion.asignarRecursoJugador(recurso);
  }

  public void construir(JugadorModel jugador) {
    if (construccion == null) {
      this.construccion = new AldeaModel(jugador);
    }
  }

  public int getIndiceAdyacencia() {
    return this.id;
  }

  public String getPosicion() {
    return this.posicion;
  }

  public void mejorarConstruccion() {
    if (construccion != null) {
      this.construccion = construccion.mejorar();
    }
  }

  public void degradarConstruccion() {
    if (construccion != null) {
      this.construccion = construccion.downgradear();
    }
  }
}
