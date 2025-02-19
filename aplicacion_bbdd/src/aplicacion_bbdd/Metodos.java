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
	static final String RELACIONES = "\nRelaciones disponibles:\n"
			+ "1. Estadsticas - Jugadores\n"
			+ "2. Jugadores - Equipos\n"
			+ "3. Equipos - Partidos\n"
			+ "Tu elección: ";

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
	
	public static String mostrar1Tabla(Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc, mensaje);
		if (tabla == null) {
			System.out.println("No existe esa tabla.");
			return null;
		}
		return "select * from " + tabla;
	}
	
	public static String mostrar2Tablas(Scanner sc, String mensaje) {
		int relacion = getInt(sc, RELACIONES);
		String join = "";
		if (relacion == 1) {
			return "select * from estadisticas join jugadores on estadisticas.jugador=jugadores.codigo";
		} else if (relacion == 2) {
			return "select * from jugadores join equipos on jugadores.nombre_equipo=equipos.nombre";
		} else if (relacion == 3) {
			System.out.print("¿Equipo local(1) o vistante(2)?: ");
			String aux = sc.nextLine();
			if (aux.equals("1")) {
				join = "equipos.nombre=partidos.equipo_local";
			} else if (aux.equals("2")) {
				join = "equipos.nombre=partidos.equipo_visitante";
			} else {
				return null;
			}
			return "select * from equipos join partidos on " + join;
		} else {
			return null;
		}
	}
	
	public static void mostrarDatos(Connection connection, String consulta, Scanner sc) {
		if (consulta == null) {
			System.out.println("Opción no disponible.");
			return;
		}
		
		try {
			ResultSet res = ejecutarConsultaDeSeleccion(connection, consulta);
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
			int contador = 1;
			while (res.next()) {
				for (int k = 1; k <= columnas; k++) {
					if (k == 1) {
						System.out.printf("%15s", res.getString(k));
					} else {
						System.out.printf("%25s", res.getString(k));
					}
				}
				System.out.println();
				
				if (contador % 250 == 0) {
					String cadena = "";
					System.out.print("Para parar 'fin': ");
					cadena = sc.nextLine();
					if (cadena.equalsIgnoreCase("fin")) {
						break;
					}
				}
				contador++;
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
