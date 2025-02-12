package aplicacion_bbdd;

import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		do {
			System.out.println("_______________________________________________\n"
					+ "Menú de opciones\n"
					+ "_______________________________________________\n"
					+ "1- Mostrar datos de tabla1\n"
					+ "2- Mostrar datos de tabla2\n"
					+ "3- Mostrar datos de tabla1 y tabla2\n"
					+ "4- Alta de datos en tabla1\n"
					+ "5- Alta de datos en tabla2"
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
		
		sc.close();

	}

}
