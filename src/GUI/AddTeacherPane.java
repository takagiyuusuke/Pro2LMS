package GUI;

import javax.swing.*;
import database.TeacherDB;
import java.awt.*;
import java.awt.event.*;

public class AddTeacherPane {

    private String name;
    private int teacherId;
    private JButton button = new JButton("Add Teacher");
    private TeacherDB teacherDB;
    private NameField namePane;
    private IdField idPane;

    public AddTeacherPane(TeacherDB teacherDB) {
        this.teacherDB = teacherDB;
    }

    public JPanel createPane() {
        this.button.setEnabled(false);
        
        ButtonAction buttonListener = new ButtonAction();
        this.button.addActionListener(buttonListener);

        namePane = new NameField("Enter Name");
        idPane = new IdField("Enter Teacher ID");

        namePane.setOnTextChanged((String s) -> {
            this.name = s;
            if (namePane.isOk() && idPane.isOk()) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        });

        idPane.setOnTextChanged((Integer id) -> {
            this.teacherId = id;
            if (namePane.isOk() && idPane.isOk()) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        });

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        pane.setLayout(new GridLayout(0, 1));
        pane.add(new JLabel("Add Teacher"));
        pane.add(namePane);
        pane.add(idPane);
        pane.add(this.button);

        return pane;
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            teacherDB.createTeacher(teacherId, name);
            AddTeacherPane.this.namePane.deleteText();
            AddTeacherPane.this.idPane.deleteText();
            AddTeacherPane.this.button.setEnabled(false);
        }
    }

    public JPanel getPane() {
        return createPane();
    }
}
