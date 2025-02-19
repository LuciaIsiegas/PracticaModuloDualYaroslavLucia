package aplicacion_bbdd;

import java.util.*;
import java.sql.*;

public class Metodos {

	static final String BBDD = "jdbc:mysql://localhost:3306/nba";
	static final String USER = "root";
	static final String MENU = "_______________________________________________\n" 
			+ "Menú de opciones\n"
			+ "_______________________________________________\n" 
			+ "1- Mostrar datos\n" 
			+ "2- Mostrar datos de dos tablas relacionadas\n"
			+ "3- Alta de datos\n"
			+ "4- Modificar datos\n" 
			+ "5- Eliminar datos\n" 
			+ "6- Salir\n"
			+ "_______________________________________________";
	static final String TABLAS = "1. Estadisticas\n" 
			+ "2. Jugadores\n" 
			+ "3. Equipos\n" 
			+ "4. Partidos\n"
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
	

	public static String[] tokenize(String cadena)
	{
		String[] tokens = new String[1];
		String token = null;
		boolean dentroToken = false;
		int tokensCount = 0;
		for(int i = 0; i < cadena.length(); i++)
		{
			if(dentroToken)
			{
				if(cadena.charAt(i) == '\'')
				{
					dentroToken = false;
					if(tokensCount + 1 > tokens.length)
					{
						String[] newBuffer = new String[tokensCount + 1];
						for(int j = 0; j < tokens.length; j++)
						{
							newBuffer[j] = tokens[j];
						}
						tokens = newBuffer;
					}
					
					tokens[tokensCount++] = token;
					token = null;
				} else
				{
					token += cadena.charAt(i);
				}
					
			} else
			{
				if(cadena.charAt(i) == '\'')
				{
					dentroToken = true;
					token = new String();
				}
			}
		}
		
		return tokens;
	}
	
	public static ResultSet ejecutarConsultaDeSeleccion(Connection conexion, String consulta)
	{
		PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(consulta);
			ResultSet resultado = sentencia.executeQuery();
			return resultado;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void ejecutarConsultaDeAccion(Connection conexion, String consulta)
	{
		PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(consulta);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

	public static String elegirTabla(Scanner sc, String mensaje) {
		int numeroDeLaTabla = Metodos.getInt(sc, mensaje + Metodos.TABLAS);

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
			if(values[i].equals(""))
			{
				consulta += "null";
			} else
			{
				consulta += "'" + values[i] + "'";
			}
			if (i != values.length - 1)
				consulta += ",";
		}
		consulta += ");";
		return consulta;
	}	

	public static void mostrarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc, mensaje);
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
	
	public static boolean existeRelacion(String tabla1, String tabla2) {
		if ((tabla1.equals("estadisticas") && tabla2.equals("jugadores"))
				|| (tabla2.equals("estadisticas") && tabla1.equals("jugadores"))) {
			return true;
		} else if ((tabla1.equals("jugadores") && tabla2.equals("equipos"))
				|| (tabla1.equals("equipos") && tabla2.equals("jugadores"))){
			return true;
		} else if ((tabla1.equals("equipos") && tabla2.equals("partidos"))
				|| (tabla1.equals("partidos") && tabla2.equals("equipos"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void mostrarDatos2Tablas(Connection connection, Scanner sc, String mensaje) {
		String tabla1 = Metodos.elegirTabla(sc, "Escoge las primera tabla:\n");
		String tabla2 = Metodos.elegirTabla(sc, "Escoge la segunda tabla:\n");
		if (tabla2 == null || tabla1 == null || !existeRelacion(tabla1, tabla2)) {
			System.out.println("No existe relacion entre esas tablas.");
			return;
		}
		
		
		
		String consulta = "select * from " + tabla1 + " join " ;
		
		
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
		String tabla = Metodos.elegirTabla(sc, mensaje);
		String[] insertValues = null;
		String consultaFinal = null;
		
		switch (tabla) {
		case "estadisticas":
			insertValues = Estadisticas.cogerDatos(sc);
			break;
		case "jugadores":
			insertValues = Jugadores.cogerDatos(sc);
			break;
		case "equipos":
			insertValues = Equipos.cogerDatos(sc);
			break;
		case "partidos":
			insertValues = Partidos.cogerDatos(sc);
			break;
		}
		consultaFinal = Metodos.prepararConsultaInsert(insertValues, tabla);
		Metodos.ejecutarConsultaDeAccion(connection, consultaFinal);
	}

	public static void modificarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc, mensaje);
	}

	public static void eliminarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc, mensaje);
	}

}
