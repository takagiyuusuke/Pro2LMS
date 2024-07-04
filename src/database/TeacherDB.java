package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Teacher;

public class TeacherDB extends DataBase {
	final private static List<String> HEADERS = new ArrayList<String>(Arrays.asList("id", "name", "courseIds"));
	final private static String ENTITY_NAME = "teachers";
	final private Map<String, Integer> HEADER_COL_INDICES =  new HashMap<>();
	
	public TeacherDB() {
		super(ENTITY_NAME, HEADERS);
		HEADER_COL_INDICES.put("id", 0);
		HEADER_COL_INDICES.put("name", 1);
		HEADER_COL_INDICES.put("courseIds", 2);
	}
	
	public Teacher getTeacherById(int id) {
		String item = super.getItem(HEADER_COL_INDICES.get("id"), Integer.toString(id));
		return new Teacher(item);
	}
	
	public Teacher createTeacher(int id, String name) {
		String itemStr = id + "," + name + "," + "[]";
		if (super.addItem(itemStr)) {
			return new Teacher(itemStr);
		} else {
			return null;
		}
	}
	
	public Teacher updateTeacher(Teacher teacher) {
		if (super.updateItem(teacher.getId(), teacher.convertToDBRawString())) {
			return teacher;
		} else {
			return null;
		}
	}
}
