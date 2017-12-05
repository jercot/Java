 import java.rmi.RemoteException;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{

	private Controller control;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws RemoteException {
        control = new Controller();
        control.startView();
	}
}