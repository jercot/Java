package controller;

import java.rmi.RemoteException;

import view.*;

public class Controller {
	
	private MainView main;
	
	public void startView() throws RemoteException {
		main = new MainView();
		main.startView();
	}
}