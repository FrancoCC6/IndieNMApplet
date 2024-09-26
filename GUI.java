import java.awt.event.*
import java.awt.*
import javax.swing.*

public class GUI {
	private JFrame frame;
	private LayoutManager frame_layout = new BorderLayout();
	private String title = "Conectarse a una red";

	public static void createFrame() {
		frame = new JFrame(title);
		frame.setLayout(frame_layout);

		// Inicializar componentes
		frame.add(Componentes.PANEL_SUPERIOR, BorderLayout.NORTH);
		frame.add(Componentes.PANEL_INFERIOR, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
