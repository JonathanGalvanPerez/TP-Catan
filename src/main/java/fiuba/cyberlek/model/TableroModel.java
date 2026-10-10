package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TableroModel {

  private GrafoModel grafo;
  private Map<JugadorModel, Set<String>> construcciones;
  private List<CasilleroModel> Casilleros;
  private Map<String, Map<Recurso, Integer>> costos;

  public TableroModel(int dimensioNX, int dimensionY, List<JugadorModel> jugadores) {
    this.grafo = new GrafoBuilderModel(dimensioNX, dimensionY).CrearGrafo();
    this.Casilleros = new ArrayList<>();
    this.construcciones = new HashMap<>();
    for (JugadorModel jugador : jugadores) {
      construcciones.put(jugador, new HashSet<>());
    }

    Map<Recurso, Integer> camino = new HashMap<>(Map.of(Recurso.MADERA, 1, Recurso.ARCILLA, 1));

    Map<Recurso, Integer> aldea =
        new HashMap<>(Map.of(Recurso.MADERA, 1, Recurso.ARCILLA, 1, Recurso.TRIGO, 1));

    Map<Recurso, Integer> ciudad = new HashMap<>(Map.of(Recurso.TRIGO, 2, Recurso.MINERAL, 3));

    this.costos = new HashMap<>(Map.of("camino", camino, "aldea", aldea, "ciudad", ciudad));
  }

  private Boolean sustraerRecursos(JugadorModel jugador, String situacion) {
    return jugador.intentarPagar(this.costos.get(situacion));
  }

  public void crearCamino(String posicionArista, JugadorModel jugador) {
    if (!this.sustraerRecursos(jugador, "camino")) {
      throw new IllegalStateException(
          "No se puede crear una aldea, no tienes los recursos suficientes");
    }

    AristaModel arista = grafo.obtenerArista(posicionArista);
    Boolean estaConstruida = arista.estaConstruida();
    if (!estaConstruida) {
      throw new IllegalStateException("Ya existe construccion en esta arista.");
    }

    Boolean construible = false;

    List<String> verticesAdyacentes = arista.devolverVertices();
    if (this.construcciones.get(jugador).contains(verticesAdyacentes.get(0))
        | this.construcciones.get(jugador).contains(verticesAdyacentes.get(1))) {
      construible = true;
    }

    List<AristaModel> aristasAdyacentes = this.grafo.obtenerAdyacenciasArista(posicionArista);

    for (AristaModel aristaAdyacente : aristasAdyacentes) {
      if (this.construcciones.get(jugador).contains(aristaAdyacente.getPosicion())) {
        construible = true;
        break;
      }
    }

    if (!construible) {
      throw new IllegalStateException("No es posible construir en esta arista.");
    }

    arista.construirCamino();
    Set<String> posiciones = this.construcciones.get(jugador);
    posiciones.add(posicionArista);
    this.construcciones.put(jugador, posiciones);
  }

  public void upgradear(String posicionVertice, JugadorModel jugador) {
    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);

    if (vertice == null) {
      throw new IllegalArgumentException("No existe vertice en esta posicion");
    }

    if (!this.construcciones.get(jugador).contains(posicionVertice)) {
      throw new IllegalStateException("Esta construccion no te pertenece.");
    }

    if (!vertice.mejorarConstruccion()) {
      throw new IllegalStateException("Esta construccion ya esta al maximo.");
    }

    if (!this.sustraerRecursos(jugador, "ciudad")) {
      vertice.degradarConstruccion();
      throw new IllegalStateException(
          "No se mejorar la construccion, no tienes los recursos suficientes");
    }
  }

  public void downgradear(String posicionVertice, JugadorModel jugador) {
    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);

    if (vertice == null) {
      throw new IllegalArgumentException("No existe vertice en esta posicion");
    }

    if (!vertice.existeConstruccion()) {
      throw new IllegalStateException("Este vertice no tiene construccion.");
    }

    if (!this.construcciones.get(jugador).contains(posicionVertice)) {
      throw new IllegalStateException("Esta construccion no te pertenece.");
    }

    vertice.degradarConstruccion();

    if (!vertice.existeConstruccion()) {
      // logica de desuscribir el vertice de las casillas
      Set<String> posiciones = this.construcciones.get(jugador);
      posiciones.remove(posicionVertice);
      this.construcciones.put(jugador, posiciones);
    }
  }

  // public void destruirCamino(String posicionArista) {}

  public void crearAldea(String posicionVertice, JugadorModel jugador) {
    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);

    if (vertice == null) {
      throw new IllegalArgumentException("No existe vertice en esa posicion.");
    }

    if (construccionBloqueadaEnVertice(posicionVertice)) {
      throw new IllegalStateException("Esa zona esta bloqueada por una tormenta.");
    }

    if (!this.sustraerRecursos(jugador, "aldea")) {
      throw new IllegalStateException(
          "No se puede crear una aldea, no tienes los recursos suficientes");
    }

    if (!tieneDistanciaAceptada(vertice)) {
      throw new IllegalStateException(
          "No se puede crear una aldea aca, rompe la regla de la distancia minima.");
    }

    List<AristaModel> aristasAdyacentes = grafo.obtenerAdyacenciasVertice(posicionVertice);
    Boolean tieneCaminoPropio = false;

    for (AristaModel aristaAdyacente : aristasAdyacentes) {
      if (this.construcciones.get(jugador).contains(aristaAdyacente.getPosicion())) {
        tieneCaminoPropio = true;
        break;
      }
    }

    if (!tieneCaminoPropio) {
      throw new IllegalStateException("No es posible construir en este sitio.");
    }

    vertice.construir(jugador);

    Set<String> posiciones = this.construcciones.get(jugador);
    posiciones.add(posicionVertice);
    this.construcciones.put(jugador, posiciones);

    suscribirVerticeACasilleros(vertice);
  }

  public void crearAldeaInicial(String posicionVertice, JugadorModel jugador) {
    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);

    if (vertice == null) {
      throw new IllegalArgumentException("No existe vertice en esa posicion.");
    }

    if (construccionBloqueadaEnVertice(posicionVertice)) {
      throw new IllegalStateException("Esa zona esta bloqueada por una tormenta.");
    }

    if (!tieneDistanciaAceptada(vertice)) {
      throw new IllegalStateException(
          "No se puede crear una aldea aca, rompe la regla de la distancia minima.");
    }

    vertice.construir(jugador);

    Set<String> posiciones = this.construcciones.get(jugador);
    posiciones.add(posicionVertice);
    this.construcciones.put(jugador, posiciones);

    suscribirVerticeACasilleros(vertice);
  }

  private boolean tieneDistanciaAceptada(VerticeModel vertice) {
    if (vertice.existeConstruccion()) {
      return false;
    }

    List<AristaModel> aristas = grafo.obtenerAdyacenciasVertice(vertice.getPosicion());

    for (AristaModel arista : aristas) {
      List<String> verticesConectados = arista.devolverVertices();
      String posicionVecina =
          verticesConectados.get(0).equals(vertice.getPosicion())
              ? verticesConectados.get(1)
              : verticesConectados.get(0);
      VerticeModel verticeVecino = grafo.obtenerVertice(posicionVecina);

      if (verticeVecino.existeConstruccion()) {
        return false;
      }
    }

    return true;
  }

  public JugadorModel obtenerDuenioDeVertice(String posicionVertice) {
    for (Map.Entry<JugadorModel, Set<String>> entrada : construcciones.entrySet()) {
      if (entrada.getValue().contains(posicionVertice)) return entrada.getKey();
    }
    return null;
  }

  public List<CasilleroModel> getCasilleros() {
    return List.copyOf(Casilleros);
  }

  public void agregarCasillero(CasilleroModel casillero) {
    this.Casilleros.add(casillero);
  }

  public boolean construccionBloqueadaEnVertice(String posicionVertice) {
    for (CasilleroModel casillero : Casilleros) {
      if (!casillero.tieneConstruccionBloqueada()) continue;
      for (VerticeModel vertice : casillero.getVertices()) {
        if (vertice.getPosicion().equals(posicionVertice)) return true;
      }
    }
    return false;
  }

  private void suscribirVerticeACasilleros(VerticeModel vertice) {
    for (CasilleroModel casillero : Casilleros) {
      for (VerticeModel verticeDelCasillero : casillero.getVertices()) {
        if (verticeDelCasillero.equals(vertice)) {
          casillero.suscribirVertice(vertice);
        }
      }
    }
  }

  /**
   * Devuelve los jugadores que tienen una construccion (Aldea o Ciudad) en algun vertice de la
   * casilla indicada. Sin duplicados.
   */
  public List<JugadorModel> obtenerDueniosDeCasillero(CasilleroModel casillero) {
    Set<JugadorModel> duenios = new LinkedHashSet<>();
    for (VerticeModel vertice : casillero.getVertices()) {
      if (!vertice.existeConstruccion()) continue;
      JugadorModel duenio = obtenerDuenioDeVertice(vertice.getPosicion());
      if (duenio != null) duenios.add(duenio);
    }
    return List.copyOf(duenios);
  }

  /**
   * Aplica el efecto del Terremoto sobre un vertice: - Ciudad -> Aldea: el jugador pierde 1 PV. -
   * Aldea -> sin construccion: el jugador pierde 1 PV y el vertice se desuscribe de sus casilleros
   * para no recibir mas recursos.
   */
  public void aplicarTerremoto(String posicionVertice) {
    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);
    if (vertice == null || !vertice.existeConstruccion()) return;

    JugadorModel duenio = obtenerDuenioDeVertice(posicionVertice);
    if (duenio == null) return;

    vertice.degradarConstruccion();
    duenio.asignarPuntoVictoria(-1);

    if (!vertice.existeConstruccion()) {
      // Aldea destruida: limpiamos registro del jugador y desuscribimos del casillero.
      Set<String> posiciones = construcciones.get(duenio);
      posiciones.remove(posicionVertice);
      construcciones.put(duenio, posiciones);

      for (CasilleroModel casillero : Casilleros) {
        for (VerticeModel v : casillero.getVertices()) {
          if (v.equals(vertice)) {
            casillero.desuscribirVertice(vertice);
          }
        }
      }
    }
  }

  public List<Recurso> recursosProducibles() {
    Set<Recurso> recursos = new LinkedHashSet<>();
    for (CasilleroModel casillero : Casilleros) {
      Recurso r = casillero.recursoQueProduce();
      if (r != Recurso.NADA) {
        recursos.add(r);
      }
    }
    return List.copyOf(recursos);
  }

  /**
   * Devuelve la unica casilla de Desierto del tablero (o vacio si aun no se cargo ninguna). La usa
   * JuegoModel para ubicar al Saqueador al inicio.
   */
  public Optional<CasilleroModel> obtenerCasilleroDesierto() {
    for (CasilleroModel casillero : Casilleros) {
      if (casillero.recursoQueProduce() == Recurso.NADA) {
        return Optional.of(casillero);
      }
    }
    return Optional.empty();
  }

  public List<VerticeModel> verticesConConstruccionEn(List<CasilleroModel> casilleros) {
    List<VerticeModel> vertices = new ArrayList<>();
    for (CasilleroModel casillero : casilleros) {
      for (VerticeModel vertice : casillero.getVertices()) {
        if (vertice.existeConstruccion() && !vertices.contains(vertice)) {
          vertices.add(vertice);
        }
      }
    }
    return vertices;
  }

  public List<CasilleroModel> casillerosQueProducen(Recurso recurso) {
    List<CasilleroModel> resultado = new ArrayList<>();
    for (CasilleroModel casillero : Casilleros) {
      if (casillero.recursoQueProduce() == recurso) {
        resultado.add(casillero);
      }
    }
    return resultado;
  }
}
