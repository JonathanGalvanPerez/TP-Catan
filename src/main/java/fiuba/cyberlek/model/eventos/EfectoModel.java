package fiuba.cyberlek.model.eventos;

public abstract class EfectoModel {

  private int turnosRestantes;

  protected EfectoModel(int duracionEnTurnos) {
    if (duracionEnTurnos <= 0) {
      throw new IllegalArgumentException("La duracion debe ser positiva");
    }
    this.turnosRestantes = duracionEnTurnos;
  }

  /** Template method: define el ciclo de vida completo del efecto. */
  public final void iniciar() {
    aplicar();
  }

  public final void avanzarUnTurno() {
    turnosRestantes--;
    if (expiro()) {
      revertir();
    }
  }

  public final boolean expiro() {
    return turnosRestantes <= 0;
  }

  public final int getTurnosRestantes() {
    return turnosRestantes;
  }

  protected abstract void aplicar();

  protected abstract void revertir();

  /** mostrar en el estado de la partida. */
  public abstract String getDescripcion();
}
