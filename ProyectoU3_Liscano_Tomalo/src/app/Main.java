package app;

import controlador.Controlador;
import modelo.*;
import vista.VistaContactos;

/**
 * Clase principal que inicia la aplicación.
 * Crea las estructuras de datos, la vista y conecta todo mediante el controlador.
 */
public class Main {
    /**
     * Método main que arranca la aplicación.
     * @param args Argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        // Crear la lista enlazada de contactos
        ListaContactos lista = new ListaContactos();
        // Crear el árbol binario de contactos
        ArbolBinarioContactos arbol = new ArbolBinarioContactos();
        // Crear el grafo de colaboraciones
        GrafoColaboraciones grafo = new GrafoColaboraciones();
        // Crear la ventana de la interfaz gráfica
        VistaContactos vista = new VistaContactos();

        // Crear el controlador que enlaza modelo y vista
        new Controlador(lista, arbol, grafo, vista);
    }
}
