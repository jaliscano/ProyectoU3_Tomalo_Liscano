package controlador;

import modelo.*;
import vista.VistaContactos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase Controlador que conecta el modelo (lista, árbol y grafo) con la vista (interfaz gráfica).
 * Gestiona la lógica de interacción para agregar, buscar contactos, mostrar listas y manejar colaboraciones.
 */
public class Controlador {
    // Estructuras de datos del modelo
    private ListaContactos lista;
    private ArbolBinarioContactos arbol;
    private GrafoColaboraciones grafo;
    // Vista para interactuar con el usuario
    private VistaContactos vista;

    /**
     * Constructor que recibe las estructuras y la vista, y configura los listeners de los botones.
     *
     * @param lista Lista enlazada de contactos
     * @param arbol Árbol binario de contactos
     * @param grafo Grafo de colaboraciones
     * @param vista Interfaz gráfica para interacción
     */
    public Controlador(ListaContactos lista, ArbolBinarioContactos arbol, GrafoColaboraciones grafo, VistaContactos vista) {
        this.lista = lista;
        this.arbol = arbol;
        this.grafo = grafo;
        this.vista = vista;

        // Cargar datos iniciales estáticos
        cargarContactosIniciales();
        vista.panelGrafo.setGrafo(grafo);
        
        // Listener para botón limpiar área texto
        this.vista.btnLimpiar.addActionListener(e -> {
            vista.txtArea.setText("");    // Limpia el área de texto
            //grafo.limpiar();              // Limpia datos del grafo
            vista.panelGrafo.setGrafo(null);  // Refresca el panel grafo (ahora vacío)
            actualizarComboContactos();   // Limpia/completa los combos (si quieres que estén vacíos)
        });

        
        // Listener para botón Agregar contacto
        this.vista.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = vista.txtNombre.getText().trim();
                String telefono = vista.txtTelefono.getText().trim();
                String correo = vista.txtCorreo.getText().trim();

                if (nombre.isEmpty()) {
                    vista.txtArea.append("El nombre es obligatorio.\n");
                    return;  // No agregar contacto sin nombre
                }

                NodoContacto nuevo = new NodoContacto(nombre, telefono, correo);
                lista.agregarContacto(nombre, telefono, correo); // Agregar a lista enlazada
                arbol.insertar(nuevo);                            // Insertar en árbol
                grafo.agregarContacto(nuevo);                     // Agregar nodo al grafo

                vista.txtArea.append("Contacto agregado: " + nombre + "\n");

                // Actualizar listas desplegables de contactos
                actualizarComboContactos();

                // Actualizar grafo visualmente
                vista.panelGrafo.setGrafo(grafo);

                // Limpiar campos de entrada
                vista.txtNombre.setText("");
                vista.txtTelefono.setText("");
                vista.txtCorreo.setText("");
            }
        });

        // Listener para botón Buscar contacto por nombre
        this.vista.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = vista.txtNombre.getText().trim();
                NodoContacto encontrado = arbol.buscar(nombre);
                if (encontrado != null) {
                    vista.txtArea.append("Encontrado: " + encontrado.getNombre() + ", " + encontrado.getTelefono() + ", " + encontrado.getCorreo() + "\n");
                } else {
                    vista.txtArea.append("Contacto no encontrado\n");
                }
            }
        });

        // Listener para botón Mostrar todo (lista, árbol y grafo)
        this.vista.btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.txtArea.append("\n--- Lista de contactos ---\n");
                vista.txtArea.append(lista.obtenerContactosTexto());
                vista.txtArea.append("\n--- Árbol en orden ---\n");
                vista.txtArea.append(arbol.obtenerInOrdenTexto());
                vista.txtArea.append("\n--- Colaboraciones ---\n");
                vista.txtArea.append(grafo.obtenerColaboracionesTexto());
                vista.panelGrafo.setGrafo(grafo);
            }
        });

        // Listener para botón Agregar colaboración entre dos contactos
        this.vista.btnAgregarColaboracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String c1 = (String) vista.cbContacto1.getSelectedItem();
                String c2 = (String) vista.cbContacto2.getSelectedItem();

                if (c1 == null || c2 == null) {
                    vista.txtArea.append("Debe seleccionar dos contactos.\n");
                    return;
                }
                if (c1.equals(c2)) {
                    vista.txtArea.append("No puede colaborar un contacto consigo mismo.\n");
                    return;
                }

                NodoContacto contacto1 = arbol.buscar(c1);
                NodoContacto contacto2 = arbol.buscar(c2);

                if (contacto1 != null && contacto2 != null) {
                    grafo.agregarColaboracion(contacto1, contacto2);  // Agregar arista en grafo
                    vista.txtArea.append("Colaboración agregada entre " + c1 + " y " + c2 + "\n");

                    // Actualizar grafo visualmente
                    vista.panelGrafo.setGrafo(grafo);
                } else {
                    vista.txtArea.append("Error al buscar contactos para colaboración.\n");
                }
            }
        });

        // Inicializa los comboboxes con contactos existentes (vacíos al inicio)
        actualizarComboContactos();
    }

    /**
     * Actualiza los comboboxes de contactos con los nombres actuales en el grafo.
     */
    private void actualizarComboContactos() {
        vista.cbContacto1.removeAllItems();
        vista.cbContacto2.removeAllItems();

        // Agrega cada contacto por su nombre a ambos comboboxes
        for (NodoContacto c : grafo.getContactos()) {
            vista.cbContacto1.addItem(c.getNombre());
            vista.cbContacto2.addItem(c.getNombre());
        }
    }
    
    private void cargarContactosIniciales() {
        String[][] datos = {
            {"Ana Perez", "0991234567", "ana.perez@email.com"},
            {"Luis Gomez", "0987654321", "luis.gomez@email.com"},
            {"Marta Ruiz", "0971122334", "marta.ruiz@email.com"},
            {"Carlos Vega", "0969988776", "carlos.vega@email.com"},
            {"Elena Diaz", "0954433221", "elena.diaz@email.com"},
            {"Jorge Lopez", "0945566778", "jorge.lopez@email.com"},
            {"Sofia Cruz", "0932211445", "sofia.cruz@email.com"}
        };

        for (String[] d : datos) {
            NodoContacto nuevo = new NodoContacto(d[0], d[1], d[2]);
            lista.agregarContacto(d[0], d[1], d[2]);
            arbol.insertar(nuevo);
            grafo.agregarContacto(nuevo);
        }

        // Crear colaboraciones estáticas
        NodoContacto a = arbol.buscar("Ana Perez");
        NodoContacto b = arbol.buscar("Luis Gomez");
        NodoContacto c = arbol.buscar("Marta Ruiz");
        NodoContacto d = arbol.buscar("Carlos Vega");

        if (a != null && b != null) grafo.agregarColaboracion(a, b);
        if (a != null && c != null) grafo.agregarColaboracion(a, c);
        if (b != null && d != null) grafo.agregarColaboracion(b, d);

        // Refrescar grafo para que se dibuje con los datos cargados
        vista.panelGrafo.setGrafo(grafo);

        // Actualizar combos para que muestren los nuevos contactos
        actualizarComboContactos();
    }

}
