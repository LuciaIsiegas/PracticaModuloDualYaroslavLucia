package aplicacion_bbdd;

import java.util.Scanner;

public class Metodos {
	
	public static int getInt(Scanner sc, String mensaje) {
		boolean correcto = false;
		int num = 0;
		do {
			System.out.print(mensaje);
			String aux = sc.nextLine();
			if (aux.matches("(-||\\+)?\\d+")) {
				num = Integer.parseInt(aux);
				correcto = true;
			}
		} while (!correcto);
		return num;
	}
	
	public static String subMenu(String opcion, Scanner sc) {
		String mensaje = opcion + ": \n"
				+ "1. Estadisticas\n"
				+ "2. Jugadores\n"
				+ "3. Equipos\n"
				+ "4. Partidos\n"
				+ "Tabla seleccionada: ";
		return mensaje;
	}

}
