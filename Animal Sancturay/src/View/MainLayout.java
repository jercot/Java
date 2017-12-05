package View;

import Controller.*;

import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.Scene;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class MainLayout
{
	private Tab tabOne = new Tab("Welcome"), tabTwo, tabThree, tabFour, tabFive, tabSix;
	private DataController data;

	public MainLayout(DataController d, String titleTwo, String titleThree, String titleFour, String titleFive, String titleSix) {
		data = d;
		tabTwo = new Tab(titleTwo);
		tabThree = new Tab(titleThree);
		tabFour = new Tab(titleFour);
		tabFive = new Tab(titleFive);
		tabSix = new Tab(titleSix);
		tabLayout();
	}

	public void tabLayout() {
		Stage window = new Stage();
		AnchorPane layout;
		TabPane tabs;
		MenuItem mOne, mTwo, mThree, mFour;
		MenuButton menuB;
		window.setTitle("Animal Sanctuary");

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		mOne = new MenuItem("Save To File");
		mTwo = new MenuItem("Load From File");
		mThree = new MenuItem("Save To Database");
		mFour = new MenuItem("Load From Database");

		tabs = new TabPane();
		tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabs.setTabMinWidth(60);
		tabs.getTabs().addAll(tabOne, tabTwo, tabThree, tabFour, tabFive, tabSix);

		mOne.setOnAction((event) -> {
			data.saveAnimalsFile();
		});
		mTwo.setOnAction((event) -> {
			data.loadAnimalsFile(); 
		});
		mThree.setOnAction((event) -> {
			data.saveAnimalsDatabase();
		});
		mFour.setOnAction((event) -> {
			data.loadAnimalsDatabase();
		});
		//         tabs.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
		//                 tabTwo.setContent(lostReset);
		//                 tabThree.setContent(foundReset);
		//                 tabFour.setContent(adoptReset);
		//             });

		menuB = new MenuButton();
		menuB.getItems().addAll(mOne, mTwo, mThree, mFour);
		ImageView menuIcon = new ImageView(new Image("file:Images/menu.png"));
		menuIcon.setFitWidth(19);
		menuIcon.setFitHeight(19);
		menuB.setGraphic(menuIcon);
		layout = new AnchorPane();
		Scene scene = new Scene(layout, 0, 0);
		layout.getChildren().add(tabs);
		layout.getChildren().add(menuB);
		AnchorPane.setLeftAnchor(tabs, 0.0);
		AnchorPane.setTopAnchor(tabs, 0.0);
		AnchorPane.setRightAnchor(tabs, 0.0);
		AnchorPane.setBottomAnchor(tabs, 0.0);
		AnchorPane.setRightAnchor(menuB, 0.0);
		firstTab();
		window.setScene(scene);        
		window.setX(primaryScreenBounds.getMinX());
		window.setY(primaryScreenBounds.getMinY());
		window.setWidth(primaryScreenBounds.getWidth());
		window.setHeight(primaryScreenBounds.getHeight());
		window.show();
	}

	public void firstTab() {
		AnchorPane tabLayout = new AnchorPane();
		ImageView imgView = new ImageView("file:Images/home.jpg");
		Label labelOne = new Label("Animal\nShelter");
		labelOne.setFont(new Font(64));
		tabLayout.getChildren().add(imgView);
		AnchorPane.setTopAnchor(imgView, 0.0);
		AnchorPane.setRightAnchor(imgView, 0.0);
		AnchorPane.setBottomAnchor(imgView, 0.0);
		AnchorPane.setLeftAnchor(imgView, 0.0);
		tabLayout.getChildren().add(labelOne);
		AnchorPane.setLeftAnchor(labelOne, 28.0);
		AnchorPane.setTopAnchor(labelOne, 52.0);
		tabOne.setContent(tabLayout);
	}

	public void secondTab(ScrollPane scroll) {
		tabTwo.setContent(scroll);
	}

	public void thirdTab(ScrollPane scroll) {
		tabThree.setContent(scroll);
	}

	public void fourthTab(ScrollPane scroll) {
		tabFour.setContent(scroll);
	}

	public void fifthTab(ScrollPane scroll) {
		tabFive.setContent(scroll);
	}

	public void sixthTab(ScrollPane scroll) {
		tabSix.setContent(scroll);
	}
}