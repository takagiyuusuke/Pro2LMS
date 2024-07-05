package GUI;

import javax.swing.*;
import database.TeacherDB;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class AddTeacherPane extends JPanel {

    private String name;
    private int teacherId;
    private JButton button = new JButton("Add Teacher");
    private TeacherDB teacherDB;
    private NameField namePane;
    private IdField idPane;

    public AddTeacherPane(TeacherDB teacherDB) {
    	super();
        this.teacherDB = teacherDB;
        
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

        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        this.setLayout(new GridLayout(0, 1));
        JPanel labelPane = new JPanel();
        labelPane.add(new JLabel("Add Teacher"));
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
            teacherDB.createTeacher(teacherId, name);
            AddTeacherPane.this.reset();
        }
    }
}
