package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.CasilleroModel;
import java.util.List;

public class TormentaEvent implements EventoClimaticoModel {

  private final int duracionEnTurnos;
  private final int cantidadDeCasillasAfectadas;

  public TormentaEvent(int duracionEnTurnos, int cantidadDeCasillasAfectadas) {
    this.duracionEnTurnos = duracionEnTurnos;
    this.cantidadDeCasillasAfectadas = cantidadDeCasillasAfectadas;
  }

  @Override
  public String getNombre() {
    return "Tormenta";
  }

  @Override
  public String getDescripcion() {
    return "Una tormenta azota la region. Casillas intransitables por "
        + duracionEnTurnos
        + " turnos.";
  }

  @Override
  public ResultadoEventoModel aplicar(ContextoEventoModel contextoEvento) {
    List<CasilleroModel> zonaAfectada =
        contextoEvento
            .getRandomizador()
            .elegirVariosSinRepeticion(
                contextoEvento.getTablero().getCasilleros(), cantidadDeCasillasAfectadas);

    ResultadoEventoModel resultado = new ResultadoEventoModel(getNombre(), getDescripcion());
    resultado.agregarConsecuencia("Casillas afectadas: " + zonaAfectada.size());
    if (!zonaAfectada.isEmpty()) {
      resultado.agregarEfectoPersistente(new TormentaEfecto(zonaAfectada, duracionEnTurnos));
    }
    return resultado;
  }
}
