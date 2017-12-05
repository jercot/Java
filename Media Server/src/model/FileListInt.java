package model;

import java.rmi.Remote;
import java.util.ArrayList;

import model.MediaInt;

public interface FileListInt extends Remote {
	public ArrayList<MediaInt> getMedia();
	public void setList(ArrayList<MediaInt> m);
}