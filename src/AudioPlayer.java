package com.soerensen;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer extends Thread {
    private File file;

    public AudioPlayer() {
        URL path = App.class.getResource("BadApple.wav");
        this.file = new File(path.getFile());
    }

    public void startMusic() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
