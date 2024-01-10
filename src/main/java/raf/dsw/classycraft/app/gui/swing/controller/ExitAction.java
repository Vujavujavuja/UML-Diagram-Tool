package raf.dsw.classycraft.app.gui.swing.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ExitAction extends AbstractClassyAction
{
    public ExitAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/exit.png"));
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK)); //Keyboard shortcut za trigerovanje akcije
        this.putValue(NAME, "Exit");
        this.putValue(SHORT_DESCRIPTION, "Exit"); //Tekst koji ce se prikazati kada hoverujemo GUI element
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
