package raf.dsw.classycraft.app.classyRepository.implementation;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.util.List;

public class ProjectExplorer extends ClassyNodeComposite
{
    public ProjectExplorer(String name)
    {
        super(null, name);
        super.getChildren();
    }

    @Override
    public void addChild(ClassyNode node)
    {
        if(node != null && node instanceof Project)
        {
            Project project = (Project) node;

            if(!this.getChildren().contains(project))
            {
                this.getChildren().add(project);
                this.setParent(this);
            }
        }
    }

    @Override
    public void removeChild(ClassyNode nodeToRemove) {

    }

    @Override
    public void notifySubscriber(Object notification, Object typeOfUpdate) {

    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {

    }

    @Override
    public int getIndex(List<ClassyNode> children) {
        return super.getIndex(children);
    }

}
