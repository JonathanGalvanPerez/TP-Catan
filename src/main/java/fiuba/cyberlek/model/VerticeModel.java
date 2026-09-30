package fiuba.cyberlek.model;

public class VerticeModel implements VerticeConstruidoModel {
  private int id;
  private ConstruccionModel construccion;

  @Override
  public void recibirRecurso(Recurso recurso) {}

  public void construir() {}

  public void mejorarConstruccion() {}

  public void degradarConstruccion() {}
}
