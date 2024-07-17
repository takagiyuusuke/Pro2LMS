package GUI.page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import database.CourseDB;
import database.TeacherDB;
import entities.Course;
import entities.Student;
import entities.Teacher;
import GUI.components.NameField;
import GUI.components.EntitySelect;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class AllTeachersPane extends JPanel {
	private TeacherDB teacherDB;
	private CourseDB courseDB;
	private String[] columnNames = {"Teacher ID", "Name"};
	private List<Teacher> teachers;
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0);

	public AllTeachersPane(TeacherDB teacherDB, CourseDB courseDB) {
		super();
		this.teacherDB = teacherDB;
		this.courseDB = courseDB;
		
		this.loadTeachers();
		
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
                	AllTeachersPane.this.showDetailFrame(teachers.get(row), e.getLocationOnScreen());
                }
            }
        });
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void loadTeachers() {
		this.teachers = this.teacherDB.getAllTeachers();
		this.model.setRowCount(0);
		for (Teacher teacher : this.teachers) {
			Object[] row = {teacher.getId(), teacher.getName()};
			this.model.addRow(row);
		}
	}
	
	private void showDetailFrame(Teacher teacher, Point location) {
        JFrame detailFrame = new JFrame("Information about " + teacher.getName());
        JLabel label = new JLabel("Name: " + teacher.getName());
        JLabel label2 = new JLabel("Teacher ID: " + teacher.getId());
        JLabel label3 = new JLabel("All Courses:");
        JPanel pane = new JPanel();
        List<Integer> allCourseIds = teacher.getCourseIds();
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
        		JFrame editFrame = new JFrame("Editing " + teacher.getName());
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
        				teacher.setName(name);
        				label.setText("Name: " + teacher.getName());
        				AllTeachersPane.this.teacherDB.updateTeacher(teacher);
        				AllTeachersPane.this.loadTeachers();
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
        		List<Course> allCourses = AllTeachersPane.this.courseDB.getAllCourses();
                List<String> allCourseNames = new ArrayList<String>();
                Map<String, Integer> courseNameIdMap = new HashMap<>();
                List<Integer> learningCourses = teacher.getCourseIds();
                
                allCourses.forEach((Course c) -> {
                	if (! learningCourses.contains(c.getId())) {
                		allCourseNames.add(c.getName());
                		courseNameIdMap.put(c.getName(), c.getId());
                	}
                });
                EntitySelect courseSelectPane = new EntitySelect("Select a Course", allCourseNames);
        		JFrame editFrame = new JFrame("Editing " + teacher.getName());
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
        				teacher.addCourseId(courseId);
        				Course addedCourse = AllTeachersPane.this.courseDB.getCourseById(courseId);
        				listModel.addElement(addedCourse.getName());
        				jlist.validate();
        				AllTeachersPane.this.teacherDB.updateTeacher(teacher);
        				addedCourse.addStudentId(teacher.getId());
        				AllTeachersPane.this.courseDB.updateCourse(addedCourse);
        				AllTeachersPane.this.loadTeachers();
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
        		List<Course> allCourses = AllTeachersPane.this.courseDB.getAllCourses();
                List<String> allCourseNames = new ArrayList<String>();
                Map<String, Integer> courseNameIdMap = new HashMap<>();
                List<Integer> learningCourses = teacher.getCourseIds();
   
                allCourses.forEach((Course c) -> {
                	if (learningCourses.contains(c.getId())) {
                		allCourseNames.add(c.getName());
                		courseNameIdMap.put(c.getName(), c.getId());
                	}
                });
                EntitySelect courseSelectPane = new EntitySelect("Select a Course", allCourseNames);
        		JFrame editFrame = new JFrame("Editing " + teacher.getName());
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
        				teacher.removeCourseId(courseId);
        				Course removedCourse = AllTeachersPane.this.courseDB.getCourseById(courseId);
        				listModel.removeElement(removedCourse.getName());
        				jlist.validate();
        				AllTeachersPane.this.teacherDB.updateTeacher(teacher);
        				removedCourse.removeStudentId(teacher.getId());
        				AllTeachersPane.this.courseDB.updateCourse(removedCourse);
        				AllTeachersPane.this.loadTeachers();
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
        JButton deleteButton = new JButton("Delete Teacher");
        ActionListener deleteListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int answer = JOptionPane.showConfirmDialog(detailFrame, "Really Delete " + teacher.getName() + "?", "Confirming Deletion of Teacher", JOptionPane.YES_NO_OPTION);
        		if (answer == JOptionPane.YES_OPTION) {
        			AllTeachersPane.this.teacherDB.deleteTeacher(teacher);
        			AllTeachersPane.this.loadTeachers();
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
        		AllTeachersPane.this.loadTeachers();
        	}
        });
    }
}
