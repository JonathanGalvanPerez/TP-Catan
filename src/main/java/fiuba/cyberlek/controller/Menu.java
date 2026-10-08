package fiuba.cyberlek.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Menu implements MenuComponent {
  private final String nombre;
  private final List<MenuComponent> componentes = new ArrayList<>();

  public Menu(String nombre) {
    this.nombre = Objects.requireNonNull(nombre);
  }

  public Menu agregar(MenuComponent componente) {
    componentes.add(Objects.requireNonNull(componente));
    return this;
  }

  public List<MenuComponent> getComponentes() {
    return List.copyOf(componentes);
  }

  @Override
  public String getNombre() {
    return nombre;
  }
}
