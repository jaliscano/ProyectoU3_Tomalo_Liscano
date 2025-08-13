package modelo;

/**
 * Nodo para el árbol binario de contactos.
 * Contiene un objeto NodoContacto y referencias a sus hijos izquierdo y derecho.
 */
public class NodoArbolContacto {
    // Objeto NodoContacto almacenado en este nodo del árbol
    NodoContacto contacto;
    // Referencia al hijo izquierdo en el árbol binario
    NodoArbolContacto izquierdo;
    // Referencia al hijo derecho en el árbol binario
    NodoArbolContacto derecho;

    /**
     * Constructor que inicializa el nodo con un contacto dado y sin hijos.
     * @param contacto El contacto a almacenar en el nodo
     */
    public NodoArbolContacto(NodoContacto contacto) {
        this.contacto = contacto; // Asigna el contacto
        izquierdo = null;         // Inicializa sin hijo izquierdo
        derecho = null;           // Inicializa sin hijo derecho
    }
}
