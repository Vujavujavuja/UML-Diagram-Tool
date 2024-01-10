package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Kompozicija;

import java.io.IOException;

public class KompozicijaSerializer extends JsonSerializer<Kompozicija> {
    @Override
    public void serialize(Kompozicija kompozicija, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeICC", "kompozicija");
        jsonGenerator.writeStringField("name", kompozicija.getName());
        jsonGenerator.writeNumberField("start_1st", kompozicija.getStart().getFirst());
        jsonGenerator.writeNumberField("start_2nd", kompozicija.getStart().getSecond());
        jsonGenerator.writeNumberField("end_1st", kompozicija.getEnd().getFirst());
        jsonGenerator.writeNumberField("end_2nd", kompozicija.getEnd().getSecond());
        jsonGenerator.writeStringField("fromMultiplicity", kompozicija.getFromMultiplicity());
        jsonGenerator.writeStringField("toMultiplicity", kompozicija.getToMultiplicity());

        if (kompozicija.getFromElement() != null) {
            jsonGenerator.writeStringField("fromElement", kompozicija.getFromElement().getName());
        }
        if (kompozicija.getToElement() != null) {
            jsonGenerator.writeStringField("toElement", kompozicija.getToElement().getName());
        }

        jsonGenerator.writeEndObject();
    }
}
