package fiuba.cyberlek.model;

public class MontaniaModel implements TerrenoModel {

  @Override
  public Recurso generarRecurso() {
    return Recurso.MINERAL;
  }
}
