package View;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import Controller.CreditCardCheck;
import Controller.DetailController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class ThirdView {

	private final String ADDRESS = "Address", LINE_ONE = "House Name(Optional)", LINE_TWO = "Street Address", TOWN = "Town", ZIP = "ZIP/Post Code",
			COUNTRY = "Country", COUNTY = "County", HOLDER = "Card Holder:  ", TYPE = "Card Type:  ", NUM = "Card Number:", SECURITY = "Security Code",
			EXPIRE = "Expiry Date:  ", MONTH = "MM", YEAR = "YYYY", PREV = "Previous", NEXT = "Next";
	private final String[] cardTypes = {"Visa", "Mastercard"}, monthList = {"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR), boxSpace = 40;
	private final int[] yearList = {currentYear, currentYear+1, currentYear+2, currentYear+3, currentYear+4};
	private int rowSpace = 10;
	private Text address, holder, type, num, security, expire, price;
	private TextField lineOne, lineTwo, town, zip, country, county, name, number, secCode;
	private ComboBox<String> card, month, year;
	private HBox[] rows;
	private HBox bottomB;
	private BorderPane pane;
	private GridPane grid;
	private ObservableList<String> types, months;
	ObservableList<int[]> years;
	private DetailController bookingDetails;
	private Button previous, next;

	public ThirdView(DetailController b) {
		bookingDetails = b;
		grid = new GridPane();
		rows = new HBox[5];
		bottomB = new HBox(boxSpace);
	}

	public void startView() {
		grid.setMaxWidth(500);
		grid.setHgap(10);
		grid.setVgap(10);

		types = FXCollections.observableArrayList(Arrays.asList(cardTypes));
		years = FXCollections.observableArrayList(Arrays.asList(yearList));
		months = FXCollections.observableArrayList(Arrays.asList(monthList));

		address = textMaker(ADDRESS, 20);
		lineOne = fieldMaker(LINE_ONE, 500);
		lineTwo = fieldMaker(LINE_TWO, 500);

		rows[0] = new HBox(rowSpace);
		town = fieldMaker(TOWN ,290);
		zip = fieldMaker(ZIP, 200);
		rows[0].getChildren().addAll(town, zip);

		rows[1] = new HBox(rowSpace);
		country = fieldMaker(COUNTRY, 290);
		county = fieldMaker(COUNTY, 200);
		rows[1].getChildren().addAll(country, county);

		rows[2] = new HBox(rowSpace);
		holder = textMaker(HOLDER, 20);
		name = fieldMaker(null, 149);
		type = textMaker(TYPE, 20);
		card = comboMaker(null, 100);
		card.setItems(types);
		rows[2].getChildren().addAll(holder, name, type, card);

		rows[3]= new HBox(rowSpace);
		num = textMaker(NUM, 20);
		number = fieldMaker(null, 149);
		security = textMaker(SECURITY, 20);
		secCode = fieldMaker(null, 80);
		rows[3].getChildren().addAll(num, number, security, secCode);

		rows[4] = new HBox(rowSpace);
		expire = textMaker(EXPIRE, 20);
		month = comboMaker(MONTH, 70);
		month.setItems(months);
		month.setOnAction(e-> removeYear());
		year = comboMaker(YEAR, 80);
		year.setDisable(true);
		//year.setItems(years);
		rows[4].getChildren().addAll(expire, month, year);


		grid.add(address, 0, 0);
		grid.add(lineOne, 0, 1);
		grid.add(lineTwo, 0, 2);
		grid.add(rows[0], 0, 3);
		grid.add(rows[1], 0, 4);
		grid.add(rows[2], 0, 5);
		grid.add(rows[3], 0, 6);
		grid.add(rows[4], 0, 7);


		previous = buttonMaker(PREV);
		next = buttonMaker(NEXT);
		next.setOnAction(e-> checkCard());
	}
	
	public void checkCard() {
		boolean correct = true;
		if(CreditCardCheck.check(number.getText()))
			number.setStyle("-fx-text-inner-color: black;");
		else {
			number.setStyle("-fx-text-inner-color: red;");
			correct = false;
		}
		if(correct)
			finish();
	}

	public void removeYear() {
		year.getItems().clear();
		for(int i=0; i<yearList.length; i++)
			year.getItems().add(yearList[i] + "");
		if(month.getValue()!=null) {
			Date date = null, currentDate = new Date();
			try {
				date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month.getValue());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance(), cal2 = Calendar.getInstance();
			cal.setTime(date);
			cal2.setTime(currentDate);
			int month = cal.get(Calendar.MONTH), currentMonth = cal2.get(Calendar.MONTH);
			if(month<currentMonth)
				year.getItems().remove(0);
			year.setDisable(false);
		}
	}

	public GridPane getGrid() {
		return grid;
	}

	public Button getReturn() {
		return previous;
	}

	public void setDetails(BorderPane p) {
		pane = p;
		price = textMaker("Price: €" + bookingDetails.getTotal(), 30);
		bottomB.getChildren().clear();
		bottomB.getChildren().addAll(price, previous, next);
		grid.getChildren().removeAll(bottomB);
		grid.add(bottomB, 0, 15);
	}

	public Button buttonMaker(String text) {
		Button b = new Button(text);
		b.setPrefWidth(100);
		return b;
	}

	public Text textMaker(String s, int f) {
		Text t = new Text(s);
		t.setFont(new Font(f));
		return t;
	}

	public TextField fieldMaker(String s, int w) {
		TextField t = new TextField();
		t.setPromptText(s);
		t.setPrefWidth(w);
		return t;
	}

	public ComboBox<String> comboMaker(String s, int w) {
		ComboBox<String> t = new ComboBox<>();
		t.setPromptText(s);
		t.setPrefWidth(w);
		return t;
	}
	
	public void finish() {
		GridPane closing = new GridPane();
		closing.setMaxWidth(500);
		closing.setHgap(10);
		closing.setVgap(10);
		String output = bookingDetails.getFlightDetails();
		String[] temp = new String[bookingDetails.getSize()];
		output += "\n\nPassengers: \n";
		for(int i=0; i<bookingDetails.getSize(); i++) { 
			temp[i] = "";
			temp[i] += bookingDetails.getDetails(i);
			output += "\n" + temp[i];
		}
		output += "\n\nCard Number: " + number.getText();
		Text last = textMaker(output, 20);
		closing.add(last, 0, 5);
		closing.add(price, 0, 7);
		pane.setCenter(closing);
		System.out.println(output);
	}
}