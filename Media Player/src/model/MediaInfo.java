package model;

import java.io.File;
import java.io.Serializable;
import java.sql.Date;

public class MediaInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	File store;
	String name, type, size;

	public void setName(File f) {
		if(f.getName().lastIndexOf('.')!=-1)
			name = f.getName().substring(0, store.getName().lastIndexOf('.'));
	}

	public String getName() {
		return name;
	}

	public void setType(File f) {
		int lastDot = f.getName().lastIndexOf('.');
		if(lastDot!=-1) {
			String temp = f.getName().substring(lastDot+1, f.getName().length());
			type =  temp.toLowerCase();
		}
		else type = "Folder";
	}

	public String getType() {
		return type;
	}

	public void setSize(File f) {
		size = f.length() + " bytes";
	}
	
	public String getSize() {
		return size;
	}

	public String getModified() {
		Date date = new Date(store.lastModified());
		String modified = ""+date;
		return modified;
	}
}