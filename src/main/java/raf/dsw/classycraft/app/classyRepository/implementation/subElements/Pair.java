package raf.dsw.classycraft.app.classyRepository.implementation.subElements;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<K, V>
{
    private K first;
    private V second;
    public Pair(K first, V second)
    {
        this.first = first;
        this.second = second;
    }
}
//U WCU SAM