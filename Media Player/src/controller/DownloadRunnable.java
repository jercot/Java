package controller;

import java.io.File;
import java.io.IOException;

import model.EncryptException;

public class DownloadRunnable implements Runnable{
	private File file, path;
	
	public DownloadRunnable(File f, File p) {
		file = f;
		path = p;
	}

	@Override
	public void run() {
		File temp = new File("Out");
		try {
			Decrypt.decrypt("I am so awesome!", file, temp);
			FileCopy.copyFile(temp, path);
		} catch (EncryptException | IOException e) {
			e.printStackTrace();
		}
	}
}