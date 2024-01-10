package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DeleteAction extends AbstractClassyAction
{
    private boolean diagramDeleted = false;
    public DeleteAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/deleteAction.png"));
        this.putValue(SHORT_DESCRIPTION, "Delete");
        this.putValue(NAME, "Delete");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();

        if (MainFrame.getInstance().getClassyTree().getSelectedNode() == null)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.NO_ITEM_TO_DELETE);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Diagram)
        {
            MainFrame.getInstance().getClassyTree().removeChild(MainFrame.getInstance().getClassyTree().getSelectedNode());
            EventBus.getInstance().notifySubscriber(selected, EventType.DIAGRAM_DELETION);
            System.out.println(selected.getClassyNode().getName() + " DELETED");
            System.out.println("DIAGRAM DELETED");
        }

        else if (MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof ProjectExplorer)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.CANT_DELETE_PROJECT_EXPLORER);
            return;
        }

        else if(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof ClassyNodeComposite && ((ClassyNodeComposite) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode()).getChildren().size() > 0)
        {
            JDialog deleteDialog = new JDialog();
            deleteDialog.setSize(450,150 );
            deleteDialog.setAlwaysOnTop(true);
            deleteDialog.setIconImage(((ImageIcon)loadIcon("/images/deleteAction.png")).getImage());
            deleteDialog.setResizable(false);
            deleteDialog.setTitle("Delete: " + MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode().getName());
            deleteDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            deleteDialog.setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel(new GridLayout(0, 1, 10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel nameLabel = new JLabel("Are you sure you want to delete " + MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode().getName() + " with all of the containing files?");
            mainPanel.add(nameLabel);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            JButton applyButton = new JButton("Yes");
            JButton cancelButton = new JButton("No");

            buttonPanel.add(applyButton);
            buttonPanel.add(cancelButton);

            cancelButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteDialog.dispose();
                }
            });

            applyButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {

                    MainFrame.getInstance().getClassyTree().removeChild(MainFrame.getInstance().getClassyTree().getSelectedNode());

                    ClassyNodeComposite parent = (ClassyNodeComposite) selected.getClassyNode();
                    List<ClassyNode> children = new ArrayList<>(parent.getChildren());
                    for(ClassyNode child : children)
                    {
                        if(child instanceof Diagram)
                        {
                            EventBus.getInstance().notifySubscriber(child, EventType.DIAGRAM_DELETION);
                            diagramDeleted = true;
                        }
                        else {
                            EventBus.getInstance().notifySubscriber(getDiagramFromPackage(child), EventType.DIAGRAM_LIST_DELETION);
                        }

                        parent.removeChild(child);
                    }

                    deleteDialog.dispose();
                }
            });

            deleteDialog.add(mainPanel, BorderLayout.CENTER);
            deleteDialog.add(buttonPanel, BorderLayout.SOUTH);
            deleteDialog.setLocationRelativeTo(null);
            deleteDialog.setVisible(true);
            return;
        }

        else
            MainFrame.getInstance().getClassyTree().removeChild(MainFrame.getInstance().getClassyTree().getSelectedNode());
    }


    private List<ClassyNode> getDiagramFromPackage(ClassyNode parent)
    {
        List<ClassyNode> tempList = new ArrayList<>();
        if(parent instanceof Diagram) {
            tempList.add(parent);
            return tempList;
        }
        ClassyNode temp;
        for(ClassyNode child : ((ClassyNodeComposite) parent).getChildren())
        {
            temp = child;
            if(temp instanceof Diagram)
                tempList.add(temp);
            else
                tempList.addAll(getDiagramFromPackage(temp));
        }
        return tempList;
    }

}
