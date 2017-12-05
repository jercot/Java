package controller;

import model.MediaInt;
import model.Observer;
import java.io.File;
import java.util.ArrayList;

public class MediaController {

	private ArrayList<MediaInt> mediaList;
	private MediaFactory factory = new MediaFactory();
	private MediaSubject subject;

	public void setFiles(File[] files) {
		mediaList = new ArrayList<>();
		subject = new MediaSubject();
		for(File f: files) {
			MediaInt temp = factory.getMedia(getType(f), f);
			if(temp!=null) {
				mediaList.add(temp);
				subject.attachObserver((Observer)temp);
			}
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

	public void setTime(long lastModified) {
		subject.setUpdate(lastModified);
	}
	
	public boolean checkUpdate(long currentModified) {
		if(subject.getUpdate()!=currentModified)
			return true;
		return false;
	}
}