package fiuba.cyberlek.model;

import fiuba.cyberlek.model.eventos.BuenaCosechaEvent;
import fiuba.cyberlek.model.eventos.ContextoEventoModel;
import fiuba.cyberlek.model.eventos.GestorEventosModel;
import fiuba.cyberlek.model.eventos.ResultadoEventoModel;
import fiuba.cyberlek.model.eventos.SaqueoEvent;
import fiuba.cyberlek.model.eventos.SequiaEvent;
import fiuba.cyberlek.model.eventos.TerremotoEvent;
import fiuba.cyberlek.model.eventos.TormentaEvent;
import java.util.List;
import java.util.Optional;

public class JuegoModel {

  private final AdministradorDeTurnosModel administradorDeTurnos;
  private final TableroModel tablero;
  private final SaqueadorModel saqueador;
  private final RandomizadorModel randomizador;
  private final GestorEventosModel gestorEventos;
  private final List<JugadorModel> jugadores;

  private boolean juegoTerminado = false;
  private boolean movimientoSaqueadorPendiente = false;

  public JuegoModel(List<JugadorModel> jugadores) {
    if (jugadores.size() < 2 || jugadores.size() > 4) {
      throw new IllegalArgumentException("La cantidad de jugadores debe ser entre 2 y 4");
    }
    this.jugadores = List.copyOf(jugadores);
    this.administradorDeTurnos = new AdministradorDeTurnosModel(jugadores);
    this.tablero = new TableroModel(10, 10, jugadores);

    this.randomizador = new RandomizadorModel(2, 6);

    CasilleroModel desierto =
        tablero
            .obtenerCasilleroDesierto()
            .orElseThrow(
                () ->
                    new IllegalStateException("El tablero no tiene casilla de Desierto cargada."));
    this.saqueador = new SaqueadorModel(desierto);

    this.gestorEventos =
        new GestorEventosModel(
            randomizador,
            0.3,
            List.of(
                new SequiaEvent(2),
                new TormentaEvent(2, 3),
                new SaqueoEvent(),
                new BuenaCosechaEvent(),
                new TerremotoEvent(3)));
  }

  // ------------------------------------------------------------------
  // Turno
  // ------------------------------------------------------------------

  public void iniciarTurno() {
    producirRecursos();
    activarEvento();
  }

  private void activarEvento() {
    ContextoEventoModel contexto =
        new ContextoEventoModel(
            jugadores, tablero, obtenerJugadorActual(), saqueador, randomizador);
    Optional<ResultadoEventoModel> resultado = gestorEventos.dispararEvento(contexto);
    resultado.ifPresent(this::aplicarResultadoEvento);
  }

  private void aplicarResultadoEvento(ResultadoEventoModel resultado) {
    if (resultado.isRequiereMoverSaqueador()) {
      this.movimientoSaqueadorPendiente = true;
    }
    // Nota: la notificacion al usuario (consecuencias) es responsabilidad de la vista/controller.
  }

  private void producirRecursos() {
    int tirada = randomizador.tirarDados();
    for (CasilleroModel casillero : tablero.getCasilleros()) {
      casillero.intentarFarmear(tirada);
    }
  }

  public void terminarTurno() {
    gestorEventos.avanzarTurno();
    administradorDeTurnos.siguienteTurno();
  }

  // ------------------------------------------------------------------
  // Saqueador
  // ------------------------------------------------------------------

  public List<JugadorModel> victimasPosiblesDeSaqueo(
      CasilleroModel destino, JugadorModel atacante) {
    return tablero.obtenerDueniosDeCasillero(destino).stream()
        .filter(j -> !j.equals(atacante))
        .filter(j -> !j.estaEliminado())
        .toList();
  }

  public Optional<Recurso> saquear(
      CasilleroModel destino, JugadorModel atacante, JugadorModel victimaElegida) {
    saqueador.mover(destino);
    this.movimientoSaqueadorPendiente = false;

    if (victimaElegida == null) {
      return Optional.empty();
    }
    Optional<Recurso> robada = victimaElegida.quitarCartaAlAzar(randomizador);
    robada.ifPresent(r -> atacante.agregar(r, 1));
    return robada;
  }

  public boolean hayMovimientoSaqueadorPendiente() {
    return movimientoSaqueadorPendiente;
  }

  public SaqueadorModel getSaqueador() {
    return saqueador;
  }

  // ------------------------------------------------------------------
  // Resto

  public JugadorModel obtenerJugadorActual() {
    return administradorDeTurnos.obtenerJugadorActual();
  }

  public void rendirse() {
    obtenerJugadorActual().eliminar();
  }

  public boolean termino() {
    return juegoTerminado;
  }
}
