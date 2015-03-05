package net.trustie.webmagic.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
	public static String readFile(String path) {
		File file = new File(path);
		String rt = "";
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				rt += sc.nextLine();
				rt+=" ";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}
}
