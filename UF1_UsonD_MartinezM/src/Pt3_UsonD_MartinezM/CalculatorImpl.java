package Pt3_UsonD_MartinezM;

import java.rmi.RemoteException;

public class CalculatorImpl extends java.rmi.server.UnicastRemoteObject implements Calculator {
	public CalculatorImpl() throws java.rmi.RemoteException {
		super();
	}

	public long add(long a, long b) throws java.rmi.RemoteException {
		System.out.println("Sumando");
		return a + b;
	}

	public long sub(long a, long b) throws java.rmi.RemoteException {
		System.out.println("Restando");
		return a - b;
	}

	public long mul(long a, long b) throws java.rmi.RemoteException {
		System.out.println("Multiplicando");
		return a * b;
	}

	public long div(long a, long b) throws java.rmi.RemoteException {
		System.out.println("Dividiendo");
		return a / b;
	}

	@Override
	public long pot(long a, long b) throws RemoteException {
		System.out.println("Potenciando");
		return (int) Math.pow(a, b);
	}
}
