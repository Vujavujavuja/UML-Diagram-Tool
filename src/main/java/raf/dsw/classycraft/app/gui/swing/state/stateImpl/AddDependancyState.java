package raf.dsw.classycraft.app.gui.swing.state.stateImpl;

import raf.dsw.classycraft.app.classyRepository.commands.AddSubElementCommand;
import raf.dsw.classycraft.app.classyRepository.commands.Command;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;
import raf.dsw.classycraft.app.gui.swing.state.State;
import raf.dsw.classycraft.app.gui.swing.view.painters.ZavisnostPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import java.awt.*;
import java.util.ArrayList;

public class AddDependancyState implements State {
    private Zavisnost dependancy = null;
    private Connection dep = null;
    private ConnectionPainter dependancyPainter = null;
    private int startX= 0;
    private int startY= 0;
    @Override
    public void execute(int x, int y, PackageView packageView) {
        packageView.setPanelCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void stateMousePressed(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateMouseDragged(int x, int y, PackageView packageView) {


        if(startX == 0 && startY == 0) {
            Connection connection = new Zavisnost(null, "name");
            connection.setStart(new Pair<>(0, 0));
            System.out.println("Start: " + x + " " + y);
            startX = x;
            startY = y;
            connection.setStart(new Pair<>(startX, startY));
            dep = connection;
            dep.setEnd(new Pair<>(x, y));
            dependancy = (Zavisnost) dep;
            if (dependancy.getToElement() == null) {
                for (DiagramElement diagramElement : packageView.currentDiagramElements()) {
                    if (diagramElement instanceof InterClass) {
                        if (isHit((InterClass) diagramElement, x, y)) {
                            dependancy.setFromElement(((InterClass) diagramElement));
                            break;
                        }
                    }
                }
            }
            if (dependancy.getToElement() == null) {
                dependancy.setToElement(new Klasa(packageView.getDiagram(), "Klasa", null, x, y));
            }
            dependancyPainter = new ZavisnostPainter(dependancy,1);
            dependancyPainter.setConnectionElement(dependancy);
            dependancyPainter.setColor(Color.BLACK);
            packageView.addPainter(dependancyPainter);
        }

        packageView.removePainter(dependancyPainter);
        System.out.println("PAINT");
        dep.setEnd(new Pair<>(x, y));
        dependancy.setToElement(new Klasa(packageView.getDiagram(), "Klasa", null, x, y));
        dependancyPainter = new ZavisnostPainter(dependancy,1);
        packageView.addPainter(dependancyPainter);

        packageView.panelRepaint();

    }

    @Override
    public void stateMouseReleased(int x, int y, PackageView packageView) {
        ClassyNode classyNode = packageView.getCurrentDiagramPanel().getDiagram();
        packageView.setPanelPainters(new ArrayList<>());
        packageView.panelRepaint();
        packageView.panelOutsideRefresh();
        startX = 0;
        startY = 0;
        for(DiagramElement diagramElement : packageView.currentDiagramElements()){
            if(diagramElement instanceof InterClass && diagramElement != dependancy.getFromElement() && !(diagramElement instanceof UmlEnum)){
                if(isHit((InterClass) diagramElement, x, y)){
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                    dependancy.setToElement(((InterClass) diagramElement));
                    dependancy.setName("Dependancy: " + dependancy.getFromElement().getName() + " -> " + dependancy.getToElement().getName());
                    setEnd(dependancy, (InterClass) diagramElement);
                    dependancyPainter.setConnectionElement(dependancy);
                    dependancyPainter = new ZavisnostPainter(dependancy, 0);
                    packageView.addDiagramElement(new Pair<>(x, y), dependancy);
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                    Command newCommand = new AddSubElementCommand(classyNode, dependancy);
                    packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                    break;
                }
                else {
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                }
            }
        }

        dep = null;
        dependancy = null;

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

    private boolean isHit(InterClass interClass, int x, int y){
        int startX = interClass.getPosition().getFirst();
        int startY = interClass.getPosition().getSecond();
        int endX = startX + interClass.getSize().getFirst();
        int endY = startY + interClass.getSize().getSecond();

        if(x >= startX && x <= endX && y >= startY && y <= endY){
            return true;
        }
        return false;

    }
    private void setEnd(Connection connection, InterClass interClass){
        int startX = connection.getFromElement().getPosition().getFirst();
        int startY = connection.getFromElement().getPosition().getSecond();
        int endXofStart = startX + connection.getFromElement().getSize().getFirst();
        int endYofStart = startY + connection.getFromElement().getSize().getSecond();

        int endStartX = interClass.getPosition().getFirst();
        int endStartY = interClass.getPosition().getSecond();
        int endXofEnd = endStartX + interClass.getSize().getFirst();
        int endYofEnd = endStartY + interClass.getSize().getSecond();

        int endMidCordX = (endStartX + endXofEnd)/2;
        int endMidCordY = (endStartY + endYofEnd)/2;

        if (endMidCordX < startX) {
            // To levo od from
            connection.getStart().setFirst(startX);
            connection.getStart().setSecond((startY + endYofStart)/2);
            connection.getEnd().setFirst(endXofEnd);
            connection.getEnd().setSecond(endMidCordY);
        }  else if (endMidCordY < startY - 10) {
            // To iznad from
            connection.getStart().setFirst((startX + endXofStart)/2);
            connection.getStart().setSecond(startY);
            connection.getEnd().setFirst(endMidCordX);
            connection.getEnd().setSecond(endYofEnd);
        }  else if (endMidCordX > endXofStart) {
            // To desno od from
            connection.getStart().setFirst(endXofStart);
            connection.getStart().setSecond((startY + endYofStart)/2);
            connection.getEnd().setFirst(endStartX);
            connection.getEnd().setSecond(endMidCordY);
        }  else {
            // To ispod from
            connection.getStart().setFirst((startX + endXofStart)/2);
            connection.getStart().setSecond(endYofStart);
            connection.getEnd().setFirst(endMidCordX);
            connection.getEnd().setSecond(endStartY);
        }
    }
}
