package modelo;

/**
 * Clase que representa un nodo en la lista enlazada de contactos.
 * Cada nodo contiene la información de un contacto y referencia al siguiente nodo.
 */
public class NodoContacto {
    // Nombre del contacto
    String nombre;
    // Teléfono del contacto
    String telefono;
    // Correo electrónico del contacto
    String correo;
    // Referencia al siguiente nodo en la lista enlazada
    NodoContacto siguiente;

    /**
     * Constructor que crea un nodo con los datos del contacto.
     * Inicializa la referencia siguiente en null.
     * 
     * @param nombre Nombre del contacto
     * @param telefono Teléfono del contacto
     * @param correo Correo electrónico del contacto
     */
    public NodoContacto(String nombre, String telefono, String correo) {
        this.nombre = nombre;       // Asignar nombre recibido
        this.telefono = telefono;   // Asignar teléfono recibido
        this.correo = correo;       // Asignar correo recibido
        this.siguiente = null;      // Inicialmente no apunta a ningún nodo siguiente
    }

    /** 
     * Devuelve el nombre del contacto.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el teléfono del contacto.
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Devuelve el correo electrónico del contacto.
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Devuelve el nodo siguiente en la lista enlazada.
     * @return siguiente nodo
     */
    public NodoContacto getSiguiente() {
        return siguiente;
    }
}
