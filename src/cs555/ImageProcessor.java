package cs555;

import java.awt.image.BufferedImage;

import marvin.image.MarvinImage;

public class ImageProcessor {
	
	public static BufferedImage subsampleAndReplication(BufferedImage inputImg, int process) {
		BufferedImage img = inputImg;
		img = subsample(img, process);
		img = zoom(img, process);
		return img;
	}
	
	/* Size options:
	 * 0 => 640 x 480
	 * 1 => 80 x 60*/
	public static BufferedImage subsample(BufferedImage img, int sizeOption) {
		
		// 0 => Subsample and Replication, from 640 x 480
		// 2 => Subsample and Nearest Neighbor, from 640 x 480
		if(sizeOption == 0 || sizeOption == 2) {
			return shrink(img, 640, 480);
			
		} // 1 => Subsample and Replication, from 80 x 60
		  // 3 => Subsample and Nearest Neighbor, from 80 x 60
		else if(sizeOption == 1 || sizeOption == 3) {
			return shrink(img, 80, 60);
			
		} else {
			System.out.println("Invalid size option.");
			return img;
		}
		
	}
	
	public static BufferedImage zoom(BufferedImage img, int process) {
		
		// 0 => Subsample and Replication, from 640 x 480
		// 1 => Subsample and Replication, from 80 x 60
		if(process == 0 || process == 1) {
			return replicate(img);
			
		} // 2 => Subsample and Nearest Neighbor, from 640 x 480
		  // 3 => Subsample and Nearest Neighbor, from 80 x 60
		else if (process == 2 || process == 3) {
			return nearestNeighbor(img);
			
		}
		
		
		return img;
	}
	
	private static BufferedImage replicate(BufferedImage img) {
		return img;
	}
	
	private static BufferedImage nearestNeighbor(BufferedImage img) {
		return img;
	}
	
	private static BufferedImage shrink(BufferedImage img, int width, int height) {
		BufferedImage smallerImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int coef_col = img.getWidth() / width;
		int coef_row = img.getHeight() / height;
		
		// Redeem pixel value and place it into new smaller array
		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
				int rgb = img.getRGB(col * coef_col, row * coef_row);
				smallerImg.setRGB(col, row, rgb);
			}
		}
		
		return smallerImg;
		
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
