package GUI;

import javax.swing.*;

import database.StudentDB;

import java.awt.*;
import java.awt.event.*;
public class AddStudentGUI {
	
	private JFrame frame = new JFrame("Add Student");
	private String name;
	private int studentId;
	private JButton button = new JButton("Add Student");
	private StudentDB studentDB;
	private boolean frameClosed = false;

	public AddStudentGUI(StudentDB studentDB) {
		this.studentDB = studentDB;
		this.initUI();
	}

	private void initUI() {
		this.button.setEnabled(false);
		
		ButtonAction buttonListener = new ButtonAction();
		this.button.addActionListener(buttonListener);
		
		NameField namePane = new NameField("Enter Name");
		IdField idPane = new IdField("Enter Student ID");
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
		pane.add(namePane);
		pane.add(idPane);
		pane.add(this.button);
		
		this.frame.getContentPane().add(pane, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setSize(400, 300);
		
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				synchronized (AddStudentGUI.this) {
					frameClosed = true;
					AddStudentGUI.this.notify();
				}
			}
		});

		this.frame.setVisible(true);
	}

	class ButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			studentDB.createStudent(studentId, name);
			frame.dispose();
		}
	}

	public synchronized void waitForClose() throws InterruptedException {
		while (!this.frameClosed) {
			wait();
		}
	}
}
