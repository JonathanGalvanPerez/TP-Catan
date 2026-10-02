package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.List;

public class AristaModel {
  private VerticeModel vertice1;
  private VerticeModel vertice2;
  private ConstruccionModel camino;

  public AristaModel(VerticeModel v1, VerticeModel v2) {
    this.vertice1 = v1;
    this.vertice2 = v2;
    this.camino = null;
  }

  // public void Construir(){}

  public List<VerticeModel> devolverVertices() {
    List<VerticeModel> aux = new ArrayList<>();
    aux.add(vertice1);
    aux.add(vertice2);
    return aux;
  }

  public boolean estaConstruida() {
    return this.camino != null;
  }
}
