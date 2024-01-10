package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddContentAction extends AbstractClassyAction
{
    public AddContentAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Content.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Content");
        this.putValue(NAME, "Add Content");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        EventBus.getInstance().notifySubscriber(this, EventType.CONTENT_STATE);
    }
}
