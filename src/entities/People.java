package entities;

import java.util.ArrayList;

abstract public class People {
	private int id;
	private String name;
	private ArrayList<Integer> courseIds;
	
	protected People(String item) {
		String[] properties = item.split(",");
		this.id = Integer.valueOf(properties[0]);
		this.name = properties[1];
		String courseIdsContent = properties[2].substring(1, properties[2].length()-1);
		String[] courseIdsStr = courseIdsContent.split("\\s+");
		if (courseIdsStr.length == 1 && courseIdsStr[0].equals("")) {
			courseIdsStr = new String[0]; 
		}
		this.courseIds = new ArrayList<Integer>();
		for (int i=0; i < courseIdsStr.length; i++) {
			courseIds.add(Integer.valueOf(courseIdsStr[i]));
		}
	}
	
	// split list item with " "
	public String convertToDBRawString() {
		String courseIdsStr = "[";
		for (int i=0; i<courseIds.size(); i++) {
			courseIdsStr += Integer.toString(courseIds.get(i));
			if (i != courseIds.size() - 1) {
				courseIdsStr += " ";
			}
		}
		courseIdsStr += "]";
		return id + "," + name + "," + courseIdsStr;
	}
	
	public int getId() {return this.id;}
	public String getName() {return this.name;}
	public ArrayList<Integer> getCourseIds() {return this.courseIds;}
	
	public void addCourseId (int newCourseId){
		this.courseIds.add(newCourseId);
	}
	public boolean removeCourseId (int removeCourseId) {
		return this.courseIds.remove(Integer.valueOf(removeCourseId));
	}
	
}
