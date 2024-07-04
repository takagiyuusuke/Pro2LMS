package GUI;

import javax.swing.*; 
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class AddStudentGUI {
	private String name;
	private int studentId;
	private boolean nameIsOk = false;
	private boolean studentIdIsOk = false;
  private JLabel errlabel = new JLabel("");
  private JLabel errlabel2 = new JLabel("");
  private JTextField nameField = new JTextField("");
  private JTextField studentIdField = new JTextField("");
  private JButton button = new JButton("Add Student");

  class ButtonAction implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      
    }
  }
  /***
   * error check with name field. 
   */
  private void nameCheck () {
    	String newName = nameField.getText();
    	if (newName.length() == 0) {
    		errlabel.setText("name should have at least 1 letter!");
    		errlabel.setForeground(Color.red);
    		nameIsOk = false;
    	} else if (newName.length() >= 20) {
    		errlabel.setText("name should be shorter than 20 letters!");
    		errlabel.setForeground(Color.red);
    		nameIsOk = false;
    	} else if (!newName.matches("[a-zA-Z\\s]*")) {
    		errlabel.setText("name should have only alphabets and space!");
    		errlabel.setForeground(Color.red);
    		nameIsOk = false;
    	} else {
    		errlabel.setText("there are no problem with name!");
    		errlabel.setForeground(Color.blue);
    		name = newName;
    		nameIsOk = true;
    	}
  }
  
  /***
   * error check with student ID field. 
   */
  private void studentIdCheck () {
		String newidString = studentIdField.getText();
		int newid;
		try {
			newid = Integer.valueOf(newidString);
		} catch (Exception ex) {
			errlabel2.setText("student ID should have numbers!");
			errlabel2.setForeground(Color.red);
			studentIdIsOk = false;
			return;
		}
		if (newidString.length() != 8) {
			errlabel2.setText("student ID should have 8 digits!");
			errlabel2.setForeground(Color.red);
			studentIdIsOk = false;
		} else {
			errlabel2.setText("there are no problem with student ID!");
			errlabel2.setForeground(Color.blue);
			studentId = newid;
			studentIdIsOk = true;
		}
	   
  }
  
  class TextFieldDocumentListener implements DocumentListener {
      @Override
      public void insertUpdate(DocumentEvent e) {
          onTextChanged(e);
      }
      @Override
      public void removeUpdate(DocumentEvent e) {
          onTextChanged(e);
      }
      @Override
      public void changedUpdate(DocumentEvent e) {
          onTextChanged(e);
      }
      private void onTextChanged(DocumentEvent e) {
          nameCheck();
          studentIdCheck();
          if (nameIsOk & studentIdIsOk) button.setEnabled(true);
          else button.setEnabled(false);
      }
  }
  TextFieldDocumentListener listener = new TextFieldDocumentListener();

  public Component createComponents() {
    button.setEnabled(false);
    
    nameField.getDocument().addDocumentListener(listener);
    studentIdField.getDocument().addDocumentListener(listener);
    
    ButtonAction buttonListener = new ButtonAction();
    button.addActionListener( buttonListener );
    
    JPanel namePane = new JPanel();
    namePane.setLayout(new GridLayout(1,0));
    namePane.add(new JLabel("Enter Name:"));
    namePane.add(nameField);
    
    JPanel studentIdPane = new JPanel();
    studentIdPane.setLayout(new GridLayout(1,0));
    studentIdPane.add(new JLabel("Enter Student ID:"));
    studentIdPane.add(studentIdField);
    
    JPanel pane = new JPanel();
    pane.setBorder(BorderFactory.createEmptyBorder( 30, 30, 10, 30 ));
    pane.setLayout(new GridLayout(0, 1));
    pane.add(namePane);
    pane.add(errlabel);
    pane.add(studentIdPane);
    pane.add(errlabel2);
    pane.add(button);
    
    return pane;
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Add Student");
    AddStudentGUI app = new AddStudentGUI();
    Component contents = app.createComponents();
    frame.getContentPane().add(contents, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,300);
    frame.setVisible(true);
  }
}