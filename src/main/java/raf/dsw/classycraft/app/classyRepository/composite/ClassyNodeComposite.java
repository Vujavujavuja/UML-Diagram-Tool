package raf.dsw.classycraft.app.classyRepository.composite;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ClassyNodeComposite extends ClassyNode
{
    protected List<ClassyNode> children;
    public ClassyNodeComposite(ClassyNode parent, String name)
    {
        super(parent, name);
        this.children = new ArrayList<>();
    }
    public ClassyNodeComposite(ClassyNode parent, String name, List<ClassyNode> children)
    {
        super(parent, name);
        this.children = children;
    }

    public abstract void addChild(ClassyNode nodeToAdd);

    public ClassyNode getChildByName(String name)
    {
        for (ClassyNode child: this.getChildren())
        {
            if (name.equals(child.getName()))
                return child;
        }
        return null;
    }

    public abstract void removeChild(ClassyNode nodeToRemove);

    public int getIndex(List<ClassyNode> children){
        return children.size() + 1;
    }

    public List<ClassyNode> getChildren() {
        return children;
    }

}
