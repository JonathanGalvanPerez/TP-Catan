package fiuba.cyberlek;

import fiuba.cyberlek.controller.JuegoController;
import java.io.IOException;

/** Hello world! */
public class App {
  public static void main(String[] args) throws IOException {
    JuegoController juegoController = new JuegoController();
    juegoController.iniciarJuego();
  }
}
