package raf.dsw.classycraft.app.classyRepository.implementation.subElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;

import java.awt.*;
import java.util.Objects;

@Getter
@Setter
public abstract class Connection extends DiagramElement
{
    private String fromElementName;
    private String toElementName;
    private InterClass fromElement;
    private InterClass toElement;
    private Pair<Integer, Integer> start;
    private Pair<Integer, Integer> end;

    public Connection(ClassyNode parent, String name)
    {
        super(parent, name);
        //this.type = "Connection";
    }
    public Connection(ClassyNode parent, String name, InterClass fromElement, int x, int y)
    {
        super(parent, name);
        this.start = new Pair(x, y);
        this.end = start; //zato sto jos ne znamo gde ce biti kraj veze, stavljamo da je kraj jednak pocetku
        this.fromElement = fromElement; //Stavljamo od kog elementa potice veza
        this.setColor(Color.BLACK);
        this.setStroke(1);
        //this.type = "Connection";
    }

    public void setEnd(int x, int y) //U slucaju da izaziva neki problem pogledaj ovaj notify i ubaci jos neki case
    {
        this.end = new Pair(x, y);
        this.notifySubscriber(this, null); //Za typeOfUpdate prosledjujemo null jer cemo u notify odredjivati na osnovu instanceof
    }
    public void setEnd(Pair<Integer, Integer> end) //U slucaju da izaziva neki problem pogledaj ovaj notify i ubaci jos neki cas
    {
        this.end = end;
        this.notifySubscriber(this, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        if (!super.equals(o)) return false;
        Connection that = (Connection) o;
        return Objects.equals(getFromElement(), that.getFromElement()) && Objects.equals(getToElement(), that.getToElement()) && Objects.equals(getStart(), that.getStart()) && Objects.equals(getEnd(), that.getEnd());
    }

}
