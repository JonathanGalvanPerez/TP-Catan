package fiuba.cyberlek.controller;

import fiuba.cyberlek.model.GrafoBuilderModel;
import fiuba.cyberlek.model.GrafoModel;

public class ConstruccionController {
  private GrafoModel grafo;

  public ConstruccionController(int dimensionX, int dimensionY) {
    this.grafo = new GrafoBuilderModel(dimensionX, dimensionY).CrearGrafo();
  }
}
