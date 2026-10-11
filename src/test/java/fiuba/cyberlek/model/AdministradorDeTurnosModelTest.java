package fiuba.cyberlek.model;

import static org.junit.Assert.assertSame;

import java.util.List;
import org.junit.Test;

public class AdministradorDeTurnosModelTest {
  @Test
  public void mantieneElOrdenDeTurnosCuandoSeEliminaUnJugador() {
    JugadorModel jugadorA = new JugadorModel("A");
    JugadorModel jugadorB = new JugadorModel("B");
    JugadorModel jugadorC = new JugadorModel("C");
    JugadorModel jugadorD = new JugadorModel("D");
    AdministradorDeTurnosModel administrador =
        new AdministradorDeTurnosModel(List.of(jugadorA, jugadorB, jugadorC, jugadorD));

    assertSame(jugadorA, administrador.obtenerJugadorActual());
    administrador.siguienteTurno();
    assertSame(jugadorB, administrador.obtenerJugadorActual());
    administrador.siguienteTurno();
    assertSame(jugadorC, administrador.obtenerJugadorActual());
    jugadorC.eliminar();
    administrador.siguienteTurno();
    assertSame(jugadorD, administrador.obtenerJugadorActual());
    administrador.siguienteTurno();
    assertSame(jugadorA, administrador.obtenerJugadorActual());
    administrador.siguienteTurno();
    assertSame(jugadorB, administrador.obtenerJugadorActual());
    administrador.siguienteTurno();

    assertSame(jugadorD, administrador.obtenerJugadorActual());
  }
}
