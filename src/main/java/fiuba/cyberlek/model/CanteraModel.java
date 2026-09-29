package fiuba.cyberlek.model;

public class CanteraModel implements TerrenoModel {

  @Override
  public Recurso generarRecurso() {
    return Recurso.ARCILLA;
  }
}
