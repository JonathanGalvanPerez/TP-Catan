package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.CasilleroModel;
import java.util.List;

public class TormentaEfecto extends EfectoModel {

  private final List<CasilleroModel> zonaAfectada;

  public TormentaEfecto(List<CasilleroModel> zonaAfectada, int duracionEnTurnos) {
    super(duracionEnTurnos);
    this.zonaAfectada = List.copyOf(zonaAfectada);
  }

  @Override
  protected void aplicar() {
    for (CasilleroModel casillero : zonaAfectada) {
      casillero.bloquearConstruccion();
    }
  }

  @Override
  protected void revertir() {
    for (CasilleroModel casillero : zonaAfectada) {
      casillero.desbloquearConstruccion();
    }
  }

  @Override
  public String getDescripcion() {
    return "Tormenta sobre " + zonaAfectada.size() + " casilla(s) intransitables";
  }
}
