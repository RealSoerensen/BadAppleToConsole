package com.soerensen;

public class App {
    public static void main(String[] args) {
        Visualizer visualizer = new Visualizer();
        try {
            visualizer.grabFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
