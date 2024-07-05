package GUI;

import java.awt.*;
import javax.swing.*;

import database.StudentDB;
import database.TeacherDB;

import java.awt.event.*;

public class GUIMain {
	private StudentDB studentDB;
	private TeacherDB teacherDB;
	
	private JFrame frame = new JFrame("Pro2LMS");
	private boolean frameClosed = false;

    private JPanel cardPanel;
    private CardLayout layout;
    
    final private String HomePaneName = "Home";
    final private String AddStudentPaneName = "AddStudentPane";
    final private String AddTeacherPaneName = "AddTeacherPane";
    
    public GUIMain(StudentDB studentDB, TeacherDB teacherDB) {
    	this.studentDB = studentDB;
    	this.teacherDB = teacherDB;
    	this.Exec();
    }

    public void Exec () {
    	
    	JPanel HomePane = new JPanel();
        AddTeacherPane addTeacherPane = new AddTeacherPane(this.teacherDB);
        AddStudentPane addStudentPane = new AddStudentPane(this.studentDB);

        // CardLayout
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);
        cardPanel.setSize(400, 300);

        cardPanel.add(HomePane, this.HomePaneName);
        cardPanel.add(addTeacherPane, this.AddTeacherPaneName);
        cardPanel.add(addStudentPane, this.AddStudentPaneName);

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

        JPanel btnPanel = new JPanel();
        btnPanel.add(homeButton);
        btnPanel.add(addTeacherButton);
        btnPanel.add(addStudentButton);

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
	    }
    }
    
    public synchronized void waitForClose() throws InterruptedException {
		while (!this.frameClosed) {
			wait();
		}
	}
}
