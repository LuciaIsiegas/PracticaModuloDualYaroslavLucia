package aplicacion_bbdd;

import java.util.Scanner;

public class Estadisticas {

	public static final String CAMPOCLAVE = "temporada";
	public static final String FOREINGKEY = "jugador";

	public static String[] cogerDatos(Scanner sc) {
		boolean hayDatos = false;
		String[] tokens = null;
		System.out.println("");
		do {
			Estadisticas.printColumnas();
			System.out.print("Introduce los datos entre comillas simples: ");
			tokens = Metodos.tokenize(sc.nextLine());
			if (!Estadisticas.validarTokens(tokens)) {
				System.out.println("Formato incorrecto");
				continue;
			}
			hayDatos = true;
		} while (!hayDatos);

		return tokens;
	}

	private static void printColumnas() {
		System.out.println("Columns:\n" + "temporada -> varchar(5) PK\n" + "jugador -> int(11) PK\n"
				+ "Puntos_por_partido -> float\n" + "Asistencias_por_partido -> float\n"
				+ "Tapones_por_partido -> float\n" + "Rebotes_por_partido -> float");
	}
	
	public static String[] cogerCamposClaves() {
		String[] campos = new String[2];
		campos[0] = "temporada";
		campos[1] = "jugador";
		return campos;
	}

	public static String[] cogerNombresDeColumnas() {
		String[] c = new String[6];
		c[0] = "temporada";
		c[1] = "jugador";
		c[2] = "Puntos_por_partido";
		c[3] = "Asistencias_por_partido";
		c[4] = "Tapones_por_partido";
		c[5] = "Rebotes_por_partido";
		return c;
	}

	private static boolean validarTokens(String[] tokens) {
		if (tokens.length != 6)
			return false;
		if (tokens[0].length() > 5)
			return false;
		if (!Metodos.isInt(tokens[1]))
			return false;
		if (!Metodos.isFloat(tokens[2]))
			return false;
		if (!Metodos.isFloat(tokens[3]))
			return false;
		if (!Metodos.isFloat(tokens[4]))
			return false;
		if (!Metodos.isFloat(tokens[5]))
			return false;

		return true;
	}
}
