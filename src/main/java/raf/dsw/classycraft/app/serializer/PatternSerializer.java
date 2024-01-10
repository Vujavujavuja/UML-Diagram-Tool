package raf.dsw.classycraft.app.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Agregacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Generalizacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Kompozicija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;
import raf.dsw.classycraft.app.core.Serializer;
import raf.dsw.classycraft.app.serializer.customDeserializers.*;
import raf.dsw.classycraft.app.serializer.customSerializers.*;

import java.io.File;
import java.io.IOException;

public class PatternSerializer implements Serializer {
    private final ObjectMapper objectMapper;

    public PatternSerializer() {
        this.objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();

        // Serializers
        module.addSerializer(Diagram.class, new DiagramSerializer());
        module.addSerializer(Agregacija.class, new AgregacijaSerializer());
        module.addSerializer(Generalizacija.class, new GeneralizacijaSerializer());
        module.addSerializer(Kompozicija.class, new KompozicijaSerializer());
        module.addSerializer(Zavisnost.class, new ZavisnostSerializer());
        module.addSerializer(Klasa.class, new KlasaSerializer());
        module.addSerializer(Interfejs.class, new InterfejsSerializer());
        module.addSerializer(UmlEnum.class, new EnumSerializer());
        module.addSerializer(Metod.class, new MetodaSerializer());
        module.addSerializer(Atribut.class, new AtributSerializer());

        // Deserializers
        module.addDeserializer(Diagram.class, new DiagramDeserializer());
        module.addDeserializer(Agregacija.class, new AgregacijaDeserializer());
        module.addDeserializer(Generalizacija.class, new GeneralizacijaDeserializer());
        module.addDeserializer(Kompozicija.class, new KompozicijaDeserializer());
        module.addDeserializer(Zavisnost.class, new ZavisnostDeserializer());
        module.addDeserializer(Klasa.class, new KlasaDeserializer());
        module.addDeserializer(Interfejs.class, new InterfejsDeserializer());
        module.addDeserializer(UmlEnum.class, new EnumDeserializer());
        module.addDeserializer(Metod.class, new MetodaDeserializer());
        module.addDeserializer(Atribut.class, new AtributDeserializer());

        objectMapper.registerModule(module);
    }

    @Override
    public void saveProject(String filePath, ClassyNode diagram) throws IOException {
        objectMapper.writeValue(new File(filePath), diagram);
    }

    @Override
    public Object openProject(String filePath) throws IOException {
        Diagram diagram = objectMapper.readValue(new File(filePath), Diagram.class);
        return diagram;
    }
}
