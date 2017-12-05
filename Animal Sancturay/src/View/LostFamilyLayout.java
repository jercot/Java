package View;

import Controller.*;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LostFamilyLayout {
	private AnchorPane anchor;
	private Label personLabel, nameLabel, addressLabel, phoneLabel, emailLabel;
	private TextField nameInput, addressInput, phoneInput, emailInput;
	private Button cancelButton, enterButton;
	static boolean delete = false;

	public boolean display(DataController d) {
		Stage window = new Stage();
		LostController lost = new LostController(d);
		personLabel = labelMaker("Owner Details", 60, 80, 0);
		anchor = new AnchorPane();
		nameLabel = labelMaker("Name", 20, 40, 100);
		nameInput = fieldMaker(40, 130);
		addressLabel = labelMaker("Address" , 20, 275, 100);
		addressInput = fieldMaker(275, 130);
		addressInput.setPrefWidth(300);
		phoneLabel = labelMaker("Phone Number", 20, 40, 160);
		phoneInput = fieldMaker(40, 190);
		emailLabel = labelMaker("Email", 20, 275, 160);
		emailInput = fieldMaker(275, 190);
		emailInput.setPrefWidth(300);
		enterButton = buttonMaker("Enter Details", 390, 220);
		cancelButton = buttonMaker("Cancel", 490, 220);
		enterButton.setOnAction((event) -> {
			if(lost.setPerson(getName(), getAddress(), getPhone(), getEmail())) {
				lost.addOwner();
				delete = true;
				window.close();
			}
		});
		cancelButton.setOnAction((event) -> {
			window.close();
		});
		anchor.getChildren().addAll(personLabel, nameLabel, nameInput, addressLabel, addressInput, phoneLabel, phoneInput, emailLabel, emailInput, enterButton, cancelButton);

		Scene scene = new Scene(anchor, 600, 300);
		window.setScene(scene);
		window.showAndWait();

		return delete;
	}

	public TextField fieldMaker(int x, int y) {
		TextField t = new TextField();
		t.setLayoutX(x);
		t.setLayoutY(y);
		return t;
	}

	public Label labelMaker(String s, int f, int x, int y){
		Label l = new Label(s);
		l.setFont(new Font(f));
		l.setLayoutX(x);
		l.setLayoutY(y);        
		return l;
	}

	public Button buttonMaker(String s, int x, int y) {
		Button b = new Button(s);
		b.setLayoutX(x);
		b.setLayoutY(y);
		return b;
	}

	public String getName() {
		labelColor(nameLabel, textCheck(nameInput.getText()));
		return nameInput.getText();
	}

	public String getAddress() {
		labelColor(addressLabel, textCheck(addressInput.getText()));
		return addressInput.getText();
	}

	public String getPhone() {
		labelColor(phoneLabel, textCheck(phoneInput.getText()));
		return phoneInput.getText();
	}

	public String getEmail() {
		labelColor(emailLabel, textCheck(emailInput.getText()));
		return emailInput.getText();
	}

	public boolean textCheck(String s) {
		if(s.equals("")){
			return false;
		}
		return true;
	}

	public void labelColor(Label l, boolean b) {
		if(b)
			l.setTextFill(Color.BLACK);
		else
			l.setTextFill(Color.RED);
	}
}