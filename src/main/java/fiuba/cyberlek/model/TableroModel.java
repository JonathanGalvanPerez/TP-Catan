package fiuba.cyberlek.model;

import java.util.List;

public class TableroModel {

  private GrafoModel grafo;

  public TableroModel(int dimensioNX, int dimensionY) {
    this.grafo = new GrafoBuilderModel(dimensioNX, dimensionY).CrearGrafo();
  }

  private List<CasilleroModel> Casilleros;

  public void crearCamino(String posicionArista) {}

  public void crearAldeaInicial(String posicionVertice) {
    VerticeModel vertice = grafo.obtenerVertice(posicionVertice);

    if (vertice == null) {
      throw new IllegalArgumentException("No existe vertice en esa posicion.");
    }

    if (!tieneDistanciaAceptada(vertice)) {
      throw new IllegalStateException(
          "No se puede crear una aldea aca, rompe la regla de la distancia minima");
    }

    // vertice.construir(jugador);

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
