package fiuba.cyberlek.model;

import java.util.List;
import java.util.function.Predicate;

public class GrafoBuilderModel {
    private GrafoModel Grafo;
    private int dimensionX;
    private int dimensionY;

    public GrafoBuilderModel(int dimensionX, int dimensionY){
        this.Grafo = new GrafoModel();
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
    }

    private void crearVertices() {
        int indiceAdyacencia = 0;
        for (int i = 0; i < dimensionY; i++){
            for (int j = 0; j < dimensionX; j++){
                String posicion = j +""+ i;
               VerticeModel vertice = new VerticeModel(indiceAdyacencia, posicion);
               this.Grafo.agregarVertice(posicion, vertice);
               indiceAdyacencia += 1;
            }
        }
    }
    private void crearAristasHorizontales() {
        int indiceX = 0;
        int indiceY = 0;
        List<VerticeModel> vertices = this.Grafo.obtenerVertices();
        VerticeModel anterior = null;
        for (VerticeModel vertice: vertices){

            if (0 < indiceX & indiceX % this.dimensionX == 0){
                indiceY += 1;
                indiceX = 0;
            }

            String posicion = indiceX + "" + indiceY;
            if (0 < indiceX & indiceX > this.dimensionX) {
                AristaModel arista = new AristaModel(posicion, vertice, anterior);
                this.Grafo.agregarArista(posicion,arista,vertice,anterior);
            }

            anterior = vertice;
            indiceX += 1;
        }
    }
    public void crearAristasVerticales(){
        List<VerticeModel> vertices = this.Grafo.obtenerVertices();
        for (int i = 0; i < this.dimensionX; i++) {
            VerticeModel anterior = vertices.get(i);
            for (int j = i + this.dimensionX; j  < this.dimensionY * this.dimensionX; j += this.dimensionX){
                VerticeModel actual = vertices.get(j);
                int posiconY = j / this.dimensionY;
                String posicion = i + "" + posiconY;
                AristaModel arista = new AristaModel(posicion,actual,anterior);
                this.Grafo.agregarArista(posicion,arista,actual,anterior);
                anterior = actual;
            }
        }
    }


    public GrafoModel CrearGrafo(){
        this.crearVertices();
        this.crearAristasHorizontales();
        this.crearAristasVerticales();
        return this.Grafo;
    }

}
