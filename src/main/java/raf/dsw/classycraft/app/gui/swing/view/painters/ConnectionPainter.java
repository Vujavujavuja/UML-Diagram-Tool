package raf.dsw.classycraft.app.gui.swing.view.painters;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;

import java.awt.geom.Line2D;

@Getter
@Setter
public abstract class ConnectionPainter extends ElementPainter
{
    protected Connection connectionElement;

    public ConnectionPainter(Connection connectionElement)
    {
        super(connectionElement);
        this.connectionElement = (Connection) connectionElement;
        setBounds();
    }

    public void setBounds()
    {
        if(connectionElement.getStart() == null || connectionElement.getEnd() == null)
            return;
        this.shape = new Line2D.Float(connectionElement.getStart().getFirst(), connectionElement.getStart().getSecond(), connectionElement.getEnd().getFirst(), connectionElement.getEnd().getSecond());
    }
}
