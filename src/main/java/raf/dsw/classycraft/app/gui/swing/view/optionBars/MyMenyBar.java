package raf.dsw.classycraft.app.gui.swing.view.optionBars;

import raf.dsw.classycraft.app.gui.swing.controller.*;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddDiagramAction;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddPackageAction;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddProjectAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenyBar extends JMenuBar
{
    public MyMenyBar()
    {

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        fileMenu.add(MainFrame.getInstance().getActionManager().getAddDiagramAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getAddPackageAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getAddProjectAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getExitAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getOpenAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAsAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getImageAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getCodeConvertAction());


        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        AboutUsAction aboutUs = new AboutUsAction();
        editMenu.add(aboutUs);

        this.add(editMenu);
        this.add(fileMenu);

    }

}