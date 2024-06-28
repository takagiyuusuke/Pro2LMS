package database;

import java.util.*;
import entities.Student;;

public class StudentDB extends DataBase {
	final private static List<String> HEADERS = new ArrayList<String>(Arrays.asList("id", "name", "courseIds"));
	final private static String ENTITY_NAME = "students";
	final private Map<String, Integer> HEADER_COL_INDICES =  new HashMap<>();
	
	public StudentDB() {
		super(ENTITY_NAME, HEADERS);
		HEADER_COL_INDICES.put("id", 0);
		HEADER_COL_INDICES.put("name", 1);
		HEADER_COL_INDICES.put("courseIds", 2);
	}
	
	public Student getStudentById(int id) {
		String item = super.getItem(HEADER_COL_INDICES.get("id"), Integer.toString(id));
		return new Student(item);
	}
	
	public Student createStudent(int id, String name) {
		String itemStr = id + "," + name + "," + "[]";
		if (super.addItem(itemStr)) {
			return new Student(itemStr);
		} else {
			return null;
		}
	}
}
