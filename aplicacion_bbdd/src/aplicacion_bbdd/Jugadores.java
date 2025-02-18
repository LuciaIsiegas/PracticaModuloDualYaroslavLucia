package aplicacion_bbdd;

import java.util.Scanner;

public class Jugadores {
	public static String[] cogerDatos(Scanner sc) {
		boolean hayDatos = false;
		String[] tokens = null;
		System.out.println("");
		do {
			Jugadores.printColumnas();
			System.out.print("Introduce datos separados por espacio: ");
			tokens = sc.nextLine().split(" ");
			if (!Jugadores.validarTokens(tokens)) {
				System.out.println("Formato incorrecto");
				continue;
			}
			hayDatos = true;
		} while (!hayDatos);
		
		return tokens;
	}

	private static void printColumnas() {
		System.out.println("Columns:\n"
				+ "codigo -> int(11) PK\n"
				+ "Nombre -> varchar(30)\n"
				+ "Procedencia -> varchar(20)\n"
				+ "Altura -> varchar(4)\n"
				+ "Peso -> int(11)\n"
				+ "Posicion -> varchar(5)\n"
				+ "Nombre_equipo -> varchar(20)");
	}

	private static boolean validarTokens(String[] tokens) {
		if (tokens.length != 7)
			return false;
		if (!Metodos.isInt(tokens[0]))
			return false;
		if (tokens[1].length() > 30)
			return false;
		if (tokens[2].length() > 20)
			return false;
		if (tokens[3].length() > 4)
			return false;
		if (!Metodos.isInt(tokens[4]))
			return false;
		if (tokens[5].length() > 5)
			return false;
		if (tokens[6].length() > 20)
			return false;

		return true;
	}
}
