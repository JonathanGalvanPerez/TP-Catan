package fiuba.cyberlek.model;

public class CanteraModel implements TerrenoModel {

  @Override
  public RecursoEnum generarRecurso() {
    return RecursoEnum.ARCILLA;
  }
}
