package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.commands.CommandManager;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeLeaf;

import java.awt.*;

@Getter
@Setter
public abstract class DiagramElement extends ClassyNodeLeaf
{
    private Integer stroke;
    private Color color;
    public DiagramElement(ClassyNode parent, String name)
    {
        super(parent, name);
        this.stroke = 2; //Defaultna vrednost za stroke
        this.color = new Color(193,228,247); //Defaultna svetlo siva boja
        this.type = "DiagramElement";
    }

    public void setColor(Color color)
    {
        this.color = color;
        this.notifySubscriber(this, null); //Za typeOfUpdate prosledjujemo null jer cemo u notify odredjivati na osnovu instanceof
    }

    public void setStroke(Integer stroke)
    {
        this.stroke = stroke;
        this.notifySubscriber(this, null); //Za typeOfUpdate prosledjujemo null jer cemo u notify odredjivati na osnovu instanceof
    }
}
