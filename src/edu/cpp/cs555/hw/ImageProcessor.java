package edu.cpp.cs555.hw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageProcessor {
	
	static int n;
	static int bdr;
	static int imageHeight;
	static int imageWidth;
	static BufferedImage image, backup;
	
	public ImageProcessor(BufferedImage img) {
		image = img;
		backup = img;
		imageHeight = image.getHeight();
		imageWidth = image.getWidth();
		
	}
	
	public void setFilterSize(int filterSize) {
		n = filterSize;
		bdr = n/2;
	}
	
	private static BufferedImage smoothAndCrop(BufferedImage result) {
		
		int ii = 0;
		int jj = 0;
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		int sum = 0;
		int avg = 0;
		
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				
				// for each pixel, apply this filter:
				for(int p = i-bdr; p <= i+bdr; p++) {
					for(int q = j-bdr; q <= j+bdr; q++) {
						sum += getGrayValue(result.getRGB(q, p));
					}
				}
				
				avg = sum / (n*n);
				avg = getRGBValue(avg);
				newResult.setRGB(jj, ii, avg);
				jj++;
				sum = 0;				
			}
			ii++;
			jj=0;
		}
		
		return newResult;
	}
	
	private static BufferedImage medianAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		int mid = 0;
		
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				
				// for each pixel, apply this filter:
				for(int p = i-bdr; p <= i+bdr; p++) {
					for(int q = j-bdr; q <= j+bdr; q++) {
						neighbors.add(getGrayValue(result.getRGB(q, p)));
					}
				}
				Collections.sort(neighbors);
				mid = neighbors.size() / 2;  // mid = the index of the arraylist that contains the rgb value
				mid = neighbors.get(mid); // mid = the gray value in the middle of the neighbors
				mid = getRGBValue(mid);
				newResult.setRGB(jj, ii, mid);
				jj++;
				neighbors.clear();				
			}
			ii++;
			jj=0;
		}
		return newResult;
	}
	
	private static BufferedImage addReflectivePadding(BufferedImage result) {
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		int imageRGB;
		///// top-left corner
		int ii = bdr-1;
		int jj = bdr-1;
		for(int i = 0; i < bdr; i++) {
			for(int j = 0; j < bdr; j++) {
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
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
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
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
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
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
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
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
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
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
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
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
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
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
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
				jj--;
			}
			ii--;
			jj = imageWidth-1;
		}
		return result;
	}
	
	private static BufferedImage zeroImagePadding(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				int imageRGB = image.getRGB(jj, ii);
				result.setRGB(j, i, imageRGB);
				jj++;
			}
			ii++;
			jj=0;
		}
		return result;
	}
	
	public BufferedImage smoothingFilter(int filterSize) {
		System.out.println("Smoothing filter in progress...");
		setFilterSize(filterSize);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = smoothAndCrop(result);
		return result;
	}
	
	public BufferedImage medianFilter(int filterSize) {
		System.out.println("Median filter in progress...");
		setFilterSize(filterSize);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = medianAndCrop(result);
		return result;
	}
	
	private BufferedImage laplacianAndCrop(BufferedImage result) {
		System.out.println("Laplacian filter in progress...");
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		int r = 0;
		int l = 0;
		int u = 0;
		int d = 0;
		int f = 0;
		int finalRGB = 0;
		int ii = 0;
		int jj = 0;
		
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				
				r = getGrayValue(result.getRGB(j+1, i));
				l = getGrayValue(result.getRGB(j-1, i));
				u = getGrayValue(result.getRGB(j, i+1));
				d = getGrayValue(result.getRGB(j, i-1));
				f = getGrayValue(result.getRGB(j, i));
				finalRGB = getRGBValue((r + l + u + d) - (4 * f)); 
				newResult.setRGB(jj, ii, finalRGB);				
				jj++;
			}
			ii++;
			jj=0;
		}
		
		return newResult;
	}
	
	public BufferedImage bitMask(boolean range, int a, int b) {
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		if(range == true) { // from a to b 
			System.out.println("Bit mask for bit plane from " + a + " to " + b + " in progress...");
			for(int i = 0; i < imageHeight; i++) {
				for(int j = 0; j < imageWidth; j++) {
					int rgb = getGrayValue(image.getRGB(j,i));
					for(int c = b; c >= a; c--) {
						c = getBitValue(c);
						if(rgb >= c) rgb = rgb - c;	
					}
					rgb = getRGBValue(rgb);
					newResult.setRGB(j,i,rgb);
				}
			}
		}
		else {
			System.out.println("Bit mask for bit plane " + a + " in progress...");
			for(int i = 0; i < imageHeight; i++) {
				for(int j = 0; j < imageWidth; j++) {
					int rgb = image.getRGB(j,i);
					rgb = getGrayValue(rgb);
					a = getBitValue(a);
					if(rgb >= a) a = rgb - a;
					a = getRGBValue(a);
					newResult.setRGB(j,i,a);
				}
			}
		}
		return newResult;
	}
	
	private static int getBitValue(int a) {
		if (a == 0) return 1;
		else if (a == 1) return 2;
		else if (a == 2) return 4;
		else if (a == 3) return 8;
		else if (a == 4) return 18;
		else if (a == 5) return 32;
		else if (a == 6) return 64;
		else if (a == 7) return 128;
		else return 0;
	}
	
	public BufferedImage laplacianFilter() {
		System.out.println("Laplacian filter in progress...");
		setFilterSize(3);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = laplacianAndCrop(result);
		return result;
	}
	
	public BufferedImage highboostFilter(int A) {
		System.out.println("High-boost filter in progress...");
		setFilterSize(3);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = laplacianAndCrop(result);
		
		BufferedImage i = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);		
		i = copyImage(image, i);
		
		i = multiplyImageByConstant(i, A);
		result = addImages(i, result);
		return result;
	}
	
	private static BufferedImage copyImage(BufferedImage toCopy, BufferedImage result) {
		if (toCopy.getHeight() == result.getHeight() && toCopy.getWidth() == result.getWidth()) {
			int height = toCopy.getHeight();
			int width = toCopy.getWidth();
			
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					result.setRGB(j, i, toCopy.getRGB(j, i));
				}
			}
			return result;
		}
		else {
			System.out.println("Dimensions do not match!");
			return toCopy;
		}
	}
	
	private static BufferedImage addImages(BufferedImage image1, BufferedImage image2) {
		
		if (image1.getHeight() == image2.getHeight() && image1.getWidth() == image2.getWidth()) {
			int height = image1.getHeight();
			int width = image1.getWidth();
			BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
						
			int rgb1 = 0;
			int rgb2 = 0;
			int resultRGB = 0;
			
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					
					rgb1 = getGrayValue(image1.getRGB(j,i));
					rgb2 = getGrayValue(image2.getRGB(j,i));
					resultRGB = getRGBValue(rgb1 + rgb2);
					
					result.setRGB(j, i, resultRGB);
					
				}
			}
			return result;
		}
		
		else {
			System.out.println("Dimensions do not match!");
			return image;
		}
	}
	
	private static BufferedImage multiplyImageByConstant(BufferedImage result, int A) {
		int height = result.getHeight();
		int width = result.getWidth();
		int value = 0;
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				value = getGrayValue(result.getRGB(j, i));
				value = value * (A-1);
				value = getRGBValue(value);
				result.setRGB(j, i, value);
			}
		}
		return result;
	}
	
	private static int getGrayValue(int rgb) {
		Color c = new Color(rgb);
		int gray = c.getRed();
		return gray;
	}
	
	private static int getRGBValue(int gray) {
		int r = gray;
		int g = gray;
		int b = gray;
		int col = (r << 16) | (g << 8) | b;
		return col;
	}
	

}
