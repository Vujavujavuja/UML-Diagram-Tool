package raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements;

import lombok.Getter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Metod;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Interfejs extends InterClass
{
    private List<Metod> metods;
    public Interfejs(ClassyNode parent, String name)
    {
        super(parent, name);
        this.metods = new ArrayList<>();
        this.type = "Interfejs";
    }

    public Interfejs(ClassyNode parent, String name, Visibility visibility, int x, int y)
    {
        super(parent, name, visibility, x, y);
        this.metods = new ArrayList<>();
        this.type = "Interfejs";
    }
    public Interfejs(Interfejs interfejs)
    {
        super(interfejs.getParent(), interfejs.getName(), interfejs.getVisibility(), interfejs.getPosition().getFirst(), interfejs.getPosition().getSecond());
        this.metods = new ArrayList<>();
        this.type = "Interfejs";
    }

    public void addClassContent(ClassContent classContent)
    {
        this.metods.add((Metod) classContent);
    }

    public void deleteClassContent(ClassContent classContent) {this.metods.remove(classContent);}

    public void updateClassContent(ClassContent classContent, ClassContent newClassContent)
    {
        int index = this.metods.indexOf(classContent);
        this.metods.set(index, (Metod) newClassContent);
    }
}
