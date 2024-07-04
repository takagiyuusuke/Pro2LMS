package entities;

import java.util.ArrayList;

public class Course {
	private int id;
	private String name;
	private String roomName;
	private String day;
	private int period;
	private int teacherId;
	private ArrayList<Integer> studentIds;
	
	public Course(String item) {
		String[] properties = item.split(",");
		this.id = Integer.valueOf(properties[0]);
		this.name = properties[1];
		this.roomName = properties[2];
		this.day = properties[3];
		this.period = Integer.valueOf(properties[4]);
		this.teacherId = Integer.valueOf(properties[5]);
		String studentIdsContent = properties[6].substring(1, properties[6].length()-1);
		String[] studentIdsStr = studentIdsContent.split("\\s+");
		if (studentIdsStr.length == 1 && studentIdsStr[0].equals("")) {
			studentIdsStr = new String[0]; 
		}
		this.studentIds = new ArrayList<Integer>();
		for (int i=0; i < studentIdsStr.length; i++) {
			studentIds.add(Integer.valueOf(studentIdsStr[i]));
		}
	}
		
	// split list item with " "
	public String convertToDBRawString() {
		String studentIdsStr = "[";
		for (int i=0; i<studentIds.size(); i++) {
			studentIdsStr += Integer.toString(studentIds.get(i));
			if (i != studentIds.size() - 1) {
				studentIdsStr += " ";
			}
		}
		studentIdsStr += "]";
		return id + "," + name + "," + roomName + "," + day + "," + period + "," + teacherId + "," + studentIdsStr;
	}
	
	public int getId() {return this.id;}

	public String getName() {return this.name;}
	public void setName(String newName) {this.name = newName;}

	public String getRoomId() {return this.roomName;}
	public void setRoomId(String newRoomName) {this.roomName = newRoomName;}

	public String getDay() {return this.day;}
	public void setDay(String newDay) {this.day = newDay;}

	public int getPeriod() {return this.period;}
	public void setPeriod(int newPeriod) {this.period = newPeriod;}

	public int getTeacherId() {return this.teacherId;}
	public void setTeacherId(int newTeacherId) {this.teacherId = newTeacherId;}

	public ArrayList<Integer> getStudentIds() {return this.studentIds;}
	public void addStudentId (int newStudentId){
		this.studentIds.add(newStudentId);
	}
	public boolean removeStudentId (int removeStudentId) {
		return this.studentIds.remove(Integer.valueOf(removeStudentId));
	}
	

	
	public void changeTeacherId(int newTeacherId) {this.teacherId = newTeacherId;}
}

