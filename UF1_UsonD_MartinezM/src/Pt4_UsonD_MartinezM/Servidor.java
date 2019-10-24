package Pt4_UsonD_MartinezM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

public class Servidor implements IAhorcado {

	ArrayList<String> palabras = new ArrayList<String>();

	// Main del Ahorcado Server, Implementa la interfaz IAhorcado
	public static void main(String[] args) {
		try {
			// Creamos el registro con el puerto y le definimos que se llama ahorcado y será
			// nuestro servidor
			Registry registry = LocateRegistry.createRegistry(5555);
			registry.rebind("Ahorcado", (IAhorcado) UnicastRemoteObject.exportObject(new Servidor(), 0));
			System.out.println("Juego listo...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	// Variables de la clase
	private String palabra;
	private int intentos;
	private String guiones;

	// Constructor de la clase
	public Servidor() {
		try {
			this.palabra = seleccionarPalabra();
			this.intentos = 8;
			this.guiones = "";
			for (int i = 0; i < palabra.length(); i++) {
				this.guiones = guiones + "_";
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// Getters de la clase
	public int getIntentos() throws RemoteException {
		return intentos;
	}

	public String getPalabra() throws RemoteException {
		return palabra;
	}

	// Metodo para saber que estamos conectados
	@Override
	public void connectar() throws RemoteException {
		System.out.println("Conectado");
	}

	// Metodo para seleccionar una palabra aleatoria:
	// Esta palabra será la que el usuario tendrá que adivinar:
	@Override
	public String seleccionarPalabra() throws RemoteException {
		leerArchivo();
		
//		String[] arrayPalabras = new String[] { "cargador", "ola", "collar", "reloj", "disco", "movil" };
		
		Random random = new Random();
		int aleatorio = random.nextInt(palabras.size());
		palabra = palabras.get(aleatorio).toString();
		
		// PARA SABER LA PALABRA ALEATORIA DESCOMENTA:
		System.out.println("La palabra es: " + palabra);

		return palabra;
	}

	// Metodo para obtener el tamaño de la palabra a adivinar:
	@Override
	public int getLengthPalabra() throws RemoteException {
		return palabra.length();
	}

	// Metodo para saber si la letra que ponga el usuario coincide con alguna de la
	// palabra a adivinar:
	@Override
	public String coincide(char c) throws RemoteException {
		for (int i = 0; i < palabra.length(); i++) {
			if (palabra.charAt(i) == c) {
				guiones = guiones.substring(0, i) + c + guiones.substring(i + 1, guiones.length());
			}
		}
		return guiones;
	}

	@Override
	public boolean coincideIntentos(char c) throws RemoteException {
		for (int i = 0; i < palabra.length(); i++) {
			if (palabra.charAt(i) == c) {
				return true;
			}
		}
		return false;
	}

	// Metodo para saber si el usuario adivina la palabra:
	@Override
	public boolean palabraAcertada() throws RemoteException {
		if (guiones.equals(palabra)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean decisiones(int opcion) throws RemoteException {
		boolean dec = false;
		if (opcion == 1) {
			dec = true;
		} else if (opcion == 2) {
			dec = false;
		}
		return dec;
	}

	@Override
	public String leerArchivo() throws RemoteException {
		String cadena;
		try {
			FileReader f = new FileReader("..\\UF1_UsonD_MartinezM\\src\\Pt4_UsonD_MartinezM\\palabras.txt");
			BufferedReader b = new BufferedReader(f);
			while ((cadena = b.readLine()) != null) {
				palabras.add(cadena);
			}
			b.close();
		} catch (Exception e) {
			System.out.println("Mensaje: " + e.getMessage());
		}

//		for (String string : palabras) {
//			System.out.println(string);
//		}

		return null;
	}

}
