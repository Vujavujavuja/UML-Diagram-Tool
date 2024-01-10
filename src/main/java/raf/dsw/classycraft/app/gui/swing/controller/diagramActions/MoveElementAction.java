package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class MoveElementAction extends AbstractClassyAction
{
    public MoveElementAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Move.png"));
        this.putValue(SHORT_DESCRIPTION, "Move element");
        this.putValue(NAME, "Move element");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        MainFrame.getInstance().getPackageView().startMoveElementState();
    }
}
