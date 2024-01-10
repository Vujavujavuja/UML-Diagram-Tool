package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Generalizacija;

import java.io.IOException;

public class GeneralizacijaSerializer extends JsonSerializer<Generalizacija> {
    @Override
    public void serialize(Generalizacija generalizacija, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeICC", "generalizacija");
        jsonGenerator.writeNumberField("start_1st", generalizacija.getStart().getFirst());
        jsonGenerator.writeNumberField("start_2nd", generalizacija.getStart().getSecond());
        jsonGenerator.writeNumberField("end_1st", generalizacija.getEnd().getFirst());
        jsonGenerator.writeNumberField("end_2nd", generalizacija.getEnd().getSecond());
        jsonGenerator.writeStringField("name", generalizacija.getName());

        if (generalizacija.getFromElement() != null) {
            jsonGenerator.writeStringField("fromElement", generalizacija.getFromElement().getName());
        }
        if (generalizacija.getToElement() != null) {
            jsonGenerator.writeStringField("toElement", generalizacija.getToElement().getName());
        }

        jsonGenerator.writeEndObject();
    }
}
