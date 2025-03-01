package database;

import java.nio.charset.Charset;
import java.nio.file.*;
import java.io.*;
import java.util.*;

abstract class DataBase {
	final private String DIR_PATH = "data";
	private String fileName;
	private List<String> headers;
	
	protected DataBase(String entityName, List<String> headers) {
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
	
	private static void addLine(BufferedWriter w, String newLineVal) throws IOException{
		w.write(newLineVal);
		w.newLine();
	}
	
	protected String getItem(int col_index, String value) {
		try {
			List<String> allItems = Files.readAllLines(Paths.get(this.fileName), Charset.defaultCharset());
			Iterator<String> itr = allItems.iterator();
			itr.next();
			for (Iterator<String> line = itr; line.hasNext();) {
				String item = line.next();
				String[] properties = item.split(",");
				if (properties[col_index].equals(value)) {
					return item;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected List<String> getItems(int col_index, String value) {
		List<String> results = new ArrayList<String>();
		try {
			List<String> allItems = Files.readAllLines(Paths.get(this.fileName), Charset.defaultCharset());
			Iterator<String> itr = allItems.iterator();
			itr.next();
			for (Iterator<String> line = itr; line.hasNext();) {
				String item = line.next();
				String[] properties = item.split(",");
				if (properties[col_index].equals(value)) {
					results.add(item);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	protected List<String> getAllItems() {
		List<String> results = new ArrayList<String>();
		try {
			List<String> allItems = Files.readAllLines(Paths.get(this.fileName), Charset.defaultCharset());
			Iterator<String> itr = allItems.iterator();
			itr.next();
			for (Iterator<String> line = itr; line.hasNext();) {
				String item = line.next();
				results.add(item);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	protected String getLastItem() {
		try {
			List<String> allItems = Files.readAllLines(Paths.get(this.fileName), Charset.defaultCharset());
			return allItems.get(allItems.size() - 1);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected boolean addItem(String value) {
		try {
			// FileWriter(this.fileName, true) <- able to append lines.
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName, true));
			DataBase.addLine(writer, value);
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	protected boolean updateItem(int id, String newVal) {
		boolean isSuccess = false;
		try {
			List<String> allItems = Files.readAllLines(Paths.get(this.fileName), Charset.defaultCharset());
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(this.fileName), Charset.defaultCharset());
			Iterator<String> itr = allItems.iterator();
			writer.write(itr.next());
			writer.newLine();
			for (Iterator<String> line = itr; line.hasNext();) {
				String item = line.next();
				String[] properties = item.split(",");
				if (properties[0].equals(Integer.toString(id))) {
					DataBase.addLine(writer, newVal);
					isSuccess = true;
				} else {
					DataBase.addLine(writer, item);
				}
			}
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}
	
	public void deleteItem(int id) {
	    try {
	        List<String> allItems = Files.readAllLines(Paths.get(this.fileName), Charset.defaultCharset());
	        Iterator<String> itr = allItems.iterator();
	        itr.next();
	        while (itr.hasNext()) {
	            String item = itr.next();
	            String[] properties = item.split(",");
	            if (properties[0].equals(String.valueOf(id))) {
	                itr.remove();
	            }
	        }
	        Files.write(Paths.get(this.fileName), allItems, Charset.defaultCharset());
	    } catch(IOException e) {
	        e.printStackTrace();
	    }
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
			DataBase.addLine(writer, headers);
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
