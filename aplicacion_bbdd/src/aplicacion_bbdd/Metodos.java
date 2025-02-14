package aplicacion_bbdd;

import java.util.Scanner;
import java.sql.*;

public class Metodos {

	static final String BBDD = "jdbc:mysql://localhost:3306/nba";
	static final String USER = "root";
	static final String MENU = "_______________________________________________\n" 
			+ "Menú de opciones\n"
			+ "_______________________________________________\n" 
			+ "1- Mostrar datos\n" 
			+ "2- Alta de datos\n"
			+ "3- Modificar datos\n" 
			+ "4- Eliminar datos\n" 
			+ "5- Salir\n"
			+ "_______________________________________________";
	static final String TABLAS = "1. Estadisticas\n" 
			+ "2. Jugadores\n" 
			+ "3. Equipos\n" 
			+ "4. Partidos\n"
			+ "Elige la tabla: ";

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

	public static String elegirTabla(Scanner sc) {
		int numeroDeLaTabla = Metodos.getInt(sc, Metodos.TABLAS);

		switch (numeroDeLaTabla) {
		case 1:
			return "estadisticas";
		case 2:
			return "jugadores";
		case 3:
			return "equipos";
		case 4:
			return "partidos";
		default:
			return null;
		}
	}

	public static boolean validarDatosParaConsulta(String str)
	{
		if(str.contains(";")) return false;
		else return true;
	}
	
	public static String prepararConsultaInsert(String[] values, String tabla) {
		String consulta = "insert into " + tabla + " values (";
		for (int i = 0; i < values.length; i++) {
			consulta += "'" + values[i] + "'";
			if (i != values.length - 1)
				consulta += ",";
		}
		consulta += ");";
		return consulta;
	}

	public static void mostrarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc);
		if (tabla == null) {
			System.out.println("No existe esa tabla.");
			return;
		}
		
		String consulta = "select * from " + tabla;
		try {
			PreparedStatement ps = connection.prepareStatement(consulta);
			ResultSet res = ps.executeQuery();
			ResultSetMetaData rmd = res.getMetaData();
			
			// ENCABEZADO
			int columnas = rmd.getColumnCount();
			for (int i = 1; i <= columnas; i++) {
				if (i == 1) {
					System.out.printf("%15s", rmd.getColumnName(i));
				} else {
					System.out.printf("%25s", rmd.getColumnName(i));
				}
			}
			System.out.println();
			for (int j = 0; j < columnas*25; j++) {
				System.out.print("*");
			}
			System.out.println();
			
			// DATOS
			while (res.next()) {
				for (int k = 1; k <= columnas; k++) {
					if (k == 1) {
						System.out.printf("%15s", res.getString(k));
					} else {
						System.out.printf("%25s", res.getString(k));
					}
					
				}
				System.out.println();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public static void altaDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc);
		String[] insertValues = null;
		
		switch (tabla) {
		case "estadisticas":

			break;
		case "jugadores":

			break;
		case "equipos":

			break;
		case "partidos":

			break;
		}
	}

	public static void modificarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc);
	}

	public static void eliminarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc);
	}

}
