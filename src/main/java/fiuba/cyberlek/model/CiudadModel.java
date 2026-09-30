package fiuba.cyberlek.model;

public class CiudadModel extends ConstruccionModel {

    public CiudadModel(JugadorModel jugador) {
        super(jugador);
    }

    @Override
    public void asignarRecursoJugador(Recurso recurso) {
        jugador.agregar(recurso, 2);
    }

    @Override 
    public ConstruccionModel mejorar() {
        return this;
    }

    @Override
    public ConstruccionModel downgradear() {
        return new AldeaModel(jugador);
    }


}
