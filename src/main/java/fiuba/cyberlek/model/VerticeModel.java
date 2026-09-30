package fiuba.cyberlek.model;

public class VerticeModel implements VerticeConstruidoModel {
  private int id;
  private ConstruccionModel construccion;

  @Override
  public void recibirRecurso(Recurso recurso) {
    construccion.asignarRecursoJugador(recurso);
  }

  public void construir() {}

  public void mejorarConstruccion() {
    if (construccion != null) {
      construccion.mejorar();
    }
  }

  public void degradarConstruccion() {
    if (construccion != null) {
      construccion.downgradear();
    }
  }
}
