package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.UmlEnum;

import java.io.IOException;

public class EnumSerializer extends JsonSerializer<UmlEnum> {
    @Override
    public void serialize(UmlEnum umlEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeICC", "enum");
        jsonGenerator.writeNumberField("position_1st", umlEnum.getPosition().getFirst());
        jsonGenerator.writeNumberField("position_2nd", umlEnum.getPosition().getSecond());
        jsonGenerator.writeNumberField("size_1st", umlEnum.getSize().getFirst());
        jsonGenerator.writeNumberField("size_2nd", umlEnum.getSize().getSecond());
        jsonGenerator.writeStringField("name", umlEnum.getName());
        jsonGenerator.writeArrayFieldStart("children");
        for (String child : umlEnum.getConstants()) {
            jsonGenerator.writeString(child);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
