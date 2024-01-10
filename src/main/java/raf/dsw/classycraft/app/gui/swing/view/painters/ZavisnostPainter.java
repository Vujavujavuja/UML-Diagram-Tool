package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;

import java.awt.*;

public class ZavisnostPainter extends ConnectionPainter
{
    private static boolean selected = false;
    int i= 0;
    private boolean release = false;
    /*
    0,0 - levo
    0,1 - desno
    1,0 - gore
    1,1 - dole
     */
    private int arrowDir[] = {0, 0};
    public ZavisnostPainter(Connection connectionElement, int i)
    {
        super(connectionElement);
        this.i = i;
    }

    @Override
    public void paint(Graphics2D g)
    {

        if(connectionElement.getToElement() == null)
            this.setBounds();
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

            int startMidCordX = (startX + endXofStart)/2;
            int startMidCordY = (startY + endYofStart)/2;

            int endMidCordX = (endStartX + endXofEnd)/2;
            int endMidCordY = (endStartY + endYofEnd)/2;

            int closestSides[] =
            findClosestSides(
                    startMidCordX, startMidCordY, endYofStart, startX, endXofStart, startY,
                    endMidCordX, endMidCordY, endYofEnd, endStartX, endXofEnd, endStartY
            );

            System.out.println("Closest sides: " + closestSides[0] + "-" + closestSides[1]);

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

        g.setPaint(Color.BLACK);

        float[] dashPattern = { 2, 2 };

        g.setStroke(new BasicStroke(
                connectionElement.getStroke(),
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10,
                dashPattern,
                0
        ));

        if(!selected)
            g.setPaint(Color.BLACK);
        else
            g.setPaint(Color.BLUE);
        g.draw(shape);
        g.fill(shape);

        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(connectionElement.getStroke()));
        if(i==0)
            drawArrowHead(g, connectionElement.getEnd().getFirst(), connectionElement.getEnd().getSecond(), arrowDir);


    }

    public void drawArrowHead(Graphics g, int x, int y, int[] arrowDir) {
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



    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return selected;
    }

    public static int[] findClosestSides(
            int startMidCordX, int startMidCordY, int endYofStart, int startX, int endXofStart, int startY,
            int endMidCordX, int endMidCordY, int endYofEnd, int endStartX, int endXofEnd, int endStartY
    ) {
        double minDistance = Double.MAX_VALUE;
        int[] closestSides = new int[2];

        int[][] startSides = {
                {startMidCordX, startY},       // Top (1)
                {startMidCordX, endYofStart},  // Bottom (2)
                {startX, startMidCordY},       // Left (3)
                {endXofStart, startMidCordY}   // Right (4)
        };

        int[][] endSides = {
                {endMidCordX, endStartY},      // Top (1)
                {endMidCordX, endYofEnd},      // Bottom (2)
                {endStartX, endMidCordY},      // Left (3)
                {endXofEnd, endMidCordY}       // Right (4)
        };

        for (int i = 0; i < startSides.length; i++) {
            for (int j = 0; j < endSides.length; j++) {
                double distance = calculateDistance(startSides[i], endSides[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestSides[0] = i + 1; // Adding 1 because array indices start at 0
                    closestSides[1] = j + 1; // Adding 1 for the same reason
                }
            }
        }

        return closestSides;
    }

    private static double calculateDistance(int[] point1, int[] point2) {
        return Math.sqrt(Math.pow(point2[0] - point1[0], 2) + Math.pow(point2[1] - point1[1], 2));
    }
}
