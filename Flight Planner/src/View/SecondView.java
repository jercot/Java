package View;

import Controller.DetailController;
import Controller.PriceCalculator;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.CheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javafx.scene.control.Button;

public class SecondView {

	private DetailController bookingDetails;
	private final String PREV = "Return", NEXT = "Next", REMOVE_P = "Remove", ADD_P = "Add Passenger",
			DETAILS = "Full Name\t\t   Date of Birth \t\t  Nationality  \t Checked Luggage", WARN = "Fill all Required Details",
			CALCULATE = "Calculate Price", ADULT = "Adults", MINOR = "Minor", KID = "Children", BABY = "Babies";
	private final String[] nation = {"Spanish", "Other"};
	private GridPane grid;
	private Text details, warning, price;
	private ArrayList<TextField> name;
	private ArrayList<DatePicker> dOB;
	private ArrayList<ComboBox<String>> origin;
	private ArrayList<CheckBox> luggageO, luggageB;
	private ArrayList<Button> remove;
	private boolean twoWay, nextPage, spainFrom, spainTo;
	private ArrayList<Boolean> checkDNI;
	private Button previous, next, addP, calculatePrice;
	private HBox bottomB;
	private VBox passengerList;
	private ArrayList<HBox> passengers;
	private int total = 8, current = 0, boxSpace = 40, addButton = 3, adults, babies;
	private ObservableList<String> countries;
	private Stage window;
	private BorderPane pane;
	private ThirdView thirdView;
	private DNIView extraView;

	public SecondView(DetailController b) {
		thirdView = new ThirdView(b);
		thirdView.startView();
		grid = new GridPane();
		bottomB = new HBox(boxSpace);
		passengerList = new VBox(10);
		name = new ArrayList<>();
		passengers = new ArrayList<>();;
		dOB = new ArrayList<>();
		luggageO = new ArrayList<>();
		luggageB = new ArrayList<>();
		origin = new ArrayList<>();
		remove = new ArrayList<>();
		bookingDetails = b;
		nextPage = true;
	}

	public void startView() {
		grid.setMaxWidth(800);
		grid.setHgap(10);
		grid.setVgap(10);
		//grid.setGridLinesVisible(true);

		//Get List of all Countries
		countries = FXCollections.observableArrayList(Arrays.asList(nation));

		//Text line at the top
		details = textMaker(DETAILS, 20);
		grid.add(details, 0, 0);


		//Passenger Details
		addP = buttonMaker(ADD_P);

		grid.add(addP, 0, addButton);

		warning = textMaker(WARN, 20);
		grid.add(warning, 0, 6);
		//Bottom Buttons
		price = textMaker(null, 20);
		price.setWrappingWidth(200);
		calculatePrice = buttonMaker(CALCULATE);
		previous = buttonMaker(PREV);
		next = buttonMaker(NEXT);

		bottomB.getChildren().addAll(price, calculatePrice, previous, next);
		grid.add(bottomB, 0, 8);

		//Actions
		addP.setOnAction(e->addPassenger());
		next.setOnAction(e-> nextPage());
		calculatePrice.setOnAction(e-> calcPrice());

		//addPassenger();

		grid.add(passengerList, 0, 2);
	}

	public void removePassenger(HBox p) {
		if(current>1) {
			int temp = -1;
			for(int i=0; i<passengers.size(); i++)
				if(p.equals(passengers.get(i)))
					temp = i;
			if(temp!=-1) {
				addP.setDisable(false);
				passengerList.getChildren().remove(temp);
				passengers.remove(temp);
				name.remove(temp);
				dOB.remove(temp);
				origin.remove(temp);
				luggageO.remove(temp);
				if(twoWay)
					luggageB.remove(temp);
				remove.remove(temp);
				current--;
			}
		}
		if(current==1)
			remove.get(0).setDisable(true);
	}

	public void addPassenger() {
		if(current<total) {
			passengers.add(new HBox(boxSpace)); 
			name.add(new TextField());
			dOB.add(new DatePicker());
			disable(dOB.get(current));
			origin.add(new ComboBox<>(countries));
			luggageO.add(new CheckBox());
			luggageO.get(current).setText("Outbound");
			passengers.get(current).getChildren().clear();
			passengers.get(current).getChildren().addAll(name.get(current), dOB.get(current), origin.get(current), luggageO.get(current));
			if(twoWay) {
				luggageB.add(new CheckBox());
				luggageB.get(current).setText("Inbound");
				passengers.get(current).getChildren().add(luggageB.get(current));
			}
			remove.add(new Button(REMOVE_P));
			HBox temp = passengers.get(current);
			CheckBox temp2 = luggageO.get(current);
			remove.get(current).setOnAction(e->removePassenger(temp));
			passengers.get(current).getChildren().add(remove.get(current));
			dOB.get(current).setOnAction(e-> checkLuggage(temp2));
			passengerList.getChildren().add(passengers.get(current++));
			if(current==total)
				addP.setDisable(true);
		}
		if(current==1)
			remove.get(0).setDisable(true);
		else
			remove.get(0).setDisable(false);
	}

	public void checkLuggage(CheckBox c1) {
		int i = luggageO.indexOf(c1);
		if(PriceCalculator.calculateAge(PriceCalculator.getDOB(getDate(i)), LocalDate.now())<2) {
			c1.setDisable(true);
			c1.setSelected(false);
			if(twoWay) {
				luggageB.get(i).setDisable(true);
				luggageB.get(i).setSelected(false);
			}
		}
		else {
			c1.setDisable(false);
			if(twoWay)
				luggageB.get(i).setDisable(false);
		}
	}

	public void nextPage() {
		window.setMinWidth(600);
		startPassengerList();
		if(nextPage&&current>0)
			if(bookingDetails.checkRebate()) {
				extraView = new DNIView(bookingDetails);
				extraView.setDetails(pane, checkDNI);
				extraView.startView();
				pane.setCenter(extraView.getGrid());
				extraView.getReturn().setOnAction(e-> {
					pane.setCenter(grid);
					window.setMinWidth(850);
				});
			}
			else {
				pane.setCenter(thirdView.getGrid());
				thirdView.setDetails(pane);
				thirdView.getReturn().setOnAction(e-> {
					pane.setCenter(grid);
					window.setMinWidth(850);
				});
			}
		textColor(nextPage, warning);
	}

	public void startPassengerList() {
		adults = 0;
		babies= 0;
		nextPage = true;
		checkDNI = new ArrayList<>();
		bookingDetails.setBooking(current);
		for(int i=0; i<current; i++) {
			if(twoWay) {
				if(!bookingDetails.addPerson(getName(i), getDate(i), getOrigin(i), luggageO.get(i).isSelected(), luggageB.get(i).isSelected()))
					nextPage = false;
			}
			else {
				if(!bookingDetails.addPerson(getName(i), getDate(i), getOrigin(i), luggageO.get(i).isSelected(), false))
					nextPage = false;
			}
			if(getOrigin(i)!=null&&getOrigin(i).equals("Spanish")) {
				checkDNI.add(i, true);
				bookingDetails.setSpanish();
			}
			else
				checkDNI.add(i, false); 
			int age = PriceCalculator.calculateAge(PriceCalculator.getDOB(getDate(i)), LocalDate.now());
			if(age>17)
				adults++;
			else if(age<6 && age>=0)
				babies++;

		}
		bookingDetails.setOut(spainFrom);
		bookingDetails.setIn(spainTo);
		if(current!=0)
			if(adults==0) {
				nextPage = false;
				setWarning("\nNo Adults");
			}
			else if(babies!=0 && babies/adults>2) {
				nextPage = false;
				System.out.println(babies/adults);
				setWarning("\nThere must be one adult to every child under 6");
			}
		if(nextPage)
			PriceCalculator.calculatePrice(bookingDetails);
	}

	public void calcPrice() {
		startPassengerList();
		if(nextPage) {
			price.setText("Price: €" + bookingDetails.getTotal());
			int[] temp = PriceCalculator.getPass();
			setWarning("\n" + ADULT + ": " + temp[0] + "\t" + MINOR + ": " + temp[1] + "\t" + KID + ": " + temp[2] + "\t" + BABY + ": " + temp[3] + "\t");
		}
	}

	public void setWarning(String t) {
		warning.setText(WARN + t);
	}

	public GridPane getGrid() {
		return grid;
	}

	public Button getReturn() {
		return previous;
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

	public void setDetails(Stage w, BorderPane p, boolean t, String o, String i) {
		pane = p;
		window = w;
		twoWay = t;
		if(o!=null && i!=null) {
			if(o.contains("Madrid")||o.contains("Malaga"))
				spainFrom = true;
			else
				spainFrom = false;
			if(i.contains("Madrid")||i.contains("Malaga"))
				spainTo = true;
			else
				spainTo = false;
		}
		calcPrice();
	}

	public void textColor(Boolean check, Text change) {
		if(check)
			change.setFill(Color.BLACK);
		else
			change.setFill(Color.RED);
	}

	public String getName(int i) {
		if(!name.get(i).getText().equals(""))
			return name.get(i).getText();
		return null;
	}

	public Date getDate(int i) {
		if(dOB.get(i).getValue()!=null) {
			Instant temp = Instant.from(dOB.get(i).getValue().atStartOfDay(ZoneId.systemDefault()));
			return Date.from(temp);
		}
		return null;
	}

	public String getOrigin(int i) {
		if(origin.get(i).getValue()!=null)
			return origin.get(i).getValue();
		return null;
	}

	public boolean getReturnCheck() {
		return twoWay;
	}

	public void disable(DatePicker pick) {
		final Callback<DatePicker, DateCell> dayCellFactory = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isAfter(LocalDate.now())) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		pick.setDayCellFactory(dayCellFactory);
	}
}