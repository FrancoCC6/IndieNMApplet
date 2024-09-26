import java.awt.event.*
import java.awt.*
import javax.swing.*
import java.util.ArrayList;
import java.util.EnumMap;

public class Componentes {
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
		p_inf__panel_menuprincipal, // Menu principal
		p_inf__panel_red_conocida, // Conectarse a una red conocida
		p_inf__panel_red_nueva, // Conectarse a una red nueva y registrarla
		p_inf__panel_splashscreen; // Pantalla para tiempos de carga

	private static LayoutManager
		p_inf__layout_menuprincipal = new GridLayout(1, 1), // TODO: Poner numeros apropiados
		p_inf__layout_red_conocida = new GridLayout(1, 1),	// TODO: ídem
		p_inf__layout_red_nueva = new GridBagLayout(),
		p_inf__layout_splashscreen = new BorderLayout(); // El spinner ira en BorderLayout.CENTER

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
	
	private static ArrayList<Red> p_inf__redes_conocidas;

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
		p_inf__panel_menuprincipal = new JPanel(
	}

	private static void queryRedesConocidas() {

	}
}
