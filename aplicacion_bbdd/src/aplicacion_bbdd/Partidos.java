package aplicacion_bbdd;

<<<<<<< HEAD
public class Partidos {
	
	public static final String CAMPOCLAVE = "codigo";
	public static final String FOREINGKEYLOCAL = "equipo_local";
	public static final String FOREINGKEYVISITANTE = "equipo_visitante";
=======
import java.util.Scanner;
>>>>>>> main

public class Partidos {
	public static String[] cogerDatos(Scanner sc) {
		boolean hayDatos = false;
		String[] tokens = null;
		System.out.println("");
		do {
			Partidos.printColumnas();
			System.out.print("Introduce los datos entre comillas simples: ");
			tokens = Metodos.tokenize(sc.nextLine());
			if (!Partidos.validarTokens(tokens)) {
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
				+ "equipo_local -> varchar(20)\n"
				+ "equipo_visitante -> varchar(20)\n"
				+ "puntos_local -> int(11)\n"
				+ "puntos_visitante -> int(11)\n"
				+ "temporada -> varchar(5)");
	}

	private static boolean validarTokens(String[] tokens) {
		if (tokens.length != 6)
			return false;
		if (!Metodos.isInt(tokens[0]))
			return false;
		if (tokens[1].length() > 20)
			return false;
		if (tokens[2].length() > 20)
			return false;
		if (!Metodos.isInt(tokens[3]))
			return false;
		if (!Metodos.isInt(tokens[4]))
			return false;
		if (tokens[5].length() > 5)
			return false;

		return true;
	}
}
