package DataBase;

import java.nio.charset.Charset;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class DataBase {
	final private String DIR_PATH = "database";
	private String fileName;
	protected List<String> headers;
	
	public DataBase(String entityName, List<String> headers) {
		Path dirPath = Paths.get(DIR_PATH);
		if (Files.notExists(dirPath)) {
			try {
				Files.createDirectory(dirPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.fileName = DIR_PATH + "/" + entityName + ".csv";
		this.headers = headers;
		Path filePath = Paths.get(this.fileName);
		if (Files.notExists(filePath)) {
			this.clear();
		}
	}
	
	public String getItem(int index, String value) {
		try {
			List<String> allItems = Files.readAllLines(Paths.get(this.fileName), Charset.defaultCharset());
			Iterator<String> itr = allItems.iterator();
			itr.next();
			for (Iterator<String> line = itr; line.hasNext();) {
				String item = line.next();
				String[] properties = item.split(",");
				if (properties[index].equals(value)) {
					return item;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addItem(String value) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName, true));
			writer.write(value);
			writer.newLine();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void clear() {
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(this.fileName), Charset.defaultCharset());
			String headers = "";
			for (int i=0; i < this.headers.size(); i++) {
				headers += this.headers.get(i);
				if (i != this.headers.size() - 1) {
					headers += ",";
				}
			}
			writer.write(headers);
			writer.newLine();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
