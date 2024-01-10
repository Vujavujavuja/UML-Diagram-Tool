package raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
@Setter
@Getter

public class Agregacija extends Connection
{
    String fromMultiplicity = "0";
    String toMultiplicity = "1";
    public Agregacija(ClassyNode parent, String name)
    {
        super(parent, name);
        this.type = "Agregacija";
    }

    public Agregacija(ClassyNode parent, String name, InterClass fromElement, int x, int y)
    {
        super(parent, name, fromElement, x, y);
        this.type = "Agregacija";
    }
}
