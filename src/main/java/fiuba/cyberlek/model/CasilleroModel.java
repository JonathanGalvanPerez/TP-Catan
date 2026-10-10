package fiuba.cyberlek.model;

import java.util.ArrayList;
import java.util.List;

public class CasilleroModel {

  private int NumeroCasillero;
  private Boolean tieneSaqueador;
  private TerrenoModel terreno;
  private List<VerticeModel> vertices;
  private List<VerticeConstruidoModel> verticesConstruidos;

  private int bloqueosProduccion = 0;
  private int bloqueosConstruccion = 0;
  private int duplicacionesProduccion = 0;

  public CasilleroModel(int numeroFarmeo, TerrenoModel terreno, List<VerticeModel> vertices) {
    this.NumeroCasillero = numeroFarmeo;
    this.tieneSaqueador = false;
    this.vertices = vertices;
    this.terreno = terreno;
    this.verticesConstruidos = new ArrayList<>();
  }

  public void intentarFarmear(int numeroDado) {
    if (numeroDado != NumeroCasillero) return;
    if (tieneSaqueador || tieneProduccionBloqueada()) return;
    Recurso recursoProducido = terreno.recursoQueProduce();
    if (recursoProducido == Recurso.NADA) return;
    int veces = getMultiplicadorProduccion();
    for (int i = 0; i < veces; i++) {
      notificarVertices(recursoProducido);
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

  // --- Saqueador ---
  public boolean tieneSaqueador() {
    return tieneSaqueador;
  }

  public void colocarSaqueador() {
    this.tieneSaqueador = true;
  }

  public void removerSaqueador() {
    this.tieneSaqueador = false;
  }

  // Eventos
  public void bloquearProduccion() {
    bloqueosProduccion++;
  }

  public void desbloquearProduccion() {
    if (bloqueosProduccion > 0) bloqueosProduccion--;
  }

  public boolean tieneProduccionBloqueada() {
    return bloqueosProduccion > 0;
  }

  public void bloquearConstruccion() {
    bloqueosConstruccion++;
  }

  public void desbloquearConstruccion() {
    if (bloqueosConstruccion > 0) bloqueosConstruccion--;
  }

  public boolean tieneConstruccionBloqueada() {
    return bloqueosConstruccion > 0;
  }

  public void duplicarProduccion() {
    duplicacionesProduccion++;
  }

  public void restaurarProduccion() {

    if (duplicacionesProduccion > 0) duplicacionesProduccion--;
  }

  private int getMultiplicadorProduccion() {

    return 1 << duplicacionesProduccion;
  }

  // --- Consultas para eventos) ---
  public List<VerticeModel> getVertices() {
    return List.copyOf(vertices);
  }

  public Recurso recursoQueProduce() {
    return terreno.recursoQueProduce();
  }
}
