package fiuba.cyberlek.model.eventos;

import java.util.ArrayList;
import java.util.List;

public class ResultadoEventoModel {

  private final String nombreEvento;
  private final String descripcion;
  private final List<String> consecuencias = new ArrayList<>();
  private final List<EfectoModel> efectosPersistentes = new ArrayList<>();
  private boolean requiereMoverSaqueador = false;

  public ResultadoEventoModel(String nombreEvento, String descripcion) {
    this.nombreEvento = nombreEvento;
    this.descripcion = descripcion;
  }

  public void agregarConsecuencia(String consecuencia) {
    consecuencias.add(consecuencia);
  }

  public void agregarEfectoPersistente(EfectoModel efecto) {
    efectosPersistentes.add(efecto);
  }

  public void marcarRequiereMoverSaqueador() {
    this.requiereMoverSaqueador = true;
  }

  public String getNombreEvento() {
    return nombreEvento;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public List<String> getConsecuencias() {
    return List.copyOf(consecuencias);
  }

  public List<EfectoModel> getEfectosPersistentes() {
    return List.copyOf(efectosPersistentes);
  }

  public boolean isRequiereMoverSaqueador() {
    return requiereMoverSaqueador;
  }
}
