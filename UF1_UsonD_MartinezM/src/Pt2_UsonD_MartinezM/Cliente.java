package Pt2_UsonD_MartinezM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String args[]) throws EOFException {
		// Leemos el primer parámetro, donde debe ir la dirección
		// IP del servidor
		InetAddress direcc = null;
		try {
			direcc = InetAddress.getByName("192.168.41.218");

		} catch (UnknownHostException uhe) {
			System.err.println("Host no encontrado : " + uhe);
			System.exit(-1);
		}
		// Puerto que hemos usado para el servidors
		int puerto = 1235;

		// Para cada uno de los argumentos...

		Socket sckt = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {

			// Creamos el Socket
			sckt = new Socket(direcc, puerto);
			// Extraemos los streams de entrada y salida
			dis = new DataInputStream(sckt.getInputStream());
			dos = new DataOutputStream(sckt.getOutputStream());

			int contadorCliente = 0;
			int contadorServ = 0;
			String eleccion;
			// este while hace vueltas asta que el ganador sea el cliente o el servidor.
			while (contadorCliente < 3 || contadorServ < 3) {
				// System.out.println(contadorCliente+" "+contadorServ);
				// pedira una de las 3 opciones asta que ponga una correcta.
				do {
					System.out.println("intorduce PIEDRA,PAPEL O TIJERA:");
					eleccion = leerS();
				} while (!eleccion.equalsIgnoreCase("PIEDRA") && !eleccion.equalsIgnoreCase("PAPEL")
						&& !eleccion.equalsIgnoreCase("TIJERA"));

				dos.writeUTF(eleccion);
				dos.writeInt(contadorCliente);
				dos.writeInt(contadorServ);
				// Leemos el resultado final
				String resultado = dis.readUTF();

				// Indicamos en pantalla
				System.out.println("cliente = " + eleccion + "\tservidor = " + resultado);
				// este if es para comprobar qual es el ganador de la ronda y aumenta en 1 el
				// contador de ronda ganada.
				if (eleccion.equalsIgnoreCase("PIEDRA") && resultado.equalsIgnoreCase("TIJERA")) {
					System.out.println("el cliente gana la ronda");
					contadorCliente++;
				} else if (eleccion.equalsIgnoreCase("PAPEL") && resultado.equalsIgnoreCase("PIEDRA")) {
					System.out.println("el cliente gana la ronda");
					contadorCliente++;
				} else if (eleccion.equalsIgnoreCase("TIJERA") && resultado.equalsIgnoreCase("PAPEL")) {
					System.out.println("el cliente gana la ronda");
					contadorCliente++;
				} else if (eleccion.equalsIgnoreCase(resultado)) {
					System.out.println("un empate");
				} else {
					System.out.println("el servidor gana la ronda");
					contadorServ++;
				}

			}
			// mostramos el resultado final.
			System.out.println("resultado: cliente = " + contadorCliente + " servidor = " + contadorServ);
			// y cerramos los streams y el socket
			dis.close();
			dos.close();
		} catch (Exception e) {
			System.err.println("Se ha producido laexcepción : " + e);
		}
		try {
			if (sckt != null)
				sckt.close();
		} catch (IOException ioe) {
			System.err.println("Error al cerrar el socket :" + ioe);
		}

	}

	public static String leerS() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
}
