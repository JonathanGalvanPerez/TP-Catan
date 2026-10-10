package fiuba.cyberlek.model;

public class MontaniaModel implements TerrenoModel {

  @Override
  public Recurso recursoQueProduce() {
    return Recurso.MINERAL;
  }
}
