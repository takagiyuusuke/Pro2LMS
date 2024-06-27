
import DataBase.DataBase;
import java.util.*;

public class Student {
	public static void main(String[] args) {
		List<String> headers = new ArrayList<String>(Arrays.asList("id", "name", "grade"));
		DataBase db = new DataBase("students", headers);
		db.addItem("1,test,3");
		System.out.println(db.getItem(1, "test"));
		db.clear();
	}
}
