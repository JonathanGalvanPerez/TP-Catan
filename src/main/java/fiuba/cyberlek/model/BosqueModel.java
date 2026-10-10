package fiuba.cyberlek.model;

public class BosqueModel implements TerrenoModel {

  @Override
  public Recurso recursoQueProduce() {
    return Recurso.MADERA;
  }
}
