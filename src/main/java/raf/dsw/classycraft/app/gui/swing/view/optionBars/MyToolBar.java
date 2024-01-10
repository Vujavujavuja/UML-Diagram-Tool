package raf.dsw.classycraft.app.gui.swing.view.optionBars;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;

public class MyToolBar extends JToolBar
{
    public MyToolBar()
    {
        super(HORIZONTAL);
        this.setFloatable(false);

        this.add(MainFrame.getInstance().getActionManager().getExitAction()); //exitAction ce se executovati automatski tako što će se pokrenuti actionPerformed metoda, klikom na odgovarajući GUI element (ikonica).
        this.add(MainFrame.getInstance().getActionManager().getAboutUsAction()); // isto
        this.add(MainFrame.getInstance().getActionManager().getAddProjectAction()); // isto
        this.add(MainFrame.getInstance().getActionManager().getAddPackageAction()); // isto
        this.add(MainFrame.getInstance().getActionManager().getAddDiagramAction()); // isto
        this.add(MainFrame.getInstance().getActionManager().getEditAction()); // isto
        this.add(MainFrame.getInstance().getActionManager().getDeleteAction());
        this.add(MainFrame.getInstance().getActionManager().getOpenAction());
        this.add(MainFrame.getInstance().getActionManager().getSaveAction());
        this.add(MainFrame.getInstance().getActionManager().getSaveAsAction());
        this.add(MainFrame.getInstance().getActionManager().getImageAction());
        this.add(MainFrame.getInstance().getActionManager().getCodeConvertAction());
    }
}
