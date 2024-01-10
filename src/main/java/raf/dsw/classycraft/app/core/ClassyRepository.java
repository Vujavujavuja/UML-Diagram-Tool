package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;

public interface ClassyRepository {
    ProjectExplorer getProjectExplorer();

    void addChild(ClassyNodeComposite parent, ClassyNode child);
}
