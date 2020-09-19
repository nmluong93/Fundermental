package remotemethodinvocation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		RMIProgramIntf stub =  (RMIProgramIntf) registry.lookup("Hello");
		
		System.err.println("Start remote call");
		// call server
		stub.printMsg();
		
		System.out.println("Ending of remote call");
		
	}
}
