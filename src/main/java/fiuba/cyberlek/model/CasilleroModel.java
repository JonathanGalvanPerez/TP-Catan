package fiuba.cyberlek.model;

import java.util.List;

public class CasilleroModel {

  private int NumeroCasillero;
  private Boolean tieneSaqueador;
  private TerrenoModel terreno;
  private List<VerticeModel> vertices;
  private List<VerticeConstruidoModel> verticesConstruidos;

  public CasilleroModel(int numeroFarmeo, TerrenoModel terreno, List<VerticeModel> vertices) {
    this.NumeroCasillero = numeroFarmeo;
    this.tieneSaqueador = false;
    this.vertices = vertices;
  }

  public void intentarFarmear(int NumeroDado) {
    if (NumeroDado == NumeroCasillero && !tieneSaqueador) {
      notificarVertices(terreno.generarRecurso());
    }
  }

  public void desuscribirVertice(VerticeConstruidoModel vertice) {
    if (verticesConstruidos.contains(vertice)) {
      verticesConstruidos.remove(vertice);
    }
  }

  public void suscribirVertice(VerticeConstruidoModel vertice) {
    if (verticesConstruidos.contains(vertice)) {
      return;
    }
    verticesConstruidos.add(vertice);
  }

  private void notificarVertices(Recurso recurso) {
    for (VerticeConstruidoModel vertice : verticesConstruidos) {
      vertice.recibirRecurso(recurso);
    }
  }
}
