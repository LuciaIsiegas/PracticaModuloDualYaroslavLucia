package aplicacion_bbdd;

import java.util.Scanner;
import java.sql.*;

public class Metodos {
	
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
	
	
	public static void mostrarDatos(Connection connection, Scanner sc, String mensaje) {
		int tabla = getInt(sc, mensaje);
		
		switch (tabla) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		default:
			
		}
	}
	
	public static void altaDatos(Connection connection, Scanner sc, String mensaje) {
		
	}
	
	public static void modificarDatos(Connection connection, Scanner sc, String mensaje) {
		
	}
	
	public static void eliminarDatos(Connection connection, Scanner sc, String mensaje) {
		
	}
	

}
