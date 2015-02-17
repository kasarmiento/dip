package edu.cpp.cs555.hw;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JComboBox;

import edu.cpp.cs555.hw.ImageProcessor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagingGui extends JFrame {

	private JPanel contentPane;
	private File file;
	private BufferedImage myImage, bw;

	/**
	 * Launch the application.
	 */
	
	//////////////////////////////////  t e s t   a r e a  //////////////////////////////////
	
	static int n = 5;
	static int bdr = n/2;
	static int imageHeight = 9;
	static int imageWidth = 17;
	
	static int[][] image = new int[imageHeight][imageWidth];
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImagingGui frame = new ImagingGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImagingGui() {
		setTitle("Image Processing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInputImagePlease = new JLabel("Input Image: Please select a jpeg image to process");
		lblInputImagePlease.setBounds(12, 13, 408, 15);
		lblInputImagePlease.setHorizontalAlignment(SwingConstants.CENTER);
		lblInputImagePlease.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblInputImagePlease);
		
		JLabel lblNoFileSelected = new JLabel("No file selected");
		lblNoFileSelected.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoFileSelected.setBounds(12, 48, 236, 16);
		contentPane.add(lblNoFileSelected);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "JPG & GIF Images", "jpg", "gif");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	file = chooser.getSelectedFile();
			    	System.out.println("You chose to open this file: " +
			            file.getName());
			    	lblNoFileSelected.setText(file.getName());
			    	myImage = loadColorImage(file);
			    	myImage = convert2Gray(myImage);
			    }
			 
			}
			
			private BufferedImage convert2Gray(BufferedImage image) {
				BufferedImage grayImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				for(int i = 0; i < image.getHeight(); i++) {
					for(int j = 0; j < image.getWidth(); j++) {
						int value = image.getRGB(j, i);
						grayImg.setRGB(j, i, value);
					}
				}
				return grayImg;
			}
			
			/**
			 * File IO for the image */
			private BufferedImage loadColorImage(File file) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(file.getAbsoluteFile());
				} catch (IOException e) {
					System.out.println("The image was unable to load. ");
				}
				return img;
			}
		});
		btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBrowse.setBounds(260, 44, 75, 25);
		contentPane.add(btnBrowse);
		
		JButton btnDisplay = new JButton("Display");
		btnDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JPanel imagePanel = new JPanel();
				JFrame displayInputImage = new JFrame("Input");
				ImageIcon inputImage = new ImageIcon(myImage/*file.getAbsolutePath()*/);
				imagePanel.add(new JLabel(inputImage));
				displayInputImage.getContentPane().add(imagePanel);
				displayInputImage.pack();
				
				/* Input Image Window Setup */
				displayInputImage.setVisible(true);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				
			}
		});
		btnDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDisplay.setBounds(347, 44, 73, 25);
		contentPane.add(btnDisplay);
		
		JLabel lblProcessPleaseSelect = new JLabel("Process: Please select a process from the dropdown menu");
		lblProcessPleaseSelect.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProcessPleaseSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblProcessPleaseSelect.setBounds(12, 94, 408, 16);
		contentPane.add(lblProcessPleaseSelect);
		
		String[] options = {
				/*0*/"============= Spatial Filtering =============",
				/*1*/"Smoothing Filter",
				/*2*/"Median Filter",
				/*3*/"Sharpening Laplacian filter",
				/*4*/"High-boosting Filter",
				/*5*/"================ Bit-Masking ===============",
				/*6*/"Mask Bits 1-4",
				/*7*/"Mask Bits 3-6",
				/*8*/"Mask Bits 5-8",
				/*9*/"=========== Restoration Filters ============",
				/*10*/"Arithmetic Mean Filter",
				/*11*/"Geometric Mean Filter",
			    /*12*/"Harmonic Mean Filter",
			    /*13*/"Contraharmonic Mean Filter",
			    /*14*/"Max Filter",
			    /*15*/"Min Filter",
			    /*16*/"Midpoint Filter",
			    /*17*/"Alpha-trimmed Mean Filter",
			    /*18*/"=============== Color Filters ==============",
				/*19*/"Color Smoothing Filter",
				/*20*/"Color Max Filter"};
		JComboBox comboBox = new JComboBox(options);
		comboBox.setBounds(12, 123, 323, 22);
		contentPane.add(comboBox);
		
		JButton btnProcess = new JButton("Go!");
		btnProcess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ImageProcessor ip = new ImageProcessor(myImage);
				BufferedImage result = myImage;
				
				int process = comboBox.getSelectedIndex();
				switch(process) {
					case 0: { // no action
						break;
					}
					case 1: { // smoothing filter
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						result = ip.smoothingFilter(Integer.parseInt(filterSize));
						createOutputWindow(result);
						break;
					}
					case 2: { // median filter
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						result = ip.medianFilter(Integer.parseInt(filterSize));
						createOutputWindow(result);
						break;
					}
					case 3: { // sharpening laplacian filter
						result = ip.laplacianFilter();
						createOutputWindow(result);
						break;
					}
					case 4: { // high-boost filter
						String A = JOptionPane.showInputDialog(null, "Please enter a value for coefficient A (A >= 1)");
						result = ip.highboostFilter(Integer.parseInt(A));
						createOutputWindow(result);
						break;
					}
					case 5: { // no action
						break;
					}
					case 6: { // bit-plane masking 1-4
						result = ip.bitMask(1, 4);
						createOutputWindow(result);
						break;
					}
					case 7: { // bit-plane masking 3-6
						result = ip.bitMask(3, 6);
						createOutputWindow(result);
						break;
					}
					case 8: { // bit-plane masking 5-8
						result = ip.bitMask(5, 8);
						createOutputWindow(result);
						break;
					}
					
					case 9: { // no action
						break;
					}
					case 10: { // arithmetic mean filter
						String resolution = JOptionPane.showInputDialog(null, "Please enter the resolution of your mask (ex: 3x3)");
						String[] dim = resolution.split("x");
						int height = Integer.parseInt(dim[1]);
						int width = Integer.parseInt(dim[0]);
						result = ip.arithmeticMeanFilter(height, width);
						createOutputWindow(result);
						break;
					}
					case 11: { // geometric mean filter
						String resolution = JOptionPane.showInputDialog(null, "Please enter the resolution of your mask (ex: 3x3)");
						String[] dim = resolution.split("x");
						int height = Integer.parseInt(dim[1]);
						int width = Integer.parseInt(dim[0]);
						result = ip.geometricMeanFilter(height, width);
						createOutputWindow(result);
						break;
					}
					case 12: { // harmonic mean filter
						String resolution = JOptionPane.showInputDialog(null, "Please enter the resolution of your mask (ex: 3x3)");
						String[] dim = resolution.split("x");
						int height = Integer.parseInt(dim[1]);
						int width = Integer.parseInt(dim[0]);
						result = ip.harmonicMeanFilter(height, width);
						createOutputWindow(result);
						break;
					}
					case 13: { // contraharmonic mean filter
						String resolution = JOptionPane.showInputDialog(null, "Please enter the resolution of your mask (ex: 3x3)");
						String[] dim = resolution.split("x");
						int height = Integer.parseInt(dim[1]);
						int width = Integer.parseInt(dim[0]);
						String q = JOptionPane.showInputDialog(null, "Please enter a value for Q");
						double Q = Double.parseDouble(q);
						result = ip.contraharmonicMeanFilter(Q, height, width);
						createOutputWindow(result);
						break;
					}
					case 14: { // max filter
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						result = ip.maxFilter(Integer.parseInt(filterSize));
						createOutputWindow(result);
						break;
					}
					case 15: { // min filter
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						result = ip.minFilter(Integer.parseInt(filterSize));
						createOutputWindow(result);
						break;
					}
					case 16: { // midpoint filter
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						result = ip.midpointFilter(Integer.parseInt(filterSize));
						createOutputWindow(result);
						break;
					}
					case 17: { // alpha-trimmed mean filter
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						String d = JOptionPane.showInputDialog(null, "Please enter a value for d (0 >= d >= " + imageWidth + "*" + imageHeight + "-1)");
						result = ip.alphaFilter(Integer.parseInt(filterSize), Integer.parseInt(d));
						createOutputWindow(result);
						break;
					}
					case 18: { 
						break;
					}
					case 19: { // color smooth and crop
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						result = ip.coloredSmoothingFilter(Integer.parseInt(filterSize));
						createOutputWindow(result);
						break;
					}
					case 20: { // color max filter
						String filterSize = JOptionPane.showInputDialog(null, "Please enter the size of your filter (odd numbers only)");
						result = ip.colorMaxFilter(Integer.parseInt(filterSize));
						createOutputWindow(result);
						break;
					}
					default: {
						break;	
					}
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
			
		});
		btnProcess.setBounds(347, 123, 73, 25);
		contentPane.add(btnProcess);
		
		JLabel lblKhamilleS = new JLabel("Khamille S - Digital Image Processing - Raheja cs555");
		lblKhamilleS.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblKhamilleS.setHorizontalAlignment(SwingConstants.CENTER);
		lblKhamilleS.setBounds(12, 158, 408, 16);
		contentPane.add(lblKhamilleS);
	}
}
