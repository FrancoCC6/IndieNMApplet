// Implementar como singleton. Mas info: https://www.baeldung.com/java-singleton
// Comandos normales y sudo: https://stackoverflow.com/questions/18708087/how-to-execute-bash-command-with-sudo-privileges-in-java
// Mas sobre comandos https://www.baeldung.com/run-shell-command-in-java

import java.io.IOException; // Provisional, sacado de SO
import java.io.InputStream; // Provisional, sacado de SO
import java.util.List; // Provisional, sacado de SO
import java.util.Arrays; // Provisional, sacado de SO
import java.util.Scanner; // Provisional, sacado de SO

public enum ShellCommands {
    INSTANCIA();

    private String pword_usuario;
    private String interfaz_wifi;
    // private final String WPA_SUPPLICANT_TEMPLATE;

    // private ShellCommands(String pword_usuario) {
    // TODO: Hacer asincrono
    // TODO: REVISAR, refactorizar, limpiar
    private ShellCommands() {
        this.pword_usuario = null;
        // this.PWORD_USUARIO = pword_usuario;

        /*
        Process p =
            Runtime
            .getRuntime()
            .exec("ls /sys/class/net | grep wlp");

        StreamGobbler stream_gobbler = new StreamGobbler(p.getInputStream(), System.out::println); // TODO: Cambiar el println

        Future<?> future = executorService.submit(stream_gobbler);
        int exit_code = p.waitFor();

        // assertDoesNotThrow(() -> future.get(10, TimeUnit.SECONDS));
        // assertEquals(0, exit_code);
        */

        try {
            List<ProcessBuilder> builders = Arrays.asList(
                new ProcessBuilder("ls", "/sys/class/net"),
                new ProcessBuilder("grep", "wlp")
            );

            List<Process> procesos = ProcessBuilder.startPipeline(builders);
            Process last = procesos.get(procesos.size() - 1);

            // List<String> output = readOutput(last.getInputStream());

            // if (output.size() == 1) {
                // interfaz_wifi = output.get(0);
                // System.out.println(interfaz_wifi); // TODO: BORRAR
            // }

            // TESTING SHIT
            Scanner s = new Scanner(last.getInputStream());
            interfaz_wifi = s.next();
            if (s.hasNext()) {
                // TODO: Manejar error
            }
            /*
            else {
                // TODO: Manejar mas de un controlador compatible
            }
            */

            /*
            // Depuracion
            for (String s : output) {
                System.out.println(s);
            }
            */

            // TODO: Revisar si no deberian ser cerrados los procesos
        }
        catch (Exception e) { // Either IOException or InterruptedException
            System.err.println("Error en comandos shell");
        }
    }

    public ShellCommands get() {
        // System.out.println(interfaz_wifi); // Funciona
        return INSTANCIA;
    }

    public void setPWord(String pword) {
        if (pword_usuario != null) {
            System.err.println("Mensaje de error");
            return;
        }

        this.pword_usuario = pword;
    }

    /*
    public void setInterfazWiFi(String interfaz) {
        if (pword_usuario != null) {
            System.err.println("Mensaje de error");
            return 1;
        }

        this.pword_usuario = pword;
    }
    */

    /*
    private void ejecutarComando(boolean is_sudo, String comando) {
        if (is_sudo) { // Para este caso si tendria que usar runtime

        }
    }
    */

    private String[] getComandoSudo(String comando) {
        return new String[] {
            "/bin/bash",
            "-c",
            "echo " + pword_usuario + " | sudo -S " + comando
        };
    }

    public void conectarWPASupplicant(String ws_path) {
        if (pword_usuario == null) {
            System.err.println("No se puede ejecutar comando porque falta suministrar password");
            return;
        }

        try {
            Process proceso_universal;
			int status;
			String[] comandos = new String[] {
				// 1. Matar wpa_supplicant
				"pkill wpa_supplicant",

				// 2. Matar dhclient
				"pkill dhclient",

				// 3. Ejecutar wpa_supplicant
				"wpa_supplicant -B -Dnl80211 -i" + interfaz_wifi + " -c" + ws_path,

				// 4. Ejecutar dhclient
				"dhclient"
			};

			for (String comando : comandos) {
				proceso_universal = Runtime.getRuntime()
					.exec(getComandoSudo(comando));
				status = proceso_universal.waitFor();
				// GUARDA: wpa_supplicant se demora demasiado poco

				// TODO: Considerar loguear las salidas, ahora es solo de prueba
				// String linea;
				// BufferedReader input = new BufferedReader(new InputStreamReader(proceso_universal.getInputStream()));
				// while ((linea = input.readLine()) != null) {
					// System.out.println(linea);
				// }

				// input.close();
			}
        }
        catch (Exception e) {
            System.err.println("Mierda");
        }
    }
}
