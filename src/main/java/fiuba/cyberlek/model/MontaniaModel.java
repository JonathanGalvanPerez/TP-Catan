package fiuba.cyberlek.model;

public class MontaniaModel implements TerrenoModel {

  @Override
  public RecursoEnum generarRecurso() {
    return RecursoEnum.MINERAL;
  }
}
