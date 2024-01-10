package raf.dsw.classycraft.app.gui.swing.state.stateImpl;


import raf.dsw.classycraft.app.classyRepository.commands.AddSubElementCommand;
import raf.dsw.classycraft.app.classyRepository.commands.Command;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.state.State;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import java.util.ArrayList;

public class CopyState implements State {
    ArrayList<InterClass> elemtentsToCopy = new ArrayList<>();
    @Override
    public void execute(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateMousePressed(int x, int y, PackageView packageView) {
        ClassyNode classyNode = packageView.getCurrentDiagramPanel().getDiagram();
        int offset = 0;
        for(DiagramElement element: packageView.getCurrentDiagramPanel().getSelectedElements()){
            if(element instanceof InterClass && !elemtentsToCopy.contains(element))
                elemtentsToCopy.add((InterClass) element);
        }
        for(InterClass element : elemtentsToCopy){
            Pair position = new Pair<>(x,y);
            InterClass elementToAdd = null;
            if(element instanceof Klasa){
                Klasa newElement = new Klasa(packageView.getDiagram(), element.getName() + "-Copy", element.getVisibility(), (int)position.getFirst() + offset, (int)position.getSecond() + offset);
                newElement.setClassContents(((Klasa) element).getClassContents());
                newElement.setSize(((Klasa) element).getSize());
                newElement.setName(element.getName() + "-Copy");
                elementToAdd = newElement;
                EventBus.getInstance().notifySubscriber(newElement, EventType.ADD_CLASS_TO_TREE_S);
                Command newCommand = new AddSubElementCommand(classyNode, newElement);
                packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
            }
            else if(element instanceof Interfejs){
                Interfejs newElement = new Interfejs(packageView.getDiagram(), element.getName() + "-Copy", element.getVisibility(), (int)position.getFirst() + offset, (int)position.getSecond() + offset);
                newElement.getMetods().addAll(((Interfejs) element).getMetods());
                newElement.setSize(((Interfejs) element).getSize());
                newElement.setName(element.getName() + "-Copy");
                elementToAdd = newElement;
                EventBus.getInstance().notifySubscriber(newElement, EventType.ADD_INTERFACE_TO_TREE_S);
                Command newCommand = new AddSubElementCommand(classyNode, newElement);
                packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
            }
            else if(element instanceof UmlEnum){
                UmlEnum newElement = new UmlEnum(packageView.getDiagram(), element.getName() + "-Copy", element.getVisibility(), (int)position.getFirst() + offset, (int)position.getSecond() + offset);
                newElement.getConstants().addAll(((UmlEnum) element).getConstants());
                newElement.setSize(((UmlEnum) element).getSize());
                newElement.setName(element.getName() + "-Copy");
                elementToAdd = newElement;
                EventBus.getInstance().notifySubscriber(newElement, EventType.ADD_ENUM_TO_TREE_S);
                Command newCommand = new AddSubElementCommand(classyNode, newElement);
                packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
            }
            offset += elementToAdd.getSize().getFirst() + 20;
            packageView.addDiagramElement(position, elementToAdd);
        }
        elemtentsToCopy.clear();
        packageView.clearSelectedAll();
        packageView.panelRepaint();
        packageView.panelOutsideRefresh();
    }

    @Override
    public void stateMouseDragged(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateMouseReleased(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateRightMouseDragged(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateRightMousePressed(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateRightMouseReleased(int x, int y, PackageView packageView) {

    }
}
