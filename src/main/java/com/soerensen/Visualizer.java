package com.soerensen;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

public class Visualizer {
    private FFmpegFrameGrabber grabber;
    private File file;
    private static BufferedImage image;

    public Visualizer() {
        // Load the videofile
        URL path = App.class.getResource("BadApple.mp4");
        setFile(new File(path.getFile()));
        setGrabber(new FFmpegFrameGrabber(getFile()));
    }

    private FFmpegFrameGrabber getGrabber() {
        return grabber;
    }

    private void setGrabber(FFmpegFrameGrabber grabber) {
        this.grabber = grabber;
    }

    private File getFile() {
        return file;
    }

    private void setFile(File file) {
        this.file = file;
    }

    public static BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage img) {
        image = img;
    }

    /**
     * It grabs a frame from the video, converts it to a buffered image, and then
     * sends it to the
     * imageParser function
     */
    public void startGrabber()
            throws Exception, IOException {
        // Start the grabber
        getGrabber().start();
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.startMusic();
        Java2DFrameConverter converter = new Java2DFrameConverter();
        try {
            for (int i = 0; i < grabber.getLengthInFrames(); i++) {
                // Sleep to match music
                Thread.sleep(29);
                // Clear console
                System.out.print("\033[H\033[2J");
                System.out.flush();
                setImage(converter.getBufferedImage(grabber.grabImage()));
                imageParser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        grabber.stop();
    }

    private static void imageParser() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        BufferedImage img = getImage();
        for (int i = 0; i < img.getHeight(); i += 3) {
            for (int j = 0; j < img.getWidth(); j += 1) {
                list.add(getPixel(j, i));
            }
        }
        outputBuilder(list);
    }

    /**
     * It takes a BufferedImage and an x and y coordinate, and returns the average
     * of the red, green, and
     * blue values of the pixel at that coordinate
     * 
     * @param img The image to be processed
     * @param x   The x coordinate of the pixel.
     * @param y   The y coordinate of the pixel to get.
     * @return The average of the red, green, and blue values of the pixel at the
     *         given x and y
     *         coordinates.
     */
    private static int getPixel(int x, int y) {
        Color color = new Color(getImage().getRGB(x, y));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int average = (red + green + blue) / 2;
        return average;
    }

    /**
     * It takes an ArrayList of integers and a BufferedImage, and then it creates a
     * StringBuilder object,
     * and then it loops through the ArrayList, and then it gets the average of the
     * RGB values of the pixel
     * at the current index, and then it appends a character to the StringBuilder
     * object based on the
     * average, and then it appends a newline character to the StringBuilder object
     * every time the index is
     * divisible by the width of the image divided by two, and then it prints the
     * StringBuilder object to
     * the console
     * 
     * @param list The list of average values for each pixel.
     * @param img  The image to be converted
     */
    private static void outputBuilder(ArrayList<Integer> list) {
        StringBuilder sb = new StringBuilder();
        BufferedImage image = getImage();
        // Loop through the the list of integers with rgb values of each pixel
        for (int i = 0; i < list.size(); i++) {
            int average = list.get(i);
            if (average < 50) {
                sb.append(" ");
            } else if (average < 100) {
                sb.append(".");
            } else if (average < 150) {
                sb.append("o");
            } else if (average < 200) {
                sb.append("O");
            } else {
                sb.append("@");
            }
            if (i % (image.getWidth()) == 0) {
                sb.append("\n");
            }
        }
        printToConsole(sb);
    }

    /**
     * It takes a StringBuilder, and prints it to the console
     * 
     * @param sb The StringBuilder object that contains the text to be printed.
     */
    private static void printToConsole(StringBuilder sb) {
        System.out.println(sb.toString());
    }
}
