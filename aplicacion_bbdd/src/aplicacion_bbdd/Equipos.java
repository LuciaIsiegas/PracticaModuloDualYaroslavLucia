package aplicacion_bbdd;

import java.util.Scanner;

public class Equipos {
	
	public static final String CAMPOCLAVE = "nombre";
	public static final String FOREINGKEY = CAMPOCLAVE;
	
	public static String[] cogerDatos(Scanner sc) {
		boolean hayDatos = false;
		String[] tokens = null;
		System.out.println("");
		do {
			Equipos.printColumnas();
			System.out.print("Introduce los datos entre comillas simples: ");
			tokens = Metodos.tokenize(sc.nextLine());
			if (!Equipos.validarTokens(tokens)) {
				System.out.println("Formato incorrecto");
				continue;
			}
			hayDatos = true;
		} while (!hayDatos);
		
		return tokens;
	}

	private static void printColumnas() {
		System.out.println("Columns:\n"
				+ "Nombre -> varchar(20) PK\n"
				+ "Ciudad -> varchar(20)\n"
				+ "Conferencia -> varchar(4)\n"
				+ "Division -> varchar(9)");
	}

	private static boolean validarTokens(String[] tokens) {
		if (tokens.length != 4)
			return false;
		if (tokens[0].length() > 20)
			return false;
		if (tokens[1].length() > 20)
			return false;
		if (tokens[2].length() > 4)
			return false;
		if (tokens[3].length() > 9)
			return false;

		return true;
	}
}
