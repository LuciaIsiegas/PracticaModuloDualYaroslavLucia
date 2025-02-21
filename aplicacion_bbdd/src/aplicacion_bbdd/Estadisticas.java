package aplicacion_bbdd;

import java.sql.Connection;
import java.util.Scanner;

public class Estadisticas {

	public static String[] cogerDatos(Scanner sc) {
		boolean hayDatos = false;
		String[] tokens = null;
		System.out.println("");
		do {
			Estadisticas.printColumnas();
			System.out.print("Introduce los datos entre comillas simples(fin para salir): ");
			String input = sc.nextLine();
			if (input.equals("fin"))
				return null;
			tokens = Metodos.tokenize(input);
			
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

	private static boolean validarCampoClave(String dato) {
		if (dato.length() > 5 || dato.length() == 0)
			return false;

		for (int i = 0; i < dato.length(); i++) {
			char letra = dato.charAt(i);
			if (!Character.isDigit(letra) && i != 2) {
				return false;
			} else if (i == 2 && letra != '/') {
				return false;
			}
		}
		return true;
	}

	public static void eliminarDatos(Connection connection, Scanner sc) {
		System.out.print(
				"\nElimnar datos de la tabla 'Estadisticas'\n" + "Indica la temporada formato '00/00': ");
		String clave = sc.nextLine();

		if (validarCampoClave(clave)) {
			String consulta = "delete from estadisticas where temporada like '" + clave + "'";
			Metodos.ejecutarConsultaDeAccion(connection, consulta);
			System.out.println("Eliminación de datos completada.");
		} else {
			System.out.println("Formato no válido");
		}
	}
}
