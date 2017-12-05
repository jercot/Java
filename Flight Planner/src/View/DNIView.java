package View;

import java.time.LocalDate;
import java.util.ArrayList;

import Controller.DetailController;
import Controller.PriceCalculator;
import Controller.DNICheck;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

public class DNIView {

	private final String PREV = "Previous", NEXT = "Next";
	private DetailController bookingDetails;
	private BorderPane pane;
	private ThirdView thirdView;
	private GridPane grid;
	private Button previous, next;
	private int boxSpace = 40;
	private HBox bottomB;
	private VBox passengerD;
	private ArrayList<HBox> pass;
	private ArrayList<Text> names;
	private ArrayList<TextField> dni;
	private ArrayList<Boolean> dniCheck;
	
	
	public DNIView(DetailController b) {
		bookingDetails = b;
		grid = new GridPane();
		bottomB = new HBox(boxSpace);
		passengerD = new VBox(10);
		pass = new ArrayList<>();
		names = new ArrayList<>();
		dni = new ArrayList<>();
		thirdView = new ThirdView(bookingDetails);
		thirdView.startView();
	}
	
	public void startView() {
		grid.setMaxWidth(400);
		grid.setHgap(10);
		grid.setVgap(10);
		
		
		next = buttonMaker(NEXT);
		previous = buttonMaker(PREV);
		
		bottomB.getChildren().addAll(previous, next);
		//grid.add(next, 0, 1);
		
		grid.add(bottomB, 0, 15);
		
		for(int i=0; i<dniCheck.size(); i++)
			if(dniCheck.get(i)&&PriceCalculator.calculateAge(PriceCalculator.getDOB(bookingDetails.getBook().getPassenger(i).getDOB()), LocalDate.now())>=6) {
				pass.add(new HBox(boxSpace));
				names.add(textMaker(bookingDetails.getPass(i), 20));
				dni.add(new TextField());
				pass.get(pass.size()-1).getChildren().addAll(names.get(names.size()-1), dni.get(dni.size()-1));
				passengerD.getChildren().add(pass.get(pass.size()-1));
				TextField temp = dni.get(dni.size()-1);
				temp.setOnKeyTyped(e -> temp.setStyle("-fx-text-inner-color: black;"));
			}
		
		grid.add(passengerD, 0, 2);
		
		next.setOnAction(e-> calculate());
	}
	
	public void calculate() {
		boolean allRight = true;
		for(int i=0; i<pass.size(); i++)
			if (DNICheck.check(dni.get(i).getText())) {
				dni.get(i).setStyle("-fx-text-inner-color: black;");
				bookingDetails.increaseRebate();
			}
			else { 
				dni.get(i).setStyle("-fx-text-inner-color: red;");
				allRight = false;
			}
		if(allRight) {
			thirdView.setDetails(pane);
			pane.setCenter(thirdView.getGrid());
			thirdView.getReturn().setOnAction(e-> pane.setCenter(grid));
		}
		else bookingDetails.resetRebate();
	}
	
	public GridPane getGrid() {
		return grid;
	}
	
	public Button getReturn() {
		return previous;
	}
	
	public void setDetails(BorderPane p, ArrayList<Boolean> b) {
		pane = p;
		dniCheck = b;
	}
	
	public Button buttonMaker(String text) {
		Button b = new Button(text);
		b.setPrefWidth(100);
		return b;
	}

	public Text textMaker(String s, int f) {
		Text t = new Text(s + ": ");
		t.setWrappingWidth(200);
		t.setFont(new Font(f));
		return t;
	} 
}