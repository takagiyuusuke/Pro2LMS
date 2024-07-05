package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import database.StudentDB;
import database.TeacherDB;

public class GUIMain extends JFrame implements ActionListener{

    JPanel cardPanel;
    CardLayout layout;
    StudentDB studentDB;
    TeacherDB teacherDB;

    public static void main(String[] args) {
        GUIMain frame = new GUIMain();
        frame.setTitle("ホームページ");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public GUIMain() {
    	studentDB = new StudentDB();
    	teacherDB = new TeacherDB();
        
        // Add Student ボタン
        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(this);
        addStudentButton.setActionCommand("addStudent");
        
        // Add Teacher ボタン
        JButton addTeacherButton = new JButton("Add Teacher");
        addTeacherButton.addActionListener(this);
        addTeacherButton.setActionCommand("addTeacher");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addStudentButton);
        btnPanel.add(addTeacherButton);

        Container contentPane = getContentPane();
        contentPane.add(btnPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("addStudent")) {
            new AddStudentGUI(studentDB);
        } else if (cmd.equals("addTeacher")) {
            new AddTeacherGUI(teacherDB);
        } else {
            layout.show(cardPanel, cmd);
        }
    }
}

