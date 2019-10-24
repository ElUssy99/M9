package Pt3_UsonD_MartinezM;

import java.rmi.Naming;
import java.rmi.registry.Registry;

import Pt3_UsonD_MartinezM_ej4_funciona.CalciRmi;

public class CalculatorServer {

	public static void main(String args[]) {
		try {
	    	Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
			r.rebind("Calculator", new CalculatorImpl());
			System.out.println("Server connected");
	     } catch (Exception e) {
	       System.out.println("Trouble: " + e);
	     }
	}
}