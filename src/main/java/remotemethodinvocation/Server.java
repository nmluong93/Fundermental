package remotemethodinvocation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server extends RMIProgramImpl {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			System.err.println("Start server code ");
			RMIProgramImpl impl = new RMIProgramImpl();

			// Binding the remote object (stub) in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Hello", impl);

			System.err.println("Server is ready!");

		} catch (Exception ex) {
			System.err.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Server exception: " + ex.toString()); 
			ex.printStackTrace();
		}

	}
}
