package fiuba.cyberlek.controller;

import fiuba.cyberlek.model.Recurso;
import java.util.Objects;
import java.util.Optional;

public final class MenuOption implements MenuComponent {
  private final String nombre;
  private final AccionMenu accion;
  private final Optional<Recurso> recurso;

  public MenuOption(String nombre, AccionMenu accion) {
    this(nombre, accion, Optional.empty());
  }

  public MenuOption(String nombre, AccionMenu accion, Recurso recurso) {
    this(nombre, accion, Optional.of(recurso));
  }

  private MenuOption(String nombre, AccionMenu accion, Optional<Recurso> recurso) {
    this.nombre = Objects.requireNonNull(nombre);
    this.accion = Objects.requireNonNull(accion);
    this.recurso = Objects.requireNonNull(recurso);
  }

  public AccionMenu getAccion() {
    return accion;
  }

  public Optional<Recurso> getRecurso() {
    return recurso;
  }

  @Override
  public String getNombre() {
    return nombre;
  }
}
