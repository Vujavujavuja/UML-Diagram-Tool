package raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Metod extends ClassContent
{
    private String returnType;
    private List<Parameter> parametars;

    public Metod(String name, Visibility visibility, boolean isStatic, String returnType, List<Parameter> parametars)
    {
        super(name, visibility, isStatic);
        this.parametars = new ArrayList<>();
        this.returnType = returnType;
        this.parametars = parametars;
    }
}
