package database;

import java.util.*;
import entities.Course;

public class CourseDB extends DataBase {
	final private static List<String> HEADERS = new ArrayList<String>(Arrays.asList("id", "name", "roomName", "day", "period", "teacherId", "studentIds"));
	final private static String ENTITY_NAME = "courses";
	final private Map<String, Integer> HEADER_COL_INDICES =  new HashMap<>();
	private static int LATEST_ID = 0;
	
	public CourseDB() {
		super(ENTITY_NAME, HEADERS);
		HEADER_COL_INDICES.put("id", 0);
		HEADER_COL_INDICES.put("name", 1);
		HEADER_COL_INDICES.put("roomName", 2);
		HEADER_COL_INDICES.put("day", 3);
		HEADER_COL_INDICES.put("period", 4);
		HEADER_COL_INDICES.put("teacherId", 5);
		HEADER_COL_INDICES.put("studentIds", 6);
		
		String lastItem = this.getLastItem();
		if (!lastItem.startsWith("id")) {
			String idStr = lastItem.split(",")[0];
			LATEST_ID = Integer.valueOf(idStr);
		}
	}
	
	public Course getCourseById(int id) {
		String item = super.getItem(HEADER_COL_INDICES.get("id"), Integer.toString(id));
		return new Course(item);
	}
	
	public List<Course> getAllCourses() {
		List<String> allCourseStrings = super.getAllItems();
		List<Course> allCourses = new ArrayList<Course>();
		for (int i = 0; i < allCourseStrings.size(); i ++) {
			allCourses.add(new Course(allCourseStrings.get(i)));
		}
		return allCourses;
	}
	
	public Course createCourse(String name, String roomName, String day, int period, int teacherId) {
		String itemStr = (LATEST_ID + 1) + "," + name + "," + roomName + "," + day + "," + period + "," + teacherId + "," + "[]";
		if (super.addItem(itemStr)) {
			LATEST_ID++;
			return new Course(itemStr);
		} else {
			return null;
		}
	}
	
	public Course updateCourse(Course course) {
		if (super.updateItem(course.getId(), course.convertToDBRawString())) {
			return course;
		} else {
			return null;
		}
	}
}
