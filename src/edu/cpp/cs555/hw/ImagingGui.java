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
	private BufferedImage myImage;

	/**
	 * Launch the application.
	 */
	
	//////////////////////////////////  t e s t   a r e a  //////////////////////////////////
	
	static int n = 5;
	static int bdr = n/2;
	static int imageHeight = 9;
	static int imageWidth = 17;
	
	static int[][] image = new int[imageHeight][imageWidth];
	
	private static void populateArray() {
		for(int i = 0; i < imageHeight; i++) { // up&down
			for(int j = 0; j < imageWidth; j++) { // across
				image[i][j] = (int) (Math.random()*10);
			}
		}
	}
	
	private static void printArray(int[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private static void imagePadding(int[][] result) {
		int ii = 0;
		int jj = 0;
		int resultHeight = result.length;
		int resultWidth = result[0].length;
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				result[i][j] = image[ii][jj];
				jj++;
			}
			ii++;
			jj=0;
		}
	}
	
	private static void reflectPadding(int[][] result) {
		int resultHeight = result.length;
		int resultWidth = result[0].length;
		
		///// top-left corner
		int ii = bdr-1;
		int jj = bdr-1;
		for(int i = 0; i < bdr; i++) {
			for(int j = 0; j < bdr; j++) {
				result[i][j] = image[ii][jj];
				jj--;
			}
			ii--;
			jj=bdr-1;
		}
		
		///// top-center
		ii = bdr-1;
		jj = 0;
		for(int i = 0; i < bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				result[i][j] = image[ii][jj];
				jj++;
			}
			ii--;
			jj=0;
		}
		
		///// top-right corner
		ii = bdr-1;
		jj = imageWidth-1;
		for(int i = 0; i < bdr; i++) {
			for(int j = resultWidth-bdr; j < resultWidth; j++) {
				result[i][j] = image[ii][jj];
				jj--;
			}
			ii--;
			jj = imageWidth-1;
		}
		
		///// left edge
		ii = 0;
		jj = bdr-1;
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = 0; j < bdr; j++) {
				result[i][j] = image[ii][jj];
				jj--;
			}
			ii++;
			jj=bdr-1;
		}
		
		///// bottom-left corner
		ii = imageHeight-1;
		jj = bdr-1;
		for(int i = resultHeight-bdr; i < resultHeight; i++) {
			for(int j = 0; j < bdr; j++) {
				result[i][j] = image[ii][jj];
				jj--;
			}
			ii--;
			jj = bdr-1;
		}
		
		///// bottom-center
		ii = imageHeight-1;
		jj = 0;
		for(int i = resultHeight-bdr; i < resultHeight; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				result[i][j] = image[ii][jj];
				jj++;
			}
			ii--;
			jj=0;
		}
		
		///// right edge
		ii = 0;
		jj = imageWidth-1;
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = resultWidth-bdr; j < resultWidth; j++) {
				result[i][j] = image[ii][jj];
				jj--;
			}
			ii++;
			jj=imageWidth-1;
		}
		
		///// bottom-right corner
		ii = imageHeight-1;
		jj = imageWidth-1;
		for(int i = resultHeight-bdr; i < resultHeight; i++) {
			for(int j = resultWidth-bdr; j < resultWidth; j++) {
				result[i][j] = image[ii][jj];
				jj--;
			}
			ii--;
			jj = imageWidth-1;
		}
	}
	
	public static void main(String[] args) {
		
		/**
		
		populateArray();
		printArray(image);
		
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		
		System.out.println();
		 
		int[][] result = new int[resultHeight][resultWidth];
		imagePadding(result);
		printArray(result);
		
		System.out.println();
		System.out.println();
		
		reflectPadding(result);
		System.out.println("Printing with reflective padding:");
		printArray(result);
		
		System.out.println();
		
		int ii = 0;
		int jj = 0;
		int[][] newResult = new int[imageHeight][imageWidth];
		
		int sum = 0;
		int avg = 0;
		
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				
				// for each pixel, apply this filter:
				for(int p = i-bdr; p <= i+bdr; p++) {
					for(int q = j-bdr; q <= j+bdr; q++) {
						sum += result[p][q];
					}
				}
				
				avg = sum / (n*n);
				newResult[ii][jj] = avg;
				jj++;
				sum = 0;				
			}
			ii++;
			jj=0;
		}

		System.out.println("Printing to test inner area:");
		printArray(newResult);
		
		*/
		
		
		////////////////////////////////// t e s t   a r e a  //////////////////////////////////	
		
		
		
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
				/*4*/"High-boosting filter",
				/*5*/"================ Bit-Masking ===============",
				/*6*/"Bit Mask"};
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
					case 6: { // bit-plane masking
						String value = JOptionPane.showInputDialog(null, "Please enter a value or a range of bit-planes to mask (Format: '5' or '0-4', cannot exceed 7)");
						boolean range = false;
						if(value.length() == 1) { // if the value inputed is only one bit-plane
							result = ip.bitMask(range, Integer.parseInt(value), 99);
						}
						else {
							int a = Integer.parseInt(value.substring(0,1));
							int b = Integer.parseInt(value.substring(2));
							range = true;
							result = ip.bitMask(range, a, b);
						}
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
