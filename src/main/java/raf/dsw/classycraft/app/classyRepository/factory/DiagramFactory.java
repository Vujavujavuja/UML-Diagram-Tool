package raf.dsw.classycraft.app.classyRepository.factory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;

public class DiagramFactory extends ClassyNodeFactory
{
    private static int cnt = 1;
    @Override
    public ClassyNode createFactory(ClassyNode node)
    {
        return new Diagram("Diagram" + cnt++,node,false);
    }
}
