import database.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import GUI.*;
import entities.*;

public class Main {
	private void initializeDB() {
		StudentDB studentDB = new StudentDB();
		TeacherDB teacherDB = new TeacherDB();
		CourseDB courseDB = new CourseDB();
		courseDB.setTeacherDB(teacherDB);
		
		studentDB.clear();
		teacherDB.clear();
		courseDB.clear();
		
		teacherDB.createTeacher(11111111, "amano hideharu");
		teacherDB.createTeacher(11111112, "takada shingo");
		teacherDB.createTeacher(11111113, "satoh takahiko");
		teacherDB.createTeacher(11111114, "hosoda masamichi");
		
		studentDB.createStudent(62210974, "seno koki");
		studentDB.createStudent(62210975, "takagi yusuke");
		studentDB.createStudent(62210976, "nakamoto koichi");
		
		Course pro2 = courseDB.createCourse("Programing2nd", "A32", "Friday", 3, 11111112);
		Course cA = courseDB.createCourse("ComputerArchitecture", "XXX", "Friday", 5, 11111111);
		Course dm = courseDB.createCourse("DataModeling", "WorkStation", "Friday", 5, 11111114);
		Course algo = courseDB.createCourse("Algorithm", "ComputerRoom", "Tuesday", 2, 11111113);
		Course hotToPro = courseDB.createCourse("HowToProgramming", "ABC123", "Wednesday", 4, 11111112);
		
		Student s = studentDB.getStudentById(62210974);
		s.addCourseId(pro2.getId());
		s.addCourseId(dm.getId());
		studentDB.updateStudent(s);
		
		s = studentDB.getStudentById(62210975);
		s.addCourseId(pro2.getId());
		s.addCourseId(algo.getId());
		studentDB.updateStudent(s);

		s = studentDB.getStudentById(62210976);
		s.addCourseId(pro2.getId());
		studentDB.updateStudent(s);
		
		pro2.addStudentId(62210974);
		pro2.addStudentId(62210975);
		pro2.addStudentId(62210976);
		courseDB.updateCourse(pro2);
		
		dm.addStudentId(62210974);
		courseDB.updateCourse(dm);
		
		algo.addStudentId(62210975);
		courseDB.updateCourse(algo);
		
	}
	public static void main(String[] args) {
		String DIR_PATH = "data";
		Path dirPath = Paths.get(DIR_PATH);
		Main app = new Main();

		if (Files.notExists(dirPath)) {
			app.initializeDB();
		}
		StudentDB studentDB = new StudentDB();
		TeacherDB teacherDB = new TeacherDB();
		CourseDB courseDB = new CourseDB();
		courseDB.setTeacherDB(teacherDB);
		courseDB.setStudentDB(studentDB);

		
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
