package entities;

// id,name,courseIds
public class Student {
	public int id;
	public String name;
	public int[] courseIds;
	
	public Student(String item) {
		String[] properties = item.split(",");
		this.id = Integer.valueOf(properties[0]);
		this.name = properties[1];
		String courseIdsContent = properties[2].substring(1, properties[2].length()-1);
		String[] courseIdsStr = courseIdsContent.split("\\s+");
		if (courseIdsStr.length == 1 && courseIdsStr[0].equals("")) {
			courseIdsStr = new String[0]; 
		}
		this.courseIds = new int[courseIdsStr.length];
		for (int i=0; i < courseIdsStr.length; i++) {
			courseIds[i] = Integer.valueOf(courseIdsStr[i]);
		}
	}
	
	public String convertToString() {
		String courseIdsStr = "[";
		for (int i=0; i<courseIds.length; i++) {
			courseIdsStr += Integer.toString(courseIds[i]);
			if (i != courseIds.length - 1) {
				courseIdsStr += " ";
			}
		}
		courseIdsStr += "]";
		return id + "," + name + "," + courseIdsStr;
	}
}
