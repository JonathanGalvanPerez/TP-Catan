package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.CasilleroModel;
import java.util.List;

public class SequiaEfecto extends EfectoModel {

  private final List<CasilleroModel> camposDeCultivo;

  public SequiaEfecto(List<CasilleroModel> camposDeCultivo, int duracionEnTurnos) {
    super(duracionEnTurnos);
    this.camposDeCultivo = List.copyOf(camposDeCultivo);
  }

  @Override
  protected void aplicar() {
    for (CasilleroModel casillero : camposDeCultivo) {
      casillero.bloquearProduccion();
    }
  }

  @Override
  protected void revertir() {
    for (CasilleroModel casillero : camposDeCultivo) {
      casillero.desbloquearProduccion();
    }
  }

  @Override
  public String getDescripcion() {
    return "Sequia sobre " + camposDeCultivo.size() + " campo(s) de cultivo";
  }
}
