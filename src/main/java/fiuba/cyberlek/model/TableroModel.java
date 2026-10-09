package fiuba.cyberlek.model;

import java.util.*;

public class TableroModel {

  private GrafoModel grafo;
  private Map<JugadorModel, Set<String>> construcciones;
  private List<List<CasilleroModel>> casilleros;
  private Map<String, Map<Recurso, Integer>> costos;
  private Map<VerticeModel, List<CasilleroModel>> verticesCasilleros;
  private int dimensionX;
  private int dimensionY;

  public TableroModel(int dimensionX, int dimensionY, List<JugadorModel> jugadores,List<List<CasilleroModel>> casilleros) {
    this.grafo = new GrafoBuilderModel(dimensionX, dimensionY).CrearGrafo();
    this.casilleros = casilleros;
    this.dimensionX = dimensionX;
    this.dimensionY = dimensionY;
    this.construcciones = new HashMap<>();
    this.asociarVerticesACasilleros();
    for (JugadorModel jugador : jugadores) {
      construcciones.put(jugador, new HashSet<>());
    }

    Map<Recurso, Integer> camino = new HashMap<>(Map.of(Recurso.MADERA, 1, Recurso.ARCILLA, 1));

    Map<Recurso, Integer> aldea =
        new HashMap<>(Map.of(Recurso.MADERA, 1, Recurso.ARCILLA, 1, Recurso.TRIGO, 1));

    Map<Recurso, Integer> ciudad = new HashMap<>(Map.of(Recurso.TRIGO, 2, Recurso.MINERAL, 3));

    this.costos = new HashMap<>(Map.of("camino", camino, "aldea", aldea, "ciudad", ciudad));
  }


  private void asociarVerticesACasilleros() {

    List<CasilleroModel> casillerosDeArriba = null ;
    List<CasilleroModel> casillerosDeAbajo = null ;
    List<VerticeModel> vertices = this.grafo.obtenerVertices();
    int indice = 0;

    for (VerticeModel vertice : vertices){
      this.verticesCasilleros.put(vertice, new ArrayList<>());
    }

    casillerosDeAbajo.getFirst().agregarVertice(vertices.getFirst());
    List<CasilleroModel> casillas = verticesCasilleros.get(vertices.getFirst());
    casillas.add(casillerosDeAbajo.getFirst());
    verticesCasilleros.put(vertices.getFirst(),casillas);


    for (int i = 0; i < this.dimensionY; i++){
      VerticeModel vertice = vertices.get(indice);
      casillas = verticesCasilleros.get(vertice);

      if ( i > 0){
        casillerosDeArriba = this.casilleros.get(i - 1);
        casillerosDeArriba.getFirst().agregarVertice(vertice);
        casillas.add(casillerosDeArriba.getFirst());
      }

      casillerosDeAbajo = this.casilleros.get(i);
      casillerosDeAbajo.getFirst().agregarVertice(vertice);

      indice ++;

      casillas.add(casillerosDeAbajo.getFirst());
      verticesCasilleros.put(vertice,casillas);


      for (int j = 1; j < this.dimensionX; j++) {
        vertice = vertices.get(indice);
        casillas = verticesCasilleros.get(vertice);

        if (i > 0) {
          casillerosDeArriba.get(j-1).agregarVertice(vertice);
          casillerosDeArriba.get(j).agregarVertice(vertice);
          casillas.add(casillerosDeArriba.get(j - 1));
          casillas.add(casillerosDeArriba.get(j));
        }

        casillerosDeAbajo.get(j-1).agregarVertice(vertice);
        casillerosDeAbajo.get(j).agregarVertice(vertice);

        casillas.add(casillerosDeAbajo.get(j-1));
        casillas.add(casillerosDeAbajo.get(j));
        verticesCasilleros.put(vertice,casillas);


        indice ++;
      }

      vertice = vertices.get(indice);
      casillas = verticesCasilleros.get(vertice);

      if (i > 0) {
        casillerosDeArriba.get(this.dimensionX - 1).agregarVertice(vertice);
        casillas.add(casillerosDeArriba.get(this.dimensionX - 1));

      }
      casillerosDeAbajo.get(this.dimensionX - 1).agregarVertice(vertice);
      casillas.add(casillerosDeAbajo.get(this.dimensionX - 1));
      indice ++;
      verticesCasilleros.put(vertice,casillas);

    }


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

      List<CasilleroModel> casillerosAsociados = verticesCasilleros.get(vertice);
      for (CasilleroModel casillero : casillerosAsociados){
        casillero.desuscribirVertice(vertice);
      }

      Set<String> posiciones = this.construcciones.get(jugador);
      posiciones.remove(posicionVertice);
      this.construcciones.put(jugador, posiciones);
    }
  }

  // public void destruirCamino(String posicionArista) {}

  public void crearAldea(String posicionVertice, JugadorModel jugador) {
    if (!this.sustraerRecursos(jugador, "aldea")) {
      throw new IllegalStateException(
          "No se puede crear una aldea, no tienes los recursos suficientes");
    }

    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);

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

    List<CasilleroModel> casillerosAsociados = verticesCasilleros.get(vertice);
    for (CasilleroModel casillero : casillerosAsociados){
      casillero.suscribirVertice(vertice);
    }
  }

  public void crearAldeaInicial(String posicionVertice, JugadorModel jugador) {
    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);

    if (vertice == null) {
      throw new IllegalArgumentException("No existe vertice en esa posicion.");
    }

    if (!tieneDistanciaAceptada(vertice)) {
      throw new IllegalStateException(
          "No se puede crear una aldea aca, rompe la regla de la distancia minima.");
    }

    vertice.construir(jugador);

    Set<String> posiciones = this.construcciones.get(jugador);
    posiciones.add(posicionVertice);
    this.construcciones.put(jugador, posiciones);


    List<CasilleroModel> casillerosAsociados = verticesCasilleros.get(vertice);
    for (CasilleroModel casillero : casillerosAsociados){
      casillero.suscribirVertice(vertice);
    }
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
}
