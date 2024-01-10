package raf.dsw.classycraft.app.gui.swing.view.optionBars;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;

public class UMLDiagramToolBar extends JToolBar
{
    public UMLDiagramToolBar()
    {
        super(VERTICAL);
        setFloatable(false);

        this.add(MainFrame.getInstance().getActionManager().getAddClassAction());
        this.add(MainFrame.getInstance().getActionManager().getAddInterfaceAction());
        this.add(MainFrame.getInstance().getActionManager().getAddEnumAction());
        this.add(MainFrame.getInstance().getActionManager().getAddRelationshipAction());
        this.add(MainFrame.getInstance().getActionManager().getMoveElementAction());
        this.add(MainFrame.getInstance().getActionManager().getAddContentAction());
        this.add(MainFrame.getInstance().getActionManager().getSelectElementAction());
        this.add(MainFrame.getInstance().getActionManager().getDeleteElementAction());
        this.add(MainFrame.getInstance().getActionManager().getZoomInAction());
        this.add(MainFrame.getInstance().getActionManager().getZoomOutAction());
        this.add(MainFrame.getInstance().getActionManager().getZoomToFitAction());
        this.add(MainFrame.getInstance().getActionManager().getCopyAction());
        this.add(MainFrame.getInstance().getActionManager().getPatternAction());
        this.add(MainFrame.getInstance().getActionManager().getUndoAction());
        this.add(MainFrame.getInstance().getActionManager().getRedoAction());
    }
}
