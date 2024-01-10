package raf.dsw.classycraft.app.gui.swing.state.stateImpl;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.classyRepository.commands.Command;
import raf.dsw.classycraft.app.classyRepository.commands.MoveCommand;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.UmlSelectionModel;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;
import raf.dsw.classycraft.app.gui.swing.state.State;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;

import java.awt.*;
import java.util.ArrayList;

public class MoveElementState implements State {
    private int startX;
    private int startY;
    private ArrayList<DiagramElement> startPositions;

    ArrayList<DiagramElement> elements = new ArrayList<>();

    @Override
    public void execute(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateMousePressed(int x, int y, PackageView packageView) {
        startPositions = new ArrayList<>();
        this.startX = x;
        this.startY = y;
        //dodamo trenutno selektovne elem u listu
        elements.clear();
        for(DiagramElement element : packageView.getCurrentDiagramPanel().getSelectedElements()) {
            if (elements.contains(element) || !(element instanceof InterClass)) {
                continue;
            } else {
                if(element instanceof Klasa){
                    startPositions.add(new Klasa((Klasa) element));
                }
                else if(element instanceof Interfejs){
                    startPositions.add(new Interfejs((Interfejs) element));
                }
                else if(element instanceof UmlEnum){
                    startPositions.add(new UmlEnum((UmlEnum) element));
                }
                elements.add(element);
            }
        }
    }

    @Override
    public void stateMouseDragged(int x, int y, PackageView packageView) {
        //move slican kao i move po dijagram panelu
        int deltaX = x - startX;
        int deltaY = y - startY;
        for (DiagramElement element : elements) {
            if (element instanceof InterClass) {
                Pair<Integer, Integer> currentPosition = ((InterClass) element).getPosition();
                ((InterClass) element).setPosition(new Pair<>(currentPosition.getFirst() + deltaX, currentPosition.getSecond() + deltaY));
            }
        }
        packageView.setPanelPainters(new ArrayList<>());
        packageView.panelRepaint();
        packageView.panelOutsideRefresh();
        startX = x;
        startY = y;
    }

    @Override
    public void stateMouseReleased(int x, int y, PackageView packageView) {
        System.out.println("move element state mouse released");
        ArrayList<DiagramElement> movedElements = new ArrayList<>();
        for(DiagramElement element : elements){
            movedElements.add(element);
        }
        Command moveCommand = new MoveCommand(packageView.getDiagram(), movedElements, startPositions);
        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(moveCommand);
        packageView.clearSelectedAll();
        elements.clear();
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

