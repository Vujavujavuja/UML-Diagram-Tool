package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Interfejs;

import java.io.IOException;

public class InterfejsSerializer extends JsonSerializer<Interfejs> {
    @Override
    public void serialize(Interfejs interfejs, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeICC", "interface");
        jsonGenerator.writeNumberField("position_1st", interfejs.getPosition().getFirst());
        jsonGenerator.writeNumberField("position_2nd", interfejs.getPosition().getSecond());
        jsonGenerator.writeNumberField("size_1st", interfejs.getSize().getFirst());
        jsonGenerator.writeNumberField("size_2nd", interfejs.getSize().getSecond());
        jsonGenerator.writeStringField("name", interfejs.getName());
        jsonGenerator.writeArrayFieldStart("metode");
        for (int i = 0; i < interfejs.getMetods().size(); i++) {
            jsonGenerator.writeObject(interfejs.getMetods().get(i));
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
