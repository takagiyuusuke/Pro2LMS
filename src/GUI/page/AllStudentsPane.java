package GUI.page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import database.CourseDB;
import database.StudentDB;
import entities.Course;
import entities.Student;
import entities.Teacher;
import GUI.components.NameField;
import GUI.components.PeopleSelect;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class AllStudentsPane extends JPanel {
	private StudentDB studentDB;
	private CourseDB courseDB;
	private String[] columnNames = {"Student ID", "Name"};
	private List<Student> students;
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0);

	public AllStudentsPane(StudentDB studentDB, CourseDB courseDB) {
		super();
		this.studentDB = studentDB;
		this.courseDB = courseDB;
		
		this.loadStudents();
		
		JTable table = new JTable(this.model) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                	AllStudentsPane.this.showDetailFrame(students.get(row), e.getLocationOnScreen());
                }
            }
        });
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void loadStudents() {
		this.students = this.studentDB.getAllStudents();
		this.model.setRowCount(0);
		for (Student student : this.students) {
			Object[] row = {student.getId(), student.getName()};
			this.model.addRow(row);
		}
	}
	
	private void showDetailFrame(Student student, Point location) {
        JFrame detailFrame = new JFrame("Information about " + student.getName());
        JLabel label = new JLabel("Name: " + student.getName());
        JLabel label2 = new JLabel("Student ID: " + student.getId());
        JLabel label3 = new JLabel("All Courses:");
        JPanel pane = new JPanel();
        List<Integer> allCourseIds = student.getCourseIds();
        List<String> stringList = new ArrayList<String>();
        for (Integer id: allCourseIds) {
        	stringList.add(this.courseDB.getCourseById(id).getName());
        }
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String s : stringList) listModel.addElement(s);
		JList<String> jlist = new JList<>(listModel);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton editButton = new JButton("Edit Name");
        
        ActionListener editListener = new ActionListener() {
        	private String name;
        	public void actionPerformed(ActionEvent e) {
        		NameField nameField = new NameField("New Name");
        		JFrame editFrame = new JFrame("Editing " + student.getName());
        		editFrame.setSize(300,150);
        		JPanel pane = new JPanel();
        		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        		pane.add(nameField);
        		JButton cancelButton = new JButton("cancel");
        		JButton okButton = new JButton("OK");
        		JPanel buttonPane = new JPanel();
        		buttonPane.setLayout(new GridLayout(1,0));
        		buttonPane.add(cancelButton);
        		buttonPane.add(okButton);
        		pane.add(buttonPane);
        		editFrame.add(pane);
        		editFrame.setLocation(location);
        		okButton.setEnabled(false);
        		nameField.setOnTextChanged((String s) -> {
                    this.name = s;
                    if (nameField.isOk()) {
                        okButton.setEnabled(true);
                    } else {
                        okButton.setEnabled(false);
                    }
                });
        		ActionListener okListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				student.setName(name);
        				label.setText("Name: " + student.getName());
        				AllStudentsPane.this.studentDB.updateStudent(student);
        				AllStudentsPane.this.loadStudents();
        				editFrame.dispose();
        			};
        		};
        		okButton.addActionListener(okListener);
        		
        		ActionListener cancelListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				editFrame.dispose();
        			};
        		};
        		cancelButton.addActionListener(cancelListener);
        		editFrame.setVisible(true);
        	}
        };
        editButton.addActionListener(editListener);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton addCourseButton = new JButton("Add Course");
        ActionListener addCourseListener = new ActionListener() {
        	private int courseId;
        	public void actionPerformed(ActionEvent e) {
        		List<Course> allCourses = AllStudentsPane.this.courseDB.getAllCourses();
                List<String> allCourseNames = new ArrayList<String>();
                Map<String, Integer> courseNameIdMap = new HashMap<>();
                List<Integer> learningCourses = student.getCourseIds();
                
                allCourses.forEach((Course c) -> {
                	if (! learningCourses.contains(c.getId())) {
                		allCourseNames.add(c.getName());
                		courseNameIdMap.put(c.getName(), c.getId());
                	}
                });
                PeopleSelect courseSelectPane = new PeopleSelect("Select a Course", allCourseNames);
        		JFrame editFrame = new JFrame("Editing " + student.getName());
        		editFrame.setSize(300,150);
        		JPanel pane = new JPanel();
        		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        		pane.add(courseSelectPane);
        		JButton cancelButton = new JButton("cancel");
        		JButton okButton = new JButton("OK");
        		JPanel buttonPane = new JPanel();
        		buttonPane.setLayout(new GridLayout(1,0));
        		buttonPane.add(cancelButton);
        		buttonPane.add(okButton);
        		pane.add(buttonPane);
        		editFrame.add(pane);
        		editFrame.setLocation(location);
        		okButton.setEnabled(false);
        		courseSelectPane.setOnSelect((String s) -> {
                	int id = courseNameIdMap.get(s);
                	this.courseId = id;
                	if (courseSelectPane.isOk()) okButton.setEnabled(true);
                	else okButton.setEnabled(false);
                });
        		ActionListener okListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				student.addCourseId(courseId);
        				Course addedCourse = AllStudentsPane.this.courseDB.getCourseById(courseId);
        				listModel.addElement(addedCourse.getName());
        				jlist.validate();
        				AllStudentsPane.this.studentDB.updateStudent(student);
        				addedCourse.addStudentId(student.getId());
        				AllStudentsPane.this.courseDB.updateCourse(addedCourse);
        				AllStudentsPane.this.loadStudents();
        				editFrame.dispose();
        			};
        		};
        		okButton.addActionListener(okListener);
        		
        		ActionListener cancelListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				editFrame.dispose();
        			};
        		};
        		cancelButton.addActionListener(cancelListener);
        		editFrame.setVisible(true);
        	}
        };
        addCourseButton.addActionListener(addCourseListener);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton rmvCourseButton = new JButton("Remove Course");
        ActionListener rmvCourseListener = new ActionListener() {
        	private int courseId;
        	public void actionPerformed(ActionEvent e) {
        		List<Course> allCourses = AllStudentsPane.this.courseDB.getAllCourses();
                List<String> allCourseNames = new ArrayList<String>();
                Map<String, Integer> courseNameIdMap = new HashMap<>();
                List<Integer> learningCourses = student.getCourseIds();
   
                allCourses.forEach((Course c) -> {
                	if (learningCourses.contains(c.getId())) {
                		allCourseNames.add(c.getName());
                		courseNameIdMap.put(c.getName(), c.getId());
                	}
                });
                PeopleSelect courseSelectPane = new PeopleSelect("Select a Course", allCourseNames);
        		JFrame editFrame = new JFrame("Editing " + student.getName());
        		editFrame.setSize(300,150);
        		JPanel pane = new JPanel();
        		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        		pane.add(courseSelectPane);
        		JButton cancelButton = new JButton("cancel");
        		JButton okButton = new JButton("OK");
        		JPanel buttonPane = new JPanel();
        		buttonPane.setLayout(new GridLayout(1,0));
        		buttonPane.add(cancelButton);
        		buttonPane.add(okButton);
        		pane.add(buttonPane);
        		editFrame.add(pane);
        		editFrame.setLocation(location);
        		okButton.setEnabled(false);
        		courseSelectPane.setOnSelect((String s) -> {
                	int id = courseNameIdMap.get(s);
                	this.courseId = id;
                	if (courseSelectPane.isOk()) okButton.setEnabled(true);
                	else okButton.setEnabled(false);
                });
        		ActionListener okListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				student.removeCourseId(courseId);
        				Course removedCourse = AllStudentsPane.this.courseDB.getCourseById(courseId);
        				listModel.removeElement(removedCourse.getName());
        				jlist.validate();
        				AllStudentsPane.this.studentDB.updateStudent(student);
        				removedCourse.removeStudentId(student.getId());
        				AllStudentsPane.this.courseDB.updateCourse(removedCourse);
        				AllStudentsPane.this.loadStudents();
        				editFrame.dispose();
        			};
        		};
        		okButton.addActionListener(okListener);
        		
        		ActionListener cancelListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				editFrame.dispose();
        			};
        		};
        		cancelButton.addActionListener(cancelListener);
        		editFrame.setVisible(true);
        	}
        };
        rmvCourseButton.addActionListener(rmvCourseListener);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton deleteButton = new JButton("Delete Student");
        ActionListener deleteListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int answer = JOptionPane.showConfirmDialog(detailFrame, "Really Delete " + student.getName() + "?", "Confirming Deletion of Student", JOptionPane.YES_NO_OPTION);
        		if (answer == JOptionPane.YES_OPTION) {
        			AllStudentsPane.this.studentDB.deleteStudent(student);
        			AllStudentsPane.this.loadStudents();
        			detailFrame.dispose();
        		}
        	}
        };
        deleteButton.addActionListener(deleteListener);
        deleteButton.setForeground(Color.red);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel subPane = new JPanel();
        subPane.setLayout(new GridLayout(1, 0));
        subPane.add(deleteButton);
        subPane.add(rmvCourseButton);
        subPane.add(addCourseButton);
        subPane.add(editButton);
        
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(label);
        pane.add(label2);
        pane.add(label3);
        pane.add(new JScrollPane(jlist));
        pane.add(subPane);
        detailFrame.add(pane);
        detailFrame.setSize(600, 200);
        detailFrame.setLocation(location);
        detailFrame.setVisible(true);
        
        detailFrame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		AllStudentsPane.this.loadStudents();
        	}
        });
    }
}
