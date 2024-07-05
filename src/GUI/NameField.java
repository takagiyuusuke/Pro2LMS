package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class NameField extends JPanel {
	private boolean nameIsOk = false;
	
	private JLabel errlabel = new JLabel("");
	private JTextField nameField = new JTextField("");
	
	private Consumer<String> onTextChanged;
	
	
	public NameField(String label) {
		super();
		this.nameField.getDocument().addDocumentListener(this.listener);
		JPanel namePane = new JPanel();
		namePane.setLayout(new GridLayout(1, 0));
		namePane.add(new JLabel(label + ":"));
		namePane.add(this.nameField);
		
		this.setLayout(new GridLayout(0, 1));
		this.add(namePane);
		this.add(this.errlabel);
	}
	
	public void setOnTextChanged(Consumer<String> onTextChanged) {
		this.onTextChanged = onTextChanged;
	}
	
	public String getText() {
		return nameField.getText();
	}
	
	public boolean isOk() {
		return this.nameIsOk;
	}
	
	private boolean nameCheck() {
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
			this.nameIsOk = true;
			return true;
		}
		return false;
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
			if (nameCheck()) {
				NameField.this.onTextChanged.accept(NameField.this.getText());
			}
			
		}
	}

	TextFieldDocumentListener listener = new TextFieldDocumentListener();

}
