package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class UndoAction extends AbstractClassyAction
{
    public UndoAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Undo.png"));
        this.putValue(SHORT_DESCRIPTION, "Undo");
        this.putValue(NAME, "Undo");
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        MainFrame.getInstance().getPackageView().getCurrentDiagramPanel().getDiagram().getCommandManager().undoCommand();
    }
}
