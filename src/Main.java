import database.StudentDB;
import entities.Student;

public class Main {
	public static void main(String[] args) {
		StudentDB studentDB = new StudentDB();
		studentDB.clear();
		studentDB.createStudent(62210974, "seno koki");
		studentDB.createStudent(62210975, "takagi yusuke");
		studentDB.createStudent(62210976, "nakamoto koichi");
		
		Student koki = studentDB.getStudentById(62210974);
		System.out.println("name: "+koki.name+", id: "+koki.id);
	}
}
