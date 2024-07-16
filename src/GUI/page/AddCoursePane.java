package GUI.page;
import javax.swing.*;

import GUI.components.DayField;
import GUI.components.NameField;
import GUI.components.EntitySelect;
import GUI.components.PeriodField;
import database.CourseDB;
import database.TeacherDB;
import entities.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class AddCoursePane extends JPanel {
    private String name;
    private String roomName;
    private String day;
    private int period;
    private int teacherId;

    private JButton button = new JButton("Add Course");
    private CourseDB courseDB;
    private TeacherDB teacherDB;
    private NameField namePane;
    private NameField roomNamePane;
    private DayField dayPane;
    private PeriodField periodPane;
    private EntitySelect teacherSelectPane;
    
    private List<String> allTeacherNames;
    private Map<String, Integer> teacherNameIdMap;

    public AddCoursePane(CourseDB courseDB, TeacherDB teacherDB) {
    	super();
        this.courseDB = courseDB;
        this.teacherDB = teacherDB;

        this.button.setEnabled(false);
        ButtonAction buttonListener = new ButtonAction();
        this.button.addActionListener(buttonListener);

        this.namePane = new NameField("Enter Name");
        this.roomNamePane = new NameField("Enter Room Name");
        this.dayPane = new DayField("Enter Day");
        this.periodPane = new PeriodField("Etner Period");

        this.initializeChoice();
        
        this.teacherSelectPane = new EntitySelect("Select a Teacher", allTeacherNames);

        this.namePane.setOnTextChanged((String s) -> {
            this.name = s;
            this.enableButtonIfOk();
        });
        
        this.roomNamePane.setOnTextChanged((String s) -> {
        	this.roomName = s;
        	this.enableButtonIfOk();
        });
        
        this.dayPane.setOnTextChanged((String s) -> {
        	this.day = s;
        	this.enableButtonIfOk();
        });
        
        this.periodPane.setOnTextChanged((Integer period) -> {
        	this.period = period;
        	this.enableButtonIfOk();
        });
        
        this.teacherSelectPane.setOnSelect((String s) -> {
        	int id = teacherNameIdMap.get(s);
        	this.teacherId = id;
        	this.enableButtonIfOk();
        });

        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        this.setLayout(new GridLayout(0, 1));
        JPanel labelPane = new JPanel();
        labelPane.add(new JLabel("Add Course"));
        this.add(labelPane);
        this.add(this.namePane);
        this.add(this.roomNamePane);
        this.add(this.dayPane);
        this.add(this.periodPane);
        this.add(this.teacherSelectPane);
        this.add(this.button);
    }
    
    private void initializeChoice() {
        List<Teacher> allTeachers = this.teacherDB.getAllTeachers();
        allTeacherNames = new ArrayList<>();
        teacherNameIdMap = new HashMap<>();
        for (Teacher t : allTeachers) {
            allTeacherNames.add(t.getName());
            teacherNameIdMap.put(t.getName(), t.getId());
        }
    }
    
    private boolean isOk() {
    	return this.namePane.isOk() 
    			&& this.roomNamePane.isOk()
    			&& this.dayPane.isOk()
    			&& this.periodPane.isOk()
    			&& this.teacherSelectPane.isOk();
    }
    
    private void enableButtonIfOk() {
    	if (this.isOk()) {
    		this.button.setEnabled(true);
    	} else {
    		this.button.setEnabled(false);
    	}
    }
    
    public void reset() {
    	this.namePane.reset();
    	this.roomNamePane.reset();
    	this.dayPane.reset();
    	this.periodPane.reset();
    	this.initializeChoice();
    	this.teacherSelectPane.reset("Select a Teacher", allTeacherNames);
        this.button.setEnabled(false);
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AddCoursePane.this.courseDB.createCourse(name, roomName, day, period, teacherId);
            AddCoursePane.this.reset();
        }
    }
}
