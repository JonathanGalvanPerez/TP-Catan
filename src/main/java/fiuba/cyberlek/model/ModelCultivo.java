package fiuba.cyberlek.model;

public class ModelCultivo implements ModelTerreno {

  @Override
  public Recurso generarRecurso() {
    return Recurso.TRIGO;
  }
}
