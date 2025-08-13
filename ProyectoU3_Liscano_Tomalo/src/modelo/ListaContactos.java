package modelo;

/**
 * Clase que representa una lista enlazada simple para manejar contactos.
 * Cada contacto es almacenado en un nodo con nombre, teléfono y correo.
 */
public class ListaContactos {
    // Referencia al primer nodo de la lista (cabeza)
    private NodoContacto cabeza;

    /**
     * Constructor que inicializa la lista vacía.
     */
    public ListaContactos() {
        cabeza = null;  // La lista comienza sin ningún nodo
    }

    /**
     * Método para agregar un nuevo contacto al final de la lista.
     * @param nombre Nombre del contacto
     * @param telefono Teléfono del contacto
     * @param correo Correo electrónico del contacto
     */
    public void agregarContacto(String nombre, String telefono, String correo) {
        NodoContacto nuevo = new NodoContacto(nombre, telefono, correo); // Crear nuevo nodo con los datos
        if (cabeza == null) {
            cabeza = nuevo;  // Si la lista está vacía, el nuevo nodo es la cabeza
        } else {
            NodoContacto temp = cabeza;  // Nodo temporal para recorrer la lista
            while (temp.siguiente != null) { // Recorrer hasta el último nodo
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;  // Asignar el nuevo nodo al final de la lista
        }
    }

    /**
     * Método que retorna un String con la lista de todos los contactos en formato legible.
     * @return String con la información de todos los contactos o mensaje si la lista está vacía
     */
    public String obtenerContactosTexto() {
        StringBuilder sb = new StringBuilder();  // Usar StringBuilder para eficiencia en concatenación
        NodoContacto temp = cabeza;  // Nodo temporal para recorrer la lista

        if (temp == null) {
            return "No hay contactos registrados.\n";  // Si la lista está vacía, retornar mensaje
        }

        while (temp != null) {  // Recorrer toda la lista
            sb.append("Nombre: ").append(temp.nombre)    // Agregar nombre del contacto
              .append(", Teléfono: ").append(temp.telefono) // Agregar teléfono
              .append(", Correo: ").append(temp.correo)  // Agregar correo
              .append("\n");                             // Agregar salto de línea
            temp = temp.siguiente;  // Avanzar al siguiente nodo
        }
        return sb.toString();  // Retornar texto con todos los contactos
    }

    /**
     * Método que busca un contacto en la lista por su nombre.
     * La búsqueda no distingue mayúsculas o minúsculas.
     * @param nombre Nombre a buscar
     * @return NodoContacto encontrado o null si no existe
     */
    public NodoContacto buscarPorNombre(String nombre) {
        NodoContacto temp = cabeza;  // Nodo temporal para recorrer la lista
        while (temp != null) {  // Mientras no llegue al final
            if (temp.nombre.equalsIgnoreCase(nombre)) { // Comparar nombre ignorando mayúsculas/minúsculas
                return temp;  // Retornar el nodo si coincide
            }
            temp = temp.siguiente;  // Avanzar al siguiente nodo
        }
        return null;  // Retornar null si no se encontró el contacto
    }
}
