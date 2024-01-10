package raf.dsw.classycraft.app.serializer;

import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageExporter {
    public static void exportPanelToImage(DiagramPanel panel, String filePath) {
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();

        panel.paint(g2d);

        String watermarkText = "ClassyCrafT by Vuja and Dule";

        addWatermark(g2d, watermarkText, panel.getWidth(), panel.getHeight());


        try {
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            g2d.dispose();
        }
    }
    private static void addWatermark(Graphics2D g2d, String watermarkText, int width, int height) {
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(alphaChannel);
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(watermarkText, g2d);

        int x = 10;
        int y = height - 10;

        g2d.drawString(watermarkText, x, y - fontMetrics.getDescent());
    }
}
