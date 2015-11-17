package com.ritcat14.GotYourSix.sfx;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    private static Sound staticSound = new Sound();

    public String        name = "";
    private Clip clip = null;
    private boolean      running     = false;
    public static float volume = 5.0f;
    private FloatControl control = null;

    private Sound() {
    }

    public Sound(String name, URL url) {
        this.name = name;
        try {
        // Open an audio input stream.
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
             // Get a sound clip resource.
             clip = AudioSystem.getClip();
             // Open audio clip and load samples from the audio input stream.
             clip.open(audioIn);
             control = (FloatControl) 
                     clip.getControl(FloatControl.Type.MASTER_GAIN);
             control.setValue(volume);
        } catch (UnsupportedAudioFileException e) {
             e.printStackTrace();
          } catch (IOException e) {
             e.printStackTrace();
          } catch (LineUnavailableException e) {
             e.printStackTrace();
          }
    }

    public void play() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (clip != null) {
             		  clip.start();
                    running = true;
                }
            }

        }).start();
    }

    public void loop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (clip != null) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    running = true;
                }
            }

        }).start();
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            running = false;
        }
    }

    public static URL getURL(String fileName) {
        return staticSound.getClass().getResource("/music/" + fileName);
    }

    public boolean active() {
        return running;
    }
  
    public void setVolume(float vol){
        if(control != null) control.setValue(vol);
    }

}
