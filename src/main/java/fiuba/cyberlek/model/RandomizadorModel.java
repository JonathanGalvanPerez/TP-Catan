package fiuba.cyberlek.model;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RandomizadorModel {

  private final Random random;
  private final int cantidadDados;
  private final int carasPorDado;

  public RandomizadorModel(int cantidadDados, int carasPorDado) {
    this(cantidadDados, carasPorDado, new Random());
  }

  public RandomizadorModel(int cantidadDados, int carasPorDado, Random random) {
    if (cantidadDados < 1 || carasPorDado < 1)
      throw new IllegalArgumentException("Parametros inválidos");
    this.cantidadDados = cantidadDados;
    this.carasPorDado = carasPorDado;
    this.random = random;
  }

  public int tirarDados() {
    int total = 0;
    for (int i = 0; i < cantidadDados; i++) total += random.nextInt(carasPorDado) + 1;
    return total;
  }

  // elegir al azar de una lista
  public <T> Optional<T> elegirAlAzar(List<T> opciones) {
    if (opciones.isEmpty()) return Optional.empty();

    return Optional.of(opciones.get(random.nextInt(opciones.size())));
  }

  // caso en que se quiera saber una probabilidad
  public boolean ocurre(double probabilidad) {
    if (probabilidad < 0 || probabilidad > 1)
      throw new IllegalArgumentException("probabilidad fuera de [0,1]");

    return random.nextDouble() < probabilidad;
  }

  @Override
  public String toString() {
    return cantidadDados + "d" + carasPorDado;
  }
}
