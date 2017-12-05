package Controller;

import View.*;

public class Controller {
	
	private MainView main;
	
	public void startView() {
		main = new MainView();
		main.startView();
	}
}