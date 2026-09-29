package fiuba.cyberlek.model;

public class CultivoModel implements TerrenoModel {

  @Override
  public Recurso generarRecurso() {
    return Recurso.TRIGO;
  }
}
