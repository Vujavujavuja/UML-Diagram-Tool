package raf.dsw.classycraft.app.gui.swing.state.stateImpl;

import raf.dsw.classycraft.app.classyRepository.commands.AddSubElementCommand;
import raf.dsw.classycraft.app.classyRepository.commands.Command;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.gui.swing.state.State;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterClassPainter;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddClassState implements State {

    @Override
    public void execute(int x, int y, PackageView packageView) {
        packageView.setPanelCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void stateMousePressed(int x, int y, PackageView packageView) {
        ClassyNode classyNode = packageView.getCurrentDiagramPanel().getDiagram();

        DiagramPanel diagramPanel = packageView.getCurrentDiagramPanel();
        System.out.println("TEST");
        String name = JOptionPane.showInputDialog("Enter class name:");

        if(name == null) return;

        else if(name.isEmpty())
        {
            packageView.raiseErrorNE();
            return;
        }
        ArrayList<String> names = new ArrayList<>();
        for(DiagramElement elem: packageView.getDiagram().getDiagramElements()){
            names.add(elem.getName());
        }
        if(names.contains("Class:"+name)){
            packageView.raiseErrorNAE();
            return;
        }

        Klasa klas = new Klasa(diagramPanel.getDiagram(), name, Visibility.PUBLIC, x, y);
        klas.setName("Class:" + name);
        klas.setPosition(x, y);
        klas.setSize(new Pair(name.length()*8+120, 170));
        InterClassPainter painter = new InterClassPainter(klas);
        System.out.println("Dodajem klasu");
        packageView.addPainter(painter);
        packageView.panelRepaint();
        packageView.addDiagramElement(new Pair(x, y),klas);
        EventBus.getInstance().notifySubscriber(packageView.getCurrentDiagramPanel().getDiagram(), EventType.SET_PANEL);
        EventBus.getInstance().notifySubscriber(klas, EventType.ADD_CLASS_TO_TREE_S);
        //parent i child se salju u add class command
        Command newCommand = new AddSubElementCommand(classyNode, klas);
        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);

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
