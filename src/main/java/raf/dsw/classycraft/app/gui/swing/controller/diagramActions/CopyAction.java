package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class CopyAction extends AbstractClassyAction {
    public CopyAction() {
        this.putValue(SMALL_ICON, loadIcon("/images/Copy.png"));
        this.putValue(SHORT_DESCRIPTION, "Copy");
        this.putValue(NAME, "Copy");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPackageView().startCopyState();
    }


}
