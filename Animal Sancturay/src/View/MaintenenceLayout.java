package View;

import Controller.*;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class MaintenenceLayout
{
    private AnchorPane anchor;
    private ScrollPane scroll;
    private Label type, typeBreed, breed, location;
    private TextField typeInput, breedInput, locationInput;
    private ComboBox<String> breedType;
    private ListView<String> typeList, breedList, locationList;
    private Button addT, removeT, addB, removeB, addL, removeL;

    public MaintenenceLayout() {
        anchor = new AnchorPane();
        scroll = new ScrollPane();
    }

    public void setup(String t, String aType, String rType, String tB, String b, String aBreed, String rBreed, String l, String aLoc, String rLoc) {
        type = labelMaker(t, 20, 40, 60);
        typeInput = fieldMaker(40, 90);
        addT = buttonMaker(aType, 40, 120);
        typeList = new ListView<>();
        typeList.setLayoutX(40);
        typeList.setLayoutY(160);
        typeList.setPrefWidth(149);
        removeT = buttonMaker(rType, 40, 570);
        typeBreed = labelMaker(tB, 20, 275, 60);
        breedType = comboMaker(275, 90, 149);
        breed = labelMaker(b, 20, 275, 120);
        breedInput = fieldMaker(275, 150);
        addB = buttonMaker(aBreed, 275, 180);
        breedList = listMaker(275, 210, 149);
        removeB = buttonMaker(rBreed, 275, 620);
        location = labelMaker(l, 20, 510, 60);
        locationInput = fieldMaker(510, 90);
        locationInput.setPrefWidth(300);
        addL = buttonMaker(aLoc, 510, 120);
        locationList = listMaker(510, 160, 300);
        removeL = buttonMaker(rLoc, 510, 570);
        anchor.getChildren().addAll(type, typeInput, addT, typeList, removeT ,typeBreed, breed, breedType, breedInput, 
            addB, breedList, removeB, location, locationInput, addL, locationList, removeL);
    }

    public void typeFill(DataController d) {
        typeList.getItems().clear();
        breedType.getItems().clear();

        for(String t: d.getType()) {
            typeList.getItems().add(t);
            breedType.getItems().add(t);
        }
    }

    public void locationFill(DataController d) {
        locationList.getItems().clear();
        for (String l: d.getLocation())
            locationList.getItems().add(l);
    }

    public void pageUpdate(DataController d) {
        addT.setOnAction((event) -> {
                getType();
                if(textCheck(typeInput.getText())) {
                    d.addType(typeInput.getText());
                    typeInput.clear();
                    typeFill(d);
                }
            });

        removeT.setOnAction((event) -> {
                if(choiceCheck(typeList.getSelectionModel().getSelectedItem())) {
                    d.removeType(typeList.getSelectionModel().getSelectedItem());
                    typeInput.clear();
                    breedType.setValue(null);
                    typeFill(d);
                }
            });

        addB.setOnAction((event) -> {
                getBreed();
                if(choiceCheck(breedType.getValue()) && textCheck(breedInput.getText())) {
                    d.addBreed(breedType.getValue(), breedInput.getText());
                    breedInput.clear();
                    breedType.setValue(null);
                }
            });

        removeB.setOnAction((event) -> {
                if(choiceCheck(breedType.getValue()) && choiceCheck(breedList.getSelectionModel().getSelectedItem())) {
                    d.removeBreed(breedType.getValue(), breedList.getSelectionModel().getSelectedItem());
                    breedInput.clear();
                    breedType.setValue(null);
                }
            });

        addL.setOnAction((event) -> {
                getLocation();
                if(textCheck(locationInput.getText())) {
                    d.addLocation(locationInput.getText());
                    locationInput.clear();
                    locationFill(d);
                }
            });

        removeL.setOnAction((event) -> {
                if(choiceCheck(locationList.getSelectionModel().getSelectedItem())) {
                    d.removeLocation(locationList.getSelectionModel().getSelectedItem());
                    locationFill(d);
                }
            });

        breedType.setOnAction((event) -> {
                breedList.getItems().clear();
                if(breedType.getValue()!=null) {
                    for(String t: d.getBreed(breedType.getValue())) {
                        breedList.getItems().add(t);
                    }
                }
            });
    }

    public ScrollPane getScroll() {
        scroll.setContent(anchor);
        return scroll;
    }

    public void getType() {
        labelColor(type, textCheck(typeInput.getText()));
    }

    public void getBreed() {
        labelColor(typeBreed, textCheck(breedType.getValue()));
        labelColor(breed, textCheck(breedInput.getText()));
    }

    public void getLocation() {
        labelColor(typeBreed, textCheck(locationInput.getText()));
    }

    public void labelColor(Label l, boolean b) {
        if(b)
            l.setTextFill(Color.BLACK);
        else
            l.setTextFill(Color.RED);
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

    public Button buttonMaker(String s, int x, int y) {
        Button b = new Button(s);
        b.setLayoutX(x);
        b.setLayoutY(y);
        return b;
    }

    public ComboBox<String> comboMaker(int x, int y, int w) {
        ComboBox<String> c = new ComboBox<>();
        c.setLayoutX(x);
        c.setLayoutY(y);
        c.setPrefWidth(w);
        return c;
    }

    public ListView<String> listMaker(int x, int y, int w) {
        ListView<String> l = new ListView<>();
        l.setLayoutX(x);
        l.setLayoutY(y);
        l.setPrefWidth(w);
        return l;
    }
}