package fiuba.cyberlek.model;

import java.util.List;

public class GrafoBuilderModel {
  private GrafoModel Grafo;
  private int dimensionX;
  private int dimensionY;

  public GrafoBuilderModel(int dimensionX, int dimensionY) {
    this.Grafo = new GrafoModel();
    this.dimensionX = dimensionX + 1;
    this.dimensionY = dimensionY + 1;
  }

  private void crearVertices() {
    int indiceAdyacencia = 0;
    for (int i = 0; i < dimensionY * 2; i += 2) {
      for (int j = 0; j < dimensionX * 2; j += 2) {
        String posicion = j + "" + i;
        VerticeModel vertice = new VerticeModel(indiceAdyacencia, posicion);
        this.Grafo.agregarVertice(posicion, vertice);
        indiceAdyacencia += 1;
      }
    }
  }

  private void crearAristasHorizontales() {
    int indiceX = 0;
    int indiceY = 0;
    List<VerticeModel> vertices = this.Grafo.obtenerVertices();
    VerticeModel anterior = null;
    for (VerticeModel vertice : vertices) {

      if (indiceX > 0 & indiceX % (this.dimensionX * 2 - 1) == 0) {
        indiceY += 2;
        indiceX = 0;
      }

      String posicion = indiceX + "" + indiceY;
      if (indiceX > 0 & indiceX < this.dimensionX * 2 - 1) {
        AristaModel arista = new AristaModel(posicion, vertice, anterior);
        this.Grafo.agregarArista(posicion, arista, vertice, anterior);
      }
      if (indiceX == 0) {
        indiceX += 1;
      } else {
        indiceX += 2;
      }
      anterior = vertice;
    }
  }

  private void crearAristasVerticales() {
    List<VerticeModel> vertices = this.Grafo.obtenerVertices();

    for (int i = 0; i < this.dimensionX; i++) {
      VerticeModel anterior = vertices.get(i);
      int aux = 1;
      for (int j = i + this.dimensionX;
          j < this.dimensionY * this.dimensionX;
          j += this.dimensionX) {
        VerticeModel actual = vertices.get(j);
        int posicionY = aux;
        String posicion = i * 2 + "" + posicionY;
        AristaModel arista = new AristaModel(posicion, actual, anterior);
        this.Grafo.agregarArista(posicion, arista, actual, anterior);
        anterior = actual;
        aux += 2;
      }
    }
  }

  public GrafoModel CrearGrafo() {
    this.crearVertices();
    this.crearAristasHorizontales();
    this.crearAristasVerticales();
    return this.Grafo;
  }
}
