package GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import database.TeacherDB;

import java.awt.*;
import java.awt.event.*;

public class AddTeacherGUI {
	
	private JFrame frame = new JFrame("Add Teacher");
	private String name;
	private int teacherId;
	private boolean nameIsOk = false;
	private boolean teacherIdIsOk = false;
	private JLabel errlabel = new JLabel("");
	private JLabel errlabel2 = new JLabel("");
	private JTextField nameField = new JTextField("");
	private JTextField teacherIdField = new JTextField("");
	private JButton button = new JButton("Add Teacher");
	private TeacherDB teacherDB;
	private boolean frameClosed = false;

	public AddTeacherGUI(TeacherDB teacherDB) {
		this.teacherDB = teacherDB;
		this.initUI();
	}

	private void initUI() {
		this.button.setEnabled(false);
		
		this.nameField.getDocument().addDocumentListener(this.listener);
		this.teacherIdField.getDocument().addDocumentListener(this.listener);
		
		ButtonAction buttonListener = new ButtonAction();
		this.button.addActionListener(buttonListener);
		
		JPanel namePane = new JPanel();
		namePane.setLayout(new GridLayout(1, 0));
		namePane.add(new JLabel("Enter Name:"));
		namePane.add(this.nameField);
		
		JPanel teacherIdPane = new JPanel();
		teacherIdPane.setLayout(new GridLayout(1, 0));
		teacherIdPane.add(new JLabel("Enter Teacher ID:"));
		teacherIdPane.add(this.teacherIdField);
		
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		pane.setLayout(new GridLayout(0, 1));
		pane.add(namePane);
		pane.add(this.errlabel);
		pane.add(teacherIdPane);
		pane.add(this.errlabel2);
		pane.add(this.button);
		
		this.frame.getContentPane().add(pane, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setSize(400, 300);
		
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				synchronized (AddTeacherGUI.this) {
					frameClosed = true;
					AddTeacherGUI.this.notify();
				}
			}
		});

		this.frame.setVisible(true);
	}

	class ButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			teacherDB.createTeacher(teacherId, name);
			frame.dispose();
		}
	}

	private void nameCheck() {
		String newName = nameField.getText();
		if (newName.length() == 0) {
			this.errlabel.setText("name should have at least 1 letter!");
			this.errlabel.setForeground(Color.red);
			this.nameIsOk = false;
		} else if (newName.length() >= 20) {
			this.errlabel.setText("name should be shorter than 20 letters!");
			this.errlabel.setForeground(Color.red);
			this.nameIsOk = false;
		} else if (!newName.matches("[a-zA-Z\\s]*")) {
			this.errlabel.setText("name should have only alphabets and space!");
			this.errlabel.setForeground(Color.red);
			this.nameIsOk = false;
		} else {
			this.errlabel.setText("there are no problem with name!");
			this.errlabel.setForeground(Color.blue);
			this.name = newName;
			this.nameIsOk = true;
		}
	}

	private void teacherIdCheck() {
		String newidString = this.teacherIdField.getText();
		int newid;
		try {
			newid = Integer.valueOf(newidString);
		} catch (Exception ex) {
			this.errlabel2.setText("teacher ID should have numbers!");
			this.errlabel2.setForeground(Color.red);
			this.teacherIdIsOk = false;
			return;
		}
		if (newidString.length() != 6) {
			this.errlabel2.setText("teacher ID should have 6 digits!");
			this.errlabel2.setForeground(Color.red);
			this.teacherIdIsOk = false;
		} else {
			this.errlabel2.setText("there are no problem with teacher ID!");
			this.errlabel2.setForeground(Color.blue);
			this.teacherId = newid;
			this.teacherIdIsOk = true;
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
			teacherIdCheck();
			if (nameIsOk && teacherIdIsOk) button.setEnabled(true);
			else button.setEnabled(false);
		}
	}

	TextFieldDocumentListener listener = new TextFieldDocumentListener();

	public synchronized void waitForClose() throws InterruptedException {
		while (!this.frameClosed) {
			wait();
		}
	}
}