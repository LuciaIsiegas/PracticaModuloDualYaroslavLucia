package aplicacion_bbdd;

import java.sql.*;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conexion = null;
		String baseActiva = "";

		try {
			conexion = DriverManager.getConnection(Metodos.BBDD, Metodos.USER, "");
			System.out.println("¡Bienvenido a la base de datos de la NBA!");

			int opcion = 0;
			do {
				opcion = Metodos.getInt(sc, "Base Activa: " + (baseActiva.equals("") ? "'null'" : baseActiva) + "\n"
						+ Metodos.MENU + "\nTu opcion: ");

				if (baseActiva.equals("") && opcion >= 4 && opcion <= 8) {
					System.out.println("No hay base de datos activa. Tienes que crearla o conectarla");
					continue;
				} else if (baseActiva.equals("") && opcion == 3) {
					System.out.println("No hay base de datos activa para eliminar");
					continue;
				}
				if (!baseActiva.equals("") && (opcion == 1 || opcion == 2)) {
					System.out.println("Base de datos ya esta activa");
					continue;
				}

				switch (opcion) {
				case 1:
					String opcion1 = "\nCrear la base\n";
					baseActiva = Metodos.crearLaBase(conexion, opcion1);
					break;
				case 2:
					String opcion2 = "\nConectar a la base\n";
					baseActiva = Metodos.conectarBase(conexion, opcion2);
					break;
				case 3:
					String opcion3 = "\nEliminar la base\n";
					baseActiva = Metodos.eliminarBase(conexion, opcion3);
					break;
				case 4:
					String opcion4 = "\nMostrar datos de:\n";
					Metodos.mostrarDatos(conexion, Metodos.mostrar1Tabla(sc, opcion4), sc);
					break;
				case 5:
					String opcion5 = "\nMostrar datos de:\n";
					Metodos.mostrarDatos(conexion, Metodos.mostrar2Tablas(sc, opcion5), sc);
					break;
				case 6:
					String opcion6 = "\nDar de alta datos en:\n";
					Metodos.altaDatos(conexion, sc, opcion6);
					break;
				case 7:
					String opcion7 = "\nModificar datos de:\n";
					Metodos.modificarDatos(conexion, sc, opcion7);
					break;
				case 8:
					String opcion8 = "\nEliminar datos de:\n";
					Metodos.eliminarDatos(conexion, sc, opcion8);
					break;
				case 9:
					System.out.println("Fin del programa.");
					break;
				default:
					System.out.println("Opción no disponible.\n");
				}
			} while (opcion != 9);

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
