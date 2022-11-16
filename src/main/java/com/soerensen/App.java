package com.soerensen;

public class App {
    public static void main(String[] args) {
        Visualizer visualizer = new Visualizer();
        try {
            visualizer.startGrabber();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
