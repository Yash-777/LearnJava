package com.github.awt.swings;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * A Swing application that uploads files to a HTTP server.
 * 
 * <p>A "PropertyChange" event gets fired whenever a bean changes a "bound" property.
 * You can register a PropertyChangeListener with a source bean so as to be notified of
 * any bound property updates.
 * 
 * <p>For more explanation refer
 * <a href="http://www.codejava.net/coding/swing-application-to-upload-files-to-http-server-with-progress-bar">
 * this URL</a></p>
 * @author yashwanth.m
 *
 */
public class SwingFileUploadHTTP extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = -8436999087526033939L;
	
	// AWT Swing panel options
	private JLabel labelURL = new JLabel("Upload URL: ");
	private JTextField fieldURL = new JTextField(30);

	private JFilePicker filePicker = new JFilePicker("Choose a file: ", "Browse");
	private JButton buttonUpload = new JButton("Upload");

	private JLabel labelProgress = new JLabel("Progress:");
	private JProgressBar progressBar = new JProgressBar(0, 100);

	public void windowClosingEvent() {
		dispose();
		System.out.println("Window is being closed.");
	}
	
	public SwingFileUploadHTTP() {
		super("Swing File Upload to HTTP server");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				windowClosingEvent();
			}
		});
		
		// set up layout
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 5, 5, 5);
		
		// set up components
		filePicker.setMode(JFilePicker.MODE_OPEN);
		
		buttonUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				buttonUploadActionPerformed(event);
			}
		});
		
		progressBar.setPreferredSize(new Dimension(200, 30));
		progressBar.setStringPainted(true);
		
		// add components to the frame
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(labelURL, constraints);
		
		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		add(fieldURL, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.0;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.NONE;
		add(filePicker, constraints);
		
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		add(buttonUpload, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.WEST;
		add(labelProgress, constraints);
		
		constraints.gridx = 1;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(progressBar, constraints);
		
		pack();
		setLocationRelativeTo(null); // center on screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * handle click event of the Upload button
	 */
	private void buttonUploadActionPerformed(ActionEvent event) {
		String uploadURL = fieldURL.getText();
		String filePath = filePicker.getSelectedFilePath();

		// validate input first
		if (uploadURL.equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter upload URL!",
					"Error", JOptionPane.ERROR_MESSAGE);
			fieldURL.requestFocus();
			return;
		}
		if (filePath.equals("")) {
			JOptionPane.showMessageDialog(this, "Please choose a file to upload!",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			File uploadFile = new File(filePath);
			progressBar.setValue(0);

			UploadTask task = new UploadTask(uploadURL, uploadFile);
			task.addPropertyChangeListener(this);
			task.execute();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error executing upload task: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Update the progress bar's state whenever the progress of upload changes.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
	}

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		try {
			// set look and feel to system dependent
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SwingFileUploadHTTP().setVisible(true);
			}
		});
	}
	
	// Inner class to find pick file. 
	class JFilePicker extends JPanel {

		private static final long serialVersionUID = 1L;
		private JLabel label;
		private JTextField textField;
		private JButton button;
		
		private JFileChooser fileChooser;
		
		private int mode;
		public static final int MODE_OPEN = 1;
		public static final int MODE_SAVE = 2;
		
		public JFilePicker(String textFieldLabel, String buttonLabel) {
			fileChooser = new JFileChooser();
			
			setLayout( new FlowLayout(FlowLayout.CENTER, 5, 5) );
			
			// creates the GUI
			label = new JLabel(textFieldLabel);
			
			textField = new JTextField(30);
			button = new JButton(buttonLabel);
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					buttonActionPerformed(evt);
				}
			});
			
			add(label);
			add(textField);
			add(button);
		}
		
		private void buttonActionPerformed(ActionEvent evt) {
			if (mode == MODE_OPEN) {
				if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
					textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			} else if (mode == MODE_SAVE) {
				if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
					textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		}

		public void addFileTypeFilter(String extension, String description) {
			FileTypeFilter filter = new FileTypeFilter(extension, description);
			fileChooser.addChoosableFileFilter(filter);
		}
		
		public void setMode(int mode) {
			this.mode = mode;
		}
		
		public String getSelectedFilePath() {
			return textField.getText();
		}
		
		public JFileChooser getFileChooser() {
			return this.fileChooser;
		}
		
	}
	// Inner class to find file name extension. 
	class FileTypeFilter extends FileFilter {
		
		private String extension;
		private String description;
		
		public FileTypeFilter(String extension, String description) {
			this.extension = extension;
			this.description = description;
		}
		
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			return file.getName().toLowerCase().endsWith(extension);
		}
		
		public String getDescription() {
			return description + String.format(" (*%s)", extension);
		}
	}
}