package raf.dsw.classycraft.app.gui.swing.view.tabbedPane;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.*;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Agregacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Generalizacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Kompozicija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.core.observer.ISubscriber;
import raf.dsw.classycraft.app.gui.swing.controller.listner.ClassyMouse;
import raf.dsw.classycraft.app.gui.swing.state.StateManager;
import raf.dsw.classycraft.app.gui.swing.view.painters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

@Getter
@Setter
public class DiagramPanel extends JPanel {

    private ArrayList<ElementPainter> painters = new ArrayList<>();
    private ArrayList<ElementPainter> tempPainters = new ArrayList<>();
    private ArrayList<ElementPainter> selectedPainters = new ArrayList<>();
    private ArrayList<DiagramElement> selectedElements = new ArrayList<>();
    private ArrayList<DiagramElement> selectedElementsD = new ArrayList<>();
    private SelectionPainter selectionPainter = null;
    private SelectionPainterDel selectionPainterDel = null;
    private Diagram diagram;
    int startDargX;
    int startDragY;
    private UmlSelectionModel selectionModel;
    private double scaleFactor;
    private double scale;
    private AffineTransform affineTransform;
    private boolean setScale;
    private int step;
    public DiagramPanel(Diagram diagram){

        super();
        this.diagram = diagram;
        init(diagram);
        selectionModel = new UmlSelectionModel();
        this.setFocusable(true);
        this.setScale = true;
        this.step = 5;
        this.affineTransform = new AffineTransform();

        this.setBackground(Color.WHITE);
        setupDeleteKeyBinding();
    }

    private void init(Diagram diagram){
        ArrayList<Connection> connectionList = getConnections(diagram);
        ArrayList<Connection> flaggedConnections = new ArrayList<>();
        ArrayList<Connection> doubleCheck = new ArrayList<>();
        for (DiagramElement element : diagram.getDiagramElements()) {
            if(selectionModel != null && selectionModel.getSelected().contains(element)){
                if (element instanceof Klasa) {
                    Klasa klasa = (Klasa) element;
                    InterClassPainter painter = new InterClassPainter(klasa);
                    painters.add(painter);
                    selectedPainters.add(painter);
                } else if (element instanceof UmlEnum) {
                    UmlEnum umlEnum = (UmlEnum) element;
                    InterClassPainter painter = new InterClassPainter(umlEnum);
                    painters.add(painter);
                    selectedPainters.add(painter);
                } else if (element instanceof Interfejs) {
                    Interfejs interfejs = (Interfejs) element;
                    InterClassPainter painter = new InterClassPainter(interfejs);
                    painters.add(painter);
                    selectedPainters.add(painter);
                }
                else if(element instanceof Kompozicija){
                    Kompozicija kompozicija = (Kompozicija) element;
                    KompozicijaPainter painter = new KompozicijaPainter(kompozicija, 0);
                    painters.add(painter);
                    selectedPainters.add(painter);
                }
                else if(element instanceof Agregacija){
                    Agregacija agregacija = (Agregacija) element;
                    AgregacijaPainter painter = new AgregacijaPainter(agregacija, 0);
                    painters.add(painter);
                    selectedPainters.add(painter);
                }
                else if(element instanceof Zavisnost){
                    Zavisnost zavisnost = (Zavisnost) element;
                    ZavisnostPainter painter = new ZavisnostPainter(zavisnost, 0);
                    painters.add(painter);
                    selectedPainters.add(painter);
                }
                else if(element instanceof Generalizacija){
                    Generalizacija generalizacija = (Generalizacija) element;
                    GeneralizacijaPainter painter = new GeneralizacijaPainter(generalizacija, 0);
                    painters.add(painter);
                    selectedPainters.add(painter);
                }
            }
            else {
                if (element instanceof Klasa) {
                    Klasa klasa = (Klasa) element;
                    InterClassPainter painter = new InterClassPainter(klasa);
                    painters.add(painter);
                } else if (element instanceof UmlEnum) {
                    UmlEnum umlEnum = (UmlEnum) element;
                    InterClassPainter painter = new InterClassPainter(umlEnum);
                    painters.add(painter);
                } else if (element instanceof Interfejs) {
                    Interfejs interfejs = (Interfejs) element;
                    InterClassPainter painter = new InterClassPainter(interfejs);
                    painters.add(painter);
                } else if (element instanceof Generalizacija && !flaggedConnections.contains(element)) {
                    Generalizacija gen = (Generalizacija) element;
                    System.out.println(gen.getFromElement().getName() + " " + gen.getToElement().getName());
                    Connection con = gen;
                    Generalizacija generalizacija = (Generalizacija) element;
                    GeneralizacijaPainter painter = new GeneralizacijaPainter(generalizacija,0);
                    painters.add(painter);
                } else if (element instanceof Zavisnost && !flaggedConnections.contains(element)) {
                    Zavisnost zavisnost = (Zavisnost) element;
                    ZavisnostPainter painter = new ZavisnostPainter(zavisnost, 0);
                    painters.add(painter);
                } else if (element instanceof Agregacija && !flaggedConnections.contains(element)) {
                    Agregacija agregacija = (Agregacija) element;
                    AgregacijaPainter painter = new AgregacijaPainter(agregacija, 0);
                    painters.add(painter);
                } else if (element instanceof Kompozicija && !flaggedConnections.contains(element)) {
                    Kompozicija kompozicija = (Kompozicija) element;
                    KompozicijaPainter painter = new KompozicijaPainter(kompozicija, 0);
                    painters.add(painter);
                }
                else if(element instanceof Connection && flaggedConnections.contains(element)){
                    if(element instanceof Generalizacija){
                        System.out.println("FLAGGED");
                        Generalizacija gen = (Generalizacija) element;
                        System.out.println(gen.getFromElement().getName() + " " + gen.getToElement().getName());
                        Connection con = gen;
                        Generalizacija generalizacija = (Generalizacija) element;
                        generalizacija.getFromElement().getPosition().setSecond(generalizacija.getFromElement().getPosition().getSecond() + 20);
                        generalizacija.getToElement().getPosition().setSecond(generalizacija.getToElement().getPosition().getSecond() + 20);
                        GeneralizacijaPainter painter = new GeneralizacijaPainter(generalizacija, 0);
                        painters.add(painter);
                    }
                    else if(element instanceof Zavisnost){
                        Zavisnost zavisnost = (Zavisnost) element;
                        ZavisnostPainter painter = new ZavisnostPainter(zavisnost, 0);
                        painters.add(painter);
                    }
                    else if(element instanceof Agregacija){
                        Agregacija agregacija = (Agregacija) element;
                        AgregacijaPainter painter = new AgregacijaPainter(agregacija, 0);
                        painters.add(painter);
                    }
                    else if(element instanceof Kompozicija){
                        Kompozicija kompozicija = (Kompozicija) element;
                        KompozicijaPainter painter = new KompozicijaPainter(kompozicija, 0);
                        painters.add(painter);
                    }
                }
            }
        }

    }

    public void treverse(Object notification){
        if (notification instanceof Triple<?, ?, ?>) {
            Triple<DiagramPanel, Integer, Integer> data = (Triple<DiagramPanel, Integer, Integer>) notification;
            if (data.getFirst() == this) {
                Pair<Integer, Integer> displacement = new Pair<>(data.getSecond(), data.getThird());
                int deltaX = displacement.getFirst();
                int deltaY = displacement.getSecond();
                for (DiagramElement elem : diagram.getDiagramElements()) {
                    if (elem instanceof InterClass) {
                        ((InterClass) elem).getPosition().setFirst(((InterClass) elem).getPosition().getFirst() + deltaX);
                        ((InterClass) elem).getPosition().setSecond(((InterClass) elem).getPosition().getSecond() + deltaY);
                    }
                }
                painters.clear();
                this.init(diagram);
                this.repaint();
            }
        }
    }
    public void zoomInS(Object notification){
        String stringX = notification.toString().split("/")[0];
        String stringY = notification.toString().split("/")[1];

        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);

        this.zoomIn(x, y);
    }
    public void zoomOutS(Object notification){
        String stringX = notification.toString().split("/")[0];
        String stringY = notification.toString().split("/")[1];

        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);

        this.zoomOut(x, y);
    }
    public void refresh(Object notification){
        System.out.println("REFRESH");
        this.init(diagram);
        this.repaint();
    }
    public void startDrag(Object notification){
        tempPainters.addAll(painters);
    }

    public void drag(Object notification){
        if(selectionPainter == null) {
            InterClass temp = new Klasa(null, "temp");
            selectionPainter = new SelectionPainter(temp);
            painters.add(selectionPainter);
        }

        String startStr[] = notification.toString().split(":");
        String start[] = startStr[0].split("/");
        String end[] = startStr[1].split("/");
        int startX = Integer.parseInt(start[0]);
        int startY = Integer.parseInt(start[1]);
        int endX = Integer.parseInt(end[0]);
        int endY = Integer.parseInt(end[1]);

        int rectX = Math.min(startX, endX);
        int rectY = Math.min(startY, endY);
        int rectWidth = Math.abs(endX - startX);
        int rectHeight = Math.abs(endY - startY);

        InterClass interClassElement = (InterClass) selectionPainter.getDiagramElement();
        interClassElement.getPosition().setFirst(rectX);
        interClassElement.getPosition().setSecond(rectY);
        interClassElement.getSize().setFirst(rectWidth);
        interClassElement.getSize().setSecond(rectHeight);

        this.repaint();
    }
    public void clearDrag(Object notification){
        painters.remove(selectionPainter);
        selectionPainter = null;
        this.repaint();
    }
    public void startDragD(Object notification){
        tempPainters.addAll(painters);
    }
    public void clearDragD(Object notification){
        painters.remove(selectionPainterDel);
        selectionPainterDel = null;
        this.repaint();
    }
    public void delRel(Object notification){
        painters.clear();
        selectedElementsD.clear();
        this.init(diagram);
        this.repaint();
        String startStr[] = notification.toString().split(":");
        String start[] = startStr[0].split("/");
        String end[] = startStr[1].split("/");
        int startX = Integer.parseInt(start[0]);
        int startY = Integer.parseInt(start[1]);
        int endX = Integer.parseInt(end[0]);
        int endY = Integer.parseInt(end[1]);
        for(DiagramElement elem : diagram.getDiagramElements()){
            if(elem instanceof InterClass){
                if(startX >= ((InterClass) elem).getPosition().getFirst() && startX <= ((InterClass) elem).getPosition().getFirst() + ((InterClass) elem).getSize().getFirst() && startY >= ((InterClass) elem).getPosition().getSecond() && startY <= ((InterClass) elem).getPosition().getSecond() + ((InterClass) elem).getSize().getSecond()){
                    selectedElementsD.add(elem);
                }
                else if(endX >= ((InterClass) elem).getPosition().getFirst() && endX <= ((InterClass) elem).getPosition().getFirst() + ((InterClass) elem).getSize().getFirst() && endY >= ((InterClass) elem).getPosition().getSecond() && endY <= ((InterClass) elem).getPosition().getSecond() + ((InterClass) elem).getSize().getSecond()){
                    selectedElementsD.add(elem);
                }
            }
            else if(elem instanceof  Connection){
                if(startX >= ((Connection) elem).getFromElement().getPosition().getFirst() && startX <= ((Connection) elem).getFromElement().getPosition().getFirst() + ((Connection) elem).getFromElement().getSize().getFirst() && startY >= ((Connection) elem).getFromElement().getPosition().getSecond() && startY <= ((Connection) elem).getFromElement().getPosition().getSecond() + ((Connection) elem).getFromElement().getSize().getSecond()){
                    selectedElementsD.add(elem);
                }
                else if(endX >= ((Connection) elem).getFromElement().getPosition().getFirst() && endX <= ((Connection) elem).getFromElement().getPosition().getFirst() + ((Connection) elem).getFromElement().getSize().getFirst() && endY >= ((Connection) elem).getFromElement().getPosition().getSecond() && endY <= ((Connection) elem).getFromElement().getPosition().getSecond() + ((Connection) elem).getFromElement().getSize().getSecond()){
                    selectedElementsD.add(elem);
                }
                else if(startX >= ((Connection) elem).getToElement().getPosition().getFirst() && startX <= ((Connection) elem).getToElement().getPosition().getFirst() + ((Connection) elem).getToElement().getSize().getFirst() && startY >= ((Connection) elem).getToElement().getPosition().getSecond() && startY <= ((Connection) elem).getToElement().getPosition().getSecond() + ((Connection) elem).getToElement().getSize().getSecond()){
                    selectedElementsD.add(elem);
                }
                else if(endX >= ((Connection) elem).getToElement().getPosition().getFirst() && endX <= ((Connection) elem).getToElement().getPosition().getFirst() + ((Connection) elem).getToElement().getSize().getFirst() && endY >= ((Connection) elem).getToElement().getPosition().getSecond() && endY <= ((Connection) elem).getToElement().getPosition().getSecond() + ((Connection) elem).getToElement().getSize().getSecond()){
                    selectedElementsD.add(elem);
                }
            }
        }
    }
    public void dragD(Object notification){
        if(selectionPainterDel == null) {
            InterClass temp = new Klasa(null, "temp");
            selectionPainterDel = new SelectionPainterDel(temp);
            painters.add(selectionPainterDel);
        }
        System.out.println(notification.toString());
        String startStr[] = notification.toString().split(":");
        String start[] = startStr[0].split("/");
        String end[] = startStr[1].split("/");
        int startX = Integer.parseInt(start[0]);
        int startY = Integer.parseInt(start[1]);
        int endX = Integer.parseInt(end[0]);
        int endY = Integer.parseInt(end[1]);

        int rectX = Math.min(startX, endX);
        int rectY = Math.min(startY, endY);
        int rectWidth = Math.abs(endX - startX);
        int rectHeight = Math.abs(endY - startY);

        InterClass interClassElement = (InterClass) selectionPainterDel.getDiagramElement();
        interClassElement.getPosition().setFirst(rectX);
        interClassElement.getPosition().setSecond(rectY);
        interClassElement.getSize().setFirst(rectWidth);
        interClassElement.getSize().setSecond(rectHeight);

        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(ElementPainter p : selectedPainters){
            System.out.println("Selected: " + p);
            selectedElements.add(p.getDiagramElement());
            selectedElementsD.add(p.getDiagramElement());
        }
        Graphics2D g2d = (Graphics2D) g;

        if (setScale)
        {
            affineTransform = g2d.getTransform();
            System.out.println(affineTransform.getScaleX());
            scale = affineTransform.getScaleX();
            step /= scale;
            setScale = false;
        }
        g2d.setTransform(affineTransform);

        for (ElementPainter painter : painters)
        {
            if(!selectedPainters.contains(painter))
            {
                if(painter instanceof GeneralizacijaPainter)
                    ((GeneralizacijaPainter) painter).setSelected(false);
                else if(painter instanceof AgregacijaPainter)
                    ((AgregacijaPainter) painter).setSelected(false);
                else if(painter instanceof KompozicijaPainter)
                    ((KompozicijaPainter) painter).setSelected(false);
                else if(painter instanceof ZavisnostPainter)
                    ((ZavisnostPainter) painter).setSelected(false);
                painter.paint(g2d);

            }
            else if(painter instanceof InterClassPainter){
                InterClassPainter interClassPainter = (InterClassPainter) painter;
                interClassPainter.paintSelected(g2d);
            }
            else if(painter instanceof GeneralizacijaPainter){
                //generalizacijaPainter.paintSelected(g2d);
                ((GeneralizacijaPainter) painter).setSelected(true);
                if(((GeneralizacijaPainter) painter).getSelected())
                    painter.paint(g2d);
            }
            else if(painter instanceof AgregacijaPainter){
                //agregacijaPainter.paintSelected(g2d);
                ((AgregacijaPainter) painter).setSelected(true);
                if(((AgregacijaPainter) painter).getSelected())
                    painter.paint(g2d);
            }
            else if(painter instanceof KompozicijaPainter){
                //kompozicijaPainter.paintSelected(g2d);
                ((KompozicijaPainter) painter).setSelected(true);
                if(((KompozicijaPainter) painter).getSelected())
                    painter.paint(g2d);
            }
            else if(painter instanceof ZavisnostPainter){
                //zavisnostPainter.paintSelected(g2d);
                ((ZavisnostPainter) painter).setSelected(true);
                if(((ZavisnostPainter) painter).getSelected())
                    painter.paint(g2d);
            }
        }
    }

    private void setUpZoomTransformation(int mouseX, int mouseY)
    {
        //AffineTransform je ugradjena klasa koja se koristi za razne transformacije
        double translateX = affineTransform.getTranslateX();
        double translateY = affineTransform.getTranslateY();

        //Na osnovu pozicije misa, translateX translateY i scaleFactora, trazimo koordinate na koje treba da se transliramo odnosno pomerimo
        affineTransform.translate(
                (mouseX - translateX) * (1 - scaleFactor),
                (mouseY - translateY) * (1 - scaleFactor)
        );

        //scale uzima dva argumenta jedan za skejlovanje duz x ose jedan za skejlovanje duz y ose
        this.affineTransform.scale(scaleFactor, scaleFactor);

        //Proveravamo da li je razlika izmedju trenutnog scalea i scalea koji smo prosledili manja od 0.01 ako jeste onda menjamo scale na default vrednost
        if(Math.abs(affineTransform.getScaleX() - scale) < 0.01) {
            AffineTransform newTransform = new AffineTransform();
            newTransform.translate(affineTransform.getTranslateX(), affineTransform.getTranslateY());
            newTransform.scale(scale, scale);
            this.affineTransform.setTransform(newTransform);
        }

        //Rekalkulisi layout i podesi ga na novu velicinu
        this.revalidate();
        //Repaint odnosno refreshujemo
        this.repaint();
    }

    public void zoomIn(int mouseX, int mouseY)
    {
        this.scaleFactor = 1.09;

        if (affineTransform.getScaleX() >= 1.9)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.MAX_ZOOM);
            return;
        }
        setUpZoomTransformation(mouseX, mouseY);
    }

    public void zoomOut(int mouseX, int mouseY)
    {
        scaleFactor = 1/ 1.09; //reciprocna vrednost zoomIna

        if (affineTransform.getScaleX() <= 0.4 * scale)
        {
            AppCore.getInstance().getMessageGenerator().generate(EventType.MIN_ZOOM);
            return;
        }
        setUpZoomTransformation(mouseX, mouseY);
    }

    public void zoomToFit() {
        Rectangle boundingBox = calculateBoundingBox();

        //Ako je boundingBox prazan, ne radimo nista
        if (boundingBox.isEmpty())
            return;

        double panelWidth = getWidth();
        double panelHeight = getHeight();

        //Racunamo scaling factor kako bi fitovali boundingBox u panel
        double scaleX = panelWidth / boundingBox.getWidth();
        double scaleY = panelHeight / boundingBox.getHeight();

        //Racunamo minScale kako bi boundingBox bio vidljiv u panelu, odnosno kako bi se videlo sve sto je iscrtano
        double minScale = Math.min(scaleX, scaleY);

        // Calculate average position of all elements
        double avgX = 0;
        double avgY = 0;
        int count = 0;

        //Iteriramo kroz listu paintera i gledamo da li je painter instnanca interClass, zato sto na osnovu njih racunamo prosecnu poziciju oko koje bi trebalo da se centriramo
        for (ElementPainter painter : painters)
        {
            DiagramElement element = painter.getDiagramElement();

            if (element instanceof InterClass)
            {
                InterClass interClass = (InterClass) element;
                avgX += interClass.getPosition().getFirst() + 0.5 * interClass.getSize().getFirst();
                avgY += interClass.getPosition().getSecond() + 0.5 * interClass.getSize().getSecond();
                count++;
            }
        }

        if (count == 0)
            return; // No elements to calculate average

        avgX /= count;
        avgY /= count;

        // Racunamo horizontalnu tranziaciju i vertikalnu tranziaciju koja nam je potrebna da bi boundingBox bio centriran
        double translateX = panelWidth / 2.0 - avgX * minScale;
        double translateY = panelHeight / 2.0 - avgY * minScale;

        //AffineTransform je ugradjena klasa koja se koristi za razne transformacije
        // Postavljamo transformaciju
        affineTransform.setToIdentity();
        //Pomeramo se na nove koordinate
        affineTransform.translate(translateX, translateY);
        //Skaliramo odnosno menjamo velicinu
        affineTransform.scale(minScale, minScale);

        //Repaint odnosno refreshujemo
        repaint();
    }

    private Rectangle calculateBoundingBox() {
        Rectangle boundingBox = new Rectangle();

        //Iteriramo kroz listu paintera i gledamo da li je painter instnanca interClass, zato sto na osnovu njih zoomToFitujemo
        for (ElementPainter painter : painters)
        {
            DiagramElement element = painter.getDiagramElement();

            if (element instanceof InterClass)
            {
                //Uzimam poziciju i velicinu klase i na osnovu toga pravim Rectangle
                InterClass interClass = (InterClass) element;
                int x = interClass.getPosition().getFirst();
                int y = interClass.getPosition().getSecond();
                int width = interClass.getSize().getFirst();
                int height = interClass.getSize().getSecond();

                //Dodajemo rectangle u boundingBox koji na osnovu tog dodavanja kreira najmanji moguci pravougaonik koji obuhvata sve Rectangleove
                boundingBox = boundingBox.union(new Rectangle(x, y, width, height));
            }
        }

        return boundingBox;
    }

    private ArrayList<Connection> getConnections(Diagram diagram)
    {
        ArrayList<Connection> connectionList = new ArrayList<>();
        for (DiagramElement element : diagram.getDiagramElements()) {
            if (element instanceof Connection) {
                Connection connection = (Connection) element;
                connectionList.add(connection);
            }
        }
        return connectionList;
    }

    public void outsideRefresh(){
        this.init(diagram);
        this.repaint();
    }



    private void setupDeleteKeyBinding() {
        Action deleteAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeleteKeyPressed();
            }
        };

        String keyStrokeAndActionKey = "DELETE";
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke("DELETE");
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(deleteKeyStroke, keyStrokeAndActionKey);
        this.getActionMap().put(keyStrokeAndActionKey, deleteAction);
    }

    private void onDeleteKeyPressed() {
        System.out.println("DELETE PRESSED");
        EventBus.getInstance().notifySubscriber(this, EventType.DELETE_KEY);
    }
}
