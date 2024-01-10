package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GeneralizacijaPainter extends ConnectionPainter
{
    int i= 0;
    private static boolean selected = false;
    private boolean release = false;
    /*
    0,0 - levo
    0,1 - desno
    1,0 - gore
    1,1 - dole
     */
    private int arrowDir[] = {0, 0};
    public GeneralizacijaPainter(Connection connectionElement, int i)
    {
        super(connectionElement);
        this.i = i;
    }

    @Override
    public void paint(Graphics2D g)
    {
        if(connectionElement.getToElement() == null) {
            System.out.println("TO NULL");
            this.setBounds();
        }
        else
        {
            int startX = connectionElement.getFromElement().getPosition().getFirst();
            int startY = connectionElement.getFromElement().getPosition().getSecond();
            int endXofStart = startX + connectionElement.getFromElement().getSize().getFirst();
            int endYofStart = startY + connectionElement.getFromElement().getSize().getSecond();

            System.out.println("Start: " + startX + " " + startY);
            System.out.println("End: " + endXofStart + " " + endYofStart);

            int endStartX = connectionElement.getToElement().getPosition().getFirst();
            int endStartY = connectionElement.getToElement().getPosition().getSecond();
            int endXofEnd = endStartX + connectionElement.getToElement().getSize().getFirst();
            int endYofEnd = endStartY + connectionElement.getToElement().getSize().getSecond();

            int endMidCordX = (endStartX + endXofEnd)/2;
            int endMidCordY = (endStartY + endYofEnd)/2;

            if (endMidCordX < startX) {
                // To levo od from
                connectionElement.getStart().setFirst(startX);
                connectionElement.getStart().setSecond((startY + endYofStart)/2);
                connectionElement.getEnd().setFirst(endXofEnd);
                connectionElement.getEnd().setSecond(endMidCordY);
                arrowDir[0] = 0;
                arrowDir[1] = 0;
                ;
            }  else if (endMidCordY < startY - 10) {
                // To iznad from
                connectionElement.getStart().setFirst((startX + endXofStart)/2);
                connectionElement.getStart().setSecond(startY);
                connectionElement.getEnd().setFirst(endMidCordX);
                connectionElement.getEnd().setSecond(endYofEnd);
                arrowDir[0] = 1;
                arrowDir[1] = 0;
            }  else if (endMidCordX > endXofStart) {
                // To desno od from
                connectionElement.getStart().setFirst(endXofStart);
                connectionElement.getStart().setSecond((startY + endYofStart)/2);
                connectionElement.getEnd().setFirst(endStartX);
                connectionElement.getEnd().setSecond(endMidCordY);
                arrowDir[0] = 0;
                arrowDir[1] = 1;
            } else if (endMidCordY > endYofStart) {
                // To ispod from
                connectionElement.getStart().setFirst((startX + endXofStart)/2);
                connectionElement.getStart().setSecond(endYofStart);
                connectionElement.getEnd().setFirst(endMidCordX);
                connectionElement.getEnd().setSecond(endStartY);
                arrowDir[0] = 1;
                arrowDir[1] = 1;
            } else {
                System.out.println("OUT OF BOUNDS");

            }
        }

        if(!selected)
            g.setPaint(Color.BLACK);
        else
            g.setPaint(Color.BLUE);

        g.setStroke(new BasicStroke(connectionElement.getStroke()));

        g.draw(shape);
        g.setPaint(connectionElement.getColor());
        g.fill(shape);

        if(i==0)
            drawArrowHead(g, connectionElement.getEnd().getFirst(), connectionElement.getEnd().getSecond(), arrowDir);


    }


    //zastareli draw bleji za svaki slucaj ako zatreba
    public void drawArrowHead1(Graphics g, int x, int y, int[] arrowDir) {
        int size = 10;
        int dx = size, dy = size;
        g.setColor(Color.BLACK);
        if (arrowDir[0] == 0 && arrowDir[1] == 0) { //levo
            g.drawLine(x, y, x + dx, y - dy); //gornja linija strelice
            g.drawLine(x, y, x + dx, y + dy); //donja
        } else if (arrowDir[0] == 0 && arrowDir[1] == 1) { // desno
            g.drawLine(x, y, x - dx, y - dy);
            g.drawLine(x, y, x - dx, y + dy);
        } else if (arrowDir[0] == 1 && arrowDir[1] == 0) { //gore
            g.drawLine(x, y, x - dx, y + dy);
            g.drawLine(x, y, x + dx, y + dy);
        } else if (arrowDir[0] == 1 && arrowDir[1] == 1) { // Dole
            g.drawLine(x, y, x - dx, y - dy);
            g.drawLine(x, y, x + dx, y - dy);
        }
    }

    //preko poligona zbog filla belom bojom
    public void drawArrowHead(Graphics g, int x, int y, int[] arrowDir) {
        int size = 10; //Velicina strelice
        int dx = size, dy = size;

        int xPoints[] = new int[3];
        int yPoints[] = new int[3];

        if (arrowDir[0] == 0 && arrowDir[1] == 0) { //Levo
            xPoints[0] = x; yPoints[0] = y;
            xPoints[1] = x + dx; yPoints[1] = y - dy;
            xPoints[2] = x + dx; yPoints[2] = y + dy;
        } else if (arrowDir[0] == 0 && arrowDir[1] == 1) {  //Desno
            xPoints[0] = x; yPoints[0] = y;
            xPoints[1] = x - dx; yPoints[1] = y - dy;
            xPoints[2] = x - dx; yPoints[2] = y + dy;
        } else if (arrowDir[0] == 1 && arrowDir[1] == 0) { //Gore
            xPoints[0] = x; yPoints[0] = y;
            xPoints[1] = x - dx; yPoints[1] = y + dy;
            xPoints[2] = x + dx; yPoints[2] = y + dy;
        } else if (arrowDir[0] == 1 && arrowDir[1] == 1) {  //Dole
            xPoints[0] = x; yPoints[0] = y;
            xPoints[1] = x - dx; yPoints[1] = y - dy;
            xPoints[2] = x + dx; yPoints[2] = y - dy;
        }

        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 3);
        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, 3);
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean getSelected() {
        return selected;
    }
}
