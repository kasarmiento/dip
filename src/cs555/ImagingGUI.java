package cs555;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImagingGUI extends JFrame {
	
	/* Sets the width of the main tool bar window */
	private static final int WIDTH = 500;
	
	/* Sets the height of the main tool bar window */
	private static final int HEIGHT = 80;
	
	/* Buttons */
	private JButton browseB, displayB, goB;
	
	/* Label: Displays the image's file name */
	private JLabel imageFileNameL;
	
	/* File path for image */
	private File file;
	
	/* Combo box for processing techniques */
	private JComboBox processList;
	
	/* Button handlers */
	private BrowseButtonHandler browseBHandler;
	private DisplayButtonHandler displayBHandler;
	private GoButtonHandler goBHandler;
	
	/* File chooser for the Browse button */
	private JFileChooser fc;
	
	/* Buffered Images */
	private BufferedImage myImage, backupImage;
	
	/* Image Processor */
	private ImageProcessor ip;
	
	/** 
	 * Creates the main toolbar for imaging assignment 1. 
	 */
	public ImagingGUI() {
		
		/* Create a new image processor object */
		ip = new ImageProcessor();
		
		/* Browse button and functionality */
		browseB = new JButton("Browse");
		browseBHandler = new BrowseButtonHandler();
		browseB.addActionListener(browseBHandler);
		
		/* Image file name label */
		imageFileNameL = new JLabel("No file selected");
		
		/* Display button and functionality */
		displayB = new JButton("Display");
		displayBHandler = new DisplayButtonHandler();
		displayB.addActionListener(displayBHandler);
		
		/* File chooser */
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG Images", "jpg", "jpeg");
		fc.setFileFilter(filter);
		
		/* Combo box set-up */
		String[] processLabels = { "0. Subsample and Replication, from 640 x 480",
				"1. Subsample and Replication, from 80 x 60", 
				"2. Subsample and Nearest Neighbor, from 640 x 480",
				"3. Subsample and Nearest Neighbor, from 80 x 60", 
				"Option 5" };
		processList = new JComboBox(processLabels);
		processList.setSelectedIndex(0);
		
		/* Go button and functionality */
		goB = new JButton("Go!");
		goBHandler = new GoButtonHandler();
		goB.addActionListener(goBHandler);
		
		/* Get the content pane of this window */
		Container pane = getContentPane();
		
		/* Set the layout of the tool bar */
		pane.setLayout(new GridLayout(1,5));
		
		/* Add all components into the JPane */
		pane.add(browseB);
		pane.add(imageFileNameL);
		pane.add(displayB);
		pane.add(processList);
		pane.add(goB);
		
		/* Main Window Setup */
		setTitle("Imaging Assignment 1");
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/* BrowseButtonHandler opens a new file chooser dialog  */
	public class BrowseButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			int returnVal = fc.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				
				/* Open up a file chooser and get the file name. */
				file = fc.getSelectedFile();
				imageFileNameL.setText(file.getName());
				
				/* Load the image, ready for use with other methods. */
				myImage = loadImage(file);
				
				/* Backup Image */
				backupImage = myImage;
				
			}
		}
		
		/* File IO for the image */
		private BufferedImage loadImage(File file) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(file.getAbsoluteFile());
			} catch (IOException e) {
				System.out.println("The image was unable to load. ");
			}
			return img;
		}
	}
	
	/* DisplayButtonHandler pops the input image into a new window */
	public class DisplayButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			buildWindowFromFilePath();
		}
		
		private void buildWindowFromFilePath() {
			/* Create a new window */
			JFrame displayInputImage = new JFrame("Input");
			
			/* Create a new image panel */
			JPanel imagePanel = new JPanel();

			/* Load display image as an icon */
			ImageIcon inputImage = new ImageIcon(file.getAbsolutePath());
			
			/* Add icon to image panel as a JLabel */
			imagePanel.add(new JLabel(inputImage));
		
			
			displayInputImage.add(imagePanel);
			displayInputImage.pack();
			
			/* Input Image Window Setup */
			displayInputImage.setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		}
		
	}
	
	/* Go button processes the image and displays the output. */
	public class GoButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			
			int process = processList.getSelectedIndex();
			BufferedImage result = myImage;
			
			// subsample and replication for both sizes
			if(process == 0 || process == 1) { 
				result = ip.subsampleAndReplication(myImage, process);
				createOutputWindow(result);
				
			}
			
			
			
		}
		
	}
	
	/* Pretty Print:
	 * A pretty and convenient way to print out a 2d array
	 */
	private static void prettyPrint2d(int[][] array) {
		for(int y = 0; y < array.length; y++) {
			for(int x = 0; x < array[y].length; x++) {
				System.out.print(array[y][x] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * The GUI for displaying an image.
	 * @param myImage
	 */
	private void createOutputWindow(BufferedImage myImage)
	{
		/* Create a new window */
		JFrame displayInputImage = new JFrame("Output");
		
		/* Create a new image panel */
		JPanel imagePanel = new JPanel();

		/* Load display image as an icon */
		ImageIcon inputImage = new ImageIcon(myImage);
		
		/* Add icon to image panel as a JLabel */
		imagePanel.add(new JLabel(inputImage));
		
		displayInputImage.add(imagePanel);
		displayInputImage.pack();
		
		/* Input Image Window Setup */
		displayInputImage.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) 
	{
		ImagingGUI imagingGUI = new ImagingGUI();
	}

}
