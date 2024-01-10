package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;

import java.awt.event.ActionEvent;

public class AddInterfaceAction extends AbstractClassyAction
{
    public AddInterfaceAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Interface.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Interface");
        this.putValue(NAME, "Add Interface");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        EventBus.getInstance().notifySubscriber(this, EventType.ADD_INTERFACE);
    }
}
