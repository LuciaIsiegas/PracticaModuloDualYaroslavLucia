package aplicacion_bbdd;

import java.util.Scanner;

public class Jugadores {

	public static final String CAMPOCLAVE = "codigo";
	public static final String FOREINGKEY = "nombre_equipo";

	public static String[] cogerDatos(Scanner sc) {
		boolean hayDatos = false;
		String[] tokens = null;
		System.out.println("");
		do {
			Jugadores.printColumnas();
			System.out.print("Introduce los datos entre comillas simples: ");
			tokens = Metodos.tokenize(sc.nextLine());
			if (!Jugadores.validarTokens(tokens)) {
				System.out.println("Formato incorrecto");
				continue;
			}
			hayDatos = true;
		} while (!hayDatos);

		return tokens;
	}

	private static void printColumnas() {
		System.out.println("Columns:\n" + "codigo -> int(11) PK\n" + "Nombre -> varchar(30)\n"
				+ "Procedencia -> varchar(20)\n" + "Altura -> varchar(4)\n" + "Peso -> int(11)\n"
				+ "Posicion -> varchar(5)\n" + "Nombre_equipo -> varchar(20)");
	}
	
	public static String[] cogerCamposClaves() {
		String[] campos = new String[1];
		campos[0] = "codigo";
		return campos;
	}

	public static String[] cogerNombresDeColumnas() {
		String[] c = new String[7];
		c[0] = "codigo";
		c[1] = "Nombre";
		c[2] = "Procedencia";
		c[3] = "Altura";
		c[4] = "Peso";
		c[5] = "Posicion";
		c[6] = "Nombre_equipo";
		return c;
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
