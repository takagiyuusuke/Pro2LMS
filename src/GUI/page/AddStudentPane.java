package GUI.page;

import javax.swing.*;

import GUI.components.IdField;
import GUI.components.NameField;
import database.StudentDB;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class AddStudentPane extends JPanel {

    private String name;
    private int studentId;
    private JButton button = new JButton("Add Student");
    private StudentDB studentDB;
    private NameField namePane;
    private IdField idPane;

    public AddStudentPane(StudentDB studentDB) {
    	super();
        this.studentDB = studentDB;

        this.button.setEnabled(false);
        ButtonAction buttonListener = new ButtonAction();
        this.button.addActionListener(buttonListener);

        namePane = new NameField("Enter Name");
        idPane = new IdField("Enter Student ID");

        namePane.setOnTextChanged((String s) -> {
            this.name = s;
            if (namePane.isOk() && idPane.isOk()) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        });

        idPane.setOnTextChanged((Integer id) -> {
            this.studentId = id;
            if (namePane.isOk() && idPane.isOk()) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        });

        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        this.setLayout(new GridLayout(0, 1));
        JPanel labelPane = new JPanel();
        labelPane.add(new JLabel("Add Student"));
        this.add(labelPane);
        this.add(namePane);
        this.add(idPane);
        this.add(this.button);
    }
    
    public void reset() {
    	this.namePane.reset();
        this.idPane.reset();
        this.button.setEnabled(false);
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            studentDB.createStudent(studentId, name);
            AddStudentPane.this.reset();
        }
    }
}
