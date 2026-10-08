package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class GrafoModel {
  private List<List<AristaModel>> listaAdyacencias;
  private Map<String, VerticeModel> indexacionVertices;
  private Map<String, AristaModel> indexacionAristas;
  private List<VerticeModel> vertices;

  public GrafoModel() {
    this.indexacionAristas = new HashMap<>();
    this.indexacionVertices = new HashMap<>();
    this.listaAdyacencias = new ArrayList<>();
    this.vertices = new ArrayList<>();
  }

  private boolean existeVertice(String posicion) {
    return indexacionVertices.containsKey(posicion);
  }

  private boolean existeArista(String posicion) {
    return indexacionAristas.containsKey(posicion);
  }

  // compilacion
  public void agregarVertice(String posicion, VerticeModel vertice) {
    this.indexacionVertices.put(posicion, vertice);
    this.vertices.add(vertice);
    this.listaAdyacencias.add(vertice.getIndiceAdyacencia(), new ArrayList<>());
  }

  public void agregarArista(
      String posicion, AristaModel arista, VerticeModel vertice1, VerticeModel vertice2) {
    this.indexacionAristas.put(posicion, arista);
    this.listaAdyacencias.get(vertice1.getIndiceAdyacencia()).add(arista);
    this.listaAdyacencias.get(vertice2.getIndiceAdyacencia()).add(arista);
  }

  // runtime
  public List<AristaModel> obtenerAdyacenciasVertice(String posicion) {
    if (!this.existeVertice(posicion)) {
      return null;
    }

    int indice = this.indexacionVertices.get(posicion).getIndiceAdyacencia();
    return List.copyOf(this.listaAdyacencias.get(indice));
  }

  public List<AristaModel> obtenerAdyacenciasArista(String posicion) {
    if (!this.existeArista(posicion)) {
      return null;
    }
    AristaModel arista = this.indexacionAristas.get(posicion);
    List<String> verticesAdyacentes = arista.devolverVertices();
    List<AristaModel> adyacenciasVertice1 =
        this.obtenerAdyacenciasVertice(verticesAdyacentes.get(0));
    List<AristaModel> adyacenciasVertice2 =
        this.obtenerAdyacenciasVertice(verticesAdyacentes.get(1));

    adyacenciasVertice1.remove(arista);
    adyacenciasVertice2.remove(arista);

    List<AristaModel> adyacencias = new ArrayList<>(adyacenciasVertice1);
    adyacencias.addAll(adyacenciasVertice2);
    return adyacencias;
  }

  public List<VerticeModel> obtenerVertices() {
    return List.copyOf(this.vertices);
  }

  public VerticeModel obtenerVertice(String posicion) {
    if (!this.existeVertice(posicion)) {
      return null;
    }
    return this.indexacionVertices.get(posicion);
  }

  public AristaModel obtenerArista(String posicion) {
    if (!this.existeArista(posicion)) {
      return null;
    }
    return this.indexacionAristas.get(posicion);
  }

  public void iterar(Predicate<VerticeModel> funcionVisitar) {
    for (VerticeModel vertice : this.vertices) {
      if (!funcionVisitar.test(vertice)) {
        break;
      }
    }
  }
}
