package raf.dsw.classycraft.app.gui.swing.classyTree.model;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClassyTreeItem extends DefaultMutableTreeNode {
    private ClassyNode classyNode;
    private List<ClassyTreeItem> children = new ArrayList<>();

    public ClassyTreeItem(ClassyNode classyNode) {
        this.classyNode = classyNode;
    }

    @Override
    public String toString() {
        return classyNode.getName();
    }

    public void setName(String name) {
        this.classyNode.setName(name);
    }

}
