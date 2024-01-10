package raf.dsw.classycraft.app.gui.swing.controller;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.Image;

public class AboutUsAction extends AbstractClassyAction
{
    public AboutUsAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/aboutUs.png"));
        this.putValue(SHORT_DESCRIPTION, "About Us");
        this.putValue(NAME, "About Us");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFrame aboutUsFrame = new JFrame("About Us");
        aboutUsFrame.setSize(500, 500);
        aboutUsFrame.setLocationRelativeTo(null);

        aboutUsFrame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel name1 = new JLabel("<html>Autor 1:<br>Ime: Nemanja<br>Prezime: Vujić<br>Broj indeksa: 63/21RN</html>");
        Icon ico1 = loadIcon("/images/vujavuja.jpg");
        ImageIcon imgIcon1 = (ImageIcon) ico1;
        Image image1 = imgIcon1.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        JLabel labelImage1 = new JLabel(new ImageIcon(image1));
        panel.add(name1);
        panel.add(labelImage1);

        JLabel name2 = new JLabel("<html>Autor 2:<br>Ime: Dušan<br>Prezime: Jevtić<br>Broj indeksa: 92/23RN</html>");
        Icon ico2 = loadIcon("/images/OpenHajniken.jpeg");
        ImageIcon imgIcon2 = (ImageIcon) ico2;
        Image image2 = imgIcon2.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        JLabel labelImage2 = new JLabel(new ImageIcon(image2));
        panel.add(name2);
        panel.add(labelImage2);

        aboutUsFrame.add(panel);
        aboutUsFrame.setVisible(true);
    }
}
