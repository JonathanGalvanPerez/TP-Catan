package fiuba.cyberlek.model;

public class ModelBosque implements ModelTerreno {

  @Override
  public Recurso generarRecurso() {
    return Recurso.Madera;
  }
}
