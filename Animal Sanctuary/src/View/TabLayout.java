package View;

import Controller.*;

import java.io.File;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.control.DateCell;

public class TabLayout {
	private AnchorPane anchor, viewAnchor, interestAnchor;
	private ScrollPane scroll;
	private Label titleLabel, personLabel, nameLabel, addressLabel, phoneLabel, emailLabel,
	animalLabel, nameAnimalLabel, ageLabel, typeLabel, genderLabel, breedLabel, descLabel,
	dateLabel, locationLabel, viewTitleLabel, aDetailLabel, typeDisplay,
	breedDisplay, nameDisplay, ageDisplay, genderDisplay, descDisplay, pDetailLabel, pNameDisplay,
	phoneDisplay, addressDisplay, emailDisplay, eDetailLabel, dateDisplay, locationDisplay,
	chipLabel, neuterLabel,vacLabel, resLabel, statusLabel, interestLabel;
	private TextField nameInput, addressInput, phoneInput, emailInput, nameAnimalInput,
	ageInput;
	private TextArea descInput;
	private ComboBox<String> typeInput, breedInput, statusInput, locationInput;
	private ImageView image;
	private boolean nameAdoption;
	private RadioButton choiceMale, choiceFemale, neuterYes, neuterNo, chipYes, chipNo, vacYes, vacNo, resYes, resNo;
	private DatePicker dateInput;
	private Button uploadPicture, submitAnimal, viewAnimal, removeAnimal, removeAltButton, returnButton, viewInterested, registerInterest;
	private TableView<DetailController> table, interestTable;
	private final int MIN_AGE = 0, MAX_AGE = 20;

	public TabLayout() {
		scroll = new ScrollPane();
		anchor = new AnchorPane();
		viewAnchor = new AnchorPane();
		interestAnchor = new AnchorPane();
	}

	public void personDetails(String title, String person, String name, String address, String phone, String email, String animal, String aName, String age) {
		titleLabel = labelMaker(title, 60, 100, 0);
		titleLabel.setFont(Font.font(null, FontWeight.BOLD, 60));
		personLabel = labelMaker(person, 60, 80, 60);
		nameLabel = labelMaker(name, 20, 40, 160);
		nameInput = fieldMaker(40, 190);
		addressLabel = labelMaker(address, 20, 275, 160);
		addressInput = fieldMaker(275, 190);
		addressInput.setPrefWidth(300);
		phoneLabel = labelMaker(phone, 20, 40, 220);
		phoneInput = fieldMaker(40, 250);
		emailLabel = labelMaker(email, 20, 275, 220);
		emailInput = fieldMaker(275, 250);
		emailInput.setPrefWidth(300);
		animalLabel = labelMaker(animal, 60, 80, 300);
		nameAnimalLabel = labelMaker(aName, 20, 40, 380);
		nameAnimalInput = fieldMaker(40, 410);
		ageLabel = labelMaker(age, 20, 40, 560);
		ageInput = fieldMaker(40, 590);
		anchor.getChildren().addAll(titleLabel, personLabel, nameLabel, nameInput, addressLabel, addressInput, phoneLabel, phoneInput, emailLabel, emailInput, animalLabel, nameAnimalLabel, ageLabel, nameAnimalInput, ageInput);
	}

	public void animalDetails(String type, String gender, String breed, String desc, DataController d) {
		ToggleGroup group = new ToggleGroup();
		typeLabel = labelMaker(type, 20, 40, 440);
		typeInput = new ComboBox<>();
		comboEdit(typeInput, 40, 470, 149);
		typeInput.setOnMouseClicked((event) -> {
			String temp = typeInput.getValue();
			typeInput.getItems().clear();
			for(String t: d.getType())
				typeInput.getItems().add(t);
			typeInput.valueProperty().set(temp);
		});
		typeInput.setOnAction((event) -> {
			if(typeInput.getValue()==null)
				breedInput.valueProperty().set(null);
		});
		genderLabel = labelMaker(gender, 20, 40, 620);
		choiceMale = radioMaker("Male", 40, 650, group);
		choiceMale.setSelected(true);
		choiceFemale = radioMaker("Female", 115, 650, group);
		breedLabel = labelMaker(breed, 20, 40, 500);
		breedInput = new ComboBox<>();
		comboEdit(breedInput, 40, 530, 149);
		breedInput.setOnMouseClicked((event) -> {
			String temp = breedInput.getValue();
			breedInput.getItems().clear();
			if(typeInput.getValue()!=null) {
				for(String t: d.getBreed(typeInput.getValue())) {
					breedInput.getItems().add(t);
				}
				if(temp!=null)
					breedInput.valueProperty().set(temp);
			}
		});
		descLabel = labelMaker(desc, 20, 275, 380);
		descInput = new TextArea();
		descInput.setLayoutX(275);
		descInput.setLayoutY(410);
		descInput.setPrefHeight(150);
		descInput.setPrefWidth(300);
		descInput.setWrapText(true);
		descInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER)  {
					keyEvent.consume();
				}
			}
		});
		anchor.getChildren().remove(ageInput);
		anchor.getChildren().addAll(typeLabel, genderLabel, breedLabel, descLabel, typeInput, breedInput, ageInput, choiceMale, choiceFemale);
	}

	public void extraDetails(String extra, String date, String location, String inList, String attach, DataController d) {
		dateLabel = labelMaker(date, 20, 40, 680);
		dateInput = dateMaker(40, 710);
		locationLabel = labelMaker(location , 20, 275, 560);
		locationInput = new ComboBox<>();
		locationInput.setLayoutX(275);
		locationInput.setLayoutY(590);
		locationInput.setPrefWidth(300);
		locationInput.setOnMouseClicked((event) -> {
			locationInput.getItems().clear();
			for (String l: d.getLocation())
				locationInput.getItems().add(l);
		});
		uploadPicture = buttonMaker(attach, 275, 650);
		anchor.getChildren().addAll(dateInput, descInput, dateLabel, locationLabel, locationInput, uploadPicture);
	}

	public void adoptDetails(String attach) {
		new ScrollPane();
		ToggleGroup neuter = new ToggleGroup();
		ToggleGroup chip = new ToggleGroup();
		ToggleGroup vac = new ToggleGroup();
		ToggleGroup res = new ToggleGroup();

		ageLabel.setLayoutX(275);
		ageLabel.setLayoutY(380);
		ageInput.setLayoutX(275);
		ageInput.setLayoutY(410);

		registerInterest = buttonMaker("Register Interest", 470, 280);
		neuterLabel = labelMaker("Neutered", 20, 40, 450);
		neuterYes = radioMaker("Yes", 40, 480, neuter);
		neuterNo = radioMaker("No", 115, 480, neuter);
		neuterNo.setSelected(true);
		chipLabel = labelMaker("Chipped", 20, 275, 450);
		chipYes = radioMaker("Yes", 275, 480, chip);
		chipNo = radioMaker("No", 350, 480, chip);
		chipNo.setSelected(true);
		vacLabel = labelMaker("Vaccinated", 20, 40, 510);
		vacYes = radioMaker("Yes", 40, 540, vac);
		vacNo = radioMaker("No", 115, 540, vac);
		vacNo.setSelected(true);
		resLabel = labelMaker("Reserved", 20, 275, 510);
		resYes = radioMaker("Yes", 275, 540, res);
		resNo = radioMaker("No", 350, 540, res);
		resNo.setSelected(true);
		statusLabel = labelMaker("Animal Status", 20, 40, 570);
		statusInput = new ComboBox<>();
		comboEdit(statusInput, 40, 600, 149);
		statusInput.getItems().addAll("Training", "Queued For Training", "Dead");        
		dateLabel = labelMaker("Date For Adoption", 20, 275, 570);
		dateInput = dateMaker(275, 600);
		viewInterested = buttonMaker("View Interested", 700, 710);
		removeAnimal.setLayoutX(815);
		uploadPicture = buttonMaker(attach, 275, 650);
		disableEntry();
		closeContact();

		anchor.getChildren().addAll(registerInterest, uploadPicture, neuterLabel, neuterYes, neuterNo, chipLabel, chipYes, chipNo, vacLabel, vacYes, vacNo, resLabel, resYes, resNo, 
				statusLabel, statusInput,dateLabel, dateInput, viewInterested);
	}

	public void table(ObservableList<DetailController> data) {
		table = tableMaker(data);
		table.setLayoutX(600);
		table.setLayoutY(140);
		submitAnimal = buttonMaker("Submit Details", 275, 710);
		viewAnimal = buttonMaker("View Animal", 600, 710);
		removeAnimal = buttonMaker("Remove Animal", 700, 710);
		anchor.getChildren().addAll(submitAnimal, table, viewAnimal, removeAnimal);
	}

	public void interestTable(ObservableList<DetailController> data) {
		interestLabel = labelMaker("Interested People", 60, 20, 0);
		interestLabel.setFont(Font.font(null, FontWeight.BOLD, 60));
		interestTable = personTableMaker(data);
		interestTable.setLayoutX(40);
		interestTable.setLayoutY(100);
		interestAnchor.getChildren().addAll(interestLabel, interestTable);
	}

	public void lostAnchor(DataController d) {
		LostController controller = new LostController(d);
		uploadPicture.setOnAction((event) -> {
			String i = null;
			i = imageLoader();
			if(i!=null)
				controller.setImage(i);
			controller.getImage();
		});
		submitAnimal.setOnAction((event) -> {
			boolean check = true;
			if(!controller.setAnimal(getAge(), getAnimalName(), getGender(), getDescription(), getBreed(), getType()))
				check = false;
			if(!controller.setPerson(getName(), getAddress(), getPhone(), getEmail()))
				check = false;
			if(!controller.setExtra(getDate(), getLocation()))
				check = false;
			if(check) {
				controller.addLostAnimal();
				resetTab();
			}
		});
		viewAnimal.setOnAction((event) -> {
			if(table.getSelectionModel().getSelectedItem()!=null){
				setView(table.getSelectionModel().getSelectedItem());
				setExtra(table.getSelectionModel().getSelectedItem());
				viewAnchor.getChildren().add(returnButton);
				scroll.setContent(viewAnchor);
				resetTab();
			}
		});
		remove(d);
	}

	public void foundAnchor(DataController d) {
		FoundController controller = new FoundController(d);
		uploadPicture.setOnAction((event) -> {
			String i = null;
			i = imageLoader();
			if(i!=null)
				controller.setImage(i);
			controller.getImage();
		});
		submitAnimal.setOnAction((event) -> {
			boolean check = true;
			if(!controller.setAnimal(getFoundAge(), getFoundAnimalName(), getGender(), getDescription(), getBreed(), getType()))
				check = false;
			if(!controller.setPerson(getName(), getAddress(), getPhone(), getEmail()))
				check = false;
			if(!controller.setExtra(getDate(), getLocation()))
				check = false;
			if(check) {
				controller.addFoundAnimal();
				resetTab();
			}
		});
		viewAnimal.setOnAction((event) -> {
			if(table.getSelectionModel().getSelectedItem()!=null){
				removeToOwner(d, table.getSelectionModel().getSelectedItem());
				setView(table.getSelectionModel().getSelectedItem());
				setExtra(table.getSelectionModel().getSelectedItem());
				viewAnchor.getChildren().add(returnButton);
				scroll.setContent(viewAnchor);
				resetTab();
			}
		});
		remove(d);
	}

	public void adoptAnchor(DataController d) {
		AdoptController controller = new AdoptController(d);
		submitAnimal.setOnAction((event) -> {
			boolean check = true;
			if(!nameAdoption) {
				if(!controller.setAdoption(getAnimalName(), getFoundAge(), getNeutered(), getChipped(), getVac(), getRes(), getStatus(), getAdoptDate()))
					check = false;
			}
			else    
				if(!controller.setAdoption(getFoundAge(), getNeutered(), getChipped(), getVac(), getRes(), getStatus(), getAdoptDate()))
					check = false;
			if(check) {
				controller.updateAdoption(table.getSelectionModel().getSelectedItem());
				resetAdopt();
			}
		});
		uploadPicture.setOnAction((event) -> {
			String i = null;
			i = imageLoader();
			if(i!=null)
				controller.setImage(i);
			controller.getImage();
		});
		viewInterested.setOnAction((event) -> { 
			if(table.getSelectionModel().getSelectedItem()!=null){ 
				controller.viewInterested(table.getSelectionModel().getSelectedItem());
				interestAnchor.getChildren().add(returnButton);
				scroll.setContent(interestAnchor);
				removeToAdopt(d, table.getSelectionModel().getSelectedItem());
				table.getSelectionModel().clearSelection();
			}
		});
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(table.getSelectionModel().getSelectedItem()!=null) {
				openContact();
				disableEntry();
				if(table.getSelectionModel().getSelectedItem().getAnimalName().equals("Name Unknown"))
					nameAnimalInput.setDisable(false);
				else
					nameAdoption = true;
				if(table.getSelectionModel().getSelectedItem().getAnimalAge() == -2)
					ageInput.setDisable(false);
				if(table.getSelectionModel().getSelectedItem().getNeutered().equals("Yes"))
					neuterYes.setSelected(true);
				if(table.getSelectionModel().getSelectedItem().getVaccinated().equals("Yes"))
					vacYes.setSelected(true);
				if(table.getSelectionModel().getSelectedItem().getChipped().equals("Yes"))
					chipYes.setSelected(true);
				if(table.getSelectionModel().getSelectedItem().getReserved().equals("Yes"))
					resYes.setSelected(true);
				enableEntry();
			}
			else {
				disableEntry();
				closeContact();
				table.getSelectionModel().select(-1);
			}
		});
		viewAnimal.setOnAction((event) -> {
			if(table.getSelectionModel().getSelectedItem()!=null){
				setView(table.getSelectionModel().getSelectedItem());
				setAdopt(table.getSelectionModel().getSelectedItem());                    
				viewAnchor.getChildren().add(returnButton);
				scroll.setContent(viewAnchor);
				table.getSelectionModel().clearSelection();
			}
		});
		registerInterest.setOnAction((event) -> {
			if(controller.setPerson(getName(), getAddress(), getPhone(), getEmail()))
				controller.updateInterested(table.getSelectionModel().getSelectedItem());
		});
		disableDateBefore();
		removeAnimal.setOnAction((event) -> {
			d.removeAnimal(table.getSelectionModel().getSelectedItem());
		});
	}

	public void remove(DataController data) {
		removeAnimal.setOnAction((event) -> {
			data.removeAnimal(table.getSelectionModel().getSelectedItem());
		});
	}

	public void removeToOwner(DataController data, DetailController d) {
		LostFamilyLayout lost = new LostFamilyLayout();
		removeAltButton = buttonMaker("Owner Claim", 100, 710);
		removeAltButton.setOnAction((event) -> {
			if(lost.display(data)) {
				data.removeAnimal(d);
				returnTab();
			}
		});
		viewAnchor.getChildren().add(removeAltButton);
	}

	public void removeToAdopt(DataController data, DetailController d) {
		removeAltButton = buttonMaker("Assign To Family", 80, 710);
		removeAltButton.setOnAction((event) -> {
			if(interestTable.getSelectionModel().getSelectedItem()!=null) {
				data.removeAnimal(d);
				returnTab();
			}
		});
		interestAnchor.getChildren().add(removeAltButton);
	}

	public void viewAnimal(String title, String animal) {
		viewTitleLabel = labelMaker(title, 60, 100, 0);
		viewTitleLabel.setFont(Font.font(null, FontWeight.BOLD, 60));
		aDetailLabel = labelMaker(animal, 60, 80, 60);
		typeDisplay = labelMaker(null, 20, 40, 150);
		breedDisplay = labelMaker(null, 20, 40, 180);
		nameDisplay = labelMaker(null, 20, 40, 210);
		ageDisplay = labelMaker(null, 20, 40, 240);
		genderDisplay = labelMaker(null, 20, 40, 270);
		descDisplay = labelMaker(null, 20, 650, 500);
		descDisplay.setPrefWidth(450);
		descDisplay.setWrapText(true);
		returnButton = buttonMaker("Return", 200, 710);
		returnButton.setOnAction((event) -> {
			returnTab();
		});
		image = new ImageView();
		viewAnchor.getChildren().addAll(aDetailLabel, typeDisplay, breedDisplay, nameDisplay, ageDisplay, genderDisplay, descDisplay, viewTitleLabel);
	}

	public void returnTab() {
		interestAnchor.getChildren().remove(returnButton);
		viewAnchor.getChildren().remove(returnButton);
		scroll.setContent(anchor);
		viewAnchor.getChildren().remove(image);
	}

	public void viewExtra(String person, String other) {
		pDetailLabel = labelMaker(person, 60, 80, 330);
		pNameDisplay = labelMaker(null, 20, 40, 410);
		phoneDisplay = labelMaker(null, 20, 40, 440);
		addressDisplay = labelMaker(null, 20, 40, 470);
		emailDisplay = labelMaker(null, 20, 40, 500);
		eDetailLabel = labelMaker(other, 60, 80, 530);
		dateDisplay = labelMaker(null, 20, 40, 610);
		locationDisplay = labelMaker(null, 20, 40, 640);
		viewAnchor.getChildren().addAll(pDetailLabel, pNameDisplay, phoneDisplay, addressDisplay, emailDisplay, eDetailLabel, dateDisplay, locationDisplay);
	}

	public void viewAdopt() {
		locationDisplay = labelMaker(null, 20, 40, 330);
		pNameDisplay = labelMaker(null, 20, 40, 360);
		phoneDisplay = labelMaker(null, 20, 40, 390);
		addressDisplay = labelMaker(null, 20, 40, 420);
		dateDisplay = labelMaker(null, 20, 40, 450);
		emailDisplay = labelMaker(null, 20, 40, 480);
		viewAnchor.getChildren().addAll(locationDisplay, pNameDisplay, phoneDisplay, addressDisplay, dateDisplay, emailDisplay);
	}

	public void setView(DetailController detail) {
		typeDisplay.setText("Type of Animal: " + detail.getAnimalType());
		breedDisplay.setText("Breed of " + detail.getAnimalType() + ": " + detail.getAnimalBreed());
		nameDisplay.setText("Name: " + detail.getAnimalName());
		ageDisplay.setText("Age: " + detail.getAnimalStringAge());
		genderDisplay.setText("Gender: " + detail.getAnimalGender());
		descDisplay.setText("Description: " + detail.getAnimalDescription());
		if(detail.getAnimalPicture() != null) {
			image.setImage(detail.getAnimalPicture());
			image.setLayoutX(650);
			image.setLayoutY(100);
			image.setFitHeight(400);
			image.setFitWidth(600);
			viewAnchor.getChildren().add(image);
		}
	}

	public void setExtra(DetailController detail) {
		pNameDisplay.setText("Name: " + detail.getPersonName());
		phoneDisplay.setText("Phone Number: " + detail.getPersonPhone());
		addressDisplay.setText("Address: " + detail.getPersonAddress());
		emailDisplay.setText("Email: " + detail.getPersonEmail());
		dateDisplay.setText("Date: " + detail.getAnimalDate());
		locationDisplay.setText("Location: " + detail.getAnimalLocation());
	}

	public void setAdopt(DetailController detail) {
		locationDisplay.setText("Neutered: " + detail.getNeutered());
		pNameDisplay.setText("Chipped: " + detail.getChipped());
		phoneDisplay.setText("Vaccinated: " + detail.getVaccinated());
		addressDisplay.setText("Reserved: " + detail.getReserved());
		dateDisplay.setText("Date: " + detail.getDate());
		if(detail.getDate()==null)
			dateDisplay.setText("Date: No adoption date set");
		emailDisplay.setText("Status: " + detail.getStatus());
	}

	public void openContact() {
		nameInput.setDisable(false);
		addressInput.setDisable(false);
		phoneInput.setDisable(false);
		emailInput.setDisable(false);
		registerInterest.setDisable(false);
	}

	public void closeContact() {
		nameInput.clear();
		nameInput.setDisable(true);
		addressInput.clear();
		addressInput.setDisable(true);
		phoneInput.clear();
		phoneInput.setDisable(true);
		emailInput.clear();
		emailInput.setDisable(true);
		registerInterest.setDisable(true);
	}

	public void enableEntry(){
		neuterYes.setDisable(false);
		neuterNo.setDisable(false);
		chipYes.setDisable(false);
		chipNo.setDisable(false);
		vacYes.setDisable(false);
		vacNo.setDisable(false);
		resYes.setDisable(false);
		resNo.setDisable(false);
		statusInput.setDisable(false);
		if(table.getSelectionModel().getSelectedItem().getDate()==null)
			dateInput.setDisable(false);
		uploadPicture.setDisable(false);
		submitAnimal.setDisable(false);
	}

	public void disableEntry(){
		neuterYes.setDisable(true);
		neuterNo.setDisable(true);
		chipYes.setDisable(true);
		chipNo.setDisable(true);
		vacYes.setDisable(true);
		vacNo.setDisable(true);
		resYes.setDisable(true);
		resNo.setDisable(true);
		statusInput.setDisable(true);
		nameAnimalInput.setDisable(true);
		ageInput.setDisable(true);
		dateInput.setDisable(true);
		uploadPicture.setDisable(true);
		submitAnimal.setDisable(true);
		neuterNo.setSelected(true);
		chipNo.setSelected(true);
		vacNo.setSelected(true);
		resNo.setSelected(true);
	}

	public TableView<DetailController> tableMaker(ObservableList<DetailController> data) {
		TableView<DetailController> table = new TableView<DetailController>(data);
		TableColumn<DetailController, String> idCol = new TableColumn<DetailController, String> ("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalID"));
		TableColumn<DetailController, String>  typeCol = new TableColumn<DetailController, String> ("Type");
		typeCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalType"));
		TableColumn<DetailController, String>  ageCol = new TableColumn<DetailController, String> ("Age");
		ageCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalStringAge"));
		TableColumn<DetailController, String>  genderCol = new TableColumn<DetailController, String> ("Gender");
		genderCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalGender"));
		TableColumn<DetailController, String>  descCol = new TableColumn<DetailController, String> ("Description");
		descCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalDescription"));
		TableColumn<DetailController, String>  nameCol = new TableColumn<DetailController, String> ("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalName"));
		TableColumn<DetailController, String>  breedCol = new TableColumn<DetailController, String> ("Breed");
		breedCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalBreed"));
		table.getColumns().add(idCol);
		table.getColumns().add(typeCol);
		table.getColumns().add(breedCol);
		table.getColumns().add(nameCol);
		table.getColumns().add(ageCol);
		table.getColumns().add(genderCol);
		table.getColumns().add(descCol);
		table.setPrefHeight(555);
		return table;
	}

	public TableView<DetailController> personTableMaker(ObservableList<DetailController> data) {
		TableView<DetailController> table = new TableView<DetailController>(data);
		TableColumn<DetailController, String> nameCol = new TableColumn<DetailController, String> ("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonName"));
		TableColumn<DetailController, String>  addressCol = new TableColumn<DetailController, String> ("Address");
		addressCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonAddress"));
		TableColumn<DetailController, String>  phoneCol = new TableColumn<DetailController, String> ("Phone");
		phoneCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonPhone"));
		TableColumn<DetailController, String>  emailCol = new TableColumn<DetailController, String> ("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonEmail"));
		table.getColumns().add(nameCol);
		table.getColumns().add(addressCol);
		table.getColumns().add(phoneCol);
		table.getColumns().add(emailCol);
		table.setPrefHeight(555);
		return table;
	}

	public void resetPerson() {
		nameInput.clear();
		addressInput.clear();
		phoneInput.clear();
		emailInput.clear();
	}

	public void resetAnimal() {
		nameAnimalInput.clear();
		ageInput.clear();
		typeInput.valueProperty().set(null);
		choiceMale.setSelected(true);
		descInput.clear();        
	}

	public void resetExtra() {
		dateInput.valueProperty().set(null);
		locationInput.valueProperty().set(null);
	}

	public void resetTab() {
		resetPerson();
		resetAnimal();
		resetExtra();
		table.getSelectionModel().clearSelection();
	}

	public void resetAdopt() {
		nameAnimalInput.clear();
		ageInput.clear();
		statusInput.valueProperty().set(null);
		dateInput.valueProperty().set(null);
	}

	public ScrollPane getScroll() {
		scroll.setContent(anchor);
		return scroll;
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

	public String getAnimalName() {
		labelColor(nameAnimalLabel, textCheck(nameAnimalInput.getText()));
		return nameAnimalInput.getText();
	}

	public int getAge() {
		if(intExtract(ageInput.getText(), MAX_AGE) != -1) {
			ageLabel.setTextFill(Color.BLACK);
			return intExtract(ageInput.getText(), MAX_AGE);
		}
		ageLabel.setTextFill(Color.RED);
		return -1;
	}

	public String getFoundAnimalName() {
		return nameAnimalInput.getText();
	}

	public int getFoundAge() {
		if(intExtract(ageInput.getText(), MAX_AGE) != -1) {
			ageLabel.setTextFill(Color.BLACK);
			return intExtract(ageInput.getText(), MAX_AGE);
		}
		if(ageInput.getText().equals("")) {
			ageLabel.setTextFill(Color.BLACK);
			return -2;
		}
		else
			ageLabel.setTextFill(Color.RED);
		return -1;
	}

	public String getType() {
		labelColor(typeLabel, choiceCheck(typeInput.getValue()));
		return typeInput.getValue();
	}

	public boolean getGender() {
		if(choiceMale.isSelected())
			return true;
		return false;
	}

	public String getBreed() {
		labelColor(breedLabel, choiceCheck(breedInput.getValue()));
		return breedInput.getValue();
	}

	public String getDescription() {
		labelColor(descLabel, textCheck(descInput.getText()));
		return descInput.getText();
	}

	public Date getDate() {
		if(dateInput.getValue() != null){
			LocalDate localDate = dateInput.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			dateLabel.setTextFill(Color.BLACK);
			return Date.from(instant);
		}
		dateLabel.setTextFill(Color.RED);
		return null;
	}

	public Date getAdoptDate() {
		if(table.getSelectionModel().getSelectedItem().getDate()!=null)
			return table.getSelectionModel().getSelectedItem().getDate();
		if(dateInput.getValue() != null){
			LocalDate localDate = dateInput.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			dateLabel.setTextFill(Color.BLACK);
			return Date.from(instant);
		}
		dateLabel.setTextFill(Color.RED);
		return null;
	}

	public String getLocation() {
		labelColor(locationLabel, choiceCheck(locationInput.getValue()));
		return locationInput.getValue();
	}

	public boolean getNeutered() {
		if(neuterYes.isSelected())
			return true;
		return false;
	}

	public boolean getChipped() {
		if(chipYes.isSelected())
			return true;
		return false;
	}

	public boolean getVac() {
		if(vacYes.isSelected())
			return true;
		return false;
	}

	public boolean getRes() {
		if(resYes.isSelected())
			return true;
		return false;
	}

	public String getStatus() {
		labelColor(statusLabel, choiceCheck(statusInput.getValue()));
		return statusInput.getValue();
	}

	public Button buttonMaker(String s, int x, int y) {
		Button b = new Button(s);
		b.setLayoutX(x);
		b.setLayoutY(y);
		return b;
	}

	public DatePicker dateMaker(int x, int y){
		DatePicker d = new DatePicker();
		d.setLayoutX(x);
		d.setLayoutY(y);
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						Date input = new Date();
						LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						if (item.isAfter(date)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}   
					}
				};
			}
		};
		d.setDayCellFactory(dayCellFactory);
		return d;
	}

	public void disableDateBefore() {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						Date input = new Date();
						LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						if (item.isBefore(date)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}   
					}
				};
			}
		};
		dateInput.setDayCellFactory(dayCellFactory);
	}

	public Label labelMaker(String s, int f, int x, int y){
		Label l = new Label(s);
		l.setFont(new Font(f));
		l.setLayoutX(x);
		l.setLayoutY(y);        
		return l;
	}

	public TextField fieldMaker(int x, int y) {
		TextField t = new TextField();
		t.setLayoutX(x);
		t.setLayoutY(y);
		return t;
	}

	public boolean textCheck(String s) {
		if(s.equals("")){
			return false;
		}
		return true;
	}

	public boolean choiceCheck(String s) {
		if(s == null){
			return false;
		}
		return true;
	}

	public int intExtract(String s, int max) {
		Scanner intCheck = new Scanner(s);
		if(intCheck.hasNextInt()) {
			int i = intCheck.nextInt();
			if(i>=MIN_AGE && i<=max) {
				intCheck.close();
				return i;
			}
		}
		intCheck.close();
		return -1;
	}

	public void labelColor(Label l, boolean b) {
		if(b)
			l.setTextFill(Color.BLACK);
		else
			l.setTextFill(Color.RED);
	}

	public void comboEdit(ComboBox<String> c, int x, int y, int w) {
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setPrefWidth(w);
	}

	public String imageLoader() {
		FileChooser fileC = new FileChooser();
		fileC.setTitle("Select Image to Attach");
		fileC.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File f = fileC.showOpenDialog(null);
		if(f != null) {
			return "file:"+f.getPath();
		}
		else 
			return null;
	}

	public RadioButton radioMaker(String s, int x, int y, ToggleGroup g) {
		RadioButton r = new RadioButton(s);
		r.setLayoutX(x);
		r.setLayoutY(y);
		r.setToggleGroup(g);
		return r;        
	}
}