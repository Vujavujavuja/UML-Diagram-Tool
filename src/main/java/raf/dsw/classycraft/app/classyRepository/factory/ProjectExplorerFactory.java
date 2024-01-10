package raf.dsw.classycraft.app.classyRepository.factory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;

public class ProjectExplorerFactory extends ClassyNodeFactory
{
    @Override
    public ClassyNode createFactory(ClassyNode node) {
        return new ProjectExplorer("Project Explorer");
    }
}
