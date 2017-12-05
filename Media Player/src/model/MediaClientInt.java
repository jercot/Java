package model;

import java.rmi.RemoteException;

public interface MediaClientInt {
	public boolean tell(String p) throws RemoteException;
}
