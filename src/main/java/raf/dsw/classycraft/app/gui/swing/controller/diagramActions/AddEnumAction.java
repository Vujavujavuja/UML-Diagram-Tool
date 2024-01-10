package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;

import java.awt.event.ActionEvent;

public class AddEnumAction extends AbstractClassyAction
{
    public AddEnumAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Enum.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Enum");
        this.putValue(NAME, "Add Enum");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        EventBus.getInstance().notifySubscriber(this, EventType.ADD_ENUM);
    }
}
