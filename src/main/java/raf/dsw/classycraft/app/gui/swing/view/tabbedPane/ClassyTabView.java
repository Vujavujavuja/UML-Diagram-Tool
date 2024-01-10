package raf.dsw.classycraft.app.gui.swing.view.tabbedPane;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.observer.ISubscriber;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Getter
@Setter
public class ClassyTabView extends JPanel implements ISubscriber {

    private JLabel projectNameLabel;
    private JLabel authorNameLabel;
    private JLabel pathLabel;
    private JLabel diagramLabel;
    private JTabbedPane tabbedPane;

    public ClassyTabView() {
        setLayout(new BorderLayout());

        projectNameLabel = new JLabel("Select a project...");
        projectNameLabel.setFont(new Font("Lato", Font.BOLD, 25));
        projectNameLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        this.add(projectNameLabel, BorderLayout.WEST);

        authorNameLabel = new JLabel("");
        authorNameLabel.setFont(new Font("Lato", Font.BOLD, 25));
        authorNameLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
        this.add(authorNameLabel, BorderLayout.EAST);
    }

    @Override
    public void update(Object notification, Object typeOfUpdate)
    {
        if (notification instanceof ClassyTreeItem && typeOfUpdate.equals(EventType.PROJECT_SELECTION))
        {
            ClassyTreeItem item = (ClassyTreeItem) notification;
            Project newItem = null;

            if(item.getClassyNode() instanceof ProjectExplorer)
            {
                projectNameLabel.setText("Select a project...");
                authorNameLabel.setText("");
                return;
            }

            else if(!(item.getClassyNode() instanceof Project))
            {
                newItem = getProject(item);

                if(newItem != null)
                {
                    projectNameLabel.setText("Project: " + newItem.getName());
                    authorNameLabel.setText("Author: " + newItem.getAuthor());
                }
            }

            else
            {
                Project classyNode = (Project) item.getClassyNode(); // Assuming ClassyNode has a method to get the project name
                projectNameLabel.setText("Project: " + classyNode.getName()); // Update the label with the project name
                authorNameLabel.setText("Author: " + classyNode.getAuthor()); // Update the label with the author name
            }
        }
    }

    private Project getProject(ClassyTreeItem item) {
        if(item == null)
            return null;
        if(item.getClassyNode() instanceof Project)
            return (Project) item.getClassyNode();
        if(item.getParent() instanceof Project)
            return (Project) item.getParent();
        return getProject((ClassyTreeItem) item.getParent());
    }

}


