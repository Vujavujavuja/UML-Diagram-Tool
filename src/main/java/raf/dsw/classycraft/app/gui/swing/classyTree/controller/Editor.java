package raf.dsw.classycraft.app.gui.swing.classyTree.controller;

import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.core.observer.IPublisher;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class Editor extends DefaultTreeCellEditor implements ActionListener, IPublisher {
    private Object clicked = null;

    private JTextField edit = null;

    public Editor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }


    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        clicked = value;
        edit = new JTextField(value.toString());
        edit.addActionListener(this);
        return edit;
    }

    public boolean isCellEditable(EventObject arg0) {
        if (arg0 instanceof MouseEvent)
            if (((MouseEvent)arg0).getClickCount()==3){
                return true;
            }
        return false;
    }

    public void actionPerformed(ActionEvent e) {

        if (!(clicked instanceof ClassyTreeItem))
            return;

        ClassyTreeItem clickedOn = (ClassyTreeItem) clicked;
        clickedOn.setName(e.getActionCommand());

    }
    @Override
    public void addSubscriber(ISubscriber subscriber) {

    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {

    }

    @Override
    public void notifySubscriber(Object notification, Object typeOfUpdate) {
    }
}
