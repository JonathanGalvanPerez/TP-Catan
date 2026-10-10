package fiuba.cyberlek.model;

public class CultivoModel implements TerrenoModel {

  @Override
  public Recurso recursoQueProduce() {
    return Recurso.TRIGO;
  }
}
