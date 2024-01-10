package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.commands.CommandManager;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeLeaf;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Diagram extends ClassyNodeComposite implements ISubscriber
{
    private List<ClassyNode> children = new ArrayList<>();
    private List<DiagramElement> diagramElements = new ArrayList<>();
    private Map<Pair, DiagramElement> mapaElemenata = new HashMap<>();
    private CommandManager commandManager;
    public Diagram(String name, ClassyNode parent, boolean isPattern) {
        super(parent, name + " ");
        this.commandManager = new CommandManager();
        EventBus.getInstance().subscribe(EventType.ADD_CLASS_TO_TREE, this);
        EventBus.getInstance().subscribe(EventType.ADD_ENUM_TO_TREE, this);
        EventBus.getInstance().subscribe(EventType.ADD_INTERFACE_TO_TREE, this);
    }


    public boolean checkName(String name)
    {
        List<String> names = new ArrayList<>();
        for(DiagramElement diagramElement : diagramElements)
        {
            names.add(diagramElement.getName());
        }
        for(String s : names)
        {
            if(s.equals(name))
                return false;
        }
        return true;
    }

    public void addDiagramElement(Pair pair, DiagramElement diagramElement)
    {
        diagramElements.add(diagramElement);
        mapaElemenata.put(pair, diagramElement);
    }


    @Override
    public void update(Object notification, Object typeOfUpdate) {
        if(EventType.ADD_CLASS.equals(typeOfUpdate))
        {
            this.addChild((ClassyNode) notification);
        }
        else if(EventType.ADD_ENUM.equals(typeOfUpdate))
        {
            this.addChild((ClassyNode) notification);
        }
        else if(EventType.ADD_INTERFACE.equals(typeOfUpdate))
        {
            this.addChild((ClassyNode) notification);
        }
    }

    @Override
    public void addChild(ClassyNode nodeToAdd) {
        this.children.add(nodeToAdd);
    }

    @Override
    public void removeChild(ClassyNode nodeToRemove) {
        this.children.remove(nodeToRemove);

    }

    public Project getProject(ClassyNode classyNode){
        if(classyNode instanceof Project)
            return (Project) classyNode;
        else
            return getProject(classyNode.getParent());
    }
    public void updateUndoRedoStateForActiveDiagram() {

        boolean canUndo = commandManager.canUndo();
        boolean canRedo = commandManager.canRedo();
        AppCore.getInstance().getGui().enableUndoAction(canUndo);
        AppCore.getInstance().getGui().enableRedoAction(canRedo);
    }
}
