package aplicacion_bbdd;

import java.util.*;
import java.sql.*;

public class Metodos {

	static final String BBDD = "jdbc:mysql://localhost:3306/nba";
	static final String BBDD_NEW = "jdbc:mysql://localhost:3306";
	static final String USER = "root";
	static final String BASE_NOMBRE = "nba";
	static final String MENU = "Menú de opciones\n" + "_______________________________________________\n"
			+ "1- Crear la base\n" + "2- Conectar a la base\n" + "3- Eliminar la base\n" + "4- Mostrar datos\n"
			+ "5- Mostrar datos de dos tablas relacionadas\n" + "6- Alta de datos\n" + "7- Modificar datos\n"
			+ "8- Eliminar datos\n" + "9- Salir\n";
	static final String TABLAS = "1. Estadisticas\n" + "2. Jugadores\n" + "3. Equipos\n" + "4. Partidos\n"
			+ "Elige la tabla: ";

	public static boolean isInt(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isFloat(String num) {
		try {
			Float.parseFloat(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String[] tokenize(String cadena) {
		String[] tokens = new String[0];
		String token = null;
		boolean dentroToken = false;
		int tokensCount = 0;
		for (int i = 0; i < cadena.length(); i++) {
			if (dentroToken) {
				if (cadena.charAt(i) == '\'') {
					dentroToken = false;
					if (tokensCount + 1 > tokens.length) {
						String[] newBuffer = new String[tokensCount + 1];
						for (int j = 0; j < tokens.length; j++) {
							newBuffer[j] = tokens[j];
						}
						tokens = newBuffer;
					}

					tokens[tokensCount++] = token;
					token = null;
				} else {
					token += cadena.charAt(i);
				}

			} else {
				if (cadena.charAt(i) == '\'') {
					dentroToken = true;
					token = new String();
				}
			}
		}

		return tokens;
	}

	public static String[] parseOperaciones(String sqlScript) {
		String[] operaciones = new String[0];
		String operacion = new String();
		int opCount = 0;

		for (int i = 0; i < sqlScript.length(); i++) {
			if (sqlScript.charAt(i) == ';') {
				if (opCount + 1 > operaciones.length) {
					String[] newBuffer = new String[opCount + 1];
					for (int j = 0; j < operaciones.length; j++) {
						newBuffer[j] = operaciones[j];
					}
					operaciones = newBuffer;
				}

				operaciones[opCount++] = operacion;
				operacion = new String();
			} else {
				operacion += sqlScript.charAt(i);
			}
		}

		return operaciones;
	}

	public static ResultSet ejecutarConsultaDeSeleccion(Connection conexion, String consulta) {
		PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(consulta);
			ResultSet resultado = sentencia.executeQuery();
			return resultado;
		} catch (SQLException e) {
			return null;
		}
	}

	public static boolean ejecutarConsultaDeAccion(Connection conexion, String consulta) {
		PreparedStatement sentencia;
		try {
			sentencia = conexion.prepareStatement(consulta);
			sentencia.executeUpdate();
			return true;
		} catch (SQLException e) {
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

	public static boolean validarDatosParaConsulta(String str) {
		if (str.contains(";"))
			return false;
		else
			return true;
	}

	public static String prepararConsultaInsert(String[] values, String tabla) {
		String consulta = "insert into " + tabla + " values (";
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals("")) {
				consulta += "null";
			} else {
				consulta += "'" + values[i] + "'";
			}
			if (i != values.length - 1)
				consulta += ",";
		}
		consulta += ");";
		return consulta;
	}
	
	public static boolean esCampoClave(String colName, String[] camposClaves)
	{
		for(int i = 0; i < camposClaves.length; i++)
		{
			if(colName.equals(camposClaves[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public static int numeroDeValue(String colName, String[] col)
	{
		for(int i = 0; i < col.length; i++)
		{
			if(colName.equals(col[i]))
			{
				return i;
			}
		}
		return -1;
	}
	
	public static String prepararConsultaUpdate(String[] values, String tabla, String[] camposClaves, String[] col)
	{
		String consulta = "update " + tabla + " set ";
		for(int i = 0; i < values.length; i++)
		{
			
			boolean esCampo = Metodos.esCampoClave(col[i], camposClaves);
			if(esCampo) continue;
			
			consulta += col[i] + " = ";
			if(values[i].equals(""))
			{
				consulta += "null";
			}
			consulta += "'" + values[i] + "'";
			if(i != values.length - 1)
				consulta += ", ";
		}
		
		consulta += "where ";
		for(int i = 0; i < camposClaves.length; i++)
		{
			consulta += camposClaves[i] + " = " + "'" + values[Metodos.numeroDeValue(camposClaves[i], col)] + "'";
			if(i != camposClaves.length - 1)
			{
				consulta += " and ";
			}
		}
		consulta += ";";
		System.out.println(consulta);
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
		String tabla1 = Metodos.elegirTabla(sc, "Escoge las primera tabla:\n");
		System.out.println();
		String tabla2 = Metodos.elegirTabla(sc, "Escoge la segunda tabla:\n");
		String join = joinTablas(sc, tabla1, tabla2);
		if (tabla2 == null || tabla1 == null || join == null) {
			System.out.println("No existe relacion entre esas tablas.");
			return null;
		}
		return "select * from " + tabla1 + " join " + tabla2 + " on " + join;
	}

	public static String joinTablas(Scanner sc, String tabla1, String tabla2) {
		if ((tabla1.equals("estadisticas") && tabla2.equals("jugadores"))
				|| (tabla2.equals("estadisticas") && tabla1.equals("jugadores"))) {
			return "estadisticas.jugador=jugadores.codigo";
		} else if ((tabla1.equals("jugadores") && tabla2.equals("equipos"))
				|| (tabla1.equals("equipos") && tabla2.equals("jugadores"))) {
			return "jugadores.nombre_equipo=equipos.nombre";
		} else if ((tabla1.equals("equipos") && tabla2.equals("partidos"))
				|| (tabla1.equals("partidos") && tabla2.equals("equipos"))) {
			System.out.print("¿Equipo local(1) o vistante(2)?: ");
			String aux = sc.nextLine();
			if (aux.equals("1")) {
				return "equipos.nombre=partidos.equipo_local";
			} else if (aux.equals("2")) {
				return "equipos.nombre=partidos.equipo_visitante";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static void mostrarDatos(Connection connection, String consulta) {
		if (consulta == null) {
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
			for (int j = 0; j < columnas * 25; j++) {
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
		boolean result = Metodos.ejecutarConsultaDeAccion(connection, consultaFinal);
		if(!result)
		{
			System.out.println("Tienes un error en tu consulta");
		}
	}

	public static void modificarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc, mensaje);
		String[] modificarValues = null;
		String[] camposClaves = null;
		String[] columnas = null;
		String consultaFinal = null;

		switch (tabla) {
		case "estadisticas":
			modificarValues = Estadisticas.cogerDatos(sc);
			camposClaves = Estadisticas.cogerCamposClaves();
			columnas = Estadisticas.cogerNombresDeColumnas();
			break;
		case "jugadores":
			modificarValues = Jugadores.cogerDatos(sc);
			camposClaves = Jugadores.cogerCamposClaves();
			columnas = Jugadores.cogerNombresDeColumnas();
			break;
		case "equipos":
			modificarValues = Equipos.cogerDatos(sc);
			camposClaves = Equipos.cogerCamposClaves();
			columnas = Equipos.cogerNombresDeColumnas();
			break;
		case "partidos":
			modificarValues = Partidos.cogerDatos(sc);
			camposClaves = Partidos.cogerCamposClaves();
			columnas = Partidos.cogerNombresDeColumnas();
			break;
		}
		consultaFinal = Metodos.prepararConsultaUpdate(modificarValues, tabla, camposClaves, columnas);
		boolean result = Metodos.ejecutarConsultaDeAccion(connection, consultaFinal);
		if(!result)
		{
			System.out.println("Tienes un error en tu consulta");
		}
	}

	public static void eliminarDatos(Connection connection, Scanner sc, String mensaje) {
		String tabla = Metodos.elegirTabla(sc, mensaje);
	}

	public static String crearLaBase(Connection connection, String mensaje) {
		System.out.println("Estamos creandolo");
		String[] operaciones = Metodos.parseOperaciones(BaseDeDatos.CREACION);

		for (String op : operaciones) {
			Metodos.ejecutarConsultaDeAccion(connection, op);
		}
		return Metodos.BASE_NOMBRE;
	}

	public static String conectarBase(Connection connection, String message) {
		System.out.println(message);

		String consulta = "use " + Metodos.BASE_NOMBRE + ";";
		boolean result = Metodos.ejecutarConsultaDeAccion(connection, consulta);
		if (!result) {
			System.out.println("No se puede conectar a una base innexistente");
		}
		return result == true ? Metodos.BASE_NOMBRE : "";
	}

	public static String eliminarBase(Connection connection, String message) {
		System.out.println(message);
		String consulta = "drop database " + Metodos.BASE_NOMBRE + ";";
		Metodos.ejecutarConsultaDeAccion(connection, consulta);
		return "";
	}

}
