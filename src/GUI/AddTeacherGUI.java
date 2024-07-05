package GUI;

import javax.swing.*;

import database.TeacherDB;

import java.awt.*;
import java.awt.event.*;
public class AddTeacherGUI {
	
	private JFrame frame = new JFrame("Add Teacher");
	private String name;
	private int teacherId;
	private JButton button = new JButton("Add Teacher");
	private TeacherDB teacherDB;
	private boolean frameClosed = false;

	public AddTeacherGUI(TeacherDB teacherDB) {
		this.teacherDB = teacherDB;
		this.initUI();
	}

	private void initUI() {
		this.button.setEnabled(false);
		
		ButtonAction buttonListener = new ButtonAction();
		this.button.addActionListener(buttonListener);
		
		NameField namePane = new NameField("Enter Name");
		IdField idPane = new IdField("Enter Teacher ID");
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
		pane.add(namePane);
		pane.add(idPane);
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

	public synchronized void waitForClose() throws InterruptedException {
		while (!this.frameClosed) {
			wait();
		}
	}
}
