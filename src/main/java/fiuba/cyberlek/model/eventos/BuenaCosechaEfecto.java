package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.CasilleroModel;
import fiuba.cyberlek.model.Recurso;
import java.util.List;

public class BuenaCosechaEfecto extends EfectoModel {

  private final List<CasilleroModel> casillerosAfectados;
  private final Recurso recursoDuplicado;

  public BuenaCosechaEfecto(List<CasilleroModel> casillerosAfectados, Recurso recursoDuplicado) {
    super(2);
    this.casillerosAfectados = List.copyOf(casillerosAfectados);
    this.recursoDuplicado = recursoDuplicado;
  }

  @Override
  protected void aplicar() {
    for (CasilleroModel casillero : casillerosAfectados) {
      casillero.duplicarProduccion();
    }
  }

  @Override
  protected void revertir() {
    for (CasilleroModel casillero : casillerosAfectados) {
      casillero.restaurarProduccion();
    }
  }

  @Override
  public String getDescripcion() {
    return "Buena cosecha de " + recursoDuplicado.getNombre() + " (produccion x2)";
  }
}
