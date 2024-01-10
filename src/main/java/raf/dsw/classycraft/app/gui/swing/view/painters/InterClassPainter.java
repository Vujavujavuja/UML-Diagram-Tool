package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class InterClassPainter extends ElementPainter
{
    private InterClass interClassElement;
    int maxWidth = 0;


    public InterClassPainter(DiagramElement diagramElement)
    {
        super(diagramElement);
        this.interClassElement = (InterClass) diagramElement;
        this.shape = new Rectangle2D.Float(interClassElement.getPosition().getFirst(), interClassElement.getPosition().getSecond(), interClassElement.getSize().getFirst(), interClassElement.getSize().getSecond());

    }

    public void paint(Graphics2D g) {
        // Set the position and size of the Rectangle
        interClassElement.setSize(new Pair<>(interClassElement.maxContentW().length()*8+120, interClassElement.maxContentL()*20 + 50));
        ((Rectangle2D)shape).setFrame(interClassElement.getPosition().getFirst(), interClassElement.getPosition().getSecond(), interClassElement.getSize().getFirst(), interClassElement.getSize().getSecond());

        //Boarder
        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(interClassElement.getStroke()));
        g.draw(shape);
        //Background
        g.setPaint(interClassElement.getColor());
        g.fill(shape);
        //Text
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        g.setPaint(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(interClassElement.getName());
        int x = (int) (interClassElement.getPosition().getFirst() + (interClassElement.getSize().getFirst() - stringWidth) / 2);
        int y = (int) (interClassElement.getPosition().getSecond() + fm.getAscent());
        g.drawString(interClassElement.getName(), x, y);



        int borderY = y + fm.getDescent();
        g.drawLine((int) interClassElement.getPosition().getFirst(), borderY, (int) (interClassElement.getPosition().getFirst() + interClassElement.getSize().getFirst()), borderY);
        if (this.interClassElement instanceof Klasa) {
            Klasa klasa = (Klasa) this.interClassElement;
            ArrayList<Atribut> atributList = new ArrayList<>();
            ArrayList<Metod> metodList = new ArrayList<>();
            for(ClassContent content : klasa.getClassContents())
            {
                if(content instanceof Atribut)
                {
                    atributList.add((Atribut) content);
                }
                else
                {
                    metodList.add((Metod) content);
                }
            }
            int currentY = borderY + fm.getAscent() + 5;
            for(Atribut atribut : atributList)
            {
                String prefix = "";
                if(atribut.getVisibility().equals(Visibility.PUBLIC))
                {
                    prefix = "+";
                }
                else if(atribut.getVisibility().equals(Visibility.PRIVATE))
                {
                    prefix = "-";
                }
                else if(atribut.getVisibility().equals(Visibility.PROTECTED))
                {
                    prefix = "#";
                }
                else
                {
                    prefix = "~";
                }
                String staticPrefix = "";
                if(atribut.isStatic())
                {
                    staticPrefix = "static ";
                }
                String displayText =prefix +  atribut.getName() + ": "+ staticPrefix + atribut.getDataType();
                g.drawString(displayText, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
                maxWidth = Math.max(maxWidth, fm.stringWidth(displayText));
            }
            if (!atributList.isEmpty() && !metodList.isEmpty()) {
                currentY -= 10;
                g.drawLine((int) interClassElement.getPosition().getFirst(), currentY, (int) (interClassElement.getPosition().getFirst() + interClassElement.getSize().getFirst()), currentY);
                currentY += 15;
            }
            for(Metod metod : metodList)
            {
                String prefix = "";
                if(metod.getVisibility().equals(Visibility.PUBLIC))
                {
                    prefix = "+";
                }
                else if(metod.getVisibility().equals(Visibility.PRIVATE))
                {
                    prefix = "-";
                }
                else if(metod.getVisibility().equals(Visibility.PROTECTED))
                {
                    prefix = "#";
                }
                else
                {
                    prefix = "~";
                }
                String staticPrefix = "";
                if(metod.isStatic())
                {
                    staticPrefix = "static ";
                }
                String displayText = prefix  + metod.getName() + "():"+ staticPrefix + metod.getReturnType();
                /*
                for(int i = 0; i < metod.getParametars().size(); i++)
                {
                    Parameter parametar = metod.getParametars().get(i);
                    displayText += parametar.getType().getTypeName() + " " + parametar.getName();
                    if(i != metod.getParametars().size() - 1)
                    {
                        displayText += ", ";
                    }
                }
                displayText += "): " + metod.getReturnType();
                */

                g.drawString(displayText, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
                maxWidth = Math.max(maxWidth, fm.stringWidth(displayText));
            }
        } else if (this.interClassElement instanceof Interfejs) {
            Interfejs interfejs = (Interfejs) this.interClassElement;
            int currentY = borderY + fm.getAscent() + 5;
            for(Metod metod : interfejs.getMetods()) {
                String prefix = "";
                if (metod.getVisibility().equals(Visibility.PUBLIC)) {
                    prefix = "+";
                } else if (metod.getVisibility().equals(Visibility.PRIVATE)) {
                    prefix = "-";
                } else if (metod.getVisibility().equals(Visibility.PROTECTED)) {
                    prefix = "#";
                } else {
                    prefix = "~";
                }
                String staticPrefix = "";
                if (metod.isStatic()) {
                    staticPrefix = "static ";
                }
                String displayText = prefix + staticPrefix + metod.getName() + "():" + metod.getReturnType();
                /*
                for(int i = 0; i < metod.getParametars().size(); i++)
                {
                    Parameter parametar = metod.getParametars().get(i);
                    displayText += parametar.getType().getTypeName() + " " + parametar.getName();
                    if(i != metod.getParametars().size() - 1)
                    {
                        displayText += ", ";
                    }
                }
                displayText += "): " + metod.getReturnType();
                */

                g.drawString(displayText, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
            }
        } else if (this.interClassElement instanceof UmlEnum) {
            UmlEnum umlEnum = (UmlEnum) this.interClassElement;
            int currentY = borderY + fm.getAscent() + 5;
            for (String constant : umlEnum.getConstants()) {
                g.drawString(constant, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
                maxWidth = Math.max(maxWidth, fm.stringWidth(constant));
            }
        }
    }

    public void paintSelected(Graphics2D g) {
        ((Rectangle2D)shape).setFrame(interClassElement.getPosition().getFirst(), interClassElement.getPosition().getSecond(), interClassElement.getSize().getFirst(), interClassElement.getSize().getSecond());

        //Boarder
        g.setPaint(Color.BLUE);
        g.setStroke(new BasicStroke(interClassElement.getStroke()));
        g.draw(shape);
        //Background
        g.setPaint(interClassElement.getColor());
        g.fill(shape);
        //Text
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        g.setPaint(Color.BLUE);
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(interClassElement.getName());
        int x = (int) (interClassElement.getPosition().getFirst() + (interClassElement.getSize().getFirst() - stringWidth) / 2);
        int y = (int) (interClassElement.getPosition().getSecond() + fm.getAscent());
        g.drawString(interClassElement.getName(), x, y);



        int borderY = y + fm.getDescent();
        g.drawLine((int) interClassElement.getPosition().getFirst(), borderY, (int) (interClassElement.getPosition().getFirst() + interClassElement.getSize().getFirst()), borderY);
        if (this.interClassElement instanceof Klasa) {
            Klasa klasa = (Klasa) this.interClassElement;
            ArrayList<Atribut> atributList = new ArrayList<>();
            ArrayList<Metod> metodList = new ArrayList<>();
            for(ClassContent content : klasa.getClassContents())
            {
                if(content instanceof Atribut)
                {
                    atributList.add((Atribut) content);
                }
                else
                {
                    metodList.add((Metod) content);
                }
            }
            int currentY = borderY + fm.getAscent() + 5;
            for(Atribut atribut : atributList)
            {
                String prefix = "";
                if(atribut.getVisibility().equals(Visibility.PUBLIC))
                {
                    prefix = "+";
                }
                else if(atribut.getVisibility().equals(Visibility.PRIVATE))
                {
                    prefix = "-";
                }
                else if(atribut.getVisibility().equals(Visibility.PROTECTED))
                {
                    prefix = "#";
                }
                else
                {
                    prefix = "~";
                }
                String staticPrefix = "";
                if(atribut.isStatic())
                {
                    staticPrefix = "static ";
                }
                String displayText =prefix + staticPrefix + atribut.getName() + ": " + atribut.getDataType();
                g.drawString(displayText, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
                maxWidth = Math.max(maxWidth, fm.stringWidth(displayText));
            }
            if (!atributList.isEmpty() && !metodList.isEmpty()) {
                currentY -= 10;
                g.drawLine((int) interClassElement.getPosition().getFirst(), currentY, (int) (interClassElement.getPosition().getFirst() + interClassElement.getSize().getFirst()), currentY);
                currentY += 15;
            }
            for(Metod metod : metodList)
            {
                String prefix = "";
                if(metod.getVisibility().equals(Visibility.PUBLIC))
                {
                    prefix = "+";
                }
                else if(metod.getVisibility().equals(Visibility.PRIVATE))
                {
                    prefix = "-";
                }
                else if(metod.getVisibility().equals(Visibility.PROTECTED))
                {
                    prefix = "#";
                }
                else
                {
                    prefix = "~";
                }
                String staticPrefix = "";
                if(metod.isStatic())
                {
                    staticPrefix = "static ";
                }
                String displayText = prefix + staticPrefix + metod.getName() + "():" + metod.getReturnType();
                /*
                for(int i = 0; i < metod.getParametars().size(); i++)
                {
                    Parameter parametar = metod.getParametars().get(i);
                    displayText += parametar.getType().getTypeName() + " " + parametar.getName();
                    if(i != metod.getParametars().size() - 1)
                    {
                        displayText += ", ";
                    }
                }
                displayText += "): " + metod.getReturnType();
                */

                g.drawString(displayText, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
                maxWidth = Math.max(maxWidth, fm.stringWidth(displayText));
            }
        } else if (this.interClassElement instanceof Interfejs) {
            Interfejs interfejs = (Interfejs) this.interClassElement;
            int currentY = borderY + fm.getAscent() + 5;
            for(Metod metod : interfejs.getMetods()) {
                String prefix = "";
                if (metod.getVisibility().equals(Visibility.PUBLIC)) {
                    prefix = "+";
                } else if (metod.getVisibility().equals(Visibility.PRIVATE)) {
                    prefix = "-";
                } else if (metod.getVisibility().equals(Visibility.PROTECTED)) {
                    prefix = "#";
                } else {
                    prefix = "~";
                }
                String staticPrefix = "";
                if (metod.isStatic()) {
                    staticPrefix = "static ";
                }
                String displayText = prefix + staticPrefix + metod.getName() + "():" + metod.getReturnType();
                /*
                for(int i = 0; i < metod.getParametars().size(); i++)
                {
                    Parameter parametar = metod.getParametars().get(i);
                    displayText += parametar.getType().getTypeName() + " " + parametar.getName();
                    if(i != metod.getParametars().size() - 1)
                    {
                        displayText += ", ";
                    }
                }
                displayText += "): " + metod.getReturnType();
                */

                g.drawString(displayText, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
            }
        } else if (this.interClassElement instanceof UmlEnum) {
            UmlEnum umlEnum = (UmlEnum) this.interClassElement;
            int currentY = borderY + fm.getAscent() + 5;
            for (String constant : umlEnum.getConstants()) {
                g.drawString(constant, (int) interClassElement.getPosition().getFirst(), currentY);
                currentY += fm.getHeight();
                maxWidth = Math.max(maxWidth, fm.stringWidth(constant));
            }
        }
    }

    public ArrayList<String> checkContent(){
        ArrayList<String> content = new ArrayList<>();

        if(interClassElement instanceof Klasa){
            Klasa klasa = (Klasa) interClassElement;
            for(ClassContent classContent : klasa.getClassContents()){
                if(classContent instanceof Atribut){
                    Atribut atribut = (Atribut) classContent;
                    content.add(atribut.getName() + ": " + atribut.getDataType());
                }
                else if(classContent instanceof Metod){
                    Metod metod = (Metod) classContent;
                    String displayText = metod.getName() + "():" + metod.getReturnType();
                    content.add(displayText);
                }
            }
        }
        else if(interClassElement instanceof Interfejs){
            Interfejs interfejs = (Interfejs) interClassElement;
            for(Metod metod : interfejs.getMetods()){
                String displayText = metod.getName() + "():" + metod.getReturnType();
                content.add(displayText);
            }
        }
        else if(interClassElement instanceof UmlEnum){
            UmlEnum umlEnum = (UmlEnum) interClassElement;
            for(String constant : umlEnum.getConstants()){
                content.add(constant);
            }

        }

        return content;
    }
}
