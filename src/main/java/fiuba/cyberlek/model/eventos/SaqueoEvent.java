package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.JugadorModel;

public class SaqueoEvent implements EventoClimaticoModel {

  @Override
  public String getNombre() {
    return "Saqueo";
  }

  @Override
  public String getDescripcion() {
    return "Una niebla densa cubre la region. El Saqueador ataca por sorpresa.";
  }

  @Override
  public ResultadoEventoModel aplicar(ContextoEventoModel contextoEvento) {
    ResultadoEventoModel resultado = new ResultadoEventoModel(getNombre(), getDescripcion());

    JugadorModel activo = contextoEvento.getJugadorActivo();
    int descartadas = activo.descartarHasta(1);
    if (descartadas > 0) {
      resultado.agregarConsecuencia(activo.getNombre() + " descarto 1 carta.");
    } else {
      resultado.agregarConsecuencia(activo.getNombre() + " no tenia cartas para descartar.");
    }

    resultado.marcarRequiereMoverSaqueador();
    resultado.agregarConsecuencia(activo.getNombre() + " debe mover al Saqueador.");

    return resultado;
  }
}
