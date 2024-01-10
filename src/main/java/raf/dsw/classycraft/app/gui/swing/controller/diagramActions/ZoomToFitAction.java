package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;

import java.awt.event.ActionEvent;

public class ZoomToFitAction extends AbstractClassyAction
{
    public ZoomToFitAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/ZoomToFit.png"));
        this.putValue(SHORT_DESCRIPTION, "Zoom to fit");
        this.putValue(NAME, "Zoom to fit");
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {

        EventBus.getInstance().notifySubscriber(this, EventType.ZOOM_TO_FIT);
    }
}
