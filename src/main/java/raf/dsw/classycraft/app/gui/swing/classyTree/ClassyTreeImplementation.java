package raf.dsw.classycraft.app.gui.swing.classyTree;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.*;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.core.observer.ISubscriber;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddType;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.classyTree.view.ClassyTreeView;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.factory.DiagramFactory;
import raf.dsw.classycraft.app.classyRepository.factory.PackageFactory;
import raf.dsw.classycraft.app.classyRepository.factory.ProjectFactory;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.ClassyTabView;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public class ClassyTreeImplementation implements ClassyTree, ISubscriber {

    private ClassyTreeView classyTreeView;
    private DefaultTreeModel defaultTreeModel;
    private Diagram currentDiagram;
    public ClassyTreeImplementation() {
        classyTreeView = null;
        defaultTreeModel = null;
        EventBus.getInstance().subscribe(EventType.DIAGRAM_RENAME, this);
        EventBus.getInstance().subscribe(EventType.PACKAGE_RENAME, this);
        EventBus.getInstance().subscribe(EventType.PROJECT_RENAME, this);
        EventBus.getInstance().subscribe(EventType.ADD_CLASS_TO_TREE, this);
        EventBus.getInstance().subscribe(EventType.ADD_INTERFACE_TO_TREE, this);
        EventBus.getInstance().subscribe(EventType.ADD_ENUM_TO_TREE, this);
        EventBus.getInstance().subscribe(EventType.SET_PANEL, this);
        EventBus.getInstance().subscribe(EventType.DELETE_ELEMENTS, this);
        EventBus.getInstance().subscribe(EventType.PROJECT_LOADED, this);
    }
    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer, ClassyTabView classyTabView, PackageView packageView){
        ClassyTreeItem root = new ClassyTreeItem(projectExplorer);
        defaultTreeModel = new DefaultTreeModel(root);
        classyTreeView = new ClassyTreeView(defaultTreeModel, classyTabView, packageView);
        return classyTreeView;
    }

    @Override
    public void addChild(ClassyTreeItem parent, AddType addType) {
        if(parent == null) {
            System.out.println("Parent is null");
            return;
        }
        if(!(parent.getClassyNode() instanceof ClassyNodeComposite))
            return;
        ClassyNode child =  createChild(parent.getClassyNode(), addType);
        if(child == null)
            return;
        ClassyTreeItem toAdd = new ClassyTreeItem(child);
        toAdd.setParent(parent);
        parent.getChildren().add(toAdd);
        parent.add(new ClassyTreeItem(child));
        parent.getChildren().add(new ClassyTreeItem(child));
        child.setParent(parent.getClassyNode());
        ((ClassyNodeComposite) parent.getClassyNode()).addChild(child);
        classyTreeView.expandPath(classyTreeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(classyTreeView);
    }

    @Override
    public void removeChild(ClassyTreeItem nodeToRemove) {
        ClassyNodeComposite parent = (ClassyNodeComposite) nodeToRemove.getClassyNode().getParent();
        if(parent == null)
            System.out.println("Node:" + nodeToRemove.getClassyNode().getName() + " has no parent");
        parent.removeChild(nodeToRemove.getClassyNode());
        defaultTreeModel.removeNodeFromParent(nodeToRemove);
        SwingUtilities.updateComponentTreeUI(classyTreeView);
    }

    @Override
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) classyTreeView.getLastSelectedPathComponent();
    }

    private ClassyNode createChild(ClassyNode classyNode, AddType addType)
    {
        if (classyNode instanceof ProjectExplorer && addType == AddType.PROJECT_ADD)
        {
            ProjectFactory projectFactory = new ProjectFactory();
            return projectFactory.createFactory(classyNode);
        }

        if(classyNode instanceof Project && addType == AddType.PACKAGE_ADD)
        {
            ((Project) classyNode).setChanged(true);
            PackageFactory packageFactory = new PackageFactory();
            return packageFactory.createFactory(classyNode);
        }
        if(classyNode instanceof Project && addType == AddType.DIAGRAM_ADD)
        {
            getProject(classyNode).setChanged(true);
            DiagramFactory diagramFactory = new DiagramFactory();
            return diagramFactory.createFactory(classyNode);
        }

        if(classyNode instanceof Package && addType == AddType.PACKAGE_ADD)
        {
            getProject(classyNode).setChanged(true);
            PackageFactory packageFactory = new PackageFactory();
            return packageFactory.createFactory(classyNode);
        }
        if(classyNode instanceof Package && addType == AddType.DIAGRAM_ADD)
        {
            getProject(classyNode).setChanged(true);
            DiagramFactory diagramFactory = new DiagramFactory();
            return diagramFactory.createFactory(classyNode);
        }

        return null;
    }


    @Override
    public void update(Object notification, Object typeOfUpdate) {
        if (typeOfUpdate.equals(EventType.PROJECT_LOADED)) {
            System.out.println("PROJECT LOADED");
            Project loadedProject = (Project) notification;
            updateTreeWithProject(loadedProject);
        }
        else if(typeOfUpdate.equals(EventType.SET_PANEL)){
            expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
            currentDiagram = (Diagram) notification;
        }
        if (notification instanceof String){
            expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
            if(typeOfUpdate.equals(EventType.DIAGRAM_RENAME)){
                notification = ((String) notification).split("/")[1];
                ClassyTreeItem selected = (ClassyTreeItem) classyTreeView.getLastSelectedPathComponent();
                selected.getClassyNode().setName((String) notification);
                classyTreeView.updateUI();
                getProject(selected.getClassyNode()).setChanged(true);
                System.out.println("UPDATE");
                SwingUtilities.updateComponentTreeUI(classyTreeView);
            }
            else if(typeOfUpdate.equals(EventType.PACKAGE_RENAME)){
                notification = ((String) notification).split("/")[1];
                ClassyTreeItem selected = (ClassyTreeItem) classyTreeView.getLastSelectedPathComponent();
                selected.getClassyNode().setName((String) notification);
                getProject(selected.getClassyNode()).setChanged(true);
                classyTreeView.updateUI();
                System.out.println("UPDATE");
                SwingUtilities.updateComponentTreeUI(classyTreeView);
            }
            else if(typeOfUpdate.equals(EventType.PROJECT_RENAME)){
                notification = ((String) notification).split("/")[1];
                ClassyTreeItem selected = (ClassyTreeItem) classyTreeView.getLastSelectedPathComponent();
                selected.getClassyNode().setName((String) notification);
                getProject(selected.getClassyNode()).setChanged(true);
                classyTreeView.updateUI();
                System.out.println("UPDATE");
                SwingUtilities.updateComponentTreeUI(classyTreeView);
            }
        }
        else if(typeOfUpdate.equals(EventType.ADD_CLASS_TO_TREE)){
            addClassToTree(notification,currentDiagram);
        }

        else if(typeOfUpdate.equals(EventType.ADD_INTERFACE_TO_TREE)){
            addInterfaceToTree(notification, currentDiagram);
        }
        else if(typeOfUpdate.equals(EventType.ADD_ENUM_TO_TREE)){
            addEnumToTree(notification, currentDiagram);
        }
        else if(typeOfUpdate.equals(EventType.DELETE_ELEMENTS)){
            System.out.println("DELETE ELEMENTS");
            expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
            Pair pair = (Pair) notification;
            ClassyTreeItem root = (ClassyTreeItem) classyTreeView.getModel().getRoot();
            DiagramPanel pnl = (DiagramPanel) pair.getFirst();
            ArrayList<DiagramElement> elements = (ArrayList<DiagramElement>) pair.getSecond();
            ClassyTreeItem targetParent = findNodeWithClassyNode(root, pnl.getDiagram());
            if (targetParent != null) {
                for(DiagramElement diagramElement : elements){
                    System.out.println("Element: " + diagramElement.getName());

                    ClassyTreeItem toRemove = findNodeWithClassyNode(targetParent, diagramElement);
                    if(toRemove != null){
                        System.out.println("Nasao ga");
                        removeChild(toRemove);
                    }
                }
            } else {
                System.out.println("Nema ga");
            }
            expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
        }
    }


    private ClassyTreeItem findNodeWithClassyNode(ClassyTreeItem node, ClassyNode classyNode) {
        if (node.getClassyNode().equals(classyNode)) {
            return node;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            ClassyTreeItem foundNode = findNodeWithClassyNode((ClassyTreeItem) node.getChildAt(i), classyNode);
            if (foundNode != null) {
                return foundNode;
            }
        }
        return null;
    }

    private void expandAllPaths(ClassyTreeItem node) {
        classyTreeView.expandPath(new TreePath(node.getPath()));
        for (int i = 0; i < node.getChildCount(); i++) {
            expandAllPaths((ClassyTreeItem) node.getChildAt(i));
        }
    }

    private void updateTreeWithProject(Project project) {
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
        System.out.println("UPDATE TREE WITH PROJECT");
        System.out.println(project.getName());
        ClassyTreeItem projectNode = new ClassyTreeItem(project);
        ClassyTreeItem root = (ClassyTreeItem) defaultTreeModel.getRoot();
        project.setParent(AppCore.getInstance().getClassyRepository().getProjectExplorer());
        if(root.getChildCount() > 0){
            for(int i = 0; i < root.getChildCount(); i++){
                ClassyTreeItem child = (ClassyTreeItem) root.getChildAt(i);
                if(child.getClassyNode().getName().equals(project.getName())){
                    project.setName(project.getName() + "(1)");
                }
            }
        }

        root.add(projectNode);
        addChildrenToTreeNode(projectNode, project);

        DefaultTreeModel treeModel = (DefaultTreeModel) classyTreeView.getModel();
        treeModel.setRoot(root);
        treeModel.reload();
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
        SwingUtilities.updateComponentTreeUI(classyTreeView);
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
    }
    private void addChildrenToTreeNode(ClassyTreeItem node, ClassyNodeComposite classyNode) {
        for (ClassyNode child : classyNode.getChildren()) {
            System.out.println("Child: " + child.getName());
            ClassyTreeItem childNode = new ClassyTreeItem(child);
            node.add(childNode);
            if(child instanceof Diagram){

                for(DiagramElement diagramElement : ((Diagram) child).getDiagramElements()){
                    System.out.println("Diagram element: " + diagramElement.getName());
                    if(diagramElement instanceof Klasa){
                        addClassToTree(diagramElement, (Diagram) child);
                    }
                    else if(diagramElement instanceof Interfejs){
                        addInterfaceToTree(diagramElement,(Diagram) child);
                    }
                    else if(diagramElement instanceof UmlEnum) {
                        addEnumToTree(diagramElement,(Diagram) child);
                    }
                }
            }
            addChildrenToTreeNode(childNode, (ClassyNodeComposite) child);
        }
    }

    private void addClassToTree(Object notification,Diagram cd){
        System.out.println(cd.getName());
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
        System.out.println("ADD CLASS TO TREE");
        ClassyNode classToAdd = (ClassyNode) notification;

        ClassyTreeItem root = (ClassyTreeItem) classyTreeView.getModel().getRoot();

        ClassyTreeItem targetParent = findNodeWithClassyNode(root, cd);

        if (targetParent != null) {
            ClassyTreeItem newItem = new ClassyTreeItem(classToAdd);
            targetParent.add(newItem);

            ((DefaultTreeModel) classyTreeView.getModel()).reload(targetParent);
        } else {
            System.out.println("Nema ga");
        }
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
    }

    private void addInterfaceToTree(Object notification,Diagram cd){
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
        ClassyNode interfaceToAdd = (ClassyNode) notification;

        ClassyTreeItem root = (ClassyTreeItem) classyTreeView.getModel().getRoot();

        ClassyTreeItem targetParent = findNodeWithClassyNode(root, cd);

        if (targetParent != null) {
            ClassyTreeItem newItem = new ClassyTreeItem(interfaceToAdd);
            targetParent.add(newItem);

            ((DefaultTreeModel) classyTreeView.getModel()).reload(targetParent);
        } else {
            System.out.println("Nema ga");
        }
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
    }

    private void addEnumToTree(Object notification, Diagram cd){
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
        ClassyNode enumToAdd = (ClassyNode) notification;

        ClassyTreeItem root = (ClassyTreeItem) classyTreeView.getModel().getRoot();

        ClassyTreeItem targetParent = findNodeWithClassyNode(root, cd);

        if (targetParent != null) {
            ClassyTreeItem newItem = new ClassyTreeItem(enumToAdd);
            targetParent.add(newItem);

            ((DefaultTreeModel) classyTreeView.getModel()).reload(targetParent);
        } else {
            System.out.println("Nema ga");
        }
        expandAllPaths((ClassyTreeItem) classyTreeView.getModel().getRoot());
    }
    private Project getProject(ClassyNode classyNode){
        if(classyNode instanceof Project)
            return (Project) classyNode;
        else
            return getProject(classyNode.getParent());
    }
}
