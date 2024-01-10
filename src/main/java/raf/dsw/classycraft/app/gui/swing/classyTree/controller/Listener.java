package raf.dsw.classycraft.app.gui.swing.classyTree.controller;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.core.observer.IPublisher;
import raf.dsw.classycraft.app.core.observer.ISubscriber;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Listener implements TreeSelectionListener, IPublisher, MouseListener {

    List<ISubscriber> subscribers = new ArrayList<>();

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        if (!e.isAddedPath()) return;
        int clickCount = 0;
        TreePath path = e.getPath();
        if (path == null) return;
        ClassyTreeItem item = (ClassyTreeItem) path.getLastPathComponent();
        if (item == null) return;

        notifySubscriber(item, EventType.PROJECT_SELECTION);


    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        subscribers.add(subscriber);

    }

private boolean clickedAgain(ClassyNode item, Object item2, int clickCount) {
        if(clickCount == 2)
            return item.equals(item2);
        return false;
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscriber(Object notification, Object typeOfUpdate) {
        for(ISubscriber subscriber : subscribers)
            subscriber.update(notification, typeOfUpdate);
    }

    private void openAllDiagramsInPackage(ClassyTreeItem item) {
        notifySubscriber(item, EventType.CLOSE_TABS);
        for(ClassyNode child : ((Package) item.getClassyNode()).getChildren()){
            if(child instanceof Diagram){
                System.out.println("Otvaram sve dijagrame u paketu" + child.getName());
                notifySubscriber(child, EventType.DIAGRAM_SELECTION);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){
            System.out.println("Double click");
            JTree tree = (JTree) e.getSource();
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path == null) return;
            ClassyTreeItem item = (ClassyTreeItem) path.getLastPathComponent();
            if (item == null) return;
            if(item.getClassyNode() instanceof Package){
                openAllDiagramsInPackage(item);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
