package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import database.StudentDB;
import entities.Student;

import java.util.List;
import java.util.ArrayList;

public class ListDisplayGUI {
	
	private JFrame frame = new JFrame("Student List");
	private StudentDB studentDB;
	private boolean frameClosed = false;

	public ListDisplayGUI(StudentDB studentDB) {
		this.studentDB = studentDB;
		this.initUI();
	}

	private void initUI() {
		String[] columnNames = {"Student ID", "Name"};
		ArrayList<Student> students = new ArrayList<>();
		
		for (int id : getAllStudentIds()) {
			Student student = studentDB.getStudentById(id);
			if (student != null) {
				students.add(student);
			}
		} 
		
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		for (Student student : students) {
			Object[] row = {student.getId(), student.getName()};
			model.addRow(row);
		}
		
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		JPanel pane = new JPanel(new BorderLayout());
		pane.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		pane.add(scrollPane, BorderLayout.CENTER);
		
		this.frame.getContentPane().add(pane, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setSize(400, 300);
		
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				synchronized (ListDisplayGUI.this) {
					frameClosed = true;
					ListDisplayGUI.this.notify();
				}
			}
		});

		this.frame.setVisible(true);
	}
	
	private ArrayList<Integer> getAllStudentIds() {
		ArrayList<Integer> studentIds = new ArrayList<>();
		return studentIds;
	}

	public synchronized void waitForClose() throws InterruptedException {
		while (!this.frameClosed) {
			wait();
		}
	}
}
