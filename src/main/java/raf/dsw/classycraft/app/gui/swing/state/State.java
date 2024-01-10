package raf.dsw.classycraft.app.gui.swing.state;

import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import java.awt.*;

public interface State {
    void execute(int x, int y, PackageView packageView);
    void stateMousePressed(int x, int y, PackageView packageView);
    void stateMouseDragged(int x, int y, PackageView packageView);
    void stateMouseReleased(int x, int y, PackageView packageView);
    void stateRightMouseDragged(int x, int y, PackageView packageView);
    void stateRightMousePressed(int x, int y, PackageView packageView);
    void stateRightMouseReleased(int x, int y, PackageView packageView);

}
