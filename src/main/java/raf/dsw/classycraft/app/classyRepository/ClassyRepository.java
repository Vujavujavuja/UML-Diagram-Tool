package raf.dsw.classycraft.app.classyRepository;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;

@Setter
@Getter
public class ClassyRepository implements raf.dsw.classycraft.app.core.ClassyRepository
{
    private ProjectExplorer projectExplorer;

    public ClassyRepository()
    {
        projectExplorer = new ProjectExplorer("Project Explorer:");
    }

    @Override
    public ProjectExplorer getProjectExplorer() {
        return projectExplorer;
    }
    @Override
    public void addChild(ClassyNodeComposite parent, ClassyNode child)
    {
    }
}
