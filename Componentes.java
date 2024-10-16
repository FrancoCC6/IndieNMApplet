import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;
import java.util.regex.*;
import java.io.File;
// import javafx.util.Pair;
import java.util.ArrayList;
// import java.util.EnumMap;

public class Componentes {
    private static final boolean DEBUGGING = true;

    // Paneles principales y layouts
    private static final LayoutManager P_SUP__LAYOUT = new FlowLayout();
    public static final JPanel PANEL_SUPERIOR = new JPanel(P_SUP__LAYOUT);

    private static final LayoutManager P_INF__LAYOUT = new CardLayout();
    public static final JPanel PANEL_INFERIOR = new JPanel(P_INF__LAYOUT);

    // Constantes para cardlayout
    // TODO: Considerar usar estos valores para maps
    private static final String
        STR_MENUPRINCIPAL = "MENU_PRINCIPAL",
        STR_REDCONOCIDA   = "RED_CONOCIDA",
        STR_REDNUEVA      = "RED_NUEVA",
        STR_SPLASH        = "SPLASH_SCREEN";

    // Panel Superior - Wrapper para botonera general

    // Subcomponentes
    private static JButton
        p_sup__boton_menu_principal,
        p_sup__boton_reiniciar_wifi;

    // Listeners
    private static ActionListener
        p_sup__listener_menu_principal = e -> {
            // TODO: Cambiar esto por una rutina de buscar redes, algo asi:
            // ((CardLayout)P_INF__LAYOUT).show(PANEL_INFERIOR, STR_SPLASH);
            // lista_redes.buscarRedes();
            // ((CardLayout)P_INF__LAYOUT).show(PANEL_INFERIOR, STR_MENUPRINCIPAL);
            ((CardLayout)P_INF__LAYOUT).show(PANEL_INFERIOR, STR_MENUPRINCIPAL);

	    // Reiniciar campos de red nueva
	    // p_inf__red_nueva_input_ssid.set
        },
        p_sup__listener_reiniciar_wifi = e -> {
            // TODO: Reiniciar wifi, comunicarse con shellcommands
        };

    // Panel Inferior - Wrapper para menus particulares

    // Subcomponentes
    private static JPanel
        p_inf__panel_menuprincipal,  // Menu principal
        p_inf__panel_red_conocida,   // Conectarse a una red conocida
        p_inf__panel_red_nueva,      // Conectarse a una red nueva y registrarla
        p_inf__panel_splashscreen;   // Pantalla para tiempos de carga

    private static JTextField p_inf__red_nueva_input_ssid;
    private static JPasswordField p_inf__red_nueva_input_psk;

    private static JButton
        p_inf__red_nueva_boton_conectar;

    // Subcomponentes para menu principal
    private static JButton
        p_inf__menu_boton_conectar_red_conocida = new JButton("Conectar red conocida"),
        p_inf__menu_boton_conectar_red_nueva = new JButton("Conectar red nueva");
        //p_inf__menu_boton_salir = new JButton("Salir");

    //private static ListModel<Pair<String, String>> lista_redes = new AbstractListModel<Red>() {
    //private static ListModel<Red> lista_redes = new AbstractListModel<Red>() {

    private static ArrayList<String> redes__ssid = new ArrayList<String>();
    private static ArrayList<String> redes__path = new ArrayList<String>();

    private static void queryRedesConocidas() {
        // Reiniciar listas
        redes__path = new ArrayList<String>();
        redes__ssid = new ArrayList<String>();

        Scanner lector;
        Pattern patron = Pattern.compile(".wpa_supplicant.conf.*");
        List<File> directorio_wpa = Stream.of(
            new File(Usuario.getDirectorioWPAConf()).listFiles())
            .filter(
            file -> !file.isDirectory()
            &&	patron.matcher(file.getName()).find())
            .collect(Collectors.toList());
        try {
            for (File archivo_wpa: directorio_wpa) {
                System.err.println(archivo_wpa.getName());
                String ssid;
                lector = new Scanner(archivo_wpa);
                while (lector.hasNextLine()) {
                    String linea = lector.nextLine();
                    if (!linea.contains("ssid=")) { // Guarda que capaz hay que castearlo a CharSequence
                    continue;
                    }

                    redes__ssid.add(
                        linea.substring(
                            linea.indexOf("\"") + 1,
                            linea.lastIndexOf("\"")
                        )
                    );
                    redes__path.add(archivo_wpa.getName());

                    break;
                }
            }

            if (redes__path.size() != redes__ssid.size()) {
            throw new Exception();
            }
        }
        catch (Exception e) {
            System.err.println("Error buscando las redes conocidas");
        }
    }

    private static ListModel<String> lista_redes = new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return redes__ssid.size();
            }

            @Override
            public String getElementAt(int index) {
                return redes__ssid.get(index);
            }

        public String getPathOf(int index) {
                return redes__path.get(index);
        }
    };
        // TODO: Estos son datos de prueba, cambiarlos
        // TODO: Instanciar en cada query, y luego cargar el panel de redes conocidas
    //private static JList<Red> p_inf__redc_lista_redes = new JList<Red>(lista_redes);
    private static JList<String> p_inf__redc_lista_redes = new JList<String>(lista_redes);

    // Listeners
    private static ActionListener
        p_inf__menu_listener_conectar_red_conocida = e -> {
		// TODO: Transicion a menu de red conocida
            ((CardLayout)P_INF__LAYOUT).show(PANEL_INFERIOR, STR_REDCONOCIDA);
        },
        p_inf__menu_listener_conectar_red_nueva = e -> {
            ((CardLayout)P_INF__LAYOUT).show(PANEL_INFERIOR, STR_REDNUEVA);
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
        p_inf__red_nueva_input_psk = new JPasswordField();
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
        // Buscar redes, por si acaso aca lo pongo primero
        // TODO: Buscar mejor manera de conectar a las redes
        // lista_redes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queryRedesConocidas();

        // Construir panel superior

        // Inicializando subcomponentes

        // Boton menu principal
        p_sup__boton_menu_principal = new JButton("Menu principal");

        // Handlers
        p_sup__boton_menu_principal
            .addActionListener(p_sup__listener_menu_principal);

        PANEL_SUPERIOR
            .add(p_sup__boton_menu_principal);

        // Boton reiniciar WiFi

        p_sup__boton_reiniciar_wifi = new JButton("Reiniciar WiFi");

        // Handlers
        p_sup__boton_reiniciar_wifi
            .addActionListener(p_sup__listener_reiniciar_wifi);

        PANEL_SUPERIOR
            .add(p_sup__boton_reiniciar_wifi);

        // Construir panel inferior

        // Inicializando subcomponentes
        p_inf__inicializarPanelMenuPrincipal();
        p_inf__inicializarPanelRedConocida();
        p_inf__inicializarPanelRedNueva();
        p_inf__inicializarPanelSplashScreen();

        // Agregando componentes al panel
        PANEL_INFERIOR
            .add(p_inf__panel_menuprincipal, STR_MENUPRINCIPAL);
        PANEL_INFERIOR
            .add(p_inf__panel_red_conocida, STR_REDCONOCIDA);
        PANEL_INFERIOR
            .add(p_inf__panel_red_nueva, STR_REDNUEVA);
        PANEL_INFERIOR
            .add(p_inf__panel_splashscreen, STR_SPLASH);
    }

	/*
    private static AbstractListModel<Pair<String, String> queryRedesConocidas() {
		AbstractListModel<Pair<String, String> 
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
