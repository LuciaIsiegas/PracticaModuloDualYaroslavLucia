package aplicacion_bbdd;

import java.sql.Connection;
import java.util.Scanner;

public class Partidos {

	public static final String CAMPOCLAVE = "codigo";

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
		System.out.println("Columns:\n" + "codigo -> int(11) PK\n" + "equipo_local -> varchar(20)\n"
				+ "equipo_visitante -> varchar(20)\n" + "puntos_local -> int(11)\n" + "puntos_visitante -> int(11)\n"
				+ "temporada -> varchar(5)");
	}
	
	public static String[] cogerCamposClaves() {
		String[] campos = new String[1];
		campos[0] = "codigo";
		return campos;
	}

	public static String[] cogerNombresDeColumnas() {
		String[] c = new String[6];
		c[0] = "codigo";
		c[1] = "equipo_local";
		c[2] = "equipo_visitante";
		c[3] = "puntos_local";
		c[4] = "puntos_visitante";
		c[5] = "temporada";
		return c;
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
	
	private static boolean validarCampoClave(String dato) {
		if (dato.length() > 11 || dato.length() == 0) return false;
		
		for (int i = 0; i < dato.length(); i++) {
			char letra = dato.charAt(i);
			if (!Character.isDigit(letra)) {
				return false;
			}
		}
		return true;
	}
	
	public static void eliminarDatos(Connection connection, Scanner sc) {
		System.out.print("\nElimnar datos de la tabla 'Partidos'\n"
				+ "Indica el " + CAMPOCLAVE + " formato 'int(11)': ");
		String clave = sc.nextLine();
		
		if (validarCampoClave(clave)) {
			String consulta = "delete from partidos where " + CAMPOCLAVE + " = " + clave;
			Metodos.ejecutarConsultaDeAccion(connection, consulta);
			System.out.println("Eliminación de datos completada.");
		} else {
			System.out.println("Formato no válido");
		}
	}
}
