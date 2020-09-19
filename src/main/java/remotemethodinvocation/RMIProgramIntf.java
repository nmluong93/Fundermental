package remotemethodinvocation;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIProgramIntf extends Remote, Serializable{

	void printMsg() throws RemoteException;
}
