package model;

import java.io.File;
import java.io.Serializable;

public class Video extends MediaInfo implements MediaInt, Observer, Serializable {
	
	private static final long serialVersionUID = 1L;
	String type, filePath, absPath;
	@Override
	public void create(File f) {
		store = f;
		setName(f);
		setType(f);
		setSize(f);
		filePath = f.getPath();
		absPath = f.getAbsolutePath();
		type = "Video";
	}

	public String getMedia() {
		return type;
	}

	public String getPath() {
		return absPath;
	}

	public File getFile() {
		return new File(filePath);
	}

	public void print() {
		System.out.print("Video Added");
	}
}