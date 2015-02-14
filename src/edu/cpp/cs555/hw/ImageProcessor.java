package edu.cpp.cs555.hw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageProcessor {
	
	static int n, m;
	static int bdr, nbdr, mbdr;
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
		m = filterSize;
		bdr = n/2;
		nbdr = n/2;
		mbdr = m/2;
	}
	
	public void setResolution(int height, int width) {
		n = height;
		m = width; 
		nbdr = n/2;
		mbdr = m/2;
	}
	
	public BufferedImage arithmeticMeanFilter(int resh, int resw) {
		System.out.println("Arithmetic mean filter in progress...");
		setResolution(resh, resw);
		int resultHeight = imageHeight+(nbdr*2);
		int resultWidth = imageWidth+(mbdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = smoothAndCrop(result);
		return result;
	}
	
	public BufferedImage geometricMeanFilter(int resh, int resw) {
		System.out.println("Geometric mean filter in progress...");
		setResolution(resh, resw);
		
		int resultHeight = imageHeight+(nbdr*2);
		int resultWidth = imageWidth+(mbdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = multiplyAndCrop(result);
		return result;
	}

	public BufferedImage harmonicMeanFilter(int resh, int resw) {
		System.out.println("Harmonic mean filter in progress...");
		setResolution(resh, resw);
		
		int resultHeight = imageHeight+(nbdr*2);
		int resultWidth = imageWidth+(mbdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = harmonizeAndCrop(result);
		return result;
	}
	
	private static BufferedImage harmonizeAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		double divisorSum = 0;
		int round = 0;
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
				
				// for each pixel, apply this filter:
				for(int p = i-nbdr; p <= i+nbdr; p++) {
					for(int q = j-mbdr; q <= j+mbdr; q++) {
						divisorSum += 1 / getGrayValueD(result.getRGB(q, p));
					}
				}
				double quotient = (m*n) / divisorSum;
				round = (int) quotient;
				round = getRGBValue(round);
				newResult.setRGB(jj, ii, round);
				jj++;
				divisorSum = 0;	
			}
			ii++;
			jj=0;
		}
		return newResult;
	}
	
	
	
	/** From writing this I learned that Math.pow works really poorly with 
	 * datatypes other than doubles */
	private static BufferedImage multiplyAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		double product = 1;
		double pwr = 1;
		int pwrRound = 1;
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
				
				// for each pixel, apply this filter:
				for(int p = i-nbdr; p <= i+nbdr; p++) {
					for(int q = j-mbdr; q <= j+mbdr; q++) {
						product *= getGrayValue(result.getRGB(q, p));
					}
				}
				double divisor = m*n;
				pwr = Math.pow(product, (1/(divisor)));
				pwrRound = (int) pwr;
				pwrRound = getRGBValue(pwrRound);
				newResult.setRGB(jj, ii, pwrRound);
				jj++;
				product = 1;	
			}
			ii++;
			jj=0;
		}
		return newResult;
	}
	
	private static BufferedImage zeroImagePadding(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
				int imageRGB = image.getRGB(jj, ii);
				result.setRGB(j, i, imageRGB);
				jj++;
			}
			ii++;
			jj=0;
		}
		return result;
	}
	
	private static BufferedImage smoothAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		int sum = 0;
		int avg = 0;
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
				// for each pixel, apply this filter:
				for(int p = i-nbdr; p <= i+nbdr; p++) {
					for(int q = j-mbdr; q <= j+mbdr; q++) {
						sum += getGrayValue(result.getRGB(q, p));
					}
				}
				avg = sum / (n*m);
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
	
	private static BufferedImage coloredSmoothAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		int rsum = 0;
		int gsum = 0;
		int bsum = 0;
		int ravg = 0;
		int gavg = 0;
		int bavg = 0;
		int xxx = 0;
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
				// for each pixel, apply this filter:
				for(int p = i-nbdr; p <= i+nbdr; p++) {
					for(int q = j-mbdr; q <= j+mbdr; q++) {
						rsum += getRed(result.getRGB(q, p));
						gsum += getGreen(result.getRGB(q, p));
						bsum += getBlue(result.getRGB(q, p));
					}
				}
				ravg = rsum / (n*m);
				gavg = gsum / (n*m);
				bavg = bsum / (n*m);
				
				xxx = getRGBValue(ravg,gavg,bavg);
				newResult.setRGB(jj, ii, xxx);
				jj++;
				rsum = 0;
				gsum = 0;
				bsum = 0;
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
		int ii = nbdr-1;
		int jj = mbdr-1;
		for(int i = 0; i < nbdr; i++) {
			for(int j = 0; j < mbdr; j++) {
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
				jj--;
			}
			ii--;
			jj=mbdr-1;
		}
		///// top-center
		ii = nbdr-1;
		jj = 0;
		for(int i = 0; i < nbdr; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
				jj++;
			}
			ii--;
			jj=0;
		}
		///// top-right corner
		ii = nbdr-1;
		jj = imageWidth-1;
		for(int i = 0; i < nbdr; i++) {
			for(int j = resultWidth-mbdr; j < resultWidth; j++) {
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
				jj--;
			}
			ii--;
			jj = imageWidth-1;
		}
		///// left edge
		ii = 0;
		jj = mbdr-1;
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = 0; j < mbdr; j++) {
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
				jj--;
			}
			ii++;
			jj=mbdr-1;
		}
		///// bottom-left corner
		ii = imageHeight-1;
		jj = mbdr-1;
		for(int i = resultHeight-nbdr; i < resultHeight; i++) {
			for(int j = 0; j < mbdr; j++) {
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
				jj--;
			}
			ii--;
			jj = mbdr-1;
		}
		///// bottom-center
		ii = imageHeight-1;
		jj = 0;
		for(int i = resultHeight-nbdr; i < resultHeight; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
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
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = resultWidth-mbdr; j < resultWidth; j++) {
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
		for(int i = resultHeight-nbdr; i < resultHeight; i++) {
			for(int j = resultWidth-mbdr; j < resultWidth; j++) {
				imageRGB = image.getRGB(jj,ii);
				result.setRGB(j, i, imageRGB);
				jj--;
			}
			ii--;
			jj = imageWidth-1;
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
	
	public BufferedImage coloredSmoothingFilter(int filterSize) {
		System.out.println("Colored moothing filter in progress...");
		setFilterSize(filterSize);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_INT_RGB);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = coloredSmoothAndCrop(result);
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
	
	private static double getGrayValueD(int rgb) {
		Color c = new Color(rgb);
		double gray = c.getRed();
		return gray;
	}
	
	private static int getRed(int rgb) {
		Color c = new Color(rgb);
		int red = c.getRed();
		return red;
	}
	
	private static int getGreen(int rgb) {
		Color c = new Color(rgb);
		int green = c.getGreen();
		return green;
	}
	
	private static int getBlue(int rgb) {
		Color c = new Color(rgb);
		int blue = c.getBlue();
		return blue;
	}
	
	private static int getRGBValue(int red, int green, int blue) {
		int r = red;
		int g = green;
		int b = blue;
		int col = (r << 16) | (g << 8) | b;
		return col;
	}
	
	private static int getRGBValue(int gray) {
		int r = gray;
		int g = gray;
		int b = gray;
		int col = (r << 16) | (g << 8) | b;
		return col;
	}
	

}
