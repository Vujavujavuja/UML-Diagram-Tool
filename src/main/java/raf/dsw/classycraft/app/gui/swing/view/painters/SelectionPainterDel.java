package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SelectionPainterDel extends ElementPainter{
    private InterClass interClassElement;
    public SelectionPainterDel(DiagramElement diagramElement) {
        super(diagramElement);
        this.interClassElement = (InterClass) diagramElement;
        this.shape = new Rectangle2D.Float(interClassElement.getPosition().getFirst(), interClassElement.getPosition().getSecond(), interClassElement.getSize().getFirst(), interClassElement.getSize().getSecond());

    }

    @Override
    public void paint(Graphics2D g) {
        // Set the position and size of the Rectangle
        ((Rectangle2D)shape).setFrame(interClassElement.getPosition().getFirst(), interClassElement.getPosition().getSecond(), interClassElement.getSize().getFirst(), interClassElement.getSize().getSecond());

        //Boarder
        Color color = new Color(255, 100, 100, 80);
        g.setPaint(color);
        g.setStroke(new BasicStroke(interClassElement.getStroke()));
        g.draw(shape);
        color = new Color(255, 100, 100, 40);
        g.setPaint(color);
        g.fill(shape);
    }
}
