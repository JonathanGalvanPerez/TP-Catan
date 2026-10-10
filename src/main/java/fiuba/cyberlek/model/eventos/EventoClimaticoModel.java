package fiuba.cyberlek.model.eventos;

public interface EventoClimaticoModel {
  String getNombre();

  String getDescripcion();

  ResultadoEventoModel aplicar(ContextoEventoModel contextoEvento);
}
