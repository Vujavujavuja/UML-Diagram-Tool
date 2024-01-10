package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.Metod;

import java.io.IOException;

public class MetodaSerializer extends JsonSerializer<Metod> {
    @Override
    public void serialize(Metod metod, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeMA", "Metod");
        jsonGenerator.writeStringField("name", metod.getName());
        jsonGenerator.writeStringField("visibility", metod.getVisibility().toString());
        jsonGenerator.writeBooleanField("isStatic", metod.isStatic());
        jsonGenerator.writeStringField("returnType", metod.getReturnType());
        jsonGenerator.writeEndObject();
    }
}
