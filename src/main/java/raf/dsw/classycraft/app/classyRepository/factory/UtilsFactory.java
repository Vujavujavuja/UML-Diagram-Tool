package raf.dsw.classycraft.app.classyRepository.factory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;

public class UtilsFactory
{
    public static ProjectExplorerFactory pef;
    public static ProjectFactory pf;
    public static DiagramFactory mmf;
    public static PackageFactory ef;

    public UtilsFactory(){
        initialise();
    }
    private void initialise(){
        initialiseActions();
    }

    private void initialiseActions()
    {
        pef = new ProjectExplorerFactory();
        pf = new ProjectFactory();
        mmf = new DiagramFactory();
        ef = new PackageFactory();
    }

    public static ClassyNodeFactory returnFactory(ClassyNode parent)
    {
        if(parent instanceof ProjectExplorer)
            return pef;
        else if(parent instanceof Project)
            return pf;
        else if(parent instanceof Diagram)
            return mmf;
        else if(parent instanceof Package)
            return ef;
        return null;
    }
}
