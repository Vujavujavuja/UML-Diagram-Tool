package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Agregacija;

import java.io.IOException;

public class AgregacijaSerializer extends JsonSerializer<Agregacija> {

    @Override
    public void serialize(Agregacija agregacija, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeICC", "agregacija");
        jsonGenerator.writeNumberField("start_1st", agregacija.getStart().getFirst());
        jsonGenerator.writeNumberField("start_2nd", agregacija.getStart().getSecond());
        jsonGenerator.writeNumberField("end_1st", agregacija.getEnd().getFirst());
        jsonGenerator.writeNumberField("end_2nd", agregacija.getEnd().getSecond());
        jsonGenerator.writeStringField("name", agregacija.getName());
        jsonGenerator.writeStringField("fromMultiplicity", agregacija.getFromMultiplicity());
        jsonGenerator.writeStringField("toMultiplicity", agregacija.getToMultiplicity());

        if (agregacija.getFromElement() != null) {
            jsonGenerator.writeStringField("fromElement", agregacija.getFromElement().getName());
        }
        if (agregacija.getToElement() != null) {
            jsonGenerator.writeStringField("toElement", agregacija.getToElement().getName());
        }

        jsonGenerator.writeEndObject();
    }
}
