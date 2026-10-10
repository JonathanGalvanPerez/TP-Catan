package fiuba.cyberlek.model;

public class CanteraModel implements TerrenoModel {

  @Override
  public Recurso recursoQueProduce() {
    return Recurso.ARCILLA;
  }
}
