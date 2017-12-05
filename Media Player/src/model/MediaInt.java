package model;

import java.io.File;
import java.rmi.Remote;

public interface MediaInt extends Remote{
	
	public void create(File f);

	public String getMedia();
	
	public String getPath();

	public File getFile();
}