import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Downloader {
	// Variables
	static BufferedImage img;
	static URL url;
	static File output;
	static URLConnection connect;
	static InputStream inStream;
	static String path, imagePath, pathUntil, imgPathUntil;
	static Scanner readSetup;
	
	static int pictures = 9, digitsOfPicturs = 1, begin = 1;
	
	public static void main(String[] args) throws IOException {
		
		readSetup = new Scanner(System.in);
		
		System.out.println("Enter IamgePath:");
		imgPathUntil = readSetup.nextLine();
		
		System.out.println("Enter path until counter:");
		pathUntil = readSetup.nextLine();
		System.out.println("Enter begin picture:");
		begin = readSetup.nextInt();
		System.out.println("Enter digits:");
		digitsOfPicturs = readSetup.nextInt();
		System.out.println("Enter amount of pictures:");
		pictures = readSetup.nextInt();
		
		for (int counter = begin; counter <= pictures; counter++) {
			path = pathUntil + convert(counter, digitsOfPicturs) + ".jpg";
			imagePath = imgPathUntil + convert(counter, digitsOfPicturs) + ".png";
			System.out.println("Begin: " + counter + "/" + pictures);
			try {
				url = new URL(path);
				connect = url.openConnection();
				connect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				inStream = connect.getInputStream();
			}
			catch (MalformedURLException e) {
				System.out.println("URL corrupt!\n URL is: " + url);
				e.printStackTrace();
			}
			catch (FileNotFoundException fne) {
				try {
					System.out.println("File not found, now trying with \".JPG\"\n...");
					path = pathUntil + convert(counter, digitsOfPicturs) + ".JPG";
					url = new URL(path);
					connect = url.openConnection();
					connect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
					inStream = connect.getInputStream();
				}
				catch (FileNotFoundException fne2) {
					System.out.println("File not found, after trying with \".JPG\"\nNow tryig different digits\n...");
					digitsOfPicturs = (digitsOfPicturs==3) ? 4 : 3;
					path = pathUntil + convert(counter, digitsOfPicturs) + ".jpg";
					url = new URL(path);
					connect = url.openConnection();
					connect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
					inStream = connect.getInputStream();
				}
			}
			try {
				// System.out.println("URL used is: " + url);
				System.out.println("Read");
				img = ImageIO.read(inStream);
			}
			catch (IOException e) {
				System.out.println("For any reason (except that the URL is correct) the image from the given URL couldn't be read \n URL is: " + url);
				e.printStackTrace();
			}
			output = new File(imagePath);
			System.out.println(imagePath);
			try {
				System.out.println("Write");
				ImageIO.write(img, "PNG", output);
			}
			catch (IOException e) {
				System.out.println("Couldn't write the image to file");
				e.printStackTrace();
			}
		}
		System.out.println("Finished");
	}
	
	public static String convert(int number, int digit) {
		String buffer = String.valueOf(number);
		while (buffer.length() != digit)
			buffer = "0" + buffer;
		return buffer;
	}
}