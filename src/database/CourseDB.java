package database;

import java.util.*;
import entities.*;

public class CourseDB extends DataBase {
	final private static List<String> HEADERS = new ArrayList<String>(Arrays.asList("id", "name", "roomName", "day", "period", "teacherId", "studentIds"));
	final private static String ENTITY_NAME = "courses";
	final private Map<String, Integer> HEADER_COL_INDICES =  new HashMap<>();
	private static int LATEST_ID = 0;
	private TeacherDB teacherDB;
	private StudentDB studentDB;
	
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
	
	public void setTeacherDB(TeacherDB teacherDB) {
		this.teacherDB = teacherDB;
	}
	
	public void setStudentDB(StudentDB studentDB) {
		this.studentDB = studentDB;
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
		int newId = LATEST_ID + 1;
		String itemStr = newId + "," + name + "," + roomName + "," + day + "," + period + "," + teacherId + "," + "[]";
		if (super.addItem(itemStr)) {
			Teacher teacher = this.teacherDB.getTeacherById(teacherId);
			teacher.addCourseId(newId);
			this.teacherDB.updateTeacher(teacher);
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
	
	public void deleteCourse(Course course) {
		int removeId = course.getId();
		super.deleteItem(removeId);
		Teacher teacher = this.teacherDB.getTeacherById(course.getTeacherId());
		teacher.removeCourseId(course.getId());
		this.teacherDB.updateTeacher(teacher);
		ArrayList<Integer> studentIds = course.getStudentIds();
		for (int i = 0; i < studentIds.size(); i ++) {
			Student student = this.studentDB.getStudentById(studentIds.get(i));
			student.removeCourseId(removeId);
			this.studentDB.updateStudent(student);
		}
	}
	
}
