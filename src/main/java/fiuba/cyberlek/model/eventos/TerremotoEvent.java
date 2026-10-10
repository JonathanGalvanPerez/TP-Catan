package fiuba.cyberlek.model.eventos;

import fiuba.cyberlek.model.CasilleroModel;
import fiuba.cyberlek.model.JugadorModel;
import fiuba.cyberlek.model.RandomizadorModel;
import fiuba.cyberlek.model.TableroModel;
import fiuba.cyberlek.model.VerticeModel;
import java.util.List;

public class TerremotoEvent implements EventoClimaticoModel {

  private final int cantidadDeCasillasDeLaZona;

  public TerremotoEvent(int cantidadDeCasillasDeLaZona) {
    this.cantidadDeCasillasDeLaZona = cantidadDeCasillasDeLaZona;
  }

  @Override
  public String getNombre() {
    return "Terremoto";
  }

  @Override
  public String getDescripcion() {
    return "La tierra tiembla en una region del mapa.";
  }

  @Override
  public ResultadoEventoModel aplicar(ContextoEventoModel contextoEvento) {
    List<CasilleroModel> zonaAfectada =
        contextoEvento
            .getRandomizador()
            .elegirVariosSinRepeticion(
                contextoEvento.getTablero().getCasilleros(), cantidadDeCasillasDeLaZona);

    List<VerticeModel> verticesConConstruccion =
        contextoEvento.getTablero().verticesConConstruccionEn(zonaAfectada);

    ResultadoEventoModel resultado = new ResultadoEventoModel(getNombre(), getDescripcion());
    resultado.agregarConsecuencia("Zona afectada: " + zonaAfectada.size() + " casilla(s)");

    if (verticesConConstruccion.isEmpty()) {
      resultado.agregarConsecuencia("No habia construcciones en la zona.");
      return resultado;
    }

    TableroModel tablero = contextoEvento.getTablero();
    RandomizadorModel randomizador = contextoEvento.getRandomizador();

    VerticeModel verticeAfectado = randomizador.elegirAlAzar(verticesConConstruccion).orElseThrow();
    JugadorModel duenio = tablero.obtenerDuenioDeVertice(verticeAfectado.getPosicion());

    tablero.aplicarTerremoto(verticeAfectado.getPosicion());

    boolean sobrevivio = verticeAfectado.existeConstruccion();
    String mensaje;
    if (sobrevivio) {
      mensaje = "Una Ciudad de " + duenio.getNombre() + " se degrado a Aldea";
    } else {
      mensaje = "Una Aldea de " + duenio.getNombre() + " fue destruida";
    }
    resultado.agregarConsecuencia(mensaje + " en el vertice " + verticeAfectado.getPosicion());
    return resultado;
  }
}
