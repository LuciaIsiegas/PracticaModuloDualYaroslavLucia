package aplicacion_bbdd;

import java.sql.*;
import java.util.Scanner;

public class Principal {
	
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
			+ "Tabla seleccionada: ";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conexion = null;
		
		try {
			conexion = DriverManager.getConnection(BBDD, USER, "");
			System.out.println("¡Bienvenido a la base de datos de la NBA!");
			
			int opcion = 0;
			do {
				opcion = Metodos.getInt(sc, MENU + "\nTu opcion: ");
				
				
				switch (opcion) {
				case 1:
					String opcion1 = "\nMostrar datos de:\n";
					int tabla = Metodos.getInt(sc, opcion1 + TABLAS);
					
					break;
				case 2:
					String opcion2 = "\nDar de alta datos en";
					
					break;
				case 3:
					String opcion3 = "\nModificar datos de";
					
					break;
				case 4:
					String opcion4 = "\nModificar datos de";
					
					break;
				case 5:
					System.out.println("Fin del programa.");
					break;
				default:
					System.out.println("Opción no disponible.\n");
				}
			} while(opcion != 5);
			
			
			conexion.close();
			System.out.println("La conexión a la base de datos de la NBA ha finalizado.");
			System.out.println("¡ADIOS!");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("La conexión a la base de datos de la NBA no a podido establecerse.");
		}
		
		sc.close();

	}

}
