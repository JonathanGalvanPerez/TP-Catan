package fiuba.cyberlek.model;

import java.util.Map;
import java.util.Objects;

// Unico agente con el que se pueden intercambiar recursos.
// Realiza el intercambio y administra la tasa (que puede modificarse
// temporalmente por eventos climaticos).

public class BancoModel {

  public static final int TASA_DEFAULT = 4;

  private int tasa = TASA_DEFAULT;
  private int turnosRestantesTasaModificada = 0;

  public boolean intercambiar(JugadorModel jugador, Recurso entrega, Recurso recibe) {
    Objects.requireNonNull(jugador, "jugador");
    Objects.requireNonNull(entrega, "entrega");
    Objects.requireNonNull(recibe, "recibe");

    if (entrega == recibe) {
      return false;
    }

    int costo = calcularCosto(1);
    if (!jugador.intentarPagar(Map.of(entrega, costo))) {
      return false;
    }

    jugador.agregar(recibe, 1);
    return true;
  }

  public int calcularCosto(int cantidad) {
    if (cantidad <= 0) {
      throw new IllegalArgumentException("cantidad debe ser > 0, recibido: " + cantidad);
    }
    return tasa * cantidad;
  }

  public int getTasa() {
    return tasa;
  }

  /*
   * Modifica la tasa durante los proximos turnos.
   * La usa Partida cuando ocurre un evento climatico que afecta la tasa.
   */

  public void modificarTasa(int nuevaTasa, int turnos) {
    if (nuevaTasa <= 0) {
      throw new IllegalArgumentException("nuevaTasa debe ser > 0, recibido: " + nuevaTasa);
    }
    if (turnos <= 0) {
      throw new IllegalArgumentException("turnos debe ser > 0, recibido: " + turnos);
    }
    this.tasa = nuevaTasa;
    this.turnosRestantesTasaModificada = turnos;
  }

  /* Lo llama Partida al cerrar cada turno para ir descontando los efectos temporales. */
  public void avanzarTurno() {
    if (turnosRestantesTasaModificada > 0 && --turnosRestantesTasaModificada == 0) {
      tasa = TASA_DEFAULT;
    }
  }
}
