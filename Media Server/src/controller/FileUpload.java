package controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import model.EncryptException;

public class FileUpload {

	private File f, encryptedFile;

	public FileUpload(File in) {
		f = in;
		encryptedFile = new File("Encrypted");
	}
	
	public void encrypt(String key) throws EncryptException {
		Encrypt.encrypt(key, f, encryptedFile);
	}
	
	public boolean done() {
		if(encryptedFile!=null)
			return true;
		return false;
	}
	
	public byte[] getFile() {
		InputStream input = null;
		try {
			input = new FileInputStream(encryptedFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];

		try {
			for (int length = 0; (length = input.read(buffer)) > 0;) {
			    output.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = output.toByteArray();
		return bytes;
	}
}