package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.List;

public class AristaModel {
  private String posicion;
  private VerticeModel vertice1;
  private VerticeModel vertice2;
  // private JugadorModel jugador;
  private boolean camino;

  public AristaModel(String posicion, VerticeModel v1, VerticeModel v2) {
    this.posicion = posicion;
    this.vertice1 = v1;
    this.vertice2 = v2;
    this.camino = false;
    this.jugador = null;
  }

  public boolean estaConstruida() {
    return this.camino;
  }

  public void destruirCamino() {
    //   this.jugador = null;
    this.camino = false;
  }

  public void construirCamino() {
    //  this.jugador = jugador;
    this.camino = true;
  }

  public List<String> devolverVertices() {
    List<String> aux = new ArrayList<>();
    aux.add(vertice1.getPosicion());
    aux.add(vertice2.getPosicion());
    return aux;
  }

  public String getPosicion() {
    return this.posicion;
  }
}
