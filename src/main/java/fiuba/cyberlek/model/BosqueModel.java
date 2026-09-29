package fiuba.cyberlek.model;

public class BosqueModel implements TerrenoModel {

  @Override
  public Recurso generarRecurso() {
    return Recurso.MADERA;
  }
}
