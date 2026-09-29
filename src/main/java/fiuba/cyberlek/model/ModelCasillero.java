package fiuba.cyberlek.model;

import java.util.List;

public class ModelCasillero {

  private int NumeroCasillero;
  private Boolean tieneSaqueador;
  private ModelTerreno terreno;
  private List<ModelVertice> vertices;
  private List<ModelVerticeConstruido> verticesConstruidos;

  public ModelCasillero(int numeroFarmeo, ModelTerreno terreno, List<ModelVertice> vertices) {
    this.NumeroCasillero = numeroFarmeo;
    this.tieneSaqueador = false;
    this.vertices = vertices;
  }

  public void intentarFarmear(int NumeroDado) {
    if (NumeroDado == NumeroCasillero && !tieneSaqueador) {
      notificarVertices(terreno.generarRecurso());
    }
  }

  public void desuscribirVertice(ModelVerticeConstruido vertice) {
    if (verticesConstruidos.contains(vertice)) {
      verticesConstruidos.remove(vertice);
    }
  }

  public void suscribirVertice(ModelVerticeConstruido vertice) {
    if (verticesConstruidos.contains(vertice)) {
      return;
    }
    verticesConstruidos.add(vertice);
  }

  private void notificarVertices(Recurso recurso) {
    for (ModelVerticeConstruido vertice : verticesConstruidos) {
      vertice.recibirRecurso(recurso);
    }
  }
}
