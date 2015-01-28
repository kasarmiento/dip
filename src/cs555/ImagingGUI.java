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
	
	/** 
	 * Creates the main toolbar for imaging assignment 1. 
	 */
	public ImagingGUI() {
		
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
		String[] processLabels = { "Grayscale", "Option 2", "Option 3",
				"Option 4", "Option 5" };
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
				
			}
		}
		
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
			buildWindow();
		}
		
		private void buildWindow() {
			
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
			
			/* Retrieve image width and height for new window size */
			int imageWidth = myImage.getWidth();
			int imageHeight = myImage.getHeight();
			
			/* Input Image Window Setup */
			displayInputImage.setSize(imageWidth, imageHeight);
			displayInputImage.setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		}
		
	}
	
	public class GoButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			int process = processList.getSelectedIndex();
			if(process == 0) {
				subsampleAndReplication();
				System.out.println("backup Image: " + backupImage.getWidth() + "x" + backupImage.getHeight());
				//createOutputWindow(backupImage);
			}
		}
		
		/* Subsample and Replication:
		 * Shrink the photo by a factor of 8 then zoom in with
		 * Replication Method.
		 */
		private void subsampleAndReplication() {
			//int[][] imgArray = getImageArray(myImage);
			//backupImage = shrinkImg(imgArray);
			//backupImage = zoomByReplication(backupImage);
		}
		 
		/* Get Image Array: 
		 * Gets the values of a MarvinImage and puts it into a 2d array
		 * with a gray scale value for each pixel.
		 * @param inputImage - a MarvinImage object that represents your input image
		 * @return the 2d array that contains the images' gray scale pixel values
		 */
		private int[][] getImageArray(MarvinImage inputImage) {
			
			// Create the 2d array
			int width = inputImage.getWidth();
			int height = inputImage.getHeight();
			int[][] tmp = new int[height][width];			
			
			// Process each gray scale pixel value
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					tmp[y][x] = inputImage.getIntComponent0(x,y);
				}
			}
			return tmp;
		}

		/**
		 * Transforms a large 2d array and shrinks it by a factor of 8.
		 * @param origImgArray
		 * @return
		 */
		private MarvinImage shrinkImg(int[][] origImgArray)
		{
			// New dimensions for the smaller image
			int width = origImgArray[0].length/8;
			int height = origImgArray.length/8;
			
			// Temporary small array
			int[][] tmp = new int[height][width];
			
			// Redeem pixel value and place it into new smaller array
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
					tmp[y][x] = origImgArray[y*8][x*8];
				}
			}
			MarvinImage imageObject = makeIntoImage(tmp);
			return imageObject;
		}
		
		/**
		 * Turns a 2d array into a MarvinImage Object
		 * @param array
		 * @return
		 */
		private MarvinImage makeIntoImage(int[][] array)	{
			int width = array[0].length;
			int height = array.length;
			MarvinImage smallerImage = new MarvinImage(width, height);
			
			for(int rows = 0; rows < height; rows++) {
				for(int cols = 0; cols < width; cols++) {
					int grayValue = array[rows][cols];
					smallerImage.setIntColor(cols, rows, grayValue, grayValue, grayValue);
				}
			}
			return smallerImage;
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
	private void createOutputWindow(MarvinImage myImage)
	{
		/* Create a new window */
		JFrame displayInputImage = new JFrame("Output");
		
		/* Create a new image panel */
		MarvinImagePanel imagePanel = new MarvinImagePanel();
		
		/* Add image panel into the window */
		displayInputImage.add(imagePanel);
		
		/* Add to image panel */
		imagePanel.setImage(myImage);
		
		/* Retrieve image width and height for new window size */
		int imageWidth = myImage.getWidth();
		int imageHeight = myImage.getHeight();
		
		/* Input Image Window Setup */
		displayInputImage.setSize(imageWidth, imageHeight);
		displayInputImage.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) 
	{
		ImagingGUI imagingGUI = new ImagingGUI();
	}

}
