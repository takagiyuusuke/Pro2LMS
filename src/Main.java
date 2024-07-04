import database.*;
import GUI.*;
import entities.*;

public class Main {
	public static void main(String[] args) {
		StudentDB studentDB = new StudentDB();
		TeacherDB teacherDB = new TeacherDB();
		CourseDB courseDB = new CourseDB();
		studentDB.clear();
		teacherDB.clear();
		courseDB.clear();
		studentDB.createStudent(62210974, "seno koki");
		studentDB.createStudent(62210975, "takagi yusuke");
		studentDB.createStudent(62210976, "nakamoto koichi");
		courseDB.createCourse(100, "Programing2nd", "A32", "Friday", 3, 111112);
		
		teacherDB.createTeacher(111111, "tanaka syozo");
		teacherDB.createTeacher(111112, "takada shingo");
		
		Student koki = studentDB.getStudentById(62210974);
		System.out.println("name: "+koki.getName()+", id: "+koki.getId());
		
		koki.setName("koki dayo");
		studentDB.updateStudent(koki);
		Student newKoki = studentDB.getStudentById(62210974);
		System.out.println("name: "+newKoki.getName()+", id: "+newKoki.getId());
		
		Teacher tanaka = teacherDB.getTeacherById(111111);
		System.out.println("name: "+tanaka.getName()+", id: "+tanaka.getId());
		
		Course pro2 = courseDB.getCourseById(100);
		System.out.println("name: "+pro2.getName()+", id: "+pro2.getId()+", roomId: "+pro2.getRoomId()+", day: "+pro2.getDay()+", period: "+pro2.getPeriod()+", Teacherid: "+teacherDB.getTeacherById(pro2.getTeacherId()).getName());
		
		while(true) {
			AddStudentGUI asg = new AddStudentGUI(studentDB);
			try {
				asg.waitForClose();
			} catch (InterruptedException e) {
				System.out.println("something wrong!!");
			} 
		
			AddTeacherGUI atg = new AddTeacherGUI(teacherDB);
			try {
				atg.waitForClose();
			} catch (InterruptedException e) {
				System.out.println("something wrong!!");
			}
			
			ListDisplayGUI ldg = new ListDisplayGUI(studentDB);
			try {
				ldg.waitForClose();
			} catch (InterruptedException e) {
				System.out.println("something wrong!!");
			}
		}
	}
}
