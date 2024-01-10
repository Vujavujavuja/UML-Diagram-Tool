package raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UmlEnum extends InterClass
{
    List<String> constants = new ArrayList<>();
    public UmlEnum(ClassyNode parent, String name, List<String> constants)
    {
        super(parent, name);
        this.constants = constants;
        this.type = "Enum";
    }

    public UmlEnum(ClassyNode parent, String name, Visibility visibility, int x, int y)
    {
        super(parent, name, visibility, x, y);
        this.type = "Enum";
    }
    public UmlEnum(UmlEnum umlEnum)
    {
        super(umlEnum.getParent(), umlEnum.getName(), umlEnum.getVisibility(), umlEnum.getPosition().getFirst(), umlEnum.getPosition().getSecond());
        this.constants = umlEnum.getConstants();
        this.type = "Enum";
    }
    public void updateEnum(String constant, String newConstant)
    {
        int index = this.constants.indexOf(constant);
        this.constants.set(index, newConstant);
    }
}
