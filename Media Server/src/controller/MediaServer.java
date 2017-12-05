package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.EncryptException;
import model.MediaInt;
import model.MediaServerInt;
import model.Observer;

public class MediaServer extends UnicastRemoteObject implements MediaServerInt {

	private static final long serialVersionUID = -3136615318119636384L;
	private ArrayList<MediaInt> mediaList;
	private MediaFactory factory = new MediaFactory();
	private MediaSubject subject;
	private FileUpload upload;
	private String path, ext;
	private File file;

	public MediaServer() throws RemoteException {
		super();
	}

	public void startServer() {
		try {
			LocateRegistry.createRegistry(1099);
			//String temp = InetAddress.getLocalHost().toString();
			//Naming.rebind("rmi://" + temp + "/myabc", b);
			Naming.rebind("localhost", this);
			System.out.println("[System] File Server is ready.");
		}catch (Exception e) {
			System.out.println("File Server failed: " + e);
		}
		path = new File("").getAbsolutePath() + "\\ServerMedia";
		if(setPath(path))
			setFiles();
	}

	public boolean setPath(String p) {
		if(p!=null) {
			file = new File(p);
			return true;
		}
		return false;
	}
	public void setFiles() {
		mediaList = new ArrayList<>();
		subject = new MediaSubject();
		for(File f: file.listFiles()) {
			MediaInt temp = factory.getMedia(getType(f), f);
			if(temp!=null) {
				mediaList.add(temp);
				subject.attachObserver((Observer)temp);
			}
		}
		setTime();
		startObserver();
	}

	public void startObserver() {
		try {
			WatchService watch = FileSystems.getDefault().newWatchService();
			Path folder = file.toPath();
			folder.register(watch, 
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);
			while(true) {
				WatchKey key;
				key = watch.take();
				Thread.sleep(2000);
				setFiles();
				key.cancel();
				watch.close();
				break;
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getType(File f) {
		String type = null;
		int lastDot = f.getName().lastIndexOf('.');
		if(lastDot!=-1) {
			type = f.getName().substring(lastDot+1, f.getName().length());
		}
		if(type!=null) {
			type.toLowerCase();
			if(type.equals("png") || type.equals("jpg") || type.equals("gif"))
				type = "IMAGE";
			else if(type.equals("mp3"))
				type = "MUSIC";
			else if(type.equals("mp4") || type.equals("flv"))
				type = "VIDEO";
		}
		return type;
	}

	public ArrayList<MediaInt> getList() {
		return mediaList;
	}

	public void setTime() {
		File temp = new File(path);
		subject.setUpdate(temp.lastModified());
	}

	public boolean checkUpdate() {
		if(subject.getUpdate()!=file.lastModified())
			return true;
		return false;
	}

	@Override
	public void setDownload(String f) throws RemoteException {
		ext = new File(f).getName().substring(new File(f).getName().lastIndexOf('.')+1, new File(f).getName().length());
		upload = new FileUpload(new File(f));
		try {
			upload.encrypt("I am so awesome!");
		} catch (EncryptException e) {
			e.printStackTrace();
		}
	}

	public boolean done() throws RemoteException {
		if(upload !=null)
			if(upload.done())
				return true;
		return false;
	}
	
	public byte[] getFile() throws RemoteException {
		return upload.getFile();
	}

	@Override
	public String getExt() throws RemoteException {
		return ext;
	}
}