package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Atribut;

import java.io.IOException;

public class AtributSerializer extends JsonSerializer<Atribut> {
    @Override
    public void serialize(Atribut atribut, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeMA", "Atribut");
        jsonGenerator.writeStringField("name", atribut.getName());
        jsonGenerator.writeStringField("visibility", atribut.getVisibility().toString());
        jsonGenerator.writeBooleanField("isStatic", atribut.isStatic());
        jsonGenerator.writeStringField("dataType", atribut.getDataType());
        jsonGenerator.writeEndObject();
    }
}
