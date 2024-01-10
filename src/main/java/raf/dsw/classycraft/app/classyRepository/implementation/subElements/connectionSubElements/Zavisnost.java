package raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;

public class Zavisnost extends Connection
{
    public Zavisnost(ClassyNode parent, String name)
    {
        super(parent, name);
        this.type = "Zavisnost";
    }

    public Zavisnost(ClassyNode parent, String name, InterClass fromElement, int x, int y)
    {
        super(parent, name, fromElement, x, y);
        this.type = "Zavisnost";
    }
}
