package fiuba.cyberlek.model;

public class ModelMontania implements ModelTerreno {

  @Override
  public Recurso generarRecurso() {
    return Recurso.MINERAL;
  }
}
