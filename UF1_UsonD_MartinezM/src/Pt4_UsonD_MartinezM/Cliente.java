package Pt4_UsonD_MartinezM;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

	// Main del Ahorcado Cliente
	public static void main(String[] args) {
		new Cliente();
	}

	// Atributo de la clase
	private IAhorcado server;

	private Cliente() {
		Scanner entrada = new Scanner(System.in);
		try {
			// Creamos un registro con la IP y puerto del servidor:
			Registry registry = LocateRegistry.getRegistry("192.168.41.33", 5555);
			// Instanciamos el server con la Interfaz
			server = (IAhorcado) registry.lookup("Ahorcado");
			// Nos conectamos al servidor
			server.connectar();
			// Le decimos al usuario el tamaño de la palabra a adivinar:
			System.out.println("Contectando cliente...");
//			System.out.println("Quieres jugar con una palabra aleatoria (1) o con una palabra que quieras introducir (2)?");
//			int opcion = entrada.nextInt();
//			
//			server.decisiones(opcion);
			
			System.out.println("// JUGUEMOS A UN JUEGO //");
			System.out.println("Si aciertas la palabra, iras al Cielo con Jesucristo. Si no... ya te lo imaginaras.");
			System.out.println("-Empecemos-");
			System.out.println("La palabra contiene " + server.getLengthPalabra() + " letras.");
			// Llamamos al metodo preguntar letra para iniciar a jugar:
			this.preguntarLetra();
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void preguntarLetra() {
		try {
			// Definimos los intentos máximos para adivinar una palabra
			int intentos = server.getIntentos();
			Scanner lector = new Scanner(System.in);
			System.out.println("Ves introduciendo las letras de la palabra:");
			// Mientras al usuario le queden intentos y no ha acertado la palabra seguira jugando:
			while (intentos > 0 && !server.palabraAcertada()) {
				System.out.print("-> ");
				char letra = lector.nextLine().charAt(0);
				System.out.println(server.coincide(letra));
				// Si la letra que introduce no coincide con las de la palabra, resta 1 intento:
				if (server.coincideIntentos(letra) == false) {
					intentos = intentos - 1;
				}
			}

			// Si el usuario gana, se le mostrara por pantalla un mensaje de ganador:
			if (server.palabraAcertada()) {
				System.out.println("\nHas acertado la palabrita de nuestro señor Jesucristo.");
				System.out.println("Te ganaste el Cielo y el respeto de nuestro señor Jesucristo.");
				// Si el usuario pierde, se le mostrara por pantalla un mensaje de perdedor y la
				// palabra que no ha podido adivinar:
			} else if (!server.palabraAcertada() && intentos == 0) {
				System.out.println("\nHas perdido, tendras que ir al Infierto con Satanas. La palabra era: " + server.getPalabra());
			}
			lector.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
