package fiuba.cyberlek.model;

public enum Recurso {
  MADERA("Madera"),
  ARCILLA("Arcilla"),
  TRIGO("Trigo"),
  MINERAL("Mineral");

  private final String nombre;

  Recurso(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return nombre;
  }
}
