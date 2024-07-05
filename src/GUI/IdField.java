package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class IdField extends JPanel {
	private boolean isOk = false;
	private int id;
	
	private JLabel errlabel = new JLabel("");
	private JTextField idField = new JTextField("");
	
	private Consumer<Integer> onTextChanged;
	
	
	public IdField(String label) {
		super();
		this.idField.getDocument().addDocumentListener(this.listener);
		JPanel namePane = new JPanel();
		namePane.setLayout(new GridLayout(1, 0));
		namePane.add(new JLabel(label + ":"));
		namePane.add(this.idField);
		
		this.setLayout(new GridLayout(0, 1));
		this.add(namePane);
		this.add(this.errlabel);
	}
	
	public void setOnTextChanged(Consumer<Integer> onTextChanged) {
		this.onTextChanged = onTextChanged;
	}
	
	public String getText() {
		return idField.getText();
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean isOk() {
		return this.isOk;
	}
	

	public void deleteText() {
		this.idField.setText("");
		this.errlabel.setText("");
	}
	
	private void idCheck() {
		String newidString = this.idField.getText();
		try {
			this.id = Integer.valueOf(newidString);
		} catch (Exception ex) {
			this.errlabel.setText("student ID should have numbers!");
			this.errlabel.setForeground(Color.red);
			this.isOk = false;
		}
		if (newidString.length() != 8) {
			this.errlabel.setText("student ID should have 8 digits!");
			this.errlabel.setForeground(Color.red);
			this.isOk = false;
		} else {
			this.errlabel.setText("there are no problem with student ID!");
			this.errlabel.setForeground(Color.blue);
			this.isOk = true;
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
			idCheck();
			IdField.this.onTextChanged.accept(IdField.this.getId());	
		}
	}

	TextFieldDocumentListener listener = new TextFieldDocumentListener();

}
