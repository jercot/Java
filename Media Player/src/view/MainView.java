package view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

import controller.MediaController;
import controller.CopyRunnable;
import controller.DownloadRunnable;
import controller.MediaRunnable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.MediaInt;
import model.MediaServerInt;

public class MainView {

	private GridPane pane, rightPane, displayPane;
	private HBox topButtons;
	private TableView<MediaInt> table;
	private MediaController media;
	private MediaServerInt mediaServerInt;
	private String path, strName = "File Name", strType = "File Type", strSize = "File Size", strModified = "Last Modified", strMedia = "Media Type",
			playView = "Play/View", stopClose = "Stop/Close", copy = "Copy", download = "Download", pause = "Pause", directory = "Change Directory", server = "Connect To Server";
	private Button executeButton, copyButton, pauseButton, directoryButton, serverButton;
	private Double paneGap = 12.0;
	private Boolean checked = true, connected = false;
	private ObservableList<MediaInt> tableList;
	private ImageView image;
	private MediaView videoDisplay;
	private MediaPlayer video;
	private Media mediaPlay;
	private Label time;
	private MediaRunnable mRunnable;
	private CopyRunnable cRunnable;
	private DownloadRunnable dRunnable;
	private Thread t1, oThread, serverThread;
	private ExecutorService threadExecutor;

	public MainView() {
		pane = new GridPane();
		rightPane = new GridPane();
		displayPane = new GridPane();
		topButtons = new HBox(25);
	}

	public void startView() {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		Stage window = new Stage();
		Scene scene = new Scene(pane,0, 0);
		pane.setHgap(paneGap);
		pane.setVgap(paneGap);
		rightPane.setHgap(paneGap);
		rightPane.setVgap(paneGap);

		media = new MediaController();
		path = new File("").getAbsolutePath() + "\\Media";
		tableList = FXCollections.observableArrayList();
		table = new TableView<MediaInt>(tableList);

		TableColumn<MediaInt, String> name = new TableColumn<>(strName);
		TableColumn<MediaInt, String> type = new TableColumn<>(strType);
		TableColumn<MediaInt, String> size = new TableColumn<>(strSize);
		TableColumn<MediaInt, String> modified = new TableColumn<>(strModified);
		TableColumn<MediaInt, String> mType = new TableColumn<>(strMedia);
		name.setCellValueFactory(new PropertyValueFactory<MediaInt, String>("Name"));
		type.setCellValueFactory(new PropertyValueFactory<MediaInt, String>("Type"));
		size.setCellValueFactory(new PropertyValueFactory<MediaInt, String>("Size"));
		modified.setCellValueFactory(new PropertyValueFactory<MediaInt, String>("Modified"));
		mType.setCellValueFactory(new PropertyValueFactory<MediaInt, String>("Media"));

		table.getColumns().add(name);
		table.getColumns().add(type);
		table.getColumns().add(size);
		table.getColumns().add(modified);
		table.getColumns().add(mType);
		table.setPrefWidth(550);
		table.setPrefHeight(600);

		pane.add(table, 2, 3);
		pane.add(rightPane, 4, 3);
		directoryButton = new Button(directory);
		serverButton = new Button(server);
		topButtons.getChildren().addAll(directoryButton, serverButton);
		pane.add(topButtons, 2, 2);
		executeButton = new Button(playView);
		pauseButton = new Button(pause);
		pauseButton.setDisable(true);
		pauseButton.setPrefWidth(70);
		copyButton = new Button(copy);
		rightPane.add(executeButton, 0, 0);
		rightPane.add(pauseButton, 1, 0);
		rightPane.add(copyButton, 2, 0);
		rightPane.getColumnConstraints().add(new ColumnConstraints(80));
		rightPane.getColumnConstraints().add(new ColumnConstraints(80));
		rightPane.add(displayPane, 0, 1);
		image = new ImageView();
		time = new Label();

		populateTable();
		directoryButton.setOnAction(e-> changeDirectory());
		serverButton.setOnAction(e-> server());
		executeButton.setOnAction(e-> playViewPress(table.getSelectionModel().getSelectedItem()));
		copyButton.setOnAction(e-> {
			if(!connected)
				copyEvent(table.getSelectionModel().getSelectedItem());
			else
				downloadEvent(table.getSelectionModel().getSelectedItem());
		});

		threadExecutor = Executors.newCachedThreadPool();

		window.setScene(scene);        
		window.setX(primaryScreenBounds.getMinX());
		window.setY(primaryScreenBounds.getMinY());
		window.setWidth(primaryScreenBounds.getWidth());
		window.setHeight(primaryScreenBounds.getHeight());
		window.show();
	}

	public void populateTable() {
		File dir = new File(path);
		media.setFiles(dir.listFiles());
		media.setTime(dir.lastModified());
		serverButton.setDisable(false);
		updateList();
		startObserver();
	}

	public void server() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				boolean run = true;
				while(run) {
					if(connect()) {
						Platform.runLater(new Runnable() {
							@Override 
							public void run() {
								updateListServer();
								serverButton.setDisable(true);
								oThread.interrupt();
								connected = true;
								copyButton.setText(download);
								executeButton.setDisable(true);
							}
						});
						run = false;
					}
					try {
						Thread.sleep(20);
					}
					catch (InterruptedException ie) {
						run = false;
					}
				}
				return null;
			}
		};
		serverThread = new Thread(task, "ObserverThread");
		serverThread.setDaemon(true);
		serverThread.start();
	}

	public boolean connect() {
		try{
			Remote reObject = Naming.lookup("localhost");
			if(reObject instanceof MediaServerInt) {
				mediaServerInt = (MediaServerInt)reObject;
				System.out.println("Server Connection Passed");
				return true;
			}
			else
				System.out.println("File Type Mismatch");
		}
		catch(Exception e){
			System.out.println("Server Connection Failed");
		}
		serverThread.interrupt();
		return false;		  
	}

	public void updateList() {
		ArrayList<MediaInt> temp = media.getList();
		table(temp);
	}

	public void updateListServer() {
		try {
			ArrayList<MediaInt> temp = mediaServerInt.getList();
			table(temp);
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
	}

	public void table(ArrayList<MediaInt> media) {
		tableList.clear();
		for(MediaInt m: media)
			tableList.add(m);
	}

	public void startObserver() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				boolean run = true;
				File dir = new File(path);
				while(run) {
					if(media.checkUpdate(dir.lastModified())) {
						Platform.runLater(new Runnable() {
							@Override 
							public void run() {
								populateTable();
							}
						});
						run = false;
					}
					try {
						Thread.sleep(200);
					}
					catch (InterruptedException ie) {
						run = false;
					}
				}
				return null;
			}
		};
		oThread = new Thread(task, "ObserverThread");
		oThread.setDaemon(true);
		oThread.start();
	}

	public void changeDirectory() {
		DirectoryChooser chooser = new DirectoryChooser();
		File newDirectory = chooser.showDialog(null);
		if(path!=null&&newDirectory!=null) {
			oThread.interrupt();
			path = newDirectory.getPath();
			populateTable();
			copyButton.setText(copy);
			executeButton.setDisable(false);
		}
	}

	public void stopViewPlay() {
		executeButton.setText(playView);
		checked = true;
		displayPane.getChildren().removeAll(image, videoDisplay, time);
		image = new ImageView();
		time.setText("0.00/0.00");
		if(mRunnable!=null)
			mRunnable.stop();
	}

	public void playViewPress(MediaInt selected) {
		if(selected!=null) {
			if(checked) {
				executeButton.setText(stopClose);
				checked = false;
				if(selected.getMedia().equals("Image"))
					displayImage(selected);
				else if(selected.getMedia().equals("Music"))
					displayVideo(selected);
				else if(selected.getMedia().equals("Video"))
					displayVideo(selected);
			}
			else
				stopViewPlay();
		}
		else
			stopViewPlay();
	}

	public void copyEvent(MediaInt selected) {
		if(selected!=null) { 
			File newFile;
			FileChooser fileC = new FileChooser();
			fileC.setTitle("Copy To Location");
			String fileType = selected.getFile().getName().substring(selected.getFile().getName().lastIndexOf('.')+1, selected.getFile().getName().length());
			fileC.getExtensionFilters().add(new ExtensionFilter(fileType.toUpperCase() + " files", "*." + fileType.toLowerCase()));
			newFile = fileC.showSaveDialog(null);
			if(newFile!=null) {
				cRunnable = new CopyRunnable(selected.getFile(), newFile);
				threadExecutor.execute(cRunnable);
			}
		}
	}

	public void downloadEvent(MediaInt selected) {
		try {
			if(selected!=null)
				mediaServerInt.setDownload(selected.getPath());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				boolean run = true;
				while(run) {
					if(mediaServerInt.done()) {
						Platform.runLater(new Runnable() {
							@Override 
							public void run() {
								download();
							}
						});
						run = false;
					}
					try {
						Thread.sleep(200);
					}
					catch (InterruptedException ie) {
						run = false;
					}
				}
				return null;
			}
		};
		Thread downloadThread = new Thread(task, "DownloadThread");
		downloadThread.setDaemon(true);
		downloadThread.start();
	}

	public void download() {
		File newFile;
		File temp = null;
		try {
			FileOutputStream fos = new FileOutputStream("encrytpedFile");
			fos.write(mediaServerInt.getFile());
			fos.close();
			temp = new File("encrytpedFile");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(temp!=null) {
			FileChooser fileC = new FileChooser();
			fileC.setTitle("Copy To Location");
			//String fileType = temp.getName().substring(temp.getName().lastIndexOf('.')+1, temp.getName().length());
			String fileType = null;
			try {
				fileType = mediaServerInt.getExt();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			fileC.getExtensionFilters().add(new ExtensionFilter(fileType.toUpperCase() + " files", "*." + fileType.toLowerCase()));
			newFile = fileC.showSaveDialog(null);
			if(newFile!=null) {
				dRunnable = new DownloadRunnable(temp, newFile);
				threadExecutor.execute(dRunnable);
			}
		}
	}

	public void displayImage(MediaInt selected) {
		File newFile = selected.getFile();
		String imagePath = newFile.getPath();
		URL temp = null;
		try {
			temp = new File(imagePath).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(temp!=null) {
			image.setImage(new Image(temp.toString()));
			image.setFitHeight(400);
			image.setPreserveRatio(true);
			displayPane.add(image, 0, 0);
		}
	}

	public void displayVideo(MediaInt selected) {
		File newFile = selected.getFile();
		String mediaPath = newFile.getPath();
		URL temp = null;
		try {
			temp = new File(mediaPath).toURI().toURL();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		mediaPlay = new Media(temp.toString());
		video = new MediaPlayer(mediaPlay);
		videoDisplay = new MediaView(video);
		mRunnable = new MediaRunnable(video, pauseButton, time);
		t1 = new Thread(mRunnable, "MediaPlayerThread");
		t1.start();
		pauseButton.setDisable(false);
		displayPane.add(videoDisplay, 0, 0);
		displayPane.add(time, 0, 1);
	}
}