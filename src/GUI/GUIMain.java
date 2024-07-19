package GUI;

import java.awt.*;
import javax.swing.*;

import database.StudentDB;
import database.TeacherDB;
import GUI.page.AddCoursePane;
import GUI.page.AddStudentPane;
import GUI.page.AddTeacherPane;
import GUI.page.AllStudentsPane;
import GUI.page.AllTeachersPane;
import GUI.page.AllCoursesPane;
import database.CourseDB;

import java.awt.event.*;

public class GUIMain {
	private JFrame frame = new JFrame("Pro2LMS");
	private boolean frameClosed = false;

    private JPanel cardPanel;
    private CardLayout layout;
    
    final private String HomePaneName = "Home";
    final private String AddStudentPaneName = "AddStudentPane";
    final private String AddTeacherPaneName = "AddTeacherPane";
    final private String AddCoursePaneName = "AddCoursePane";
    final private String AllStudentsPaneName = "AllStudentsPane";
    final private String AllTeachersPaneName = "AllTeachersPane";
    final private String AllCoursesPaneName = "AllCoursesPane";
    
    private AddStudentPane addStudentPane;
    private AddTeacherPane addTeacherPane;
    private AddCoursePane addCoursePane;
    private AllStudentsPane allStudentsPane;
    private AllTeachersPane allTeachersPane;
    private AllCoursesPane allCoursesPane;
    
    public GUIMain(StudentDB studentDB, TeacherDB teacherDB, CourseDB courseDB) {
    	this.addStudentPane = new AddStudentPane(studentDB);
    	this.addTeacherPane = new AddTeacherPane(teacherDB);
    	this.addCoursePane = new AddCoursePane(courseDB, teacherDB);
    	this.allStudentsPane = new AllStudentsPane(studentDB, courseDB);
    	this.allTeachersPane = new AllTeachersPane(teacherDB, courseDB);
    	this.allCoursesPane = new AllCoursesPane(courseDB, teacherDB, studentDB);
    	this.Exec();
    }
    
    public void Exec () {
    	
    	JPanel HomePane = new JPanel();

        // CardLayout
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        cardPanel.add(HomePane, this.HomePaneName);
        cardPanel.add(addTeacherPane, this.AddTeacherPaneName);
        cardPanel.add(addStudentPane, this.AddStudentPaneName);
        cardPanel.add(addCoursePane, this.AddCoursePaneName);
        cardPanel.add(allStudentsPane, this.AllStudentsPaneName);
        cardPanel.add(allTeachersPane, this.AllTeachersPaneName);
        cardPanel.add(allCoursesPane, this.AllCoursesPaneName);

        // card transitions
        JButton homeButton = new JButton("Home");
        TransitionButtonAction transitionButtonAction = new TransitionButtonAction();
        homeButton.addActionListener(transitionButtonAction);
        homeButton.setActionCommand(this.HomePaneName);

        JButton addTeacherButton = new JButton("Add Teacher");
        addTeacherButton.addActionListener(transitionButtonAction);
        addTeacherButton.setActionCommand(this.AddTeacherPaneName);

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(transitionButtonAction);
        addStudentButton.setActionCommand(this.AddStudentPaneName);
        
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(transitionButtonAction);
        addCourseButton.setActionCommand(this.AddCoursePaneName);
        
        JButton allStudentsButton = new JButton("All Students");
        allStudentsButton.addActionListener(transitionButtonAction);
        allStudentsButton.setActionCommand(this.AllStudentsPaneName);
        
        JButton allTeachersButton = new JButton("All Teachers");
        allTeachersButton.addActionListener(transitionButtonAction);
        allTeachersButton.setActionCommand(this.AllTeachersPaneName);
        
        JButton allCoursesButton = new JButton("All Courses");
        allCoursesButton.addActionListener(transitionButtonAction);
        allCoursesButton.setActionCommand(this.AllCoursesPaneName);

        JPanel btnPanel = new JPanel();
        btnPanel.add(homeButton);
        btnPanel.add(addTeacherButton);
        btnPanel.add(addStudentButton);
        btnPanel.add(addCourseButton);
        btnPanel.add(allStudentsButton);
        btnPanel.add(allTeachersButton);
        btnPanel.add(allCoursesButton);

        Container contentPane = this.frame.getContentPane();
        contentPane.add(cardPanel, BorderLayout.CENTER);
        contentPane.add(btnPanel, BorderLayout.PAGE_END);
        
        this.frame.setTitle("Pro2LMS");
        this.frame.setSize(900, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				synchronized (this) {
					GUIMain.this.frameClosed = true;
					GUIMain.this.notify();
				}
			}
		});
        
        this.frame.setVisible(true);
    }
    
    class TransitionButtonAction implements ActionListener {    	
	    public void actionPerformed(ActionEvent e) {
	        String cmd = e.getActionCommand();
	        layout.show(cardPanel, cmd);
	        
	        GUIMain.this.addStudentPane.reset();
	        GUIMain.this.addTeacherPane.reset();
	        GUIMain.this.addCoursePane.reset();
	        GUIMain.this.allStudentsPane.loadStudents();
	        GUIMain.this.allTeachersPane.loadTeachers();
	        GUIMain.this.allCoursesPane.loadCourses();
	    }
    }
    
    public synchronized void waitForClose() throws InterruptedException {
		while (!this.frameClosed) {
			wait();
		}
	}
}
