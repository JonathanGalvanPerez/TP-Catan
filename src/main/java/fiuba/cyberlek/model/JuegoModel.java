package fiuba.cyberlek.model;

import java.util.List;

public class JuegoModel {
  private AdministradorDeTurnosModel administradorDeTurnos;
  private TableroModel tablero;
  private BancoModel banco;
  private List<JugadorModel> jugadores;
  private boolean juegoTerminado = false;

  public JuegoModel(List<JugadorModel> jugadores) {
    if (jugadores.size() < 2 || jugadores.size() > 4) {
      throw new IllegalArgumentException("La cantidad de jugadores debe ser entre 2 y 4");
    }
    this.administradorDeTurnos = new AdministradorDeTurnosModel(jugadores);
    this.banco = new BancoModel();
    this.jugadores = jugadores;
    this.tablero = new TableroModel(10, 10, jugadores);
  }

  public void crearAldeaInicial(String posicionVertice, JugadorModel jugador) {
    tablero.crearAldeaInicial(posicionVertice, jugador);
  }

  public void crearCaminoInicial(String posicionArista, JugadorModel jugador) {
    tablero.crearCamino(posicionArista, jugador);
  }

  public JugadorModel obtenerJugadorActual() {
    return administradorDeTurnos.obtenerJugadorActual();
  }

  public void iniciarTurno() {
    this.activarEvento();
    this.producirRecursos();
  }

  public void terminarTurno() {
    administradorDeTurnos.siguienteTurno();
  }

  public void rendirse() {
    JugadorModel jugadorActual = obtenerJugadorActual();
    jugadorActual.eliminar();
  }

  public boolean termino() {
    return juegoTerminado;
  }

  public void crearAldea(String posicionVertice) {
    JugadorModel jugadorActual = obtenerJugadorActual();
    tablero.crearAldea(posicionVertice, jugadorActual);
  }

  public void crearCamino(String posicionArista) {
    JugadorModel jugadorActual = obtenerJugadorActual();
    tablero.crearCamino(posicionArista, jugadorActual);
  }

  public void upgradear(String posicionVertice) {
    JugadorModel jugadorActual = obtenerJugadorActual();
    tablero.upgradear(posicionVertice, jugadorActual);
  }

  public void comerciar(Recurso recursoOfrecido, Recurso recursoDeseado) {
    JugadorModel jugadorActual = obtenerJugadorActual();
    if (!banco.intercambiar(jugadorActual, recursoOfrecido, recursoDeseado)) {
      throw new IllegalStateException("No se puede realizar el comercio.");
    }
  }

  private void activarEvento() {
    // Lógica para activar un evento en el juego
    // ...
  }

  private void producirRecursos() {
    // Lógica para producir recursos en el juego
    // ...
  }
}
