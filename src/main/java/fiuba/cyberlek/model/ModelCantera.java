package fiuba.cyberlek.model;

public class ModelCantera implements ModelTerreno {

  @Override
  public Recurso generarRecurso() {
    return Recurso.ARCILLA;
  }
}
