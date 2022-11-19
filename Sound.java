import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    private Clip clip;
    private URL soundURL;

    public Sound(String source) {
        soundURL = getClass().getResource(source);
    }

    public void setFile() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void playMusic() {
        setFile();
        play();
        loop();
    }

    public void stopMusic() {
        stop();
    }

    public void playMusicEffect() {
        setFile();
        play();
    }
}