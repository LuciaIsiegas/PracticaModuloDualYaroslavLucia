package aplicacion_bbdd;

import java.sql.*;
import java.util.Scanner;

public class Principal {
	
	static final String BBDD = "jdbc:mysql://localhost:3306/nba";
	static final String USER = "root";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conexion = null;
		
		try {
			conexion = DriverManager.getConnection(BBDD, USER, "");
			System.out.println("¡Bienvenido a la base de datos de la NBA!");
			
			int opcion = 0;
			do {
				System.out.println("_______________________________________________\n"
						+ "Menú de opciones\n"
						+ "_______________________________________________\n"
						+ "1- Mostrar datos de tabla1\n"
						+ "2- Mostrar datos de tabla2\n"
						+ "3- Mostrar datos de tabla1 y tabla2\n"
						+ "4- Alta de datos en tabla1\n"
						+ "5- Alta de datos en tabla2\n"
						+ "6- Modificar datos de tabla1\n"
						+ "7- Modificar datos de tabla2\n"
						+ "8- Eliminar datos de tabla1\n"
						+ "9- Eliminar datos de tabla1\n"
						+ "10-Salir\n"
						+ "_______________________________________________");
				
				System.out.print("Tu opción: ");
				String aux = sc.nextLine();
				if (aux.matches("\\d+")) {
					opcion = Integer.parseInt(aux);
				} 
				
				
				switch (opcion) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					System.out.println("Fin del programa.");
					break;
				default:
					System.out.println("Opción no disponible.\n");
				}
			} while(opcion != 10);
			
			
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
