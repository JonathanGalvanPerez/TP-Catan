package fiuba.cyberlek.model;

public class CultivoModel implements TerrenoModel {

  @Override
  public RecursoEnum generarRecurso() {
    return RecursoEnum.TRIGO;
  }
}
