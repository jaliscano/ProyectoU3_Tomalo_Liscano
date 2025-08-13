package vista;

import modelo.NodoContacto;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Ventana principal para la gestión de contactos y sus colaboraciones.
 * Permite agregar, buscar y mostrar contactos, además de gestionar colaboraciones
 * y visualizar un grafo con las relaciones.
 */
public class VistaContactos extends JFrame {
    // Campos de texto para entrada de datos del contacto
    public JTextField txtNombre, txtTelefono, txtCorreo;
    // Botones para agregar, buscar y mostrar contactos
    public JButton btnAgregar, btnBuscar, btnMostrar, btnLimpiar;

    // Comboboxes para seleccionar contactos y agregar colaboraciones
    public JComboBox<String> cbContacto1, cbContacto2;
    public JButton btnAgregarColaboracion;

    // Área de texto para mostrar resultados o listas de contactos
    public JTextArea txtArea;
    // Panel personalizado para dibujar el grafo de colaboraciones
    public GrafoPanel panelGrafo;

    /**
     * Constructor que configura la interfaz gráfica.
     */
    public VistaContactos() {
        setTitle("Gestión de Contactos y Colaboraciones");  // Título ventana
        setSize(700, 600);                                  // Tamaño ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Cerrar app al cerrar ventana
        setLayout(new BorderLayout());                      // Layout principal

        // Panel para entrada y búsqueda de contactos con GridLayout
        JPanel panelEntrada = new JPanel(new GridLayout(4, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Agregar / Buscar Contacto"));

        // Etiqueta y campo para nombre
        panelEntrada.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelEntrada.add(txtNombre);

        // Etiqueta y campo para teléfono
        panelEntrada.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelEntrada.add(txtTelefono);

        // Etiqueta y campo para correo
        panelEntrada.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelEntrada.add(txtCorreo);

        // Botones para agregar y buscar contacto
        btnAgregar = new JButton("Agregar");
        btnBuscar = new JButton("Buscar");
        btnMostrar = new JButton("Mostrar Todo");
        btnLimpiar = new JButton("Limpiar Área");

        panelEntrada.add(btnAgregar);
        panelEntrada.add(btnBuscar);
        // El botón mostrar se agrega en otro panel para no romper el GridLayout

        // Panel para agregar colaboraciones con FlowLayout
        JPanel panelColaboracion = new JPanel(new FlowLayout());
        panelColaboracion.setBorder(BorderFactory.createTitledBorder("Agregar Colaboración"));

        // Comboboxes para seleccionar dos contactos
        cbContacto1 = new JComboBox<>();
        cbContacto2 = new JComboBox<>();
        btnAgregarColaboracion = new JButton("Agregar Colaboración");

        panelColaboracion.add(new JLabel("Contacto 1:"));
        panelColaboracion.add(cbContacto1);
        panelColaboracion.add(new JLabel("Contacto 2:"));
        panelColaboracion.add(cbContacto2);
        panelColaboracion.add(btnAgregarColaboracion);

        // Panel para botones adicionales (solo mostrar todo)
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnMostrar);
        panelBotones.add(btnLimpiar);

        // Panel izquierdo con entrada y colaboraciones, organizado verticalmente
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(panelEntrada, BorderLayout.NORTH);
        panelIzquierdo.add(panelColaboracion, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        // Área de texto para mostrar información, no editable
        txtArea = new JTextArea(10, 30);
        txtArea.setEditable(false);

        // Panel personalizado para mostrar grafo
        panelGrafo = new GrafoPanel();

        // Añadir componentes al JFrame con BorderLayout
        add(panelIzquierdo, BorderLayout.WEST);            // Panel izquierdo
        add(new JScrollPane(txtArea), BorderLayout.SOUTH); // Área texto con scroll abajo
        add(panelGrafo, BorderLayout.CENTER);               // Panel grafo en centro

        setVisible(true); // Mostrar ventana
    }

    /**
     * Clase interna para dibujar el grafo de contactos y colaboraciones.
     */
    public static class GrafoPanel extends JPanel {
        // Lista de nodos para dibujar
        private List<NodoContacto> nodos;
        // Mapa de posiciones (coordenadas) para cada nodo
        private java.util.Map<NodoContacto, Point> posiciones;

        // Referencia al grafo a visualizar
        private modelo.GrafoColaboraciones grafo;

        /**
         * Constructor que inicializa estructuras y configura panel.
         */
        public GrafoPanel() {
            this.nodos = new ArrayList<>();
            this.posiciones = new java.util.HashMap<>();
            setPreferredSize(new Dimension(400, 400)); // Tamaño preferido del panel
            setBackground(Color.WHITE);                 // Fondo blanco
        }

        /**
         * Establece el grafo que se va a dibujar y actualiza nodos.
         * @param grafo Grafo de colaboraciones
         */
        public void setGrafo(modelo.GrafoColaboraciones grafo) {
            this.grafo = grafo;
            actualizarNodos(); // Calcula posiciones de los nodos
            repaint();         // Pide redibujado
        }

        /**
         * Actualiza la lista de nodos y calcula sus posiciones en círculo.
         */
        private void actualizarNodos() {
            nodos.clear();
            posiciones.clear();

            if (grafo == null) return;

            nodos.addAll(grafo.getContactos());

            int width = getWidth();
            int height = getHeight();

            // Si no está inicializado el tamaño, usar tamaño preferido
            if (width <= 0) width = getPreferredSize().width;
            if (height <= 0) height = getPreferredSize().height;

            int centerX = width / 2;
            int centerY = height / 2;
            int radio = Math.min(centerX, centerY) - 50;
            int n = nodos.size();

            for (int i = 0; i < n; i++) {
                double angulo = 2 * Math.PI * i / n;
                int x = (int) (centerX + radio * Math.cos(angulo));
                int y = (int) (centerY + radio * Math.sin(angulo));
                posiciones.put(nodos.get(i), new Point(x, y));
            }
        }


        /**
         * Método que dibuja el componente: nodos y aristas.
         * @param g Contexto gráfico
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (grafo == null) return;

            actualizarNodos();  // Actualizar posiciones cada vez que se pinta

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2)); // Grosor líneas

            // Dibujar aristas (colaboraciones)
            for (NodoContacto nodo : nodos) {
                Point p1 = posiciones.get(nodo);
                for (NodoContacto vecino : grafo.getVecinos(nodo)) {
                    Point p2 = posiciones.get(vecino);
                    if (p1 != null && p2 != null) {
                        g2.setColor(Color.LIGHT_GRAY);
                        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }

            // Dibujar nodos (círculos y nombres)
            int diametro = 40;
            for (NodoContacto nodo : nodos) {
                Point p = posiciones.get(nodo);
                if (p != null) {
                    g2.setColor(Color.CYAN);
                    g2.fillOval(p.x - diametro / 2, p.y - diametro / 2, diametro, diametro);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(p.x - diametro / 2, p.y - diametro / 2, diametro, diametro);

                    // Escribir nombre centrado en el nodo
                    FontMetrics fm = g2.getFontMetrics();
                    String nombre = nodo.getNombre();
                    int ancho = fm.stringWidth(nombre);
                    int alto = fm.getHeight();
                    g2.drawString(nombre, p.x - ancho / 2, p.y + alto / 4);
                }
            }
        }
    }
}
