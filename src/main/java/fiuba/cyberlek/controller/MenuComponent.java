package fiuba.cyberlek.controller;

public sealed interface MenuComponent permits Menu, MenuOption {
  String getNombre();
}
