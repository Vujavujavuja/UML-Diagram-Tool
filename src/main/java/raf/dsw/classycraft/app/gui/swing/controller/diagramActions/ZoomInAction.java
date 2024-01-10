package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class ZoomInAction extends AbstractClassyAction
{
    public ZoomInAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/ZoomIn.png"));
        this.putValue(SHORT_DESCRIPTION, "Zoom in");
        this.putValue(NAME, "Zoom in");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MainFrame.getInstance().getPackageView().getStateManager().getCurrentState().execute(0, 0, MainFrame.getInstance().getPackageView());
        EventBus.getInstance().notifySubscriber(this, EventType.ZOOM_IN);
    }
}
