package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.RandomizadorModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GestorEventosModel {

  private final RandomizadorModel randomizador;
  private final double probabilidadDeDisparo;
  private final List<EventoClimaticoModel> eventosDisponibles;
  private final List<EfectoModel> efectosActivos = new ArrayList<>();

  public GestorEventosModel(
      RandomizadorModel randomizador,
      double probabilidadDeDisparo,
      List<EventoClimaticoModel> eventosDisponibles) {
    this.randomizador = randomizador;
    this.probabilidadDeDisparo = probabilidadDeDisparo;
    this.eventosDisponibles = List.copyOf(eventosDisponibles);
  }

  public Optional<ResultadoEventoModel> dispararEvento(ContextoEventoModel contextoEvento) {
    if (!randomizador.ocurre(probabilidadDeDisparo)) {
      return Optional.empty();
    }
    EventoClimaticoModel evento = randomizador.elegirAlAzar(eventosDisponibles).orElse(null);
    if (evento == null) {
      return Optional.empty();
    }
    ResultadoEventoModel resultado = evento.aplicar(contextoEvento);
    for (EfectoModel efecto : resultado.getEfectosPersistentes()) {
      efecto.iniciar();
      efectosActivos.add(efecto);
    }
    return Optional.of(resultado);
  }

  public void avanzarTurno() {
    Iterator<EfectoModel> iterador = efectosActivos.iterator();
    while (iterador.hasNext()) {
      EfectoModel efecto = iterador.next();
      efecto.avanzarUnTurno();
      if (efecto.expiro()) {
        iterador.remove();
      }
    }
  }

  public List<EfectoModel> getEfectosActivos() {
    return List.copyOf(efectosActivos);
  }
}
