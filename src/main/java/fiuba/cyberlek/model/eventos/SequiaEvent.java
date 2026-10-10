package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.CasilleroModel;
import fiuba.cyberlek.model.Recurso;
import java.util.List;

public class SequiaEvent implements EventoClimaticoModel {

  private final int duracionEnTurnos;

  public SequiaEvent(int duracionEnTurnos) {
    this.duracionEnTurnos = duracionEnTurnos;
  }

  @Override
  public String getNombre() {
    return "Sequia";
  }

  @Override
  public String getDescripcion() {
    return "Los campos de cultivo dejan de producir Trigo por " + duracionEnTurnos + " turnos.";
  }

  @Override
  public ResultadoEventoModel aplicar(ContextoEventoModel contextoEvento) {

    List<CasilleroModel> camposDeCultivo =
        contextoEvento.getTablero().casillerosQueProducen(Recurso.TRIGO);

    ResultadoEventoModel resultado = new ResultadoEventoModel(getNombre(), getDescripcion());
    resultado.agregarConsecuencia(
        "Campos afectados: "
            + camposDeCultivo.size()
            + " | Duracion: "
            + duracionEnTurnos
            + " turnos");
    if (!camposDeCultivo.isEmpty()) {
      resultado.agregarEfectoPersistente(new SequiaEfecto(camposDeCultivo, duracionEnTurnos));
    }
    return resultado;
  }
}
