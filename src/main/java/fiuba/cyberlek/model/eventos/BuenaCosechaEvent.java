package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.CasilleroModel;
import fiuba.cyberlek.model.Recurso;
import java.util.List;

public class BuenaCosechaEvent implements EventoClimaticoModel {

  @Override
  public String getNombre() {
    return "Buena Cosecha";
  }

  @Override
  public String getDescripcion() {
    return "El clima favorece la produccion: un recurso duplica su produccion.";
  }

  @Override
  public ResultadoEventoModel aplicar(ContextoEventoModel contextoEvento) {
    List<Recurso> recursosDisponibles = contextoEvento.getTablero().recursosProducibles();

    Recurso recursoElegido =
        contextoEvento.getRandomizador().elegirAlAzar(recursosDisponibles).orElse(Recurso.TRIGO);

    List<CasilleroModel> casillerosAfectados =
        contextoEvento.getTablero().casillerosQueProducen(recursoElegido);

    ResultadoEventoModel resultado = new ResultadoEventoModel(getNombre(), getDescripcion());
    resultado.agregarConsecuencia("Recurso duplicado: " + recursoElegido.getNombre());
    resultado.agregarConsecuencia("Casillas afectadas: " + casillerosAfectados.size());
    if (!casillerosAfectados.isEmpty()) {
      resultado.agregarEfectoPersistente(
          new BuenaCosechaEfecto(casillerosAfectados, recursoElegido));
    }
    return resultado;
  }
}
