package cs555;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame
{
	
	/** Sets the width of the main tool bar window */
	private static final int WIDTH = 500;
	
	/** Sets the height of the main tool bar window */
	private static final int HEIGHT = 80;
	
	/** Buttons */
	private JButton browseB, displayB, goB;
	
	/** Label: Displays the image's file name */
	private JLabel imageL;
	
	/** File path for image */
	private File file;
	
	/** Combo box for processing techniques */
	private JComboBox processList;
	
	/** Button handlers */
	private BrowseButtonHandler browseBHandler;
	private DisplayButtonHandler displayBHandler;
	private GoButtonHandler goBHandler;
	
	/** File chooser for the Browse button */
	private JFileChooser fc;
	
	
	/** 
	 * Constructor: Creates the main tool bar for 
	 * imaging assignment 1. 
	 */
	public Main() 
	{
		
		/* Browse button and functionality */
		browseB = new JButton("Browse");
		browseBHandler = new BrowseButtonHandler();
		browseB.addActionListener(browseBHandler);
		
		/* Image file name label */
		imageL = new JLabel("No file selected");
		
		/* Display button and functionality */
		displayB = new JButton("Display");
		displayBHandler = new DisplayButtonHandler();
		displayB.addActionListener(displayBHandler);
		
		/* File chooser */
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG Images", "jpg", "jpeg");
		fc.setFileFilter(filter);
		
		/* Go button and functionality */
		goB = new JButton("Go!");
		goBHandler = new GoButtonHandler();
		goB.addActionListener(goBHandler);
		
		/* Combo box set-up */
		String[] processLabels = { "Option 1", "Option 2", "Option 3",
				"Option 4", "Option 5" };
		/* The physical combo box */
		processList = new JComboBox(processLabels);
		processList.setSelectedIndex(0);
		
		
		/* Get the content pane of this window */
		Container pane = getContentPane();
		
		/* Set the layout of the tool bar */
		pane.setLayout(new GridLayout(1,5));
		
		/* Add all components into the JPane */
		pane.add(browseB);
		pane.add(imageL);
		pane.add(displayB);
		pane.add(processList);
		pane.add(goB);
		
		
		/* Main Window Setup */
		setTitle("CS 555 - Image Processing Assignment 1");
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public class BrowseButtonHandler implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			int returnVal = fc.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				file = fc.getSelectedFile();
				imageL.setText(file.getName());
			}
		}
	}
	
	public class DisplayButtonHandler implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			JFrame displayInputImage = new JFrame();
			
			
			
			
			displayInputImage.setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	
	public class GoButtonHandler implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			
		}
	}
	
	public static void main(String[] args) 
	{
		Main main = new Main();
	}

}
