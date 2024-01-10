
package raf.dsw.classycraft.app.gui.swing.controller;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;

public abstract class AbstractClassyAction extends AbstractAction
{
    public Icon loadIcon(String iconPath)
    {
        URL imageURl = getClass().getResource(iconPath);
        Icon icon = null;

        if(imageURl != null)
            icon = new ImageIcon(imageURl);

        else
            System.err.println("Resource not found: " + iconPath);

        return icon;
    }
}
