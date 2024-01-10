package raf.dsw.classycraft.app.gui.swing.classyTree.view;

import lombok.Getter;
import raf.dsw.classycraft.app.gui.swing.classyTree.controller.Editor;
import raf.dsw.classycraft.app.gui.swing.classyTree.controller.Listener;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.ClassyTabView;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Getter
public class ClassyTreeView extends JTree {

    public ClassyTreeView(DefaultTreeModel defaultTreeModel, ClassyTabView classyTabView, PackageView packageView)
    {
        this.setToggleClickCount(0);
        this.setModel(defaultTreeModel);
        ClassyTreeRenderer classyTreeCellRenderer = new ClassyTreeRenderer();
        Listener listener = new Listener();
        listener.addSubscriber(classyTabView);
        listener.addSubscriber(packageView);
        this.addTreeSelectionListener(listener);
        this.addMouseListener(listener);
        this.setCellEditor(new Editor(this, classyTreeCellRenderer));
        this.setCellRenderer(classyTreeCellRenderer);
        this.setEditable(true);
    }

    public List<TreeNode> getAllNodes() {
        List<TreeNode> nodeList = new ArrayList<>();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.getModel().getRoot();
        traverseTree(root, nodeList);
        return nodeList;
    }

    private void traverseTree(TreeNode node, List<TreeNode> nodeList) {
        if (node == null) {
            return;
        }
        nodeList.add(node);
        Enumeration<?> children = node.children();
        while (children.hasMoreElements()) {
            traverseTree((TreeNode) children.nextElement(), nodeList); // Process children
        }
    }

}


