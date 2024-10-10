import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Usuario {
	private static String password = null;
	// TODO: Apartar estos atributos a otra clase
	private static String interfaz_wifi = null;
	private static String directorio_wpa_conf = null;

	public void conectarARed(String nombre_archivo) {
		// TODO: Adaptar
        if (!Usuario.hasPassword()) {
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
				"wpa_supplicant -B -Dnl80211 -i" 
				+ interfaz_wifi 
				+ " -c" + Usuario.directorio_wpa_conf + "/" + nombre_archivo,

				// 4. Ejecutar dhclient
				"dhclient"
			};

			for (String comando : comandos) {
				proceso_universal = Runtime.getRuntime()
					.exec(new String[] {
						"/bin/bash",
						"-c",
						"echo " + Usuario.getPassword() 
						+ " | sudo -S " + comando
        });
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
            System.err.println("Fallo al conectar a la red");
        }
	}

	public static String getPassword() {
		return Usuario.password;
	}

	public static boolean hasPassword() {
		return Usuario.password != null;
	}

	public static void setPassword(String password) {
		Usuario.password = password;
	}

	public static boolean hasInterfazWifi() {
		return Usuario.interfaz_wifi != null;
	}

	public static String getInterfazWifi() {
		if (!hasInterfazWifi()) {
			try {
				List<ProcessBuilder> builders = Arrays.asList(
					new ProcessBuilder("ls", "/sys/class/net"),
					new ProcessBuilder("grep", "wlp")
				);

				List<Process> procesos = ProcessBuilder.startPipeline(builders);
				Process last = procesos.get(procesos.size() - 1);

				Scanner s = new Scanner(last.getInputStream());
				Usuario.interfaz_wifi = s.next();
				/*
				if (s.hasNext()) {
					// TODO: Manejar error (?)
				}
				*/
				/*
				else {
					// TODO: Manejar mas de un controlador compatible
				}
				*/
			}
			catch (Exception e) { // Either IOException or InterruptedException
				System.err.println("Error detectando interfaz wifi");
			}
		}
		
		return Usuario.interfaz_wifi;
	}

	public static String getDirectorioWPAConf() {
		return Usuario.directorio_wpa_conf;
	}

	public static boolean hasDirectorioWPAConf() {
		return Usuario.directorio_wpa_conf != null;
	}

	public static void setDirectorioWPAConf(String directorio_wpa_conf) {
		if (Usuario.directorio_wpa_conf == null) {
			Usuario.directorio_wpa_conf = directorio_wpa_conf;
		}
	}
}
