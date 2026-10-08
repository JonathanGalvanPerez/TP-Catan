package fiuba.cyberlek.controller;

import fiuba.cyberlek.model.TableroModel;

public class ConstruccionController {
  private TableroModel tablero;

  public ConstruccionController(TableroModel tablero) {
    this.tablero = tablero;
  }

  public void pedirConstruirAldeaInicial(String posicionVertice, JugadorModel jugador) {
    try {
      tablero.crearAldeaInicial(posicionVertice, jugador);
    } catch (RuntimeException e) {
      // .....
    }
  }

  public void pedirConstruirCamino(String posicionArista, JugadorModel jugador) {
    try {
      tablero.crearCamino(posicionArista, jugador);
    } catch (RuntimeException e) {

    }
  }

  public void pedirUpgradear(String posicionVertice, JugadorModel jugador) {
    try {
      tablero.upgradear(posicionVertice, jugador);
    } catch (RuntimeException e) {

    }
  }
}
