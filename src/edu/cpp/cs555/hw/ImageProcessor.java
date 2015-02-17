package edu.cpp.cs555.hw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageProcessor {
	
	/**
	 * Height (n) and width (m) of the processing filter.
	 */
	static int n, m;
	
	/**
	 * When applying a filter with size n*m, it is crucial
	 * to process all pixels of the image. In order to accomodate
	 * for the pixels along the edge of an image, we create a 
	 * border of size n/2=nbdr and m/2=mbdr around the image. 
	 */
	static int bdr, nbdr, mbdr;
	
	/**
	 * The height of the image to be processed.
	 */
	static int imageHeight;
	
	/**
	 * The width of the image to be processed.
	 */
	static int imageWidth;
	
	/**
	 * The image object.
	 */
	static BufferedImage image;
	
	/**
	 * ImageProcessor Constructor builds an image processor
	 * that works to process an image, which is specified 
	 * in the parameter.
	 * 
	 * @param img BufferedImage object that represents the image to be processed
	 */
	public ImageProcessor(BufferedImage img) {
		image = img;
		imageHeight = image.getHeight();
		imageWidth = image.getWidth();
	}
	
	/**
	 * Sets the filter size for a square shaped filter n*n.
	 * @param filterSize The square dimension of a filter.
	 */
	private void setFilterSize(int filterSize) {
		n = filterSize;
		m = filterSize;
		bdr = n/2;
		nbdr = n/2;
		mbdr = m/2;
	}
	
	/**
	 * Sets the resolution size for a filter. 
	 * Allows the resolution to be a rectangle n*m.
	 * 
	 * @param height Height of the filter
	 * @param width Width of the filter
	 */
	private void setResolution(int height, int width) {
		n = height;
		m = width; 
		nbdr = n/2;
		mbdr = m/2;
	}
	
	/**
	 * Blurs an image/smooths a image in grayscale for noise
	 * reduction. This smoothing filter is implemented using 
	 * an averaging method amongst its neighbors.
	 * 
	 * @param filterSize The dimension of a square-shapped filter
	 * @return Smoothed out image
	 */
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

	/**
	 * Blurs an image/smooths a image in color for noise
	 * reduction. This smoothing filter is implemented using 
	 * an averaging method amongst its neighbors for each
	 * color channel.
	 * 
	 * @param filterSize The dimension of a square-shapped filter
	 * @return Smoothed out color image
	 */
	public BufferedImage coloredSmoothingFilter(int filterSize) {
		System.out.println("Colored smoothing filter in progress...");
		setFilterSize(filterSize);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_INT_RGB);
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = coloredSmoothAndCrop(result);
		return result;
	}

	/**
	 * Replaces the value of a pixel by the median
	 * of gray levels in the neighborhood of that pixel.
	 *  
	 * Median filters are particularly good at removing
	 * impulse noise/salt-and-pepper noise.
	 * 
	 * @param filterSize The dimension of a square-shapped filter
	 * @return Smoothed out image
	 */
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

	/**
	 * Replaces the value of a pixel by the largest 
	 * gray level in the neighborhood of that pixel.
	 *  
	 * This filter is particularly good for finding the 
	 * brightest points in an image.
	 * 
	 * @param filterSize The dimension of a square-shapped filter
	 * @return Smoothed out image with brightest points highlighted
	 */
	public BufferedImage maxFilter(int filterSize) {
		System.out.println("Max filter in progress...");
		setFilterSize(filterSize);
		
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = maxAndCrop(result);
		return result;
	}

	/**
	 * Replaces the value of a pixel by the smallest 
	 * gray level in the neighborhood of that pixel. 
	 * 
	 * This filter is particularly good for finding the 
	 * darkest points in an image.
	 * 
	 * @param filterSize The dimension of a square-shapped filter.
	 * @return Smoothed out image with brightest points highlighted
	 */
	public BufferedImage minFilter(int filterSize) {
		System.out.println("Min filter in progress...");
		setFilterSize(filterSize);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = minAndCrop(result);
		return result;
	}
	
	/**
	 * Replaces the value of a pixel by the smallest 
	 * gray level in the neighborhood of that pixel. 
	 * 
	 * This filter is particularly good for finding the 
	 * darkest points in an image.
	 * 
	 * @param filterSize The dimension of a square-shapped filter
	 * @return Smoothed out color image with brightest points highlighted
	 */
	public BufferedImage colorMaxFilter(int filterSize) {
		System.out.println("Min filter in progress...");
		setFilterSize(filterSize);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_INT_RGB);
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = colorMaxAndCrop(result);
		return result;
	}

	/**
	 * Replaces each pixel with the midpoint between the
	 * max and min values within that pixel's neighborhood.
	 * 
	 * Midpoint Filter is good for randomly distributed 
	 * noise like Gaussian noise, or for uniform noise.
	 * 
	 * @param filterSize Desired square dimension for this filter
	 * @return Image processed using midpoint filter
	 */
	public BufferedImage midpointFilter(int filterSize) {
		System.out.println("Midpoint filter in progress...");
		setFilterSize(filterSize);
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = midpointAndCrop(result);
		return result;
	}
	
	/**
	 * The alpha-trimmed filter extracts the lowest d pixels
	 * and the highest d pixels, and with the remaining pixels
	 * finds the average value to replace the original pixel.
	 * 
	 * Good for use on images that involve multiple types of
	 * noise, i.e. salt-and-peper noise mixed with Gaussian noise.
	 * 
	 * @param filterSize Desired square dimension for this filter
	 * @param d The number of pixels to extract from the set of pixels to average
	 * @return An image with less noise
	 */
	public BufferedImage alphaFilter(int filterSize, int d) {
		System.out.println("Alpha-trimmed filter in progress...");
		setFilterSize(filterSize);
		
		int resultHeight = imageHeight+(bdr*2);
		int resultWidth = imageWidth+(bdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = alphaAndCrop(result, d);
		return result;
	}

	/**
	 * An isotropic, second derivative filter that highlights
	 * gray-level discontinuities and deemphasizes regions with
	 * slowly varying gray levels.
	 * 
	 * @return An image with grayish edge lines, all superimposed on a dark, featureless background
	 */
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

	/**
	 * A filter that is useful for unsharp masking. Its associated
	 * coefficient A changes the appearance of the result, where a
	 * result A closer to 1 is more Laplacian-like, while a result 
	 * farther away from A allows the sharpening process to become
	 * less influential.
	 * @param A The coefficient which changes the result of the image
	 * @return An image that has been sharpened using the high-boost filter
	 */
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

	/**
	 * A method that allows the user to specify either a
	 * range of bitplanes, or a single bitplane, to mask.
	 * Masking bitplanes can reveal certain characteristics
	 * of an image. 
	 * 
	 * @param a Used as the lower bound for the range
	 * @param b Used as the upper bound for the range
	 * @return An image with bitplanes a through b masked away from the result
	 */
	public BufferedImage bitMask(int a, int b) {
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		System.out.println("Bit mask for bit plane from " + a + " to " + b + " in progress...");
		for(int i = 0; i < imageHeight; i++) {
			for(int j = 0; j < imageWidth; j++) {
				int rgb = getGrayValue(image.getRGB(j,i));
				for(int c = b; c >= a; c--) {
					int x = getBitValue(c);
					if(rgb >= x) rgb = rgb - x;	
				}
				rgb = getRGBValue(rgb);
				newResult.setRGB(j,i,rgb);
			}
		}
	
		return newResult;
	}
	

	/**
	 * Smoothes local variations in an images. Noise
	 * is reduced as a result of blurring from the
	 * arithmetic mean filter. Implemented as a convlution
	 * mask.
	 * 
	 * @param resh The height of the resolution of the filter 
	 * @param resw The width of the resolution of the filter
	 * @return An image with reduced noise through blurring
	 */
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
	
	/**
	 * Smoothes local variations in an images while
	 * preserving some details. Noise is reduced as 
	 * from an image a result of blurring using the
	 * geometric mean filter. Implemented as a 
	 * convolution mask.
	 * 
	 * @param resh The height of the resolution of the filter 
	 * @param resw The width of the resolution of the filter
	 * @return An image with reduced noise through blurring
	 */
	public BufferedImage geometricMeanFilter(int resh, int resw) {
		System.out.println("Geometric mean filter in progress...");
		setResolution(resh, resw);int resultHeight = imageHeight+(nbdr*2);
		int resultWidth = imageWidth+(mbdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = multiplyAndCrop(result);
		return result;
	}

	/**
	 * A filter that is good for removing salt noise, as well
	 * as Gaussian noise.
	 * 
	 * @param resh The height of the resolution of the filter 
	 * @param resw The width of the resolution of the filter
	 * @return An image with reduced salt noise
	 */
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
	
	/**
	 * A filter that is good for removing salt-and-pepper noise,
	 * but not salt and pepper at the same time. A positive value 
	 * of Q eliminates pepper noise while a negative value 
	 * eliminates salt noise. The two cannot be removed simultaneously
	 * using this filter.
	 * 
	 * @param resh The height of the resolution of the filter 
	 * @param resw The width of the resolution of the filter
	 * @param Q The order of the filter
	 * @return An image with reduced salt or pepper noise
	 */
	public BufferedImage contraharmonicMeanFilter(double Q, int resh, int resw) {
		System.out.println("Contraharmonic mean filter in progress...");
		setResolution(resh, resw);
		int resultHeight = imageHeight+(nbdr*2);
		int resultWidth = imageWidth+(mbdr*2);
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_BYTE_GRAY);
		result = zeroImagePadding(result);
		result = addReflectivePadding(result);
		result = contraharmonizeAndCrop(result, Q);
		return result;
	}
	
	/**
	 * Helper method for Smoothing Filter and Arithmetic Mean Filter.
	 * Finds the average gray values within each pixel's neighbor.
	 * 
	 * @param result Input image
	 * @return An image whose grayscale values were averaged out; a blurry image
	 */
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

	
	/**
	 * Helper method for Smoothing Filter and Arithmetic Mean Filter.
	 * Finds the average rgb color values within each pixel's neighbor.
	 * 
	 * @param result Input image
	 * @return An image whose rgb color values were averaged out; a blurry colored image
	 */
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

	
	/**
	 * Helper method for the Geometric Mean Filter.
	 * 
	 * Implements the formula:
	 * result = productof( image(s,t) ^ ( 1 / (m*n) ) )
	 * 
	 * @param result Input image
	 * @return Image with applied filter and original dimensions
	 */
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
	

	/**
	 * Helper method for the 2nd order derivative Laplacian filter.
	 * 
	 * Implements this formula:
	 * result = ( f(x+1,y) 
	 * 			  f(x-1,y)
	 * 			  f(x,y+1)
	 *            f(x,y-1) ) - 4 * f(x,y) 
	 * 
	 * @param result Input image
	 * @return An image sharpened using the Laplacian Filter
	 */
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

	/**
	 * Helper Method for the Median Filter.
	 * @param result Input image
	 * @return An image whose median values are exposed
	 */
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
	
	/**
	 * Helper Method for the Max Filter.
	 * @param result Input image
	 * @return An image whose maximum/brightest values are exposed
	 */

	private static BufferedImage maxAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		int max = 0;
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				// for each pixel, apply this filter:
				for(int p = i-bdr; p <= i+bdr; p++) {
					for(int q = j-bdr; q <= j+bdr; q++) {
						neighbors.add(getGrayValue(result.getRGB(q, p)));
					}
				}
				Collections.sort(neighbors);
				max = neighbors.get(neighbors.size()-1);
				max = getRGBValue(max);
				newResult.setRGB(jj, ii, max);
				jj++;
				neighbors.clear();				
			}
			ii++;
			jj=0;
		}
		return newResult;
	}
	
	/**
	 * Helper Method for the Max Filter.
	 * @param result Colored input image
	 * @return An image whose maximum/brightest rgb values are exposed
	 */

	private static BufferedImage colorMaxAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		ArrayList<Integer> neighborsr = new ArrayList<Integer>();
		ArrayList<Integer> neighborsg = new ArrayList<Integer>();
		ArrayList<Integer> neighborsb = new ArrayList<Integer>();
		int minr = 0;
		int ming = 0;
		int minb = 0;
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				
				for(int p = i-bdr; p <= i+bdr; p++) {
					for(int q = j-bdr; q <= j+bdr; q++) {
						neighborsr.add(getRed(result.getRGB(q, p)));
						neighborsg.add(getGreen(result.getRGB(q, p)));
						neighborsb.add(getBlue(result.getRGB(q, p)));
					}
				}
				Collections.sort(neighborsr);
				Collections.sort(neighborsg);
				Collections.sort(neighborsb);
				minr = getRGBValue(neighborsr.get(neighborsr.size()-1));
				ming = getRGBValue(neighborsg.get(neighborsg.size()-1));
				minb = getRGBValue(neighborsb.get(neighborsb.size()-1));
				minb = getRGBValue(minr,ming,minb);
				newResult.setRGB(jj, ii, minb);
				jj++;
				neighborsr.clear();
				neighborsg.clear();	
				neighborsb.clear();	
			}
			ii++;
			jj=0;
		}
		return newResult;
	}
	
	/**
	 * Helper Method for the Min Filter.
	 * @param result Input image
	 * @return An image whose minimum/darkset values are exposed
	 */

	private static BufferedImage minAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		int max = 0;
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				// for each pixel, apply this filter:
				for(int p = i-bdr; p <= i+bdr; p++) {
					for(int q = j-bdr; q <= j+bdr; q++) {
						neighbors.add(getGrayValue(result.getRGB(q, p)));
					}
				}
				Collections.sort(neighbors);
				max = neighbors.get(0);
				max = getRGBValue(max);
				newResult.setRGB(jj, ii, max);
				jj++;
				neighbors.clear();				
			}
			ii++;
			jj=0;
		}
		return newResult;
	}

	
	/**
	 * Helper method for the Harmonic Mean Filter method.
	 * Implements the formula:
	 * 
	 * result = (m*n) / ( sumof( 1 / image(s,t) ) )
	 * 
	 * @param result Input image
	 * @return Image with applied filter and original dimensions
	 */
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
	
	/**
	 * Helper method for the Contraharmonic Mean Filter method.
	 * Implements the formula:
	 * 
	 * result = ( sumof( image(s,t)^(Q+1) ) ) / ( sumof( image(s,t)^Q ) )
	 * 
	 * @param result Input image
	 * @param Q Order of the filter
	 * @return Image with applied filter and original dimensions
	 */
	private static BufferedImage contraharmonizeAndCrop(BufferedImage result, double Q) {
		int ii = 0;
		int jj = 0;
		double numSum = 0;
		double divSum = 0;
		int round = 0;
		int rgb = 0;
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		for(int i = nbdr; i < resultHeight-nbdr; i++) {
			for(int j = mbdr; j < resultWidth-mbdr; j++) {
				// for each pixel, apply this filter:
				for(int p = i-nbdr; p <= i+nbdr; p++) {
					for(int q = j-mbdr; q <= j+mbdr; q++) {
						
						rgb = result.getRGB(q, p);
						numSum += Math.pow(getGrayValueD(rgb), (Q+1.0));
						divSum += Math.pow(getGrayValueD(rgb), Q);
					}
				}
				double quotient = numSum / divSum;
				round = (int) quotient;
				round = getRGBValue(round);
				newResult.setRGB(jj, ii, round);
				jj++;
				numSum = 0;
				divSum = 0;	
			}
			ii++;
			jj=0;
		}
		return newResult;
	}

	/**
	 * Helper method for the Midpoint Filter.
	 * @param result Input image
	 * @return An image with applied midpoint filter
	 */
	private static BufferedImage midpointAndCrop(BufferedImage result) {
		int ii = 0;
		int jj = 0;
		BufferedImage newResult = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		int resultHeight = result.getHeight();
		int resultWidth = result.getWidth();
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		int max = 0;
		int min = 0;
		for(int i = bdr; i < resultHeight-bdr; i++) {
			for(int j = bdr; j < resultWidth-bdr; j++) {
				// for each pixel, apply this filter:
				for(int p = i-bdr; p <= i+bdr; p++) {
					for(int q = j-bdr; q <= j+bdr; q++) {
						neighbors.add(getGrayValue(result.getRGB(q, p)));
					}
				}
				Collections.sort(neighbors);
				max = neighbors.get(0);
				max = getRGBValue(max);
				min = neighbors.get(neighbors.size()-1); 
				min = getRGBValue(min);
				max = max + min;
				max = max / 2;
				newResult.setRGB(jj, ii, max);
				jj++;
				neighbors.clear();				
			}
			ii++;
			jj=0;
		}
		return newResult;
	}
	
	/**
	 * Helper method for the Alpha-trimmed mean filter.
	 * 
	 * Implements this formula:
	 * result = 1 / ( m*n - d ) * productof( image(s,t) )
	 * 
	 * @param result Input image
	 * @param d Pixels to remove from the average
	 * @return Image with applied Alpha-trimmed mean filter
	 */
	private static BufferedImage alphaAndCrop(BufferedImage result, int d) {
		int ii = 0;
		int jj = 0;
		int sum = 0;
		double mm = m;
		double nn = n;
		double quotient = 1.0 / (mm*nn-d) ;
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
				sum = (int) (quotient * sum);
				sum = getRGBValue(sum);
				newResult.setRGB(jj, ii, sum);
				jj++;
				sum = 0;				
			}
			ii++;
			jj=0;
		}
		return newResult;
	}	
	
	/**
	 * Copys the RGB values from this image to a new,
	 * larger in size BufferedImage object. The new object
	 * is large enough to accomodate for both this image 
	 * and for its borders which are necessary for filtering.
	 * 
	 * @param result Empty image holder
	 * @return return Buffered image with a black border around it
	 */
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

	/** 
	 * Meant for use after using the method Zero Image
	 * Padding. Copies the edes of the original image 
	 * and reflects them as a new border.
	 * 
	 * @param result An image that has already experienced life without image padding
	 * @return result An image bigger than the original size, with an image reflected on each edge
	 */
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

	/**
	 * Copies a buffered image from 'toCopy' to 'result'
	 * 
	 * @param toCopy Source of the image
	 * @param result Image placeholder that will store the copied image
	 * @return A copy of the source image
	 */
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
	
	/**
	 * Helper method for the highboost filter. 
	 * Adds two images together by summing pixel values
	 * that correspond to each other by coordinates.
	 * @param image1 Augend image
	 * @param image2 Addend image
	 * @return The "sum" of the images
	 */
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
	
	/**
	 * Helper method for the highboost filter. 
	 * Multiplies two images together by summing pixel 
	 * values that correspond to each other by coordinates.
	 * @param image1 Multiplicand image
	 * @param image2 Multiplier image
	 * @return The "product" of the images
	 */
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
	
	/**
	 * Returns the value of a bit in the ath position.
	 * @param a Bit number
	 * @return Corresponding bit value
	 */
	private static int getBitValue(int a) {
		if (a == 1) return 1;
		else if (a == 2) return 2;
		else if (a == 3) return 4;
		else if (a == 4) return 8;
		else if (a == 5) return 18;
		else if (a == 6) return 32;
		else if (a == 7) return 64;
		else if (a == 8) return 128;
		else return 0;
	}
	
	/**
	 * Given an rgb value that represents a gray color image,
	 * this method returns its corresponding single gray value.
	 * @param rgb An rgb value that represents a gray color, it R225 G225 B225 (all channel values are the same)
	 * @return Single gray value
	 */
	private static int getGrayValue(int rgb) {
		Color c = new Color(rgb);
		int gray = c.getRed();
		return gray;
	}
	
	/**
	 * Given an rgb value that represents a gray color image,
	 * this method returns its corresponding single gray value
	 * in double form. Used when making a Math.pow calculation.
	 * @param rgb An rgb value that represents a gray color, it R225 G225 B225 (all channel values are the same)
	 * @return Single gray value in double form
	 */
	private static double getGrayValueD(int rgb) {
		Color c = new Color(rgb);
		double gray = c.getRed();
		return gray;
	}
	
	/**
	 * Returns the intensity value in the red channel
	 * @param rgb A color rgb value 
	 * @return Red channel intensity value for a single pixel
	 */
	private static int getRed(int rgb) {
		Color c = new Color(rgb);
		int red = c.getRed();
		return red;
	}
	
	/**
	 * Returns the intensity value in the green channel
	 * @param rgb A color rgb value 
	 * @return Green channel intensity value for a single pixel
	 */
	private static int getGreen(int rgb) {
		Color c = new Color(rgb);
		int green = c.getGreen();
		return green;
	}
	
	/**
	 * Returns the intensity value in the blue channel
	 * @param rgb A color rgb value 
	 * @return Blue channel intensity value for a single pixel
	 */
	private static int getBlue(int rgb) {
		Color c = new Color(rgb);
		int blue = c.getBlue();
		return blue;
	}
	
	/**
	 * Translates three values into one rgb value.
	 * @param red Desired value of the red channel
	 * @param green Desired value of the green channel
	 * @param blue Desired value of the blue channel
	 * @return Unified RGB value
	 */
	private static int getRGBValue(int red, int green, int blue) {
		int r = red;
		int g = green;
		int b = blue;
		int col = (r << 16) | (g << 8) | b;
		return col;
	}
	
	/**
	 * Translates one grayscale value into a unified rgb value.
	 * @param gray Desired grayscale value
	 * @return Unified RGB value that represents one grayscale value
	 */
	private static int getRGBValue(int gray) {
		int r = gray;
		int g = gray;
		int b = gray;
		int col = (r << 16) | (g << 8) | b;
		return col;
	}
	

}
