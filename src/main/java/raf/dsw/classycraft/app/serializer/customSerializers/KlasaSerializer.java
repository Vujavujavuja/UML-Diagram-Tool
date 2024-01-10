package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.ClassContent;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Klasa;

import java.io.IOException;

public class KlasaSerializer extends JsonSerializer<Klasa> {
    @Override
    public void serialize(Klasa klasa, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeICC", "class");
        jsonGenerator.writeNumberField("position_1st", klasa.getPosition().getFirst());
        jsonGenerator.writeNumberField("position_2nd", klasa.getPosition().getSecond());
        jsonGenerator.writeNumberField("size_1st", klasa.getSize().getFirst());
        jsonGenerator.writeNumberField("size_2nd", klasa.getSize().getSecond());
        jsonGenerator.writeStringField("name", klasa.getName());
        jsonGenerator.writeArrayFieldStart("ClassContent");
        for (ClassContent child : klasa.getClassContents()) {
            jsonGenerator.writeObject(child);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}