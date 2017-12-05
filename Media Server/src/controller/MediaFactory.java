package controller;

import java.io.File;

import model.*;

public class MediaFactory {

	public MediaInt getMedia(String mediaType, File f){
		if(mediaType == null){
			return null;
		}
		if(mediaType.equals("IMAGE")) {
			Image temp = new Image();
			temp.create(f);
			return temp;
		} 
		else if(mediaType.equals("MUSIC")) {
			Music temp = new Music();
			temp.create(f);
			return temp;
		}
		else if(mediaType.equals("VIDEO")) {
			Video temp = new Video();
			temp.create(f);
			return temp;
		}
		return null;
	}
}