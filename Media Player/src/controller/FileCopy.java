package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {

	public static void copyFile(File in, File out) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(in);
			os = new FileOutputStream(out);
			byte[] buffer = new byte[1024];
			int bytes;
			while ((bytes = is.read(buffer)) > 0) {
				os.write(buffer, 0, bytes);
			}
		}
		finally {
			is.close();
			os.close();
		}
	}
}