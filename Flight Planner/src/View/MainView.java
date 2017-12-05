package View;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import Controller.DetailController;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainView {

	private DetailController bookingDetails;
	private final String HEADING = "JavaAir Flight Booking", ONE_WAY = "One Way", TWO_WAY = "Return", FROM = "From:", TO = "To:",
			OUTWARD = "Fly Out:", RETURN = "Fly Back:", BOOK = "Next", TIME_OUT = "Flight Out", TIME_IN = "Flight Back",
			PRICES = "Price per Person\nFlying Out:", RETURN_PRICES = "Return Flights:", OVER_SIX = "Over 6: €", TWO_TO_SIX = "2 - 6: €60",
			UNDER_TWO = "Under 2: Free";
	private final String[] locations = {"Cork(ORK)", "Madrid(MAD)", "St Brieuc(SBK)", "Jersey(JER)",
			"Paris(CDG)", "Stansted(STN)", "Malaga(AGP)"}, abr = {"orkmad", "orksbk", "orkjer", "orkcdg", "orkstn", "orkagp", "madsbk",
					"madjer", "madcdg", "madstn", "madagp", "sbkcdg", "sbkstn", "sbkagp", "jercdg", "jerstn", "jeragp", "cdgstn", "cdgagp", "stnagp", 
					"madork", "sbkork", "sbkmad", "jerork", "jermad", "cdgork", "cdgmad", "cdgsbk", "cdgjer", "stnork", "stnmad", "stnsbk", "stnjer",
					"stncdg", "agpork", "agpmad", "agpsbk", "agpjer", "agpcdg", "agpstn"};
	private final String[][] flightTimes ={{"09:20 - 13:00"}, {"10:30 - 14:00"}, {"14:00 - 16:00"}, {"09:00 - 12:15", "1820 - 21:05"},
			{"08:20 - 09:50", "11:20 - 1305"}, {"08:00 - 11:30"}, {"12:00 - 13:30"}, {"06:20 - 08:00"}, {"08:00 - 10:00"}, {"14:00 - 15:20", "19:05 - 21:20"},
			{"08:00 - 09:05"}, {"06:20 - 07:15"}, {"08:05 - 08:30"}, {"12:00 - 15:30"}, {"08:00 - 10:15"}, {"17:00 - 18:30"}, {"08:00 - 11:30"},
			{"18:00 - 18:30"}, {"11:50 - 13:30"}, {"08:00 - 11:00", "13:30 - 16:20"}, {"18:00 - 20:00"}, {"19:00 - 20:20"}, {"18:00 - 20:20"}, 
			{"10:00 - 12:00"}, {"18:00 - 21:20"}, {"13:30 - 15:00", "22:00 - 23:50"}, {"19:20 - 21:05"}, {"19:00 - 20:05"}, {"20:00 - 20:15"}, 
			{"11:00 - 12:20", "18:00 - 19:20"}, {"10:20 - 14:00"}, {"18:00 - 20:00"}, {"09:00 - 10:30"}, {"09:00 - 10:30"}, {"13:00 - 14:20"}, 
			{"20:00 - 21:05"}, {"20:00 - 21:30"}, {"18:00 - 19:30"}, {"18:05 - 12:30"}, {"15:00 - 16:10", "20:25 - 21:05"}};
	private String outLoc, toLoc;
	private SecondView secondView;
	private BorderPane pane, topPane;
	private GridPane grid;
	private HBox returnBox;
	private Text heading, from, to, flyOut, flyIn, timeOut, timeIn, price, returnPrice;
	private RadioButton oneWay, twoWay;
	private ComboBox<String> fromLocation, toLocation, outgoingTime, incomingTime;
	private DatePicker inDate, outDate;
	private int boxSpace = 40, comboSize = 190, currentPrice;
	private int[] priceList = {100, 100, 120, 80, 40, 240, 200, 200, 60, 60, 60, 150, 80, 140, 250, 250, 280, 60, 100, 120};
	private Button book;
	private final int MARCH = 3, APRIL = 4;
	private boolean returnCheck = true;

	public MainView() {
		bookingDetails = new DetailController();
		secondView = new SecondView(bookingDetails);
		pane = new BorderPane();
		topPane = new BorderPane();
		grid = new GridPane();
		returnBox = new HBox(boxSpace);
		secondView.startView();
	}

	public void startView() {
		Stage window = new Stage();
		Scene scene = new Scene(pane,0, 0);

		//Heading
		heading = textMaker(HEADING, 50);
		pane.setTop(topPane);
		topPane.setCenter(heading);
		BorderPane.setAlignment(heading, Pos.CENTER);

		//Grid Layout
		pane.setCenter(grid);
		grid.setMaxWidth(400);
		grid.setHgap(10);
		grid.setVgap(10);
		//grid.setGridLinesVisible(true);

		//RadioButtons
		ToggleGroup group = new ToggleGroup();
		oneWay = new RadioButton(ONE_WAY);
		oneWay.setToggleGroup(group);
		twoWay = new RadioButton(TWO_WAY);
		twoWay.setToggleGroup(group);
		twoWay.setSelected(true);

		returnBox.getChildren().addAll(oneWay, twoWay);
		grid.add(returnBox, 0, 2);


		//ComboBoxes Location Pickers
		from = textMaker(FROM, 20);
		to = textMaker(TO, 20);
		fromLocation = comboMaker();
		fromLocation.setItems(FXCollections.observableArrayList(Arrays.asList(locations)));
		fromLocation.setValue(locations[0]);
		toLocation = comboMaker();
		toLocation.setItems(FXCollections.observableArrayList(Arrays.asList(locations)));
		toLocation.getItems().remove(0);

		grid.add(from, 0, 3);
		grid.add(to, 6, 3);
		grid.add(fromLocation, 0, 4);
		grid.add(toLocation, 6, 4);

		//DatePickers
		flyOut = textMaker(OUTWARD, 20);
		flyIn = textMaker(RETURN, 20);
		outDate = new DatePicker();
		inDate = new DatePicker();

		outDate.setDisable(true);
		inDate.setDisable(true);

		grid.add(flyOut, 0, 5);
		grid.add(flyIn, 6, 5);
		grid.add(outDate, 0, 6);
		grid.add(inDate, 6, 6);


		//Flight Times
		timeOut = textMaker(TIME_OUT, 20);
		timeIn = textMaker(TIME_IN, 20);
		outgoingTime = comboMaker();
		incomingTime = comboMaker();

		grid.add(timeOut, 0, 7);
		grid.add(timeIn, 6, 7);
		grid.add(outgoingTime, 0, 8);
		grid.add(incomingTime, 6, 8);
		outgoingTime.setDisable(true);
		incomingTime.setDisable(true);

		//Warnings And Price
		price = textMaker(PRICES + "\n" + OVER_SIX + "\n" + TWO_TO_SIX + "\n" + UNDER_TWO, 20);
		returnPrice = textMaker(RETURN_PRICES, 20);

		book = buttonMaker(BOOK);

		grid.add(price, 0, 9);
		grid.add(book, 6, 14);

		//Actions
		oneWay.setOnAction(e-> setOneWay());
		twoWay.setOnAction(e-> setReturn());
		fromLocation.setOnAction(e-> updateRoutes());
		toLocation.setOnAction(e-> updatePrice());
		outDate.setOnAction(e-> outDateAction());
		inDate.setOnAction(e-> inDateAction());
		book.setOnAction(e-> bookAction(window));

		window.setScene(scene);
		window.setMinHeight(600);
		window.setMinWidth(550);
		window.show();
	}

	public void setOneWay() {
		inDate.valueProperty().set(null);
		inDate.setDisable(true);
		incomingTime.setValue(null);
		incomingTime.setDisable(true);
		returnCheck = false;
		textColor(true, flyIn);
		textColor(true, timeIn);
		grid.getChildren().remove(returnPrice);
	}

	public void setReturn() {
		returnCheck = true;
		if(outDate.getValue()!=null)
			inDate.setDisable(false);
	}

	public void updateRoutes() {
		if(fromLocation.getSelectionModel().getSelectedItem()!=null) {
			toLocation.setItems(FXCollections.observableArrayList(Arrays.asList(locations)));
			String temp = fromLocation.getSelectionModel().getSelectedItem(), tempTo = toLocation.getSelectionModel().getSelectedItem();
			toLocation.getItems().remove(fromLocation.getSelectionModel().getSelectedIndex());
			if(tempTo!=null && (tempTo.equals(temp)||tempTo.equals(locations[3])||tempTo.equals(locations[2])))
				toLocation.valueProperty().set(null);
			if(temp.equals(locations[2])||temp.equals(locations[3]))
				toLocation.getItems().remove(2);
			updatePrice();
		}
	}

	public void updatePrice() {
		outLoc = fromLocation.getSelectionModel().getSelectedItem();
		toLoc = toLocation.getSelectionModel().getSelectedItem();
		if(flightCheck(outDate, LocalDate.now())) {
			outDate.setDisable(false);
			outDateAction();
		}
		outDate.valueProperty().set(null);
		inDate.valueProperty().set(null);
		updateTimes();
	}

	public void outDateAction() {
		if(outDate.getValue()!=null) {
			Double setPrice = getPrice(outDate.getValue());
			price.setText(PRICES + "\n" + OVER_SIX + setPrice + "\n" + TWO_TO_SIX + "\n" + UNDER_TWO);
			flightCheck(inDate, outDate.getValue());
			if(returnCheck)
				inDate.setDisable(false);
			outgoingTime.setDisable(false);
			if(inDate.getValue()!=null && inDate.getValue().isBefore(outDate.getValue()))
				inDate.valueProperty().set(null);
		}
		else {
			inDate.setDisable(true);
			outgoingTime.setDisable(true);
			incomingTime.setDisable(true);
		}
		updateTimes();
	}

	public boolean flightCheck(DatePicker d, LocalDate n) {
		if(outLoc!=null && toLoc!=null) {
			if(outLoc.length()>3 && toLoc.length()>3) {
				outLoc = outLoc.substring(outLoc.length()-4, outLoc.length()-1).toLowerCase();
				toLoc = toLoc.substring(toLoc.length()-4, toLoc.length()-1).toLowerCase();
			}
			for(int i=0; i<priceList.length; i++) {
				if(abr[i].contains(outLoc) && abr[i].contains(toLoc))
					currentPrice = priceList[i];
			}
			if(abr[12].contains(outLoc) && abr[12].contains(toLoc)) {
				disable(d, n.plusDays(1), 1);
			}
			else if(abr[17].contains(outLoc) && abr[17].contains(toLoc)) {
				disable(d, n.plusDays(1), 2);
			}
			else if(getFromLocation()!=null&&toLocation.getValue()!=null) {
				disable(d, n.plusDays(1), 0);
			}
			return true;
		}
		return false;
	}

	public void updateTimes() {
		boolean first = false, second = false, run = true;
		int i = 0;
		if(fromLocation.getValue()!=null && toLocation.getValue()!=null) {
			while(run) {
				if(abr[i].equals(outLoc+toLoc)) {
					outgoingTime.setItems(FXCollections.observableArrayList(Arrays.asList(flightTimes[i])));
					first = true;
				}
				else if(abr[i].equals(toLoc+outLoc)) {
					incomingTime.setItems(FXCollections.observableArrayList(Arrays.asList(flightTimes[i])));
					second = true;
				}
				else if(i==abr.length-1||(first&&second))
					run = false;
				i++;
			}
		}
	}

	public void inDateAction() {
		if(inDate.getValue()!=null && toLocation.getValue()!=null) {
			Double setPrice = getPrice(inDate.getValue());
			returnPrice.setText("\n" + RETURN_PRICES + "\n" + OVER_SIX + setPrice + "\n" + TWO_TO_SIX + "\n" + UNDER_TWO);
			grid.getChildren().remove(returnPrice);
			grid.add(returnPrice, 6, 9);
			incomingTime.setDisable(false);
		}
	}

	public Double getPrice(LocalDate dateTemp) {
		if(dateTemp!=null) {
			Calendar c = Calendar.getInstance();
			Instant dateSet = Instant.from(dateTemp.atStartOfDay(ZoneId.systemDefault()));
			c.setTime(Date.from(dateSet));
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			Double retPrice = (double) currentPrice;
			if(dayOfWeek<2 || dayOfWeek>5) {
				retPrice*=1.2;
			}
			return retPrice;
		}
		return null;
	}

	public void bookAction(Stage window) {
		boolean nextPage, returnFlight;
		if(!returnCheck)
			nextPage = bookingDetails.createBooking(getFromLocation(), getToLocation(), getOutTime(), getDate(flyOut, outDate), getPrice(outDate.getValue()));
		else
			nextPage = bookingDetails.createBooking(getFromLocation(), getToLocation(), getOutTime(), getInTime(), getDate(flyOut, outDate), getDate(flyIn, inDate), getPrice(outDate.getValue()), getPrice(inDate.getValue()));
		if(twoWay.isSelected())
			returnFlight = true;
		else
			returnFlight = false;
		if(nextPage){
			if(returnFlight!=secondView.getReturnCheck()) {
				secondView = new SecondView(bookingDetails);
				secondView.startView();
			}
			secondView.setDetails(window, pane, returnFlight, fromLocation.getValue(), toLocation.getValue());
			pane.setCenter(secondView.getGrid());
			window.setMinWidth(850);
			Button temp = secondView.getReturn();
			temp.setOnAction(e-> returnPane(window));
		}
	}

	public String getFromLocation() {
		return fromLocation.getValue();
	}

	public String getToLocation() {
		textColor(checkInput(toLocation.getValue()), to);
		return toLocation.getValue();
	}

	public String getOutTime() {
		textColor(checkInput(outgoingTime.getValue()), timeOut);
		return outgoingTime.getValue();
	}

	public String getInTime() {
		textColor(checkInput(incomingTime.getValue()), timeIn);
		return incomingTime.getValue();
	}

	public Date getDate(Text t, DatePicker p) {
		boolean check = false;
		if(p.getValue()!=null)
			check = true;
		textColor(check, t);
		if(p.getValue() != null){
			LocalDate localDate = p.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			return Date.from(instant);
		}
		return null;
	}

	public void textColor(Boolean check, Text change) {
		if(check)
			change.setFill(Color.BLACK);
		else
			change.setFill(Color.RED);
	}

	public Boolean checkInput(String input) {
		if(input!=null)
			return true;
		return false;
	}

	public void returnPane(Stage window) {
		pane.setCenter(grid);
		window.setMinWidth(550);
	}

	public Text textMaker(String s, int f) {
		Text t = new Text(s);
		t.setFont(new Font(f));
		return t;
	} 

	public ComboBox<String> comboMaker() {
		ComboBox<String> c = new ComboBox<>();
		c.setPrefWidth(comboSize);
		return c;
	}

	public Button buttonMaker(String s) {
		Button b = new Button(s);
		b.setPrefWidth(75);
		return b;
	}

	public void disable(DatePicker pick, ChronoLocalDate d, int flight) {
		final Callback<DatePicker, DateCell> dayCellFactory = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						boolean startAndEnd = item.isBefore(d)||item.isAfter(LocalDate.now().plusMonths(6)),
								march=flight==1&&item.getMonthValue()==MARCH, april=flight!=0&&item.getMonthValue()==APRIL;
						if (startAndEnd||march||april) {
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