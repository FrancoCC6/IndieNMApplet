import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GUI {
    private static final LayoutManager FRAME_LAYOUT = new BorderLayout();
    private static final String TITLE = "Conectarse a una red";
    public static final int
        FRAME_ANCHO = 300,
        FRAME_ALTO = 200;

    private static final JFrame FRAME = new JFrame(TITLE);

    public static void createFrame() {
        FRAME
            .setLayout (FRAME_LAYOUT);

        // Inicializar componentes
        FRAME
            .add (Componentes.PANEL_SUPERIOR, BorderLayout.NORTH);
        FRAME
            .add (Componentes.PANEL_INFERIOR, BorderLayout.CENTER);

        FRAME
            .setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        FRAME
            .setResizable (false);
        FRAME
            .setSize (FRAME_ANCHO, FRAME_ALTO);
        FRAME
            .setLocationRelativeTo (null);
        FRAME
            .setVisible (true);
    }
}
