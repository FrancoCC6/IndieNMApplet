import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.EnumMap;

public class Componentes {
    private static final boolean DEBUGGING = true;
    // Panel Superior

    public static final JPanel PANEL_SUPERIOR; // Wrapper para botonera general

    // Layout
    private static final LayoutManager P_SUP__LAYOUT = new FlowLayout();

    // Subcomponentes
    private static JButton
        p_sup__boton_menu_principal,
        p_sup__boton_reiniciar_wifi;


    // Panel Inferior

    public static final JPanel PANEL_INFERIOR; // Wrapper para menus particulares

    // Layout
    private static final LayoutManager P_INF__LAYOUT = new CardLayout();

    // Subcomponentes
    private static JPanel
        p_inf__panel_menuprincipal,  // Menu principal
        p_inf__panel_red_conocida,   // Conectarse a una red conocida
        p_inf__panel_red_nueva,      // Conectarse a una red nueva y registrarla
        p_inf__panel_splashscreen;   // Pantalla para tiempos de carga

    private static JTextField
        p_inf__red_nueva_input_ssid,
        p_inf__red_nueva_input_psk;

    private static JButton
        p_inf__red_nueva_boton_conectar;

    // Subcomponentes para menu principal
    private static JButton
        p_inf__menu_boton_conectar_red_conocida = new JButton("Conectar red conocida"),
        p_inf__menu_boton_conectar_red_nueva = new JButton("Conectar red nueva");
        //p_inf__menu_boton_salir = new JButton("Salir");

    //private static ListModel<Red> lista_redes = new AbstractListModel<Red>() {
    private static ListModel<String> lista_redes = new AbstractListModel<String>() {
        @Override
        public int getSize() { return 1; }
        @Override
        //public Red getElementAt(int index) { return null; }
        public String getElementAt(int index) { return "Test"; }
    };
        // TODO: Estos son datos de prueba, cambiarlos
        // TODO: Instanciar en cada query, y luego cargar el panel de redes conocidas
    //private static JList<Red> p_inf__redc_lista_redes = new JList<Red>(lista_redes);
    private static JList<String> p_inf__redc_lista_redes = new JList<String>(lista_redes);

    private static ActionListener
        p_inf__menu_listener_conectar_red_conocida = e -> {
            System.out.println("Puto el");
                // TODO: Transicion a menu de red conocida
        },
        p_inf__menu_listener_conectar_red_nueva = e -> {
            System.out.println("que lee");
                // TODO: Transicion a menu de red nueva
        },

        p_inf__redn_listener_conectar = e -> {
            return;
                // TODO: Rutina de conexion a red nueva
        };

    // Layouts componentes panel inferior
    private static LayoutManager
        p_inf__layout_menuprincipal = new GridLayout(2, 1, 7, 7),
        p_inf__layout_red_conocida  = new BorderLayout(),	// Solo la lista en el medio
        p_inf__layout_red_nueva     = new GridBagLayout(),
        p_inf__layout_splashscreen  = new BorderLayout(); // El spinner ira en BorderLayout.CENTER

    // Inicializacion componentes panel inferior
    private static void p_inf__inicializarPanelMenuPrincipal() {
        p_inf__panel_menuprincipal = new JPanel(p_inf__layout_menuprincipal);

        p_inf__menu_boton_conectar_red_conocida
            .addActionListener (p_inf__menu_listener_conectar_red_conocida);
        p_inf__menu_boton_conectar_red_nueva
            .addActionListener (p_inf__menu_listener_conectar_red_nueva);

        p_inf__panel_menuprincipal
            .add (p_inf__menu_boton_conectar_red_conocida);
        p_inf__panel_menuprincipal
            .add (p_inf__menu_boton_conectar_red_nueva);
    }

    private static void p_inf__inicializarPanelRedConocida() {
        p_inf__panel_red_conocida = new JPanel(p_inf__layout_red_conocida);
        // Por ahora solo pongo la lista, despues vemos que onda

        p_inf__panel_red_conocida
            .add (p_inf__redc_lista_redes, BorderLayout.CENTER);

        // Red.cargarRedes();
    }

    private static void p_inf__inicializarPanelRedNueva() {
        p_inf__panel_red_nueva = new JPanel(p_inf__layout_red_nueva);

        GridBagConstraints gbc = new GridBagConstraints();

        p_inf__red_nueva_input_ssid = new JTextField();
        p_inf__red_nueva_input_psk = new JTextField();
            // TODO: Ocultar input
        p_inf__red_nueva_boton_conectar = new JButton("Conectar");

        p_inf__red_nueva_boton_conectar
            .addActionListener (p_inf__redn_listener_conectar);

        // Las etiquetas las voy a crear aparte, porque se me cantan los huevos

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            //gbc.anchor = GridBagConstraints.LINE_START;
        p_inf__panel_red_nueva
            .add (new JLabel("Nombre de la red"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.BOTH;
        p_inf__panel_red_nueva
            .add (p_inf__red_nueva_input_ssid, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0;
            //gbc.fill = GridBagConstraints.NONE;
        p_inf__panel_red_nueva
                .add (new JLabel("Contraseña"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.BOTH;
        p_inf__panel_red_nueva
            .add (p_inf__red_nueva_input_psk, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
        p_inf__panel_red_nueva
            .add (p_inf__red_nueva_boton_conectar, gbc);
    }

    private static void p_inf__inicializarPanelSplashScreen() {
        p_inf__panel_splashscreen = new JPanel(p_inf__layout_splashscreen);

        JLabel
            spinner = new JLabel(
                // Leyenda
                "Cargando..."//,
                // ICONO DESHABILITADO TEMPORALMENTE
                /*
                    // TODO: Contemplar posibilidad de mensajes especificos para cada caso
                // Icono
                new ImageIcon("res/loading_spinner.gif"),
                    // TODO: Contemplar poner el path en una variable
                    // TODO: El gif va muy rapido, ponerlo a velocidad normal
                // Orientacion
                SwingConstants.HORIZONTAL
                    // TODO: Buscar manera de cambiar la orientacion
                */
            );

        p_inf__panel_splashscreen
            .add (spinner, BorderLayout.CENTER);
    }

    /*
    private static final enum PISC { // Panel Inferior SubComponentes, no se me ocurrió algo mejor
        MENU_PRINCIPAL,
        RED_CONOCIDA,
        RED_NUEVA,
        SPLASH_SCREEN
    }

    private static final EnumMap<PISC, JPanel> P_INF__SUBC_PANELES = new EnumMap<>(PISC.class);
    private static final EnumMap<PISC, LayoutManager> P_INF__SUBC_LAYOUTS = new EnumMap<>(PISC.class);
    */

    //private static ArrayList<Red> p_inf__redes_conocidas;

    // Inicializador
    static {
        // TODO: Construir componentes
        PANEL_SUPERIOR = new JPanel(P_SUP__LAYOUT);
        PANEL_INFERIOR = new JPanel(P_INF__LAYOUT);

        // Construir panel superior

        // Inicializando subcomponentes

        // Boton menu principal
        p_sup__boton_menu_principal = new JButton("Menu principal");

        // Handlers
        // TODO: Hacer handlers

        PANEL_SUPERIOR.add(p_sup__boton_menu_principal);

        // Boton reiniciar WiFi

        p_sup__boton_reiniciar_wifi = new JButton("Reiniciar WiFi");

        // Handlers
        // TODO: Hacer handlers

        PANEL_SUPERIOR.add(p_sup__boton_reiniciar_wifi);

        // Construir panel inferior

        // Inicializando subcomponentes
        p_inf__inicializarPanelMenuPrincipal();
        p_inf__inicializarPanelRedConocida();
        p_inf__inicializarPanelRedNueva();
        p_inf__inicializarPanelSplashScreen();
    }

    private static void queryRedesConocidas() {

    }

  /*
	private static void main(String[] args) {
      if (args.length != 1) {
          return 1;
      }



      switch (args[0]) {
      case "MENU_PRINCIPAL":
          // TODO: Handle debug
          break;
      case "RED_CONOCIDA":
          // TODO: Handle debug
          break;
      case "RED_NUEVA":
          // TODO: Handle debug
          break;
      case "SPLASH_SCREEN":
          // TODO: Handle debug
          break;
      default:
          return 1;
      }
	}
  */

    public static void main(String [] args) {
        if (!DEBUGGING || args.length != 1) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Test");
            JPanel p;

            switch (args[0]) {
            case "s":
                p = p_inf__panel_splashscreen;
                break;
            case "p":
                p = p_inf__panel_menuprincipal;
                break;
            case "n":
                p = p_inf__panel_red_nueva;
                break;
            case "c":
                p = p_inf__panel_red_conocida;
                break;
            default:
                return;
            }

            f.add(p);

            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(300, 200);
            f.setResizable(false);
            f.setVisible(true);
        });
    }
}
