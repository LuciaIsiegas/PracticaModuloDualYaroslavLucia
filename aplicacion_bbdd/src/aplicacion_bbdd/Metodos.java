package aplicacion_bbdd;

import java.util.Scanner;

public class Metodos {
	
	public static int seleccionTabla(String opcion, Scanner sc) {
		System.out.print(opcion + ": \n"
				+ "1. Estadisticas\n"
				+ "2. Jugadores\n"
				+ "3. Equipos\n"
				+ "4. Partidos\n"
				+ "Tabla seleccionada: ");
		String aux = sc.nextLine();
		
		if (aux.matches("[1-4]")) {
			return Integer.parseInt(aux);
		}
	}

}
