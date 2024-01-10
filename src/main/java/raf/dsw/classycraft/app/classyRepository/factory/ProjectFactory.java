package raf.dsw.classycraft.app.classyRepository.factory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;

public class ProjectFactory extends ClassyNodeFactory
{

    private static int cnt = 1;
    @Override
    public ClassyNode createFactory(ClassyNode node)
    {
        String userName = System.getProperty("user.name");
        Project newProject = new Project("Project" + cnt++, node, userName, null);

        return newProject;
    }
}
