package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import model.MediaInt;

public interface MediaServerInt extends Remote {
	public boolean setPath(String p) throws RemoteException;
	public ArrayList<MediaInt> getList() throws RemoteException;
	public void setFiles() throws RemoteException;
	public void setTime() throws RemoteException;
	public void setDownload(String n) throws RemoteException;
	public boolean done() throws RemoteException;
	public byte[] getFile() throws RemoteException;
	public String getExt() throws RemoteException;
}