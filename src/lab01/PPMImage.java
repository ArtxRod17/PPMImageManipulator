package lab01;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PPMImage {
	// Data fields
	private String magicNumber = "";
	private int width = 0;
	private int height = 0;
	// max color value can only be 255
	private int maxColorValue = 0;
	private char[] raster;// Data for the body, RGB values

	// Constructor
	public PPMImage(String imageFileName) {
		// Load image data into object
		readImage(imageFileName);
	}

	// Getters and setters
	public String getMagicNumber() {
		return magicNumber;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMaxColorValue() {
		return maxColorValue;
	}

	public char[] getRaster() {
		return raster;
	}

	public void setRaster(char[] raster) {
		this.raster = raster;
	}

	// Write image Constructor
	public void writeImage(String outputImageFileName) {
		try {

			String ppmValid = ".ppm";
			boolean check = outputImageFileName.contains(ppmValid);

			if (check == true) {

				FileOutputStream fos = new FileOutputStream(new File(outputImageFileName));
				// Write magic number
				fos.write(getMagicNumber().getBytes());
				// write width
				fos.write(String.valueOf(getWidth()).getBytes());
				// Separate by a white space
				fos.write(' ');
				// Write height
				fos.write(String.valueOf(getHeight()).getBytes());
				// Separate by a new line
				fos.write('\n');
				// Write maxColorvalue
				fos.write(String.valueOf(getMaxColorValue()).getBytes());
				// separate by a new line
				fos.write('\n');
				// Write the raster data using a for loop
				for (int i = 0; i < raster.length; i++) {
					// Convert the char array to byte for binary
					fos.write((byte) raster[i]);
				}
				fos.flush();
				fos.close();
			} else {
				System.out.println("Please make sure the fileName ends with .ppm");
				System.out.println("The program will now close");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Eclipse said i needed this exception
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		System.out.println("Image was successfully written");
	}

	// Constructor will call this Method
	private void readImage(String imageFileName) {
		try {

			FileInputStream fileIn = new FileInputStream(new File(imageFileName));
			System.out.println("Please Wait for image to load in....");

			// Create temporary variable
			char reader;
			// -----FORMAT: MagicNumber white space, width whitespace,height whitespace,
			// maxValue whitespace, then raster data!!

			// Read the first values as P6 then stop when a whitespace is read
			do {
				reader = (char) fileIn.read();
				// Setting the magicNumber as P6
				magicNumber = magicNumber + reader;

			} while (reader != '\n');// Breaks when whitespace is read

			if (!(magicNumber.contains("P6"))) {
				System.out.println("Wrong Format, Please try again with a ppm image!");
				System.out.println("The program will now close!");
				System.exit(0);
			}

			// Reading the next value set as width
			String widthValue = new String();
			reader = (char) fileIn.read();
			do {
				widthValue = widthValue + reader;
				reader = (char) fileIn.read();
			} while (reader != ' ');
			width = Integer.parseInt(widthValue);

			// Reading next value and set as height
			String heightValue = new String();
			reader = (char) fileIn.read();
			do {
				heightValue = heightValue + reader;
				reader = (char) fileIn.read();
			} while (reader != '\n');
			height = Integer.parseInt(heightValue);
			reader = (char) fileIn.read();

			// Reading next value and setting as max rgb value
			String maxColor = new String();
			do {
				maxColor = maxColor + reader;
				reader = (char) fileIn.read();
			} while (reader != '\n');
			maxColorValue = Integer.parseInt(maxColor);

			// Reading entire body pixel by pixel and add to char raster
			raster = new char[getWidth() * getHeight() * 3];
			for (int i = 0; i < raster.length; i++) {
				raster[i] = (char) fileIn.read();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	// Methods to alter the data of each pixel
	// Grayscale conversion
	public void grayscale() {
		// R' = (R * .299) + (G * .587) + (B * .114)
		// G' = (R * .299) + (G * .587) + (B * .114)
		// B' = (R * .299) + (G * .587) + (B * .114)
		// loop through entire raster
		// I want to separate the values for every 3rd number, R1G1B1 and then another
		// R2G2B2
		for (int i = 0; i < raster.length; i += 3) {
			// Set first value as R
			char R = raster[i];
			// Set second value as G
			char G = raster[i + 1];
			// Set 3rd value as B
			char B = raster[i + 2];
			// Use formula to set the new values
			char newColor = (char) ((R * .299) + (G * .587) + (B * .114));
			// This new color is now being assigned to the old R G B values
			raster[i] = newColor; // R value assigned as newColor
			raster[i + 1] = newColor;// G value assigned as new color
			raster[i + 2] = newColor;// B value assigned as new color
		}

	}

	// Sepia conversion
	public void sepia() {
		// R' = (R * .393) + (G *.769) + (B * .189)
		// G' = (R * .349) + (G *.686) + (B * .168)
		// B' = (R * .272) + (G *.534) + (B * .131)
		for (int i = 0; i < raster.length; i += 3) {
			// RGB all have different formulas, so create 3 colors to assign RGB to
			// Set first value as R
			char R = raster[i];
			// Set second value as G
			char G = raster[i + 1];
			// Set 3rd value as B
			char B = raster[i + 2];
			// Use formula to set the new values
			char colorOfR = (char) ((R * .393) + (G * .769) + (B * .189));
			char colorOfG = (char) ((R * .349) + (G * .686) + (B * .168));
			char colorOfB = (char) ((R * .272) + (G * .534) + (B * .131));
			// --VERIFY THESE VALUES ARE LESS THAN 255
			if (colorOfR > 255) {
				colorOfR = 255;
			}
			if (colorOfG > 255) {
				colorOfG = 255;
			}
			if (colorOfB > 255) {
				colorOfB = 255;
			}
			// Assign these new colors to each RGB value
			raster[i] = colorOfR;// new color of R
			raster[i + 1] = colorOfG; // new color of G
			raster[i + 2] = colorOfB;// new color of B
		}

	}

	// Negative conversion
	public void negative() {
		// R' = 255 - R
		// G' = 255 - G
		// B' = 255 – B
		// loop to loop through body
		for (int i = 0; i < raster.length; i++) {

			char RGB = raster[i];

			// Negative color value formula
			char newRGB = (char) (255 - RGB);

			// Assign new color value
			raster[i] = newRGB;

		}
	}

}
