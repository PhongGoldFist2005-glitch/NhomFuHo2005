package game.core;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Music {
    private Clip clip;
    private boolean isPlaying = false;

    public Music(String musicFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(musicFile);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }

    public void play() {
        if (clip != null && !isPlaying) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // lặp vô hạn
            clip.start();
            isPlaying = true;
        }
    }

    public void stop() {
        if (clip != null && isPlaying) {
            clip.stop();
            isPlaying = false;
        }
    }

    public void toggle() {
        if (isPlaying) {
            stop();
        } else {
            play();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
