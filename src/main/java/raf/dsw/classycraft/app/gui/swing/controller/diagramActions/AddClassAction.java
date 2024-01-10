package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;

import java.awt.event.ActionEvent;

public class AddClassAction extends AbstractClassyAction
{
    public AddClassAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Class.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Class");
        this.putValue(NAME, "Add Class");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        EventBus.getInstance().notifySubscriber(this, EventType.ADD_CLASS);
    }
}
