package raf.dsw.classycraft.app.classyRepository.implementation.subElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public abstract class InterClass extends DiagramElement
{
    private Visibility visibility;
    private String name = "";//dodato zbog nadklase stavljen prazan string zbog errora
    private Pair<Integer, Integer> size = new Pair<>(0, 0);
    private Pair<Integer, Integer> position =  new Pair<>(0, 0);
    public InterClass(ClassyNode parent, String name)
    {
        super(parent, name);
        this.visibility = Visibility.PUBLIC;
        //this.type = "InterClass";
    }

    public InterClass(ClassyNode parent, String name, Visibility visibility, int x, int y)
    {   //size za x osu odnosno za sirinu ima formulu jer se ono prilagodjava duzini imena
        super(parent, name);
        this.visibility = visibility;
        this.position = new Pair(x, y);
        this.size = new Pair(this.maxContentW().length()*8+120, maxContentL()*10 + 50); //Ciljana velicina je negde oko x = 150 i y = 200 u zavisnosti od velicine imena
        //this.type = "InterClass";
    }

    public void setPosition(Integer x, Integer y)
    {
        if (this.position == null) {
            this.position = new Pair<>(x, y);
        } else {
            this.position.setFirst(x);
            this.position.setSecond(y);
        }
        this.notifySubscriber(this, null);
    }

    public void setSize(Pair<Integer, Integer> size)
    {
        this.size = size;
        this.notifySubscriber(this, null); //Za typeOfUpdate prosledjujemo null jer cemo u notify odredjivati na osnovu instanceof
    }

    public String maxContentW(){
        ArrayList<String> maxContent = new ArrayList<>();
        maxContent.add(this.name);
        if(this instanceof Klasa){
            Klasa klasa = (Klasa) this;
            if(klasa.getClassContents() != null)
                for(ClassContent classContent : klasa.getClassContents()){
                    maxContent.add(classContent.getName());
                }
        }
        else if(this instanceof Interfejs){
            Interfejs interfejs = (Interfejs) this;
            if(interfejs.getMetods() != null)
                for(Metod metod : interfejs.getMetods()){
                    maxContent.add(metod.getName());
                }
        }
        else if(this instanceof UmlEnum){
            UmlEnum umlEnum = (UmlEnum) this;
            if(umlEnum.getConstants() != null)
                for(String constant : umlEnum.getConstants()){
                    maxContent.add(constant);
                }
        }
        String max = "";
        for(String content : maxContent){
            if(content.length() > max.length()){
                max = content;
            }
        }
        return max;
    }
    public int maxContentL(){
        int counter = 0;
        ArrayList<String> content = new ArrayList<>();
        if(this instanceof Klasa){
            Klasa klasa = (Klasa) this;
            if(klasa.getClassContents() != null)
                for(ClassContent classContent : klasa.getClassContents()){
                    content.add(classContent.getName());
                }
        }
        else if(this instanceof Interfejs){
            Interfejs interfejs = (Interfejs) this;
            if(interfejs.getMetods() != null)
                for(Metod metod : interfejs.getMetods()){
                    content.add(metod.getName());
                }
        }
        else if(this instanceof UmlEnum){
            UmlEnum umlEnum = (UmlEnum) this;
            if(umlEnum.getConstants() != null)
                for(String constant : umlEnum.getConstants()){
                    content.add(constant);
                }
        }
        for(String s : content){
            counter++;
        }
        return counter;
    }

}
