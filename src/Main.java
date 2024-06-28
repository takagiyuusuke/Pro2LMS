import database.*;
import entities.*;

public class Main {
	public static void main(String[] args) {
		StudentDB studentDB = new StudentDB();
		TeacherDB teacherDB = new TeacherDB();
		studentDB.clear();
		teacherDB.clear();
		studentDB.createStudent(62210974, "seno koki");
		studentDB.createStudent(62210975, "takagi yusuke");
		studentDB.createStudent(62210976, "nakamoto koichi");
		
		teacherDB.createTeacher(111111, "tanaka syozo");
		
		Student koki = studentDB.getStudentById(62210974);
		System.out.println("name: "+koki.name+", id: "+koki.id);
		
		Teacher tanaka = teacherDB.getTeacherById(111111);
		System.out.println("name: "+tanaka.name+", id: "+tanaka.id);
	}
}
