package raf.dsw.classycraft.app.gui.swing.state.stateImpl;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.state.State;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ZoomOutState implements State
{
    @Override
    public void execute(int x, int y, PackageView packageView)
    {
    }

    @Override
    public void stateMousePressed(int x, int y, PackageView packageView)
    {
        String notification = x + "/" + y;
        EventBus.getInstance().notifySubscriber(notification, EventType.ZOOM_OUT_STATE);
    }

    @Override
    public void stateMouseDragged(int x, int y, PackageView packageView)
    {

    }

    @Override
    public void stateMouseReleased(int x, int y, PackageView packageView)
    {

    }

    @Override
    public void stateRightMouseDragged(int x, int y, PackageView packageView)
    {

    }

    @Override
    public void stateRightMousePressed(int x, int y, PackageView packageView)
    {

    }

    @Override
    public void stateRightMouseReleased(int x, int y, PackageView packageView)
    {

    }
}
