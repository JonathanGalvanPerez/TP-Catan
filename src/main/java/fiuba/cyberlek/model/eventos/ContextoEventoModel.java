package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.JugadorModel;
import fiuba.cyberlek.model.RandomizadorModel;
import fiuba.cyberlek.model.SaqueadorModel;
import fiuba.cyberlek.model.TableroModel;
import java.util.List;

public class ContextoEventoModel {

  private final List<JugadorModel> jugadores;
  private final TableroModel tablero;
  private final JugadorModel jugadorActivo;
  private final SaqueadorModel saqueador;
  private final RandomizadorModel randomizador;

  public ContextoEventoModel(
      List<JugadorModel> jugadores,
      TableroModel tablero,
      JugadorModel jugadorActivo,
      SaqueadorModel saqueador,
      RandomizadorModel randomizador) {
    this.jugadores = jugadores;
    this.tablero = tablero;
    this.jugadorActivo = jugadorActivo;
    this.saqueador = saqueador;
    this.randomizador = randomizador;
  }

  public List<JugadorModel> getJugadores() {
    return jugadores;
  }

  public TableroModel getTablero() {
    return tablero;
  }

  public JugadorModel getJugadorActivo() {
    return jugadorActivo;
  }

  public SaqueadorModel getSaqueador() {
    return saqueador;
  }

  public RandomizadorModel getRandomizador() {
    return randomizador;
  }
}
