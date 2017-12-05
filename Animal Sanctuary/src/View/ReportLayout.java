package View;

import Controller.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.control.DatePicker;
import java.util.Date;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.Instant;

public class ReportLayout {
    private Label title, extra, section, type, animals, pictures, error, incDate, date, location, highDate;
    private ComboBox<String> sectionInput, comboChoiceOne, comboChoiceTwo, comboChoiceThree;
    private CheckBox picInput, dateChoice;
    private Button submit;
    private DatePicker dateInput, hDateInput;
    private TableView<DetailController> table;
    private AnchorPane anchor;
    private ScrollPane scroll;
    private DataController data;
    private ReportController report;
    private TableColumn<DetailController, String> idCol, typeCol, ageCol, genderCol, descCol, nameCol, breedCol, pNameCol, phoneCol, addressCol, emailCol, sectionCol, pSectionCol, dateCol, adoptCol;
    private TableColumn<DetailController, ImageView> imageCol;

    public ReportLayout() {
        anchor = new AnchorPane();
        scroll = new ScrollPane();
    }

    public void reportStart(DataController d) {
        data = d;
        report = new ReportController(data);
        anchor.getChildren().addAll(title, extra, section, sectionInput, submit, table);
    }

    public void mainView() {
        title = labelMaker("Reports Section", 60, 100, 0);
        title.setFont(Font.font(null, FontWeight.BOLD, 60));
        extra = labelMaker("Select section first", 60, 80, 60);
        section = labelMaker("Section", 20, 40, 160);
        sectionInput = comboMaker(149, 40, 190);
        sectionInput.getItems().addAll("All", "Lost", "Found", "Adopt");
        submit = buttonMaker("Search", 275, 300);
        submit.setDisable(true);
        sectionInput.setOnAction((event) -> {
                comboChoiceOne.valueProperty().set(null);
                extra.setText("Select section first");
                resetPane();
                submit.setDisable(true);
                if(sectionInput.getValue().equals("All")) {
                    sectionAll();
                }
                else if(sectionInput.getValue().equals("Lost")) {
                    sectionLost();
                }
                else if(sectionInput.getValue().equals("Found")) {
                    sectionFound();
                }
                else if(sectionInput.getValue().equals("Adopt")) {
                    sectionAdoption();
                }
                report.clear();
                report.fillTable();
            });
        comboChoiceOne = comboMaker(149, 200, 190);
        error = labelMaker("Animal Sanctuary is empty", 40, 40, 230);
    }

    public void sectionAll() {
        extra.setText("Select type of search");
        type = labelMaker("Report Type", 20, 200, 160);
        comboChoiceOne.getItems().clear();
        comboChoiceOne.getItems().addAll("Sponsors", "Animals");
        comboChoiceOne.setOnAction(e -> {
                picInput.setSelected(false);
                comboChoiceTwo.valueProperty().set(null);
                anchor.getChildren().removeAll(animals, comboChoiceTwo, pictures, picInput);
                if(comboChoiceOne.getValue()!=null) {
                    if(comboChoiceOne.getValue().equals("Animals"))
                        anchor.getChildren().addAll(animals, comboChoiceTwo, pictures, picInput);
                    submit.setDisable(false);
                }
            });
        animals = labelMaker("Animal", 20, 360, 160);
        comboChoiceTwo = comboMaker(149, 360, 190);
        comboChoiceTwo.setOnMouseClicked(e -> {
                comboChoiceTwo.getItems().clear();
                comboChoiceTwo.getItems().add("All");
                for(int i=0; i<data.getType().size(); i++)
                    comboChoiceTwo.getItems().add(data.getType().get(i));
            });
        picInput = checkMaker(520, 190);
        pictures = labelMaker("Include Picture", 20, 520, 160);
        submit.setOnAction((event) -> {
                anchor.getChildren().remove(error);
                if(comboChoiceOne.getValue()!=null) {
                    if(data.getAnimals().getLength()!=0 || data.getAnimals().getRemoved().size()!=0) {
                        if(comboChoiceOne.getValue().equals("Sponsors")) {
                            report.getSponsors();
                            tableLayoutPerson();
                            //scroll.setContent(viewAnchor);
                        }
                        else if(comboChoiceTwo.getValue()!=null && data.getAnimals().getLength()!=0) {
                            report.getAllAnimals(comboChoiceTwo.getValue());
                            report.fillTable();
                            tableLayoutAnimal();
                            if(picInput.isSelected())
                                table.getColumns().add(imageCol);
                            //scroll.setContent(viewAnchor);
                        }
                    }
                    else
                        anchor.getChildren().add(error);
                }
            });
        anchor.getChildren().addAll(type, comboChoiceOne);
    }

    public void sectionLost() {
        extra.setText("Select location(Date optional)");
        type = labelMaker("Location", 20, 200, 160);
        comboChoiceOne.getItems().clear();
        comboChoiceOne.getItems().add("All");
        for(int i=0; i<data.getLocation().size(); i++)
            comboChoiceOne.getItems().add(data.getLocation().get(i));
        comboChoiceOne.setOnAction(e -> {
                if(comboChoiceOne.getValue()!=null)
                    submit.setDisable(false);
            });
        pictures = labelMaker("Include Picture", 20, 360, 160);
        picInput = checkMaker(360, 190);
        incDate = labelMaker("Include Date", 20, 520, 160);
        dateChoice = checkMaker(520, 190);
        dateChoice.setOnMouseClicked(e -> {
                anchor.getChildren().removeAll(date, dateInput);
                if(dateChoice.isSelected())
                    anchor.getChildren().addAll(date, dateInput);
                else
                    dateInput.valueProperty().set(null);
            });
        date = labelMaker("Choose Date", 20, 680, 160);
        dateInput = dateMaker(149, 680, 190);
        submit.setOnAction((event) -> {
                anchor.getChildren().remove(error);
                if(comboChoiceOne.getValue()!=null) {
                    if(data.getAnimals().getLength()!=0) {
                        if(!dateChoice.isSelected()) {
                            report.getAnimalsLocation("Lost", comboChoiceOne.getValue());
                            report.fillTable();
                            //scroll.setContent(viewAnchor);
                        }
                        else if(dateInput.getValue()!=null) {
                            report.getAnimalsLocationDate(getDate().getTime(), comboChoiceOne.getValue());
                            report.fillTable();
                            table.getColumns().add(dateCol);
                            //scroll.setContent(viewAnchor);
                        }
                        tableLayoutAnimal();
                        if(picInput.isSelected())
                            table.getColumns().add(imageCol);
                    }
                    else
                        anchor.getChildren().add(error);
                }
            });
        anchor.getChildren().addAll(type, comboChoiceOne, pictures, picInput, incDate, dateChoice);
    }

    public void sectionFound() {
        extra.setText("Select type of search");
        type = labelMaker("Report Type", 20, 200, 160);
        location = labelMaker("Location", 20, 360, 160);
        comboChoiceThree = comboMaker(149, 360, 190);
        comboChoiceThree.getItems().clear();
        comboChoiceThree.getItems().add("All");
        for(int i=0; i<data.getLocation().size(); i++)
            comboChoiceThree.getItems().add(data.getLocation().get(i));
        comboChoiceOne.getItems().clear();
        comboChoiceOne.getItems().addAll("Location", "Date Range", "Location and Dates");
        comboChoiceOne.setOnAction((event) -> {
                comboChoiceThree.valueProperty().set(null);
                dateInput.valueProperty().set(null);
                hDateInput.valueProperty().set(null);
                moveDates(520, 520, 680, 680, 840, 840);
                anchor.getChildren().removeAll(location, comboChoiceThree, date, dateInput, highDate, hDateInput, pictures, picInput);
                if(comboChoiceOne.getValue()!=null) {
                    if(comboChoiceOne.getValue().equals("Location")) {
                        anchor.getChildren().addAll(location, comboChoiceThree, pictures, picInput);
                        pictures.setLayoutX(520);
                        picInput.setLayoutX(520);
                    }
                    else if(comboChoiceOne.getValue().equals("Date Range")) {
                        moveDates(360, 360, 520, 520, 680, 680);
                        anchor.getChildren().addAll(date, dateInput, highDate, hDateInput, pictures, picInput);
                    }
                    else if(comboChoiceOne.getValue().equals("Location and Dates"))
                        anchor.getChildren().addAll(location, comboChoiceThree, date, dateInput, highDate, hDateInput, pictures, picInput);
                    submit.setDisable(false);
                }
            });
        pictures = labelMaker("Include Picture", 20, 520, 160);
        picInput = checkMaker(680, 190);
        incDate = labelMaker("Include Date", 20, 680, 160);
        dateChoice = checkMaker(680, 190);
        date = labelMaker("Earlier Date", 20, 840, 160);
        dateInput = dateMaker(149, 840, 190);
        highDate = labelMaker("Later Date", 20, 840, 160);
        hDateInput = dateMaker(149, 840, 190);
        submit.setOnAction((event) -> {
                anchor.getChildren().remove(error);
                tableLayoutAnimal();
                if(comboChoiceOne.getValue()!=null) {
                    if(data.getAnimals().getLength()!=0) {
                        if(comboChoiceOne.getValue().equals("Location")) {
                            if(comboChoiceThree.getValue()!=null) {
                                //scroll.setContent(viewAnchor);
                                report.getAnimalsLocation("Found", comboChoiceThree.getValue());
                                report.fillTable();
                            }
                        }
                        else if(comboChoiceOne.getValue().equals("Date Range")) {
                            if(dateInput.getValue()!=null && hDateInput.getValue()!=null) {
                                //scroll.setContent(viewAnchor);
                                report.getAnimalsRange(getDate().getTime(), getHighDate().getTime());
                                report.fillTable();
                                table.getColumns().add(dateCol);
                            }
                        }
                        else {
                            if(dateInput.getValue()!=null && hDateInput.getValue()!=null && comboChoiceThree.getValue()!=null) {
                                report.getAnimalsRangeLocation(comboChoiceThree.getValue(), getDate().getTime(), getHighDate().getTime());
                                report.fillTable();
                                //scroll.setContent(viewAnchor);
                                table.getColumns().add(dateCol);
                            }
                        }
                        if(picInput.isSelected())
                            table.getColumns().add(imageCol);
                    }
                    else
                        anchor.getChildren().add(error);
                }
            });
        anchor.getChildren().addAll(type, comboChoiceOne);
    }

    public void sectionAdoption() {
        extra.setText("Select the type of search");
        type = labelMaker("Report Type", 20, 200, 160);
        comboChoiceOne.getItems().clear();
        comboChoiceOne.getItems().addAll("Ready(All)", "Ready(Type)", "In training(Puppies)");
        comboChoiceOne.setOnAction(e -> {
                picInput.setSelected(false);
                comboChoiceThree.valueProperty().set(null);
                anchor.getChildren().removeAll(location, comboChoiceThree, pictures, picInput);
                if(comboChoiceOne.getValue()!=null) {
                    submit.setDisable(false);
                    if(comboChoiceOne.getValue().equals("Ready(Type)")) {
                        anchor.getChildren().addAll(location, comboChoiceThree, pictures, picInput);
                        pictures.setLayoutX(520);
                        picInput.setLayoutX(520);
                    }
                    else {
                    	anchor.getChildren().addAll(pictures, picInput);
                    	pictures.setLayoutX(360);
                        picInput.setLayoutX(360);
                        }
                }
        });
        location = labelMaker("Type", 20, 360, 160);
        comboChoiceThree = comboMaker(149, 360, 190);
        comboChoiceThree.getItems().clear();
        for(int i=0; i<data.getType().size(); i++)
            comboChoiceThree.getItems().add(data.getType().get(i));
        pictures = labelMaker("Include Picture", 20, 520, 160);
        picInput = checkMaker(520, 190);
        submit.setOnAction((event) -> {
                anchor.getChildren().remove(error);
                if(comboChoiceOne.getValue()!=null) {
                    if(data.getAnimals().getLength()!=0) {
                        if(comboChoiceOne.getValue().equals("Ready(All)")) {
                            tableLayoutAnimal();
                            report.getAnimalsAdoptionAll();
                            report.fillTable();
                            table.getColumns().add(adoptCol);
                            //scroll.setContent(viewAnchor);
                        }
                        else if(comboChoiceOne.getValue().equals("Ready(Type)")) {
                            if(comboChoiceThree.getValue()!=null) {
                                tableLayoutAnimal();
                                report.getAnimalsAdoptionType(comboChoiceThree.getValue());
                                report.fillTable();
                                table.getColumns().add(adoptCol);
                               // scroll.setContent(viewAnchor);
                            }
                        }
                        else if(comboChoiceOne.getValue().equals("In training")) {
                        	tableLayoutAnimal();
                        	report.getAnimalsAdoptionTraining();
                        	report.fillTable();
                            table.getColumns().add(adoptCol);
                        }
                        if(picInput.isSelected())
                            table.getColumns().add(imageCol);
                    }
                    else
                        anchor.getChildren().add(error);
                }
            });
        anchor.getChildren().addAll(type, comboChoiceOne);
    }

    public void moveDates(int x1, int x2, int x3, int x4, int x5, int x6) {
        date.setLayoutX(x1);
        dateInput.setLayoutX(x2);
        highDate.setLayoutX(x3);
        hDateInput.setLayoutX(x4);
        pictures.setLayoutX(x5);
        picInput.setLayoutX(x6);
    }

    public void viewSection(ObservableList<DetailController> data) {
        table = tableMaker(data);
    }

    public ScrollPane getScroll() {
        scroll.setContent(anchor);
        return scroll;
    }

    public void resetPane() {
        anchor.getChildren().removeAll(type, comboChoiceOne, animals, comboChoiceTwo, incDate, dateChoice, date, dateInput, pictures, picInput, location, comboChoiceThree);
    }

    public Label labelMaker(String s, int f, int x, int y){
        Label l = new Label(s);
        l.setFont(new Font(f));
        l.setLayoutX(x);
        l.setLayoutY(y);        
        return l;
    }

    public ComboBox<String> comboMaker(int w, int x, int y) {
        ComboBox<String> temp = new ComboBox<>();
        temp.setPrefWidth(w);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
    }

    public CheckBox checkMaker(int x, int y) {
        CheckBox temp = new CheckBox();
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
    }

    public DatePicker dateMaker(int w, int x, int y) {
        DatePicker temp = new DatePicker();
        temp.setPrefWidth(w);
        temp.setLayoutX(x);
        temp.setLayoutY(y);
        return temp;
    }

    public Button buttonMaker(String s, int x, int y) {
        Button b = new Button(s);
        b.setLayoutX(x);
        b.setLayoutY(y);
        return b;
    }

    public Date getDate() {
        LocalDate localDate = dateInput.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    public Date getHighDate() {
        LocalDate localDate = hDateInput.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    public TableView<DetailController> tableMaker(ObservableList<DetailController> data) {
        TableView<DetailController> temp = new TableView<DetailController>(data);
        idCol = new TableColumn<DetailController, String> ("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalID"));
        typeCol = new TableColumn<DetailController, String> ("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalType"));
        ageCol = new TableColumn<DetailController, String> ("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalStringAge"));
        genderCol = new TableColumn<DetailController, String> ("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalGender"));
        descCol = new TableColumn<DetailController, String> ("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalDescription"));
        nameCol = new TableColumn<DetailController, String> ("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalName"));
        breedCol = new TableColumn<DetailController, String> ("Breed");
        breedCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalBreed"));
        imageCol = new TableColumn<DetailController, ImageView> ("Image");
        imageCol.setCellValueFactory(new PropertyValueFactory<DetailController, ImageView>("AnimalImage"));
        sectionCol = new TableColumn<DetailController, String> ("Section");
        sectionCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalSection"));
        dateCol = new TableColumn<DetailController, String> ("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("AnimalDate"));
        adoptCol = new TableColumn<DetailController, String> ("Adoption Date");
        adoptCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("Date"));
        pNameCol = new TableColumn<DetailController, String> ("Name");
        pNameCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonName"));
        addressCol = new TableColumn<DetailController, String> ("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonAddress"));
        phoneCol = new TableColumn<DetailController, String> ("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonPhone"));
        emailCol = new TableColumn<DetailController, String> ("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonEmail"));
        pSectionCol = new TableColumn<DetailController, String> ("Section");
        pSectionCol.setCellValueFactory(new PropertyValueFactory<DetailController, String>("PersonSection"));
        temp.setPrefHeight(500);
        temp.setPrefWidth(800);
        temp.setLayoutX(40);
        temp.setLayoutY(350);
        return temp;
    }

    public void tableLayoutAnimal() {
        table.getColumns().clear();
        table.getColumns().add(idCol);
        table.getColumns().add(typeCol);
        table.getColumns().add(breedCol);
        table.getColumns().add(nameCol);
        table.getColumns().add(ageCol);
        table.getColumns().add(genderCol);
        //table.getColumns().add(descCol);
        table.getColumns().add(sectionCol);
    }

    public void tableLayoutPerson() {
        table.getColumns().clear();
        table.getColumns().add(pNameCol);
        table.getColumns().add(addressCol);
        table.getColumns().add(phoneCol);
        table.getColumns().add(emailCol);
        table.getColumns().add(pSectionCol);
    }
}