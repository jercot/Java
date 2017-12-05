package controller;

import java.io.File;
import java.io.IOException;

import controller.FileCopy;

public class CopyRunnable implements Runnable {

	private File file, path;

	public CopyRunnable(File f, File p) {
		file = f;
		path = p;
	}

	public void run() {
		if(file!=null) {
			try {
				FileCopy.copyFile(file, path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}