package GUI;

import javax.swing.*;
import database.StudentDB;
import java.awt.*;
import java.awt.event.*;

public class AddStudentPane {

    private String name;
    private int studentId;
    private JButton button = new JButton("Add Student");
    private StudentDB studentDB;
    private NameField namePane;
    private IdField idPane;

    public AddStudentPane(StudentDB studentDB) {
        this.studentDB = studentDB;
    }

    public JPanel createPane() {
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

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        pane.setLayout(new GridLayout(0, 1));
        pane.add(new JLabel("Add Student"));
        pane.add(namePane);
        pane.add(idPane);
        pane.add(this.button);

        return pane;
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            studentDB.createStudent(studentId, name);
            AddStudentPane.this.namePane.deleteText();
            AddStudentPane.this.idPane.deleteText();
            AddStudentPane.this.button.setEnabled(false);
        }
    }

    public JPanel getPane() {
        return createPane();
    }
}
