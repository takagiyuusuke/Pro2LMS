package GUI;

import java.awt.*;
import javax.swing.*;

import database.StudentDB;
import database.TeacherDB;
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
    
    private AddStudentPane addStudentPane;
    private AddTeacherPane addTeacherPane;
    private AddCoursePane addCoursePane;
    
    public GUIMain(StudentDB studentDB, TeacherDB teacherDB, CourseDB courseDB) {
    	this.addStudentPane = new AddStudentPane(studentDB);
    	this.addTeacherPane = new AddTeacherPane(teacherDB);
    	this.addCoursePane = new AddCoursePane(courseDB);
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

        JPanel btnPanel = new JPanel();
        btnPanel.add(homeButton);
        btnPanel.add(addTeacherButton);
        btnPanel.add(addStudentButton);
        btnPanel.add(addCourseButton);

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
	    }
    }
    
    public synchronized void waitForClose() throws InterruptedException {
		while (!this.frameClosed) {
			wait();
		}
	}
}
