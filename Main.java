import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // args[0]: Contraseña del usuario que ejecuta, para ejecutar comandos sudo
        SwingUtilities.invokeLater(() -> GUI.createFrame());
        // if (args.length < 1) {
            // System.err.println("Faltan argumentos");
            // return;
        // }
        // ShellCommands sc = ShellCommands.INSTANCIA.get();
        // sc.setPWord(args[0]);
        // sc.conectarWPASupplicant("/home/franco/.wpa_supplicant.conf.bk2");
    }
}
