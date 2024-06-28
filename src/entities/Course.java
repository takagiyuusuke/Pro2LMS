package entities;

public class Course {
	protected int id;
	protected String name;
	protected int roomId;
	protected String day;
	protected int period;
	protected int[] studentIds;
	protected int teacherId;
	
	protected Course(String item) {
		String[] properties = item.split(",");
		this.id = Integer.valueOf(properties[0]);
		this.name = properties[1];
		this.roomId = properties[2];
		this.day = properties[3];
		this.period = properties[4];
		this.teacherId = properties[5];
		String studentIdsContent = properties[6].substring(1, properties[6].length()-1);
		String[] studentIdsStr = studentIdsContent.split("\\s+");
		if (studentIdsStr.length == 1 && studentIdsStr[0].equals("")) {
			studentIdsStr = new String[0]; 
		}
		this.studentIds = new int[studentIdsStr.length];
		for (int i=0; i < studentIdsStr.length; i++) {
			studentIds[i] = Integer.valueOf(studentIdsStr[i]);
		}
	}
		
	// split list item with " "
	public String convertToDBRawString() {
		String studentIdsStr = "[";
		for (int i=0; i<studentIds.length; i++) {
			studentIdsStr += Integer.toString(studentIds[i]);
			if (i != studentIds.length - 1) {
				studentIdsStr += " ";
			}
		}
		studentIdsStr += "]";
		return id + "," + name + "," + studentIdsStr;
	}
}

