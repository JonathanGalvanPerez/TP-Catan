package fiuba.cyberlek.controller;

import fiuba.cyberlek.model.TableroModel;

public class ConstruccionController {
  private TableroModel tablero;

  public ConstruccionController(TableroModel tablero) {
    this.tablero = tablero;
  }

  public void pedirConstruirAldeaInicial(String posicionVertice) {
    try {
      tablero.crearAldeaInicial(posicionVertice);
    } catch (RuntimeException e) {

    }
  }
}
