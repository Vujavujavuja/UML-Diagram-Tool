package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;

import java.awt.event.ActionEvent;

public class SelectElementAction extends AbstractClassyAction
{
    public SelectElementAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Select.png"));
        this.putValue(SHORT_DESCRIPTION, "Select item");
        this.putValue(NAME, "Select item");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        EventBus.getInstance().notifySubscriber(this, EventType.SELECT_ELEMENT);
    }
}
