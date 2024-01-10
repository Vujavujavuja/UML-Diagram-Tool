package raf.dsw.classycraft.app.gui.swing.controller.addAction;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import java.awt.event.ActionEvent;

public class AddDiagramAction extends AbstractClassyAction
{
    public AddDiagramAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/addDiagram.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Diagram");
        this.putValue(NAME, "Add Diagram");
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
            AppCore.getInstance().getMessageGenerator().generate(EventType.NODE_IS_NOT_PROJECT_OR_PACKAGE);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Diagram)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.CANT_ADD_DIAGRAM_IN_DIARGAM);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Project)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.CANT_ADD_DIAGRAM_IN_PROJECT);
            return;
        }

        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        MainFrame.getInstance().getClassyTree().addChild(selected, AddType.DIAGRAM_ADD);
    }
}
