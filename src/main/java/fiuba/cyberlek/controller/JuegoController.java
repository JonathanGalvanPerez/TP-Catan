package fiuba.cyberlek.controller;

import fiuba.cyberlek.model.JuegoModel;
import fiuba.cyberlek.model.JugadorModel;
import fiuba.cyberlek.model.Recurso;
import fiuba.cyberlek.view.JuegoView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JuegoController {
  private JuegoModel juego;
  private JuegoView juegoView;
  private InputController inputController;
  private MenuController menuController;
  private Menu menuTurno;

  public JuegoController() throws IOException {
    this.inputController = new InputController();
    this.menuController = new MenuController(inputController);
    this.menuTurno = crearMenuTurno();

    List<JugadorModel> jugadores = ingresarJugadores();
    this.juego = new JuegoModel(jugadores);
    this.juegoView = new JuegoView(juego);
    prepararConstrucciones(jugadores);
  }

  public void iniciarJuego() {
    while (!juego.termino()) {
      jugarTurno();
    }
    System.out.println("El juego ha terminado.");
  }

  private List<JugadorModel> ingresarJugadores() {
    List<JugadorModel> jugadores = new ArrayList<>();
    int cantidadDeJugadores =
        inputController.leerNumero("Ingrese la cantidad de jugadores (2-4): ");
    for (int i = 0; i < cantidadDeJugadores; i++) {
      String nombreJugador =
          inputController.leerTexto("Ingrese el nombre del jugador " + (i + 1) + ": ");
      JugadorModel jugador = new JugadorModel(nombreJugador);
      jugadores.add(jugador);
    }
    return jugadores;
  }

  private void prepararConstrucciones(List<JugadorModel> jugadores) {
    for (int i = 0; i < jugadores.size() * 2; i++) {
      JugadorModel jugador = jugadores.get(i);
      System.out.println("Turno de " + jugador.getNombre());
      String posicionAldea =
          inputController.leerTexto(
              "Ingrese el vertice donde desea construir la aldea inicial del jugador "
                  + jugador.getNombre()
                  + ": ");
      juego.crearAldeaInicial(posicionAldea, jugador);

      String posicionCamino =
          inputController.leerTexto(
              "Ingrese la arista donde desea construir el camino inicial del jugador "
                  + jugador.getNombre()
                  + ": ");
      juego.crearCaminoInicial(posicionCamino, jugador);
    }
  }

  private void jugarTurno() {
    boolean turnoTerminado = false;
    juego.iniciarTurno();
    juegoView.mostrarJuego();
    while (!turnoTerminado) {
      MenuOption opcionSeleccionada = menuController.mostrarMenu(menuTurno);
      switch (opcionSeleccionada.getAccion()) {
        case CONSTRUIR_ALDEA:
          String posicionAldea =
              inputController.leerTexto("Ingrese el vertice donde desea construir la aldea: ");
          juego.crearAldea(posicionAldea);
          break;
        case CONSTRUIR_CIUDAD:
          String posicionCiudad =
              inputController.leerTexto("Ingrese el vertice donde desea construir la ciudad: ");
          juego.upgradear(posicionCiudad);
          break;
        case CONSTRUIR_CAMINO:
          String posicionCamino =
              inputController.leerTexto("Ingrese la arista donde desea construir el camino: ");
          juego.crearCamino(posicionCamino);
          break;
        case COMERCIAR:
          opcionSeleccionada.getRecurso().orElseThrow();
          // Lógica para comerciar el recurso seleccionado
          break;
        case TERMINAR_TURNO:
          turnoTerminado = true;
          break;
        case RENDIRSE:
          turnoTerminado = true;
          juego.rendirse();
          break;
      }
    }
    juego.terminarTurno();
  }

  private Menu crearMenuTurno() {
    Menu construccion =
        new Menu("Construcción")
            .agregar(new MenuOption("Aldea", AccionMenu.CONSTRUIR_ALDEA))
            .agregar(new MenuOption("Ciudad", AccionMenu.CONSTRUIR_CIUDAD))
            .agregar(new MenuOption("Camino", AccionMenu.CONSTRUIR_CAMINO));
    Menu comercio = new Menu("Recurso a comerciar");
    for (Recurso recurso : Recurso.values()) {
      if (recurso != Recurso.NADA) {
        comercio.agregar(new MenuOption(recurso.getNombre(), AccionMenu.COMERCIAR, recurso));
      }
    }

    return new Menu("Acciones")
        .agregar(construccion)
        .agregar(comercio)
        .agregar(new MenuOption("Terminar turno", AccionMenu.TERMINAR_TURNO))
        .agregar(new MenuOption("Rendirse", AccionMenu.RENDIRSE));
  }

  public boolean termino() {
    return juego.termino();
  }
}
