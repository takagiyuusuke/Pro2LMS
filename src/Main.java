import database.*;

import java.util.List;

import GUI.*;
import entities.*;

public class Main {
	public static void main(String[] args) {
		StudentDB studentDB = new StudentDB();
		TeacherDB teacherDB = new TeacherDB();
		CourseDB courseDB = new CourseDB();
		courseDB.setTeacherDB(teacherDB);
		
		studentDB.clear();
		teacherDB.clear();
		courseDB.clear();

		studentDB.createStudent(62210974, "seno koki");
		studentDB.createStudent(62210975, "takagi yusuke");
		studentDB.createStudent(62210976, "nakamoto koichi");

		teacherDB.createTeacher(11111111, "tanaka syozo");
		teacherDB.createTeacher(11111112, "takada shingo");

		courseDB.createCourse("Programing2nd", "A32", "Friday", 3, 11111112);

		Student koki = studentDB.getStudentById(62210974);
		System.out.println("name: "+koki.getName()+", id: "+koki.getId());
		
		Teacher tanaka = teacherDB.getTeacherById(11111111);
		System.out.println("name: "+tanaka.getName()+", id: "+tanaka.getId());
		
		List<Student> s = studentDB.getAllStudents();
		for (int i = 0; i < s.size(); i ++) {
			System.out.println(s.get(i).getName());
		}
		
		while(true) {
			GUIMain gui = new GUIMain(studentDB, teacherDB, courseDB);
			try {
				gui.waitForClose();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
