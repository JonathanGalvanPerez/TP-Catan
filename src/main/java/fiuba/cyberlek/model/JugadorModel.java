package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class JugadorModel {

  private final String nombre;
  private int puntosVictoria = 0;
  private boolean eliminado = false;
  private final Map<Recurso, Integer> recursos = new EnumMap<>(Recurso.class);

  public JugadorModel(String nombre) {
    this.nombre = Objects.requireNonNull(nombre, "nombre");
    if (nombre.isBlank()) {
      throw new IllegalArgumentException("El nombre no puede estar vacio");
    }
    for (Recurso r : Recurso.values()) {
      if (r != Recurso.NADA) {
        recursos.put(r, 0);
      }
    }
  }

  public void asignarPuntoVictoria(int puntoVictoria) {
    this.puntosVictoria += puntoVictoria;
  }

  public String getNombre() {
    return nombre;
  }

  // ---------------------------------------------------------------------
  // Comandos
  // ---------------------------------------------------------------------

  public void agregar(Recurso recurso, int cantidad) {
    Objects.requireNonNull(recurso, "recurso");
    if (cantidad <= 0) {
      return;
    }
    recursos.merge(recurso, cantidad, Integer::sum);
  }

  /**
   * Intenta pagar un costo completo de forma ATOMICA. Si no alcanza, NO modifica ningun recurso y
   * devuelve false.
   */
  public boolean intentarPagar(Map<Recurso, Integer> costo) {
    Objects.requireNonNull(costo, "costo");
    for (Map.Entry<Recurso, Integer> entry : costo.entrySet()) {
      if (get(entry.getKey()) < entry.getValue()) {
        return false;
      }
    }
    costo.forEach((r, c) -> recursos.merge(r, -c, Integer::sum));
    return true;
  }

  /**
   * Descarta hasta n cantidad de cartas. Devuelve cuantas pudo descartar realmente (puede ser menos
   * si no tenia suficientes). Si devolvio menos, Partida decide que hacer (perder construccion,
   * marcar quiebra, etc.).
   */
  public int descartarHasta(int cantidad) {
    if (cantidad <= 0) {
      return 0;
    }
    int descartadas = 0;
    for (Recurso r : Recurso.values()) {
      while (descartadas < cantidad && get(r) > 0) {
        recursos.merge(r, -1, Integer::sum);
        descartadas++;
      }
      if (descartadas == cantidad) {
        break;
      }
    }
    return descartadas;
  }

  /**
   * Quita UNA carta al azar y la devuelve. Devuelve Optional.empty()} si no tenia cartas: es un
   * caso normal, no un error. Usado por el Saqueador al robar.
   */
  public Optional<Recurso> quitarCartaAlAzar(RandomizadorModel randomizador) {
    Objects.requireNonNull(randomizador, "randomizador");
    List<Recurso> cartas = new ArrayList<>(totalCartas());
    recursos.forEach(
        (r, c) -> {
          for (int i = 0; i < c; i++) {
            cartas.add(r);
          }
        });
    return randomizador
        .elegirAlAzar(cartas)
        .map(
            r -> {
              recursos.merge(r, -1, Integer::sum);
              return r;
            });
  }

  // ---------------------------------------------------------------------
  // Consultas
  // ---------------------------------------------------------------------

  public int get(Recurso recurso) {
    Objects.requireNonNull(recurso, "recurso");
    return recursos.getOrDefault(recurso, 0);
  }

  public int totalCartas() {
    return recursos.values().stream().mapToInt(Integer::intValue).sum();
  }

  public int getPuntosVictoria() {
    return puntosVictoria;
  }

  public boolean estaEliminado() {
    return eliminado;
  }

  public void eliminar() {
    this.eliminado = true;
  }

  /**
   * Vista inmutable de las cartas en mano. SOLO para mostrar por consola. NO usar para tomar
   * decisiones de dominio (para eso esta {@link #intentarPagar} u operaciones especificas).
   */
  public Map<Recurso, Integer> getRecursos() {
    return Collections.unmodifiableMap(recursos);
  }
}
