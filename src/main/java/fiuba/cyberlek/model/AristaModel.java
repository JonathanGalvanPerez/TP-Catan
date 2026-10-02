package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.List;

public class AristaModel {
  private String posicion;
  private VerticeModel vertice1;
  private VerticeModel vertice2;
  private ConstruccionModel camino;

  public AristaModel(VerticeModel v1, VerticeModel v2) {
    this.vertice1 = v1;
    this.vertice2 = v2;
    this.camino = null;
  }

  // public void Construir(){}

  public List<String> devolverVertices() {
    List<String> aux = new ArrayList<>();
    aux.add(vertice1.getPosicion());
    aux.add(vertice2.getPosicion());
    return aux;
  }

  public String getPosicion() {
    return this.posicion;
  }

  public boolean estaConstruida() {
    return this.camino != null;
  }
}
