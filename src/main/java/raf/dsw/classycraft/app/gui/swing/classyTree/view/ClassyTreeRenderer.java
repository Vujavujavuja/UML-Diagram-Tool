package raf.dsw.classycraft.app.gui.swing.classyTree.view;

import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class ClassyTreeRenderer extends DefaultTreeCellRenderer
{

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        URL imageURL = null;

        ClassyTreeItem treeItemSelected = ((ClassyTreeItem)value);

        if (treeItemSelected.getClassyNode() instanceof ProjectExplorer)
            imageURL = getClass().getResource("/images/PE.png");

        else if (treeItemSelected.getClassyNode() instanceof Project)
            imageURL = getClass().getResource("/images/Project.png");

        else if (treeItemSelected.getClassyNode() instanceof Diagram)
            imageURL = getClass().getResource("/images/Diagram.png");

        else if (treeItemSelected.getClassyNode() instanceof Package)
            imageURL = getClass().getResource("/images/Package.png");
        else if (treeItemSelected.getClassyNode() instanceof Klasa){
            imageURL = getClass().getResource("/images/Class.png");
        }
        else if (treeItemSelected.getClassyNode() instanceof Interfejs){
            imageURL = getClass().getResource("/images/Interface.png");
        }
        else if (treeItemSelected.getClassyNode() instanceof UmlEnum){
            imageURL = getClass().getResource("/images/Enum.png");
        }


        ImageIcon icon = null;

        if (imageURL != null)
            icon = new ImageIcon(imageURL);

        else
        {
            System.out.println("Nema slike");
            icon = new ImageIcon(getClass().getResource("/images/exit.png"));
        }

        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(newimg));

        return this;
    }


}
