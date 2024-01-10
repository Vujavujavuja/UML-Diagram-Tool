package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class RedoAction extends AbstractClassyAction
{
    public RedoAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Redo.png"));
        this.putValue(SHORT_DESCRIPTION, "Redo");
        this.putValue(NAME, "Redo");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MainFrame.getInstance().getPackageView().getCurrentDiagramPanel().getDiagram().getCommandManager().redoCommand();
    }
}
