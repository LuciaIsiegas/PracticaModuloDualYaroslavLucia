package aplicacion_bbdd;

import java.sql.*;
import java.util.Scanner;

public class Principal {


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conexion = null;
		
		try {
			conexion = DriverManager.getConnection(Metodos.BBDD, Metodos.USER, "");
			System.out.println("¡Bienvenido a la base de datos de la NBA!");
			
			int opcion = 0;
			do {
				opcion = Metodos.getInt(sc, Metodos. MENU + "\nTu opcion: ");
				
				
				switch (opcion) {
				case 1:
					String opcion1 = "\nMostrar datos de:\n";
					Metodos.mostrarDatos(conexion, Metodos.mostrar1Tabla(sc, opcion1), sc);
					break;
				case 2:
					String opcion12 = "\nMostrar datos de:\n";
					Metodos.mostrarDatos(conexion, Metodos.mostrar2Tablas(sc), sc);
					break;
				case 3:
					String opcion2 = "\nDar de alta datos en:\n";
					Metodos.altaDatos(conexion, sc, opcion2);
					break;
				case 4:
					String opcion3 = "\nModificar datos de:\n";
					Metodos.modificarDatos(conexion ,sc, opcion3);
					break;
				case 5:
					String opcion4 = "\nEliminar datos de:\n";
					Metodos.eliminarDatos(conexion, sc, opcion4);
					break;
				case 6:
					System.out.println("Fin del programa.");
					break;
				default:
					System.out.println("Opción no disponible.\n");
				}
			} while(opcion != 6);
			
			
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
