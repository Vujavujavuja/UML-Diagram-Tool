package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;

import java.io.IOException;

public class ZavisnostSerializer extends JsonSerializer<Zavisnost> {
    @Override
    public void serialize(Zavisnost zavisnost, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeICC", "zavisnost");
        jsonGenerator.writeNumberField("start_1st", zavisnost.getStart().getFirst());
        jsonGenerator.writeNumberField("start_2nd", zavisnost.getStart().getSecond());
        jsonGenerator.writeNumberField("end_1st", zavisnost.getEnd().getFirst());
        jsonGenerator.writeNumberField("end_2nd", zavisnost.getEnd().getSecond());
        jsonGenerator.writeStringField("name", zavisnost.getName());
        jsonGenerator.writeStringField("fromElement", zavisnost.getFromElement().getName());
        jsonGenerator.writeStringField("toElement", zavisnost.getToElement().getName());
        jsonGenerator.writeEndObject();
    }
}
