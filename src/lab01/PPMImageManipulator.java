package lab01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PPMImageManipulator {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter relative path of a PPM file: ");
		String filepath = input.nextLine().trim();
		
		PPMImage img = new PPMImage(filepath);
		
		System.out.println("How would you like to manipulate this file?");
		System.out.println("\t[G] Convert to grayscale.");
		System.out.println("\t[S] Convert it sepia.");
		System.out.println("\t[N] Convert it to its negative.");
		System.out.println("\t[C] Copy without altering.");
		
		char operation = input.nextLine().charAt(0);
		operation = Character.toUpperCase(operation);
		
		switch (operation) {
		case 'G': img.grayscale(); break;
		case 'S': img.sepia(); break;
		case 'N': img.negative(); break;
		case 'C': /* do nothing... */ break;
		default: 
			System.out.println(operation + " is not a recognized command.");
			System.out.println("Exiting.");
			System.exit(1);
		}
		
		System.out.print("Enter filepath for the manipulated image: ");
		String filepathOut = input.nextLine().trim();
		img.writeImage(filepathOut);
		
		System.out.println("Thank you for using PPMImageManipulator!");	
	}
}