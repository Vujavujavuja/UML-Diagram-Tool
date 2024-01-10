package raf.dsw.classycraft.app.gui.swing.controller.listner;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.DiagramPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class ClassyMouse implements MouseListener, MouseMotionListener{

    private boolean rightMouseButtonPressed = false;
    private AffineTransform affineTransform;

    public ClassyMouse(AffineTransform affineTransform) {
        this.affineTransform = affineTransform;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Cordinates: " + e.getX() + " " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //offset za y kordinatu je dodat jer iz nekog razloga nije lepo havatao poziciju misa idk
        MouseEvent correctedEvent = transformMouseEvent(e);

        if (e.getButton() == MouseEvent.BUTTON3) {
            rightMouseButtonPressed = true;
        }
        System.out.println("Cordinates: " + e.getX() + " " + e.getY());
        MainFrame.getInstance().getPackageView().handleMousePressed(correctedEvent, rightMouseButtonPressed);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MouseEvent correctedEvent = transformMouseEvent(e);
        MainFrame.getInstance().getPackageView().handleMouseReleased(correctedEvent, rightMouseButtonPressed);
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightMouseButtonPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MouseEvent correctedEvent = transformMouseEvent(e);
        MainFrame.getInstance().getPackageView().handleMouseDragged(correctedEvent, rightMouseButtonPressed);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    private Point2D transformMouseCoordinates(int mouseX, int mouseY) {
        try {
            AffineTransform inverse = affineTransform.createInverse();
            Point2D transformedPoint = new Point2D.Float();
            inverse.transform(new Point2D.Float(mouseX, mouseY), transformedPoint);
            return transformedPoint;
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return new Point2D.Float(mouseX, mouseY); //vrati stare koordinate ako nece da radi
        }
    }

    private MouseEvent transformMouseEvent(MouseEvent e) {
        AffineTransform affineTransform = MainFrame.getInstance().getPackageView().getAffineTransform();
        Point2D ptSrc = new Point2D.Float(e.getX(), e.getY());
        Point2D ptDst = new Point2D.Float();

        try {
            affineTransform.inverseTransform(ptSrc, ptDst);
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
            return e; // ako ne vrati stari :D
        }

        return new MouseEvent(
                e.getComponent(),
                e.getID(),
                e.getWhen(),
                e.getModifiers(),
                (int) ptDst.getX(),
                (int) ptDst.getY(), //Namesteno da ga ubodemo zbog offseta
                e.getClickCount(),
                e.isPopupTrigger(),
                MouseEvent.NOBUTTON
        );
    }

}
