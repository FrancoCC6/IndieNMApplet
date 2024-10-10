import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // args[0]: Contraseña del usuario que ejecuta, para ejecutar comandos sudo
        // args[1]: Path de los/al wpa_supplicant.conf
        // if (args.length != 2) {
            // System.err.println("Faltan argumentos");
            // return;
        // }
		Usuario.setPassword(args[0]);
		Usuario.setDirectorioWPAConf(args[1]);
        SwingUtilities.invokeLater(() -> GUI.createFrame());
        // ShellCommands sc = ShellCommands.INSTANCIA.get();
        // sc.setPWord(args[0]);
        // sc.conectarWPASupplicant(args[1]);
    }
}
