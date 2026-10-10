package fiuba.cyberlek.model;

public class SaqueadorModel {

  private CasilleroModel posicionActual;

  public SaqueadorModel(CasilleroModel casilleroInicial) {
    if (casilleroInicial == null) {
      throw new IllegalArgumentException("Se requiere casilla inicial");
    }
    this.posicionActual = casilleroInicial;
    casilleroInicial.colocarSaqueador();
  }

  public void mover(CasilleroModel destino) {
    if (destino == null) {
      throw new IllegalArgumentException("Destino invalido");
    }
    if (destino == posicionActual) {
      return;
    }
    posicionActual.removerSaqueador();
    this.posicionActual = destino;
    destino.colocarSaqueador();
  }

  public CasilleroModel getPosicion() {
    return posicionActual;
  }
}
