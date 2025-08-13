package modelo;

/**
 * Clase que representa un árbol binario de búsqueda para almacenar contactos.
 * Permite insertar, buscar contactos por nombre y obtener la lista de contactos en orden alfabético.
 */
public class ArbolBinarioContactos {
    // Nodo raíz del árbol binario
    private NodoArbolContacto raiz;

    /**
     * Constructor que inicializa el árbol vacío.
     */
    public ArbolBinarioContactos() {
        raiz = null;  // El árbol comienza sin nodos
    }

    /**
     * Método público para insertar un contacto en el árbol.
     * @param contacto NodoContacto a insertar
     */
    public void insertar(NodoContacto contacto) {
        raiz = insertarRec(raiz, contacto);  // Llama al método recursivo para insertar desde la raíz
    }

    /**
     * Método recursivo para insertar un nodo en la posición correcta según orden alfabético por nombre.
     * @param nodo NodoArbolContacto actual en el recorrido
     * @param contacto NodoContacto a insertar
     * @return NodoArbolContacto actualizado
     */
    private NodoArbolContacto insertarRec(NodoArbolContacto nodo, NodoContacto contacto) {
        if (nodo == null) {
            return new NodoArbolContacto(contacto);  // Crear nuevo nodo si llegamos a un lugar vacío
        }
        // Comparar nombres ignorando mayúsculas/minúsculas para decidir dirección
        if (contacto.nombre.compareToIgnoreCase(nodo.contacto.nombre) < 0) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, contacto);  // Insertar en subárbol izquierdo
        } else if (contacto.nombre.compareToIgnoreCase(nodo.contacto.nombre) > 0) {
            nodo.derecho = insertarRec(nodo.derecho, contacto);      // Insertar en subárbol derecho
        }
        // Si el nombre es igual, no inserta duplicados (podría modificarse)
        return nodo;  // Retorna el nodo actual actualizado
    }

    /**
     * Busca un contacto por nombre en el árbol.
     * @param nombre Nombre del contacto a buscar
     * @return NodoContacto encontrado o null si no existe
     */
    public NodoContacto buscar(String nombre) {
        NodoArbolContacto resultado = buscarRec(raiz, nombre);  // Llama a búsqueda recursiva
        return (resultado != null) ? resultado.contacto : null;  // Retorna el contacto o null
    }

    /**
     * Método recursivo para buscar un nodo por nombre en el árbol.
     * @param nodo Nodo actual en el recorrido
     * @param nombre Nombre a buscar
     * @return NodoArbolContacto encontrado o null si no existe
     */
    private NodoArbolContacto buscarRec(NodoArbolContacto nodo, String nombre) {
        if (nodo == null || nodo.contacto.nombre.equalsIgnoreCase(nombre)) {
            return nodo;  // Nodo encontrado o fin del camino sin encontrar
        }
        if (nombre.compareToIgnoreCase(nodo.contacto.nombre) < 0) {
            return buscarRec(nodo.izquierdo, nombre);  // Buscar en subárbol izquierdo
        }
        return buscarRec(nodo.derecho, nombre);        // Buscar en subárbol derecho
    }

    /**
     * Devuelve un String con la lista de contactos en orden alfabético (recorrido inorden).
     * @return String con la información de todos los contactos
     */
    public String obtenerInOrdenTexto() {
        StringBuilder sb = new StringBuilder();  // Para construir el texto
        obtenerInOrdenRec(raiz, sb);             // Llamada recursiva al recorrido inorden
        return sb.toString();                     // Retorna el resultado completo
    }

    /**
     * Método recursivo para recorrer el árbol inorden y agregar datos al StringBuilder.
     * @param nodo Nodo actual
     * @param sb StringBuilder para concatenar la información
     */
    private void obtenerInOrdenRec(NodoArbolContacto nodo, StringBuilder sb) {
        if (nodo != null) {
            obtenerInOrdenRec(nodo.izquierdo, sb);   // Recorrer subárbol izquierdo
            sb.append("Nombre: ").append(nodo.contacto.nombre)  // Agregar nombre
              .append(", Teléfono: ").append(nodo.contacto.telefono) // Agregar teléfono
              .append(", Correo: ").append(nodo.contacto.correo).append("\n");  // Agregar correo
            obtenerInOrdenRec(nodo.derecho, sb);    // Recorrer subárbol derecho
        }
    }
}
