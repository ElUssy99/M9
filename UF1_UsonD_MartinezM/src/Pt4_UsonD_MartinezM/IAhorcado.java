package Pt4_UsonD_MartinezM;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAhorcado extends Remote{
	
	// Interfaz con los metodos que usaremos en el Server que extiende de Remote 
	// Todos los metodos tienen que tener RemoteException para poder hacer la conexión
	
	public void connectar() throws RemoteException;
	
	public String seleccionarPalabra() throws RemoteException;
	
	public int getLengthPalabra() throws RemoteException;
	
	public String getPalabra() throws RemoteException;
	
	public int getIntentos() throws RemoteException;
	
	public String coincide(char c) throws RemoteException;
	
	public boolean coincideIntentos(char c) throws RemoteException;
	
	public boolean palabraAcertada() throws RemoteException;
	
	public boolean decisiones(int opcion) throws RemoteException;
	
	public String leerArchivo() throws RemoteException;

}
