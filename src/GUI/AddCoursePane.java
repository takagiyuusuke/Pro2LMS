package GUI;
import javax.swing.*;
import database.CourseDB;
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
    private NameField namePane;
    private NameField roomNamePane;
    private DayField dayPane;
    private PeriodField periodPane;
    private IdField idPane;

    public AddCoursePane(CourseDB courseDB) {
    	super();
        this.courseDB = courseDB;

        this.button.setEnabled(false);
        ButtonAction buttonListener = new ButtonAction();
        this.button.addActionListener(buttonListener);

        namePane = new NameField("Enter Name");
        roomNamePane = new NameField("Enter Room Name");
        dayPane = new DayField("Enter Day");
        periodPane = new PeriodField("Etner Period");
        idPane = new IdField("Enter Teacher ID");

        namePane.setOnTextChanged((String s) -> {
            this.name = s;
            this.enableButtonIfOk();
        });
        
        roomNamePane.setOnTextChanged((String s) -> {
        	this.roomName = s;
        	this.enableButtonIfOk();
        });
        
        dayPane.setOnTextChanged((String s) -> {
        	this.day = s;
        	this.enableButtonIfOk();
        });
        
        periodPane.setOnTextChanged((Integer period) -> {
        	this.period = period;
        	this.enableButtonIfOk();
        });

        idPane.setOnTextChanged((Integer id) -> {
            this.teacherId = id;
            this.enableButtonIfOk();
        });

        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        this.setLayout(new GridLayout(0, 1));
        JPanel labelPane = new JPanel();
        labelPane.add(new JLabel("Add Course"));
        this.add(labelPane);
        this.add(namePane);
        this.add(roomNamePane);
        this.add(dayPane);
        this.add(periodPane);
        this.add(idPane);
        this.add(this.button);
    }
    
    private boolean isOk() {
    	return this.namePane.isOk() 
    			&& this.roomNamePane.isOk()
    			&& this.dayPane.isOk()
    			&& this.periodPane.isOk()
    			&& this.idPane.isOk();
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
        this.idPane.reset();
        this.button.setEnabled(false);
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AddCoursePane.this.courseDB.createCourse(name, roomName, day, period, teacherId);
            AddCoursePane.this.reset();
        }
    }
}
