package raf.dsw.classycraft.app.gui.swing.controller.addAction;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import java.awt.event.ActionEvent;


public class AddPackageAction extends AbstractClassyAction
{
    public AddPackageAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/addPackageAction.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Package");
        this.putValue(NAME, "Add Package");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(MainFrame.getInstance().getClassyTree().getSelectedNode() == null)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.NODE_IS_NOT_SELECTED);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof ProjectExplorer)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.NODE_IS_NOT_PROJECT);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Diagram)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.CANT_ADD_ANYTHING_IN_DIAGRAM);
            return;
        }

        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        MainFrame.getInstance().getClassyTree().addChild(selected, AddType.PACKAGE_ADD);
    }
}

