package hu.hj.gif;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame {
    private static final int PIXELS_SQUARE = 50;
    private final Graphics2D graphics;
    private final BufferedImage image;

    public Frame(int width, int height) {
        image = new BufferedImage(width * PIXELS_SQUARE, height * PIXELS_SQUARE, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
    }

    public void printSquare(int x, int y, Color color) {
        graphics.setPaint(color);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fill(new Rectangle(x * PIXELS_SQUARE, y * PIXELS_SQUARE, PIXELS_SQUARE, PIXELS_SQUARE));
    }

    BufferedImage getBufferedImage() {
        return image;
    }
}