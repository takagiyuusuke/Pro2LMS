package GUI.page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import database.CourseDB;
import database.TeacherDB;
import database.StudentDB;
import entities.Course;
import entities.Teacher;
import entities.Student;
import GUI.components.NameField;
import GUI.components.DayField;
import GUI.components.PeriodField;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class AllCoursesPane extends JPanel {
	private CourseDB courseDB;
	private TeacherDB teacherDB;
	private StudentDB studentDB;
	private String[] columnNames = {"Course ID", "Name"};
	private List<Course> courses;
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0);

	public AllCoursesPane(CourseDB courseDB, TeacherDB teacherDB, StudentDB studentDB) {
		super();
		this.courseDB = courseDB;
		this.teacherDB = teacherDB;
		this.studentDB = studentDB;
		
		this.loadCourses();
		
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
                	AllCoursesPane.this.showDetailFrame(courses.get(row), e.getLocationOnScreen());
                }
            }
        });
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void loadCourses() {
		this.courses = this.courseDB.getAllCourses();
		this.model.setRowCount(0);
		for (Course course : this.courses) {
			Object[] row = {course.getId(), course.getName()};
			this.model.addRow(row);
		}
	}
	
	private void showDetailFrame(Course course, Point location) {
		Teacher teacher = teacherDB.getTeacherById(course.getTeacherId());
        JFrame detailFrame = new JFrame("Information about " + course.getName());
        JLabel label = new JLabel("Course ID: " + course.getId());
        JLabel label2 = new JLabel("Name: " + course.getName());
        JLabel label3 = new JLabel("Room Name: " + course.getRoomId());
        JLabel label4 = new JLabel("Day / Period: " + course.getDay() + "/" + course.getPeriod());
        JLabel label5 = new JLabel("Teacher Name: " + teacher.getName());
        JLabel label6 = new JLabel("Students:");
        JPanel pane = new JPanel();
        List<Integer> allStudentsName = course.getStudentIds();
        List<String> stringList = new ArrayList<String>();
        for (Integer id: allStudentsName) {
        	stringList.add(studentDB.getStudentById(id).getName());
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
        		JFrame editFrame = new JFrame("Editing " + course.getName());
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
        				course.setName(name);
        				label2.setText("Name: " + course.getName());
        				AllCoursesPane.this.courseDB.updateCourse(course);
        				AllCoursesPane.this.loadCourses();
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
        JButton editRoomButton = new JButton("Edit Room Name");
        
        ActionListener editRoomListener = new ActionListener() {
        	private String roomName;
        	public void actionPerformed(ActionEvent e) {
        		NameField roomField = new NameField("New Room Name");
        		JFrame editFrame = new JFrame("Editing " + course.getName());
        		editFrame.setSize(300,150);
        		JPanel editpane = new JPanel();
        		editpane.setLayout(new BoxLayout(editpane, BoxLayout.Y_AXIS));
        		editpane.add(roomField);
        		JButton cancelButton = new JButton("cancel");
        		JButton okButton = new JButton("OK");
        		JPanel buttonPane = new JPanel();
        		buttonPane.setLayout(new GridLayout(1,0));
        		buttonPane.add(cancelButton);
        		buttonPane.add(okButton);
        		editpane.add(buttonPane);
        		editFrame.add(editpane);
        		editFrame.setLocation(location);
        		okButton.setEnabled(false);
        		roomField.setOnTextChanged((String s) -> {
                    this.roomName = s;
                    if (roomField.isOk()) {
                        okButton.setEnabled(true);
                    } else {
                        okButton.setEnabled(false);
                    }
                });
        		ActionListener okListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				course.setRoomId(roomName);
        				label3.setText("Room Name: " + course.getRoomId());
        				AllCoursesPane.this.courseDB.updateCourse(course);
        				AllCoursesPane.this.loadCourses();
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
        editRoomButton.addActionListener(editRoomListener);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton editDayButton = new JButton("Edit Day");
        
        ActionListener editDayListener = new ActionListener() {
        	private String day;
        	public void actionPerformed(ActionEvent e) {
        		DayField dayField = new DayField("New Day");
        		JFrame editFrame = new JFrame("Editing " + course.getName());
        		editFrame.setSize(300,150);
        		JPanel pane = new JPanel();
        		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        		pane.add(dayField);
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
        		dayField.setOnTextChanged((String s) -> {
                    this.day = s;
                    if (dayField.isOk()) {
                        okButton.setEnabled(true);
                    } else {
                        okButton.setEnabled(false);
                    }
                });
        		ActionListener okListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				course.setDay(day);
        				label4.setText("Day / Period: " + course.getDay() + "/" + course.getPeriod());
        				AllCoursesPane.this.courseDB.updateCourse(course);
        				AllCoursesPane.this.loadCourses();
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
        editDayButton.addActionListener(editDayListener);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton editPeriodButton = new JButton("Edit Period");
        
        ActionListener editPeriodListener = new ActionListener() {
        	private int period;
        	public void actionPerformed(ActionEvent e) {
        		PeriodField periodField = new PeriodField("New Period");
        		JFrame editFrame = new JFrame("Editing " + course.getName());
        		editFrame.setSize(300,150);
        		JPanel pane = new JPanel();
        		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        		pane.add(periodField);
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
        		periodField.setOnTextChanged((Integer s) -> {
                    this.period = s;
                    if (periodField.isOk()) {
                        okButton.setEnabled(true);
                    } else {
                        okButton.setEnabled(false);
                    }
                });
        		ActionListener okListener = new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				course.setPeriod(period);
        				label4.setText("Day / Period: " + course.getDay() + "/" + course.getPeriod());
        				AllCoursesPane.this.courseDB.updateCourse(course);
        				AllCoursesPane.this.loadCourses();
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
        editPeriodButton.addActionListener(editPeriodListener);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        JButton deleteButton = new JButton("Delete Course");
        ActionListener deleteListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int answer = JOptionPane.showConfirmDialog(detailFrame, "Really Delete " + course.getName() + "?", "Confirming Deletion of Student", JOptionPane.YES_NO_OPTION);
        		if (answer == JOptionPane.YES_OPTION) {
        			AllCoursesPane.this.courseDB.deleteCourse(course);
        			AllCoursesPane.this.loadCourses();
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
        subPane.add(editRoomButton);
        subPane.add(editButton);
        subPane.add(editDayButton);
        subPane.add(editPeriodButton);
        
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(label);
        pane.add(label2);
        pane.add(label3);
        pane.add(label4);
        pane.add(label5);
        pane.add(label6);
        pane.add(new JScrollPane(jlist));
        pane.add(subPane);
        detailFrame.add(pane);
        detailFrame.setSize(600, 200);
        detailFrame.setLocation(location);
        detailFrame.setVisible(true);
        
        detailFrame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		AllCoursesPane.this.loadCourses();
        	}
        });
    }
}
