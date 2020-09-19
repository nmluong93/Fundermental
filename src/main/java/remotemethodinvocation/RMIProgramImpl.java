package remotemethodinvocation;

import java.rmi.RemoteException;

public class RMIProgramImpl implements RMIProgramIntf {

	@Override
	public void printMsg() throws RemoteException {
		System.out.println("This is an example program. I am a service");
	}

}
