package raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;

import java.util.Objects;

@Getter
@Setter
public abstract class ClassContent
{
    private String name;
    private Visibility visibility;
    private boolean isStatic;

    public ClassContent(String name, Visibility visibility, boolean isStatic)
    {
        this.name = name;
        this.visibility = visibility;
        this.isStatic = isStatic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassContent that = (ClassContent) o;
        return isStatic == that.isStatic && Objects.equals(name, that.name) && visibility == that.visibility;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, visibility, isStatic);
    }
}
