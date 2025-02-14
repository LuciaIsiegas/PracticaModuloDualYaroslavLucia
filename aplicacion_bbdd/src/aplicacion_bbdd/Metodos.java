package aplicacion_bbdd;

import java.util.Scanner;
import java.sql.*;

public class Metodos {

	static final String BBDD = "jdbc:mysql://localhost:3306/nba";
	static final String USER = "root";
	static final String MENU = "_______________________________________________\n" + "Menú de opciones\n"
			+ "_______________________________________________\n" + "1- Mostrar datos\n" + "2- Alta de datos\n"
			+ "3- Modificar datos\n" + "4- Eliminar datos\n" + "5- Salir\n"
			+ "_______________________________________________";
	static final String TABLAS = "1. Estadisticas\n" + "2. Jugadores\n" + "3. Equipos\n" + "4. Partidos\n"
			+ "Elige la tabla: ";

	public static boolean isInt(String num)
	{
		try
		{
			Integer.parseInt(num);
			return true;
		} catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	public static boolean isDouble(String num)
	{
		try
		{
			Double.parseDouble(num);
			return true;
		} catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	public static boolean isFloat(String num)
	{
		try
		{
			Float.parseFloat(num);
			return true;
		} catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	
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

	}

	public static void altaDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc);
		String[] insertValues = null;
		
		switch (tabla) {
		case "estadisticas":
			insertValues = Estadisticas.cogerDatos(sc);
			String consultaFinal = Metodos.prepararConsultaInsert(insertValues, tabla);
			try {
				PreparedStatement sentencia = connection.prepareStatement(consultaFinal);
				sentencia.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
