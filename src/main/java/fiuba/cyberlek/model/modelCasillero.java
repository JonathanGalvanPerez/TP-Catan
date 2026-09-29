package fiuba.cyberlek.model;

import java.util.List;

public class modelCasillero {

  private int NumeroCasillero;
  private Boolean tieneSaqueador;
  private modelTerreno terreno;
  private List<modelVertice> vertices;
  private List<modelVerticeConstruido> verticesConstruidos;

  public modelCasillero(int numeroFarmeo, modelTerreno terreno, List<modelVertice> vertices) {
    this.NumeroCasillero = numeroFarmeo;
    this.tieneSaqueador = false;
    this.vertices = vertices;
  }

  public void intentarFarmear(int NumeroDado) {
    if (NumeroDado == NumeroCasillero && !tieneSaqueador) {
      notificarVertices(terreno.generarRecurso());
    }
  }

  public void desuscribirVertice(modelVerticeConstruido vertice) {
    if (verticesConstruidos.contains(vertice)) {
      verticesConstruidos.remove(vertice);
    }
  }

  public void suscribirVertice(modelVerticeConstruido vertice) {
    if (verticesConstruidos.contains(vertice)) {
      return;
    }
    verticesConstruidos.add(vertice);
  }

  private void notificarVertices(Recurso recurso) {
    for (modelVerticeConstruido vertice : verticesConstruidos) {
      vertice.recibirRecurso(recurso);
    }
  }
}
