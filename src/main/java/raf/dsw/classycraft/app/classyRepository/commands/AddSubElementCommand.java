package raf.dsw.classycraft.app.classyRepository.commands;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;

import java.util.ArrayList;

public class AddSubElementCommand implements Command
{
    private ClassyNode parent;
    private ClassyNode child;

    public AddSubElementCommand(ClassyNode parent, ClassyNode child)
    {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public void undoCommand() {
        MainFrame.getInstance().getPackageView().getDiagram().getDiagramElements().remove(child);
        MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<ElementPainter> ());
        MainFrame.getInstance().getPackageView().panelOutsideRefresh();
        DiagramPanel diagramPanel = MainFrame.getInstance().getPackageView().getCurrentDiagramPanel();
        ArrayList<DiagramElement> diagramElements = new ArrayList<>();
        diagramElements.add((DiagramElement) child);
        Pair pair = new Pair(diagramPanel, diagramElements);
        EventBus.getInstance().notifySubscriber(pair, EventType.DELETE_ELEMENTS);
    }

    @Override
    public void redoCommand() {
        MainFrame.getInstance().getPackageView().getDiagram().getDiagramElements().add((DiagramElement) child);
        System.out.println(child.getName());
        MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<ElementPainter> ());
        MainFrame.getInstance().getPackageView().panelOutsideRefresh();
        if(child instanceof Klasa)
            EventBus.getInstance().notifySubscriber(child, EventType.ADD_CLASS_TO_TREE);
        else if(child instanceof Interfejs)
            EventBus.getInstance().notifySubscriber(child, EventType.ADD_INTERFACE_TO_TREE);
        else if(child instanceof UmlEnum)
            EventBus.getInstance().notifySubscriber(child, EventType.ADD_ENUM_TO_TREE);
    }
}
