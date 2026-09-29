package fiuba.cyberlek.model;

public class BosqueModel implements TerrenoModel {

  @Override
  public RecursoEnum generarRecurso() {
    return RecursoEnum.MADERA;
  }
}
