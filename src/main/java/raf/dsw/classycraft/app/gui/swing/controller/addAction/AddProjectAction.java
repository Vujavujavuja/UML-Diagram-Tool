package raf.dsw.classycraft.app.gui.swing.controller.addAction;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import java.awt.event.ActionEvent;

public class AddProjectAction extends AbstractClassyAction
{
    public AddProjectAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/addProject.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Project");
        this.putValue(NAME, "Add Project");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(MainFrame.getInstance().getClassyTree().getSelectedNode() == null)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.NODE_IS_NOT_SELECTED);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Project)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.CANT_ADD_PROJECT_IN_PROJECT);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Package)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.CANT_ADD_PROJECT_IN_PACKAGE);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Diagram)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.CANT_ADD_ANYTHING_IN_DIAGRAM);
            return;
        }

        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        MainFrame.getInstance().getClassyTree().addChild(selected, AddType.PROJECT_ADD);
    }
}
