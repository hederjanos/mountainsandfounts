package hu.hj.gif;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class AnimatedGIF {

    private final AnimatedGifEncoder encoder = new AnimatedGifEncoder();
    private OutputStream outputStream;
    private Path path;

    public AnimatedGIF() {
        initializeOutputStream();
        encoder.start(outputStream);
        encoder.setDelay(200);
    }

    public void initializeOutputStream() {
        try {
            String userHomeDirectory = System.getProperty("user.home");
            path = Path.of(userHomeDirectory + "/simulation.gif");
            outputStream = Files.newOutputStream(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFrame(Frame frame) {
        encoder.addFrame(frame.getBufferedImage());
    }

    public void finish() {
        encoder.finish();
    }

    public Path getPath() {
        return path;
    }
}