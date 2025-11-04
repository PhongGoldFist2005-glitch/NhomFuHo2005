package game.core;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Xây dựng lớp xử lý âm thanh cho game.
 */
public class Music {
    private Clip clip;
    private boolean isPlaying = false;

    /**
     * Music constructor.
     * @param musicFile
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public Music(String musicFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(musicFile);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }

    /**
     * Chơi nhạc liên lục.
     */
    public void play() {
        if (clip != null && !isPlaying) {
            // lặp vô hạn.
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            isPlaying = true;
        }
    }

    /**
     * Ngừng nhạc.
     */
    public void stop() {
        if (clip != null && isPlaying) {
            clip.stop();
            isPlaying = false;
        }
    }

    /**
     * Chuyển qua lại giữa 2 tác vụ.
     */
    public void toggle() {
        if (isPlaying) {
            stop();
        } else {
            play();
        }
    }

    /**
     * Kiểm tra trạng thái nhạc.
     * @return
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Set trạng thái chơi nhạc.
     * @param playing
     */
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
