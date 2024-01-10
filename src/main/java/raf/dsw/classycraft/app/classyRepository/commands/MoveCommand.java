package raf.dsw.classycraft.app.classyRepository.commands;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.util.ArrayList;

public class MoveCommand implements Command {
    private ClassyNode parent;
    private ArrayList<DiagramElement> movedElements = new ArrayList<>();
    private ArrayList<DiagramElement> startPositions = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> endPositions = new ArrayList<>();


    public MoveCommand(ClassyNode parent, ArrayList<DiagramElement> movedElements, ArrayList<DiagramElement> startPositions ) {
        this.parent = parent;
        this.movedElements = movedElements;
        this.startPositions = startPositions;
        for(DiagramElement element : movedElements) {
            if(element instanceof InterClass) {
                InterClass interClassElement = (InterClass) element;
                endPositions.add(interClassElement.getPosition());
            }
        }
    }

    @Override
    public void undoCommand() {
        System.out.println("undo move");
        for(int i = 0; i < movedElements.size(); i++) {
            if (movedElements.get(i) instanceof InterClass && startPositions.get(i) instanceof InterClass) {
                InterClass startElement = (InterClass) startPositions.get(i);
                InterClass interClassElement = (InterClass) movedElements.get(i);
                interClassElement.setPosition(new Pair<>(
                        startElement.getPosition().getFirst(),
                        startElement.getPosition().getSecond()
                ));
            }

            for(DiagramElement conn : MainFrame.getInstance().getPackageView().getDiagram().getDiagramElements()) {
                if(conn instanceof Connection) {
                    Connection connection = (Connection) conn;
                    if(connection.getFromElement() == movedElements.get(i) || connection.getToElement() == movedElements.get(i)) {

                        InterClass fromElement = connection.getFromElement();
                        InterClass toElement = connection.getToElement();

                        int startX = fromElement.getPosition().getFirst();
                        int startY = fromElement.getPosition().getSecond();
                        int endXofStart = startX + fromElement.getSize().getFirst();
                        int endYofStart = startY + fromElement.getSize().getSecond();

                        int endStartX = toElement.getPosition().getFirst();
                        int endStartY = toElement.getPosition().getSecond();
                        int endXofEnd = endStartX + toElement.getSize().getFirst();
                        int endYofEnd = endStartY + toElement.getSize().getSecond();

                        int endMidCordX = (endStartX + endXofEnd) / 2;
                        int endMidCordY = (endStartY + endYofEnd) / 2;

                        if (endMidCordX < startX) {
                            connection.getStart().setFirst(startX);
                            connection.getStart().setSecond((startY + endYofStart) / 2);
                            connection.getEnd().setFirst(endXofEnd);
                            connection.getEnd().setSecond(endMidCordY);
                        } else if (endMidCordY < startY - 10) {
                            connection.getStart().setFirst((startX + endXofStart) / 2);
                            connection.getStart().setSecond(startY);
                            connection.getEnd().setFirst(endMidCordX);
                            connection.getEnd().setSecond(endYofEnd);
                        } else if (endMidCordX > endXofStart) {
                            connection.getStart().setFirst(endXofStart);
                            connection.getStart().setSecond((startY + endYofStart) / 2);
                            connection.getEnd().setFirst(endStartX);
                            connection.getEnd().setSecond(endMidCordY);
                        } else if (endMidCordY > endYofStart) {
                            connection.getStart().setFirst((startX + endXofStart) / 2);
                            connection.getStart().setSecond(endYofStart);
                            connection.getEnd().setFirst(endMidCordX);
                            connection.getEnd().setSecond(endStartY);
                        } else {
                            System.out.println("Connection out of bounds");
                        }
                    }
                }
            }
        }
        MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
        MainFrame.getInstance().getPackageView().setPanelSelectionPainters(new ArrayList<>());
        MainFrame.getInstance().getPackageView().panelRepaint();
        MainFrame.getInstance().getPackageView().panelOutsideRefresh();
    }

    @Override
    public void redoCommand() {
        System.out.println("redo move");
        for (int i = 0; i < movedElements.size(); i++) {
            if (movedElements.get(i) instanceof InterClass) {
                InterClass interClassElement = (InterClass) movedElements.get(i);
                Pair<Integer, Integer> endPosition = endPositions.get(i);
                interClassElement.setPosition(endPosition);

                // Update the connections related to this element
                for (DiagramElement conn : MainFrame.getInstance().getPackageView().getDiagram().getDiagramElements()) {
                    if (conn instanceof Connection) {
                        Connection connection = (Connection) conn;
                        if (connection.getFromElement() == interClassElement || connection.getToElement() == interClassElement) {

                            InterClass fromElement = connection.getFromElement();
                            InterClass toElement = connection.getToElement();

                            int startX = fromElement.getPosition().getFirst();
                            int startY = fromElement.getPosition().getSecond();
                            int endXofStart = startX + fromElement.getSize().getFirst();
                            int endYofStart = startY + fromElement.getSize().getSecond();

                            int endStartX = toElement.getPosition().getFirst();
                            int endStartY = toElement.getPosition().getSecond();
                            int endXofEnd = endStartX + toElement.getSize().getFirst();
                            int endYofEnd = endStartY + toElement.getSize().getSecond();

                            int endMidCordX = (endStartX + endXofEnd) / 2;
                            int endMidCordY = (endStartY + endYofEnd) / 2;

                            if (endMidCordX < startX) {
                                connection.getStart().setFirst(startX);
                                connection.getStart().setSecond((startY + endYofStart) / 2);
                                connection.getEnd().setFirst(endXofEnd);
                                connection.getEnd().setSecond(endMidCordY);
                            } else if (endMidCordY < startY - 10) {
                                connection.getStart().setFirst((startX + endXofStart) / 2);
                                connection.getStart().setSecond(startY);
                                connection.getEnd().setFirst(endMidCordX);
                                connection.getEnd().setSecond(endYofEnd);
                            } else if (endMidCordX > endXofStart) {
                                connection.getStart().setFirst(endXofStart);
                                connection.getStart().setSecond((startY + endYofStart) / 2);
                                connection.getEnd().setFirst(endStartX);
                                connection.getEnd().setSecond(endMidCordY);
                            } else if (endMidCordY > endYofStart) {
                                connection.getStart().setFirst((startX + endXofStart) / 2);
                                connection.getStart().setSecond(endYofStart);
                                connection.getEnd().setFirst(endMidCordX);
                                connection.getEnd().setSecond(endStartY);
                            } else {
                                System.out.println("Connection out of bounds");
                            }
                        }
                    }
                }
            }
        }
        MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
        MainFrame.getInstance().getPackageView().setPanelSelectionPainters(new ArrayList<>());
        MainFrame.getInstance().getPackageView().panelRepaint();
        MainFrame.getInstance().getPackageView().panelOutsideRefresh();
    }

}
