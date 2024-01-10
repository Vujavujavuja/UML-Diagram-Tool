package raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.ClassContent;

import java.lang.reflect.Type;

@Getter
@Setter
public class Atribut extends ClassContent
{
    private String dataType;

    public Atribut(String name, Visibility visibility, boolean isStatic, String dataType)
    {
        super(name, visibility, isStatic);
        this.dataType = dataType;
    }
}
