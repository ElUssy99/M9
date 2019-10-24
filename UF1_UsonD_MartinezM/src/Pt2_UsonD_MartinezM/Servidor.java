package Pt2_UsonD_MartinezM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Servidor {

	public static void main(String[] args) {
		ArrayList<String> juego = new ArrayList<String>();
		juego.add("PIEDRA");
		juego.add("PAPEL");
		juego.add("TIJERA");
		
		int contador = 0;
		
		// Primero indicamos la dirección IP local
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la dirección IP	local : " + uhe);
		}
		// Abrimos un "Socket de Servidor" TCP en el puerto 1234.
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(1235);
		} catch (IOException ioe) {
			System.err.println("Error al abrir el socket de servidor : " + ioe);
			System.exit(-1);
		}
		String entrada;
		int entradaCC;
		int entradaCS;
		String result;
		String salida = null;
		// Bucle infinito
		while (true) {
			try {
				// Esperamos a que alguien se conecte a nuestro Socket
				Socket sckt = ss.accept();
				// Extraemos los Streams de entrada y de salida
				DataInputStream dis = new DataInputStream(sckt.getInputStream());
				DataOutputStream dos = new DataOutputStream(sckt.getOutputStream());
				// Podemos extraer información del socket
				// Nº de puerto remoto
				int puerto = sckt.getPort();
				// Dirección de Internet remota
				InetAddress direcc = sckt.getInetAddress();
				// Leemos datos de la peticion
				entrada = dis.readUTF();
				entradaCC = dis.readInt();
				entradaCS = dis.readInt();
				
				while (entradaCC < 3 || entradaCS < 3) {
					// Se crea un numero aleatorio entre el 1 y el 3:
					int numRandom = (int) (Math.random()*3) + 1;
					// Hacemos el resultado
					// El numero random es la posicion de cada opcion del juego (piedra, papel, tijera) del ArrayList:
					if (numRandom == 1) {
						salida = juego.get(0);
					} else if (numRandom == 2) {
						salida = juego.get(1);
					} else if (numRandom == 3) {
						salida = juego.get(2);
					}
					// Escribimos el resultado
					dos.writeUTF(salida.toString());
				}
				
				// Cerramos los streams
				dis.close();
				dos.close();
				sckt.close();
				// Mostramos el resultado por pantalla:
//				System.out.println("Cliente = " + direcc + ":" + puerto + "\tEntrada = " + entrada + "\tSalida = " + salida);
			} catch (Exception e) {
				System.err.println("Se ha producido la	excepción : " + e);
			}
		}
	}

}
