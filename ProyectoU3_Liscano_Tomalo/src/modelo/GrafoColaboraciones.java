package modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que representa un grafo no dirigido para modelar colaboraciones entre contactos.
 * Cada contacto es un nodo, y las colaboraciones son las aristas.
 */
public class GrafoColaboraciones {
    // Mapa que asocia cada nodo (contacto) con un conjunto de nodos adyacentes (colaboradores)
    private HashMap<NodoContacto, HashSet<NodoContacto>> adyacencias;

    /**
     * Constructor que inicializa el mapa de adyacencias vacío.
     */
    public GrafoColaboraciones() {
        adyacencias = new HashMap<>();  // Inicializa el grafo sin nodos ni aristas
    }

    /**
     * Agrega un nuevo contacto al grafo como nodo sin aristas.
     * Si el contacto ya existe, no hace nada.
     * 
     * @param contacto NodoContacto a agregar
     */
    public void agregarContacto(NodoContacto contacto) {
        adyacencias.putIfAbsent(contacto, new HashSet<>()); // Añade contacto si no existe aún
    }

    /**
     * Agrega una colaboración (arista) entre dos contactos existentes en el grafo.
     * La colaboración es bidireccional.
     * 
     * @param c1 Primer contacto
     * @param c2 Segundo contacto
     */
    public void agregarColaboracion(NodoContacto c1, NodoContacto c2) {
        if (adyacencias.containsKey(c1) && adyacencias.containsKey(c2)) {
            adyacencias.get(c1).add(c2); // Agrega c2 a la lista de vecinos de c1
            adyacencias.get(c2).add(c1); // Agrega c1 a la lista de vecinos de c2 (arista bidireccional)
        }
    }

    /**
     * Obtiene una representación textual de todas las colaboraciones del grafo.
     * Por cada contacto, lista con quiénes colabora.
     * 
     * @return String con las colaboraciones formateadas
     */
    public String obtenerColaboracionesTexto() {
        StringBuilder sb = new StringBuilder();
        for (NodoContacto c : adyacencias.keySet()) {  // Para cada nodo en el grafo
            sb.append(c.nombre).append(" colabora con: ");
            for (NodoContacto colaborador : adyacencias.get(c)) { // Lista de colaboradores
                sb.append(colaborador.nombre).append(", ");       // Agrega nombres separados por espacio
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // Métodos nuevos para la vista gráfica

    /**
     * Devuelve la lista de todos los contactos (nodos) en el grafo.
     * 
     * @return Listado de nodos
     */
    public List<NodoContacto> getContactos() {
        return new ArrayList<>(adyacencias.keySet()); // Convierte el conjunto de claves a lista
    }

    /**
     * Devuelve el conjunto de vecinos (contactos colaboradores) de un nodo dado.
     * Si el nodo no existe, devuelve un conjunto vacío.
     * 
     * @param nodo NodoContacto del que se obtienen vecinos
     * @return Conjunto de vecinos
     */
    public Set<NodoContacto> getVecinos(NodoContacto nodo) {
        return adyacencias.getOrDefault(nodo, new HashSet<>()); // Retorna vecinos o vacío si no existe
    }
    
    public void limpiar() {
        adyacencias.clear();
    }
}
