package raf.dsw.classycraft.app.gui.swing.view.painters;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;

import java.awt.*;
import java.awt.geom.Rectangle2D;

@Getter
@Setter
public abstract class ElementPainter
{
    //protected jer hocu odmah da im pristupim u subklasama bez pozivanja getera
    protected DiagramElement diagramElement;
    protected Shape shape;
    protected Color color;

    public ElementPainter(DiagramElement diagramElement) {
        this.diagramElement = diagramElement;
    }

    public abstract void paint(Graphics2D g); //Metoda koju ce implementirati sve subklase sa soptsvenom logikom iscrtavanja na 2d grafiku

    //Svrha ove metode jeste da proveri npr kada se desava MouseDragged event (proces selektovanja), da li se u toj napravljenoj povrsini nalazi neki element
    public boolean elementAt(Point pos) //Kada budes radio state sablon koristi ovo za  dodavanje, selektovanje i brisanje elemenata
    {
        return shape.intersects(new Rectangle2D.Float((float) pos.getX(), (float) pos.getY(), 6, 6));
    }
}
